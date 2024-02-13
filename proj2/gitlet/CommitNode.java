package gitlet;

import java.util.ArrayList;
import java.util.List;

public class CommitNode {
  private final Commit commit;
  private List<CommitNode> children;
  private CommitNode firstParent;
  private CommitNode secondParent;

  public CommitNode(Commit commit) {
    this.commit = commit;
    this.children = new ArrayList<>();
  }

  public Commit getCommit() {
    return commit;
  }

  public CommitNode getFirstParent() {
    return firstParent;
  }

  public CommitNode getSecondParent() {
    return secondParent;
  }

  public List<CommitNode> getChildren() {
    return children;
  }

  public void addChild(CommitNode child) {
    children.add(child);
    child.firstParent = this;
  }
}