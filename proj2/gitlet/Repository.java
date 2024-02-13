package gitlet;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    private HashMap<String, Branch> branches = new HashMap<>();
    private Branch currentBranch = null;
    // staging area's staged files: filename -> hash
    private StagingArea stagingArea = new StagingArea();
    private CommitTree commitTree = null;

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** singleton repository. */
    private static Repository instance;

    /** already initiated repository, rebuilt from local files.
     * ToDo: 如何解决initiate和非initiate两种情况下的repository constructor. */
    private Repository(boolean init) {
        // initiate repository
        if (init) {
            if (GITLET_DIR.exists()) {
                throw new RuntimeException(
                        "A Gitlet version-control system already exists in the current directory.");
            }
        }
        // construct from local files
        else {
            if (!GITLET_DIR.exists()) {
                throw new RuntimeException("Not in an initialized Gitlet directory.");
            }
            for (File file : join(GITLET_DIR, "branches").listFiles()) {
                Branch branch = readObject(file, Branch.class);
                branches.put(branch.getName(), branch);
            }
            currentBranch = branches.get("master");
            this.stagingArea = new StagingArea();
            this.commitTree = CommitTree.load();
        }
    }

    /** get singleton repository instance. */
    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository(false);
        }
        return instance;
    }

    public Repository(String init) {
        instance = new Repository(true);
    }

    /** make initial commit. */
    public void makeInitialCommit(String message){
        GITLET_DIR.mkdir();

        Commit initialCommit = new Commit(message, Instant.EPOCH,
                                null, null, new HashMap<>());
        initialCommit.save();
        this.commitTree = new CommitTree(initialCommit);
        this.commitTree.save();

        Branch master = new Branch("master", initialCommit);
        branches.put("master", master);
        currentBranch = master;
        master.save();
    }

    /** stage file for addition. */
    public void addFile(String filename) {
        File file = Utils.join(CWD, filename);
        // if the file does not exist, print the error message
        // File does not exist. and exit without changing anything.
        if (!file.exists()) {
            throw new RuntimeException("File does not exist.");
        }
        String fileHash = Utils.sha1(Utils.readContents(file));
        //  If the current working version of the file is identical to the version in the current
        //  commit, do not stage it to be added, and remove it from the staging area if it is
        //  already there (as can happen when a file is changed, added, and then changed back to
        //  it’s original version).
        if (currentBranch.getHead().getBlobs().containsKey(filename)) {
            String preHash = currentBranch.getHead().getBlobs().get(filename);
            if (preHash.equals(fileHash)) {
                stagingArea.getStagedFiles().remove(filename);
                return;
            }
        }
        // Staging an already-staged file overwrites the previous
        // entry in the staging area with the new contents.
        if (stagingArea.getStagedFiles().containsKey(filename)) {
            stagingArea.getStagedFiles().put(filename, fileHash);
        }
        // The file will be staged for addition.
        else {
            stagingArea.addFile(filename, fileHash);
        }
    }

    /** make commit.
     * @param message commit message. */
    public void makeCommit(String message) {
        // Each commit should contain the date and time it was made.
        Instant timestamp = Instant.now();

        HashMap<String, String> blobs = new HashMap<>();
        // copy blobs from parent commit
        Commit parent = currentBranch.getHead();
        if (parent != null) {
            blobs.putAll(parent.getBlobs());
        }

        // If no files have been staged, abort. Print the message No changes added to the commit.
        if (stagingArea.getStagedFiles().isEmpty()) {
            throw new RuntimeException("No changes added to the commit.");
        }

        // copy blobs from staging area
        for (String filename : stagingArea.getStagedFiles().keySet()) {
            blobs.put(filename, stagingArea.getStagedFiles().get(filename));
        }

        // Every commit must have a non-blank message.
        if (message.isEmpty()) {
            throw new RuntimeException("Please enter a commit message.");
        }

        // make new commit
        Commit commit = new Commit(message, timestamp, parent, null, blobs);
        commit.save();
        commitTree.addNode(commit, parent);
        commitTree.save();

        // The commit just made becomes the “current commit”
        currentBranch.setHead(commit);

        // The staging area is cleared after a commit.
        stagingArea.clear();
    }

    /** remove file.
     * @param filename file name. */
    public void removeFile(String filename) {
        // If the file is neither staged nor tracked by the head commit, print the error message
        // No reason to remove the file. and exit.
        if (!stagingArea.getStagedFiles().containsKey(filename)
                && !currentBranch.getHead().getBlobs().containsKey(filename)) {
            throw new RuntimeException("No reason to remove the file.");
        }
        // remove file from staging area
        stagingArea.getStagedFiles().remove(filename);
        // remove the file from the working directory if the user has not already done so
        // (do not remove it unless it is tracked in the current commit).
        if (currentBranch.getHead().getBlobs().containsKey(filename)) {
            removeFileFromWorkingDir(filename);
        }
    }

    /** helper method: remove file from working directory. */
    private void removeFileFromWorkingDir(String filename) {
        File file = Utils.join(CWD, filename);
        if (file.exists()) {
            Utils.restrictedDelete(file);
        }
    }

    /** print log. */
    public void printLog() {
        CommitNode head = commitTree.findNode(commitTree.getRoot(), currentBranch.getHead());
        commitTree.traceToRoot(head);
    }

    /** print global log.
     * use Utils.plainFilenamesIn to print global log
     * as the commits are stored as tree. */
    public void printGlobalLog() {
        File dir = join(GITLET_DIR, "commits");
        for (String fileName : plainFilenamesIn(dir)) {
            File file = join(dir, fileName);
            Commit commit = readObject(file, Commit.class);
            commit.log();
        }
    }
}
