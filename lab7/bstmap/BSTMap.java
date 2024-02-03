package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

  private BSTNode root;

  public BSTMap() {
    root = null;
  }

  @Override
  public void clear() {
    root = null;
  }

/*  @Override
  public boolean containsKey(K key) {
    return get(key) != null;
  }
  this will fail test containsKeyNullTest. */

  public boolean containsKey(K key) {
    return containsKeyHelper(key, root);
  }

  /** helper function for containsKey(key) */
  private boolean containsKeyHelper(K key, BSTNode node) {
    if (node == null) {
      return false;
    }
    int cmp = key.compareTo(node.key);
    if (cmp == 0) {
      return true;
    } else if (cmp < 0) {
      return containsKeyHelper(key, node.left);
    } else {
      return containsKeyHelper(key, node.right);
    }
  }


  @Override
  public V get(K key) {
    return getHelper(key, root);
  }

  /** helper function for get(key) */
  private V getHelper(K key, BSTNode node) {
    if (node == null) {
      return null;
    }
    int cmp = key.compareTo(node.key);
    if (cmp == 0) {
      return node.value;
    } else if (cmp < 0) {
      return getHelper(key, node.left);
    } else {
      return getHelper(key, node.right);
    }
  }

  @Override
  public int size() {
    return sizeHelper(root);
  }

  /** get size helper function */
  private int sizeHelper(BSTNode node) {
    if (node == null) {
      return 0;
    }
    return sizeHelper(node.left) + sizeHelper(node.right) + 1;
  }

/*  @Override
  public void put(K key, V value) {
    if (root == null) {
      root = new BSTNode(key, value);
    } else {
      int cmp = key.compareTo(root.key);
      if (cmp == 0) {
        root.value = value;
      } else if (cmp < 0) {
        BSTMap<K, V> left = new BSTMap<>();
        left.root = root.left;
        left.put(key, value);
      } else {
        BSTMap<K, V> right = new BSTMap<>();
        right.root = root.right;
        right.put(key, value);
      }
    }
  }*/

  public void put(K key, V value) {
    root = putHelper(root, key, value);
  }

  /** put helper function */
  private BSTNode putHelper(BSTNode node, K key, V value) {
    if (node == null) {
      return new BSTNode(key, value);
    }
    int cmp = key.compareTo(node.key);
    if (cmp == 0) {
      node.value = value;
    } else if (cmp < 0) {
      node.left = putHelper(node.left, key, value);
    } else {
      node.right = putHelper(node.right, key, value);
    }
    node.size = sizeHelper(node.left) + sizeHelper(node.right) + 1;
    return node;
  }

  public void printInOrder() {
    printInOrderHelper(root);
  }

  private void printInOrderHelper(BSTNode node) {
    if (node == null) {
      return;
    }
    printInOrderHelper(node.left);
    System.out.println(node.key + ", " + node.value);
    printInOrderHelper(node.right);
  }

  @Override
  public Set<K> keySet() {
    throw new UnsupportedOperationException("keySet() is not supported");
  }

  @Override
  public V remove(K key) {
    throw new UnsupportedOperationException("remove(key) is not supported");
  }

  @Override
  public V remove(K key, V value) {
    throw new UnsupportedOperationException("remove(key, value) is not supported");
  }

  @Override
  public Iterator<K> iterator() {
    throw new UnsupportedOperationException("iterator() is not supported");
  }

  private class BSTNode {
    private K key;
    private V value;
    private BSTNode left;
    private BSTNode right;
    private int size;

    public BSTNode(K key, V value) {
      this.key = key;
      this.value = value;
      this.size = 1;
    }
  }
}
