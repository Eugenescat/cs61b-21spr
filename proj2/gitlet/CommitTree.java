package gitlet;

import java.io.Serializable;

public class CommitTree implements Serializable{
  private CommitNode root;

  public CommitTree(Commit rootCommit) {
    this.root = new CommitNode(rootCommit);
  }

  public void addNode(Commit commit, Commit parentCommit) {
    CommitNode parentNode = findNode(root, parentCommit);
    if (parentNode != null) {
      parentNode.addChild(new CommitNode(commit));
    }
  }

  public void addNode(Commit commit, Commit firstParentCommit, Commit secondParentCommit) {
    CommitNode childNode = new CommitNode(commit);
    CommitNode firstParentNode = findNode(root, firstParentCommit);
    CommitNode secondParentNode = findNode(root, secondParentCommit);
    if (firstParentNode != null) {
      firstParentNode.addChild(childNode);
    }
    if (secondParentNode != null) {
      secondParentNode.addChild(childNode);
    }
  }

  /** helper method.
   * @param node root node.
   * @param commit target commit. */
  public CommitNode findNode(CommitNode node, Commit commit) {
    if (node.getCommit().equals(commit)) {
      return node;
    }
    for (CommitNode child : node.getChildren()) {
      CommitNode result = findNode(child, commit);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  public CommitNode getRoot() {
    return root;
  }

  public static CommitTree load() {
    return Utils.readObject(Utils.join(Repository.GITLET_DIR, "commitTree"),
            CommitTree.class);
  }

  /** save to .gitlet. */
  public void save() {
    Utils.writeObject(Utils.join(Repository.GITLET_DIR, "commitTree"), this);
  }

  /** trace from node to root. */
  public void traceToRoot(CommitNode node) {
    node.getCommit().log();
    if (node.getFirstParent() != null) {
      traceToRoot(node.getFirstParent());
    }
  }

  /** traverse from root to child. */
  public void traverseTree(CommitNode node) {
    node.getCommit().log();
    for (CommitNode child : node.getChildren()) {
      traverseTree(child);
    }
  }
}
