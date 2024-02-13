package gitlet;

public class Branch implements java.io.Serializable {
    private String name;
    private Commit head;

    public Branch(String name, Commit head) {
        this.name = name;
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public Commit getHead() {
        return head;
    }

    public void setHead(Commit head) {
        this.head = head;
    }

    /** save branch to .gitlet by branch name. */
    public void save() {
        Utils.writeObject(Utils.join(Repository.GITLET_DIR,
                "branches", name), this);
    }
}
