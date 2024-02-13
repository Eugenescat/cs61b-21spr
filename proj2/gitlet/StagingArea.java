package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.io.File;

/** use a k-v hashmap to store staged files' SHA1s. */
public class StagingArea implements Serializable {
  // filename -> hash
  private HashMap<String, String> stagedFiles;
  private static final File STAGING_FILE = new File(Repository.GITLET_DIR, "stagingArea");

  public StagingArea() {
    this.stagedFiles = new HashMap<>();
    if (STAGING_FILE.exists()) {
      loadStagingArea();
    }
  }

  /** add file to staging area and write stagedFiles object to local. */
  public void addFile(String filename, String hash) {
    this.stagedFiles.put(filename, hash);
    saveStagingArea();
  }

  public void clear() {
    this.stagedFiles.clear();
  }

  public HashMap<String, String> getStagedFiles() {
    return this.stagedFiles;
  }

  private void saveStagingArea() {
    Utils.writeObject(STAGING_FILE, this);
  }

  private void loadStagingArea() {
    StagingArea stagingArea = Utils.readObject(STAGING_FILE, StagingArea.class);
    this.stagedFiles = stagingArea.getStagedFiles();
  }
}
