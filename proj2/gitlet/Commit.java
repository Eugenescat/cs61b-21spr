package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements java.io.Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Instant timestamp;
    private Commit firstParent;
    private Commit secondParent;
    private Map<String, String> blobs;

    /* commit: message, timestamp, parents1&2, blobs. */
    public Commit(String message, Instant timestamp, Commit firstParent, Commit secondParent,
                  Map<String, String> blobs) {
        this.message = message;
        this.timestamp = timestamp;
        this.firstParent = firstParent;
        this.secondParent = secondParent;
        this.blobs = blobs;
    }

    /** save commit objects to .gitlet named by its sha1. */
    public void save() {
        File outFile = Utils.join(Repository.GITLET_DIR, "commits", getUID());
        Utils.writeObject(outFile, this);
    }

    /** Each commit is identified by its SHA-1 id, which must include the file (blob)
     * references of its files, parent reference, log message, and commit time.*/
    public String getUID() {
        return Utils.sha1(blobs, firstParent, secondParent, message, timestamp);
    }

    public void log() {
        if (secondParent == null) {
            System.out.println("===\n"
                    + "commit " + getUID() + "\n"
                    + "Date: " + getTimestampFormatted() + "\n"
                    + message + "\n"
            );
        } else {
            System.out.println("===\n"
                    + "commit " + getUID() + "\n"
                    + "Merge: " + firstParent.getUID().substring(0, 7)
                    + " " + secondParent.getUID().substring(0, 7) + "\n"
                    + "Date: " + getTimestampFormatted() + "\n"
                    + "Merged development into master.\n"
            );
        }

    }
    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Commit getFirstParent() {
        return firstParent;
    }

    public Commit getSecondParent() {
        return secondParent;
    }

    public Map<String, String> getBlobs() {
        return blobs;
    }

    public String getTimestampFormatted() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(timestamp, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z");
        return zdt.format(formatter);
    }
}
