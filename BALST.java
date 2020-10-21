/**
 * Samson Cain's implementation of an AVL tree
 * 
 * The inner class "Node" defines the Nodes used in the tree.
 * 
 * @author Samson Cain
 * @email srcain@wisc.edu
 * @class CS400 - Programming 3
 * @lecture 001
 * 
 * @project p2 BST
 * 
 * @date October 6, 2019
 * 
 * 
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AVL tree
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

  private Node root;

  private int numKeys;

  /**
   * AVL Tree constructor. Initialize values.
   */
  public BALST() {
    this.root = null;
    this.numKeys = 0;
  }
  
  /**
   * Performs a right rotate on the provided node.
   * 
   * @param Node node - rotated node
   * 
   * @return Node - new node in place
   */
  private Node rotateRight(Node node) {
    Node grandParent= node;
    Node parent = grandParent.left;
    
    grandParent.left = parent.right;
    parent.right = grandParent;
    
    return parent;
  }
  
  /**
   * Performs a left rotate on the provided node.
   * 
   * @param Node node - rotated node
   * 
   * @return Node - new node in place
   */
  private Node rotateLeft(Node node) {
    Node grandParent= node;
    Node parent = grandParent.right;
    
    grandParent.right = parent.left;
    parent.left = grandParent;
    
    return parent;
  }
  
  /**
   * Performs a right left rotate on the provided node.
   * 
   * @param Node node - rotated node
   * 
   * @return Node - new node in place
   */
  private Node rotateRightLeft(Node node) {
    Node grandParent = node;
    Node parent = grandParent.right;
    Node key = parent.left;

    parent.left = key.right;
    grandParent.right = key.left;
    key.left = grandParent;
    key.right = parent;
    
    return key;
  }
  
  /**
   * Performs a left right rotate on the provided node.
   * 
   * @param Node node - rotated node
   * 
   * @return Node - new node in place
   */
  private Node rotateLeftRight(Node node) {
    Node grandParent = node;
    Node parent = grandParent.left;
    Node key = parent.right;

    parent.right = key.left;
    grandParent.left = key.right;
    key.left = parent;
    key.right = grandParent;
    
    return key;
  }
  
  /**
   * Check if rebalancing is necessary and rebalance if it is. Possible rebalance
   * options are right, left, right left, and left right.
   * 
   * @param Node node - root of tree to rebalance
   * 
   * @return Node - root of tree after rebalancing is done
   */
  private Node rebalance(Node node) {
    if (node != null) {
      
      // rebalance children first so cascading does not/can not ruin balance
      node.left = rebalance(node.left);
      node.right = rebalance(node.right);

      // get balance factor
      int balanceFactor = getBalanceFactor(node);
      
      // check balance factor and rebalance if necessary
      if (balanceFactor > 1) {
        if (getBalanceFactor(node.left) > 0) {
          node = rotateRight(node);
        } else {
          node = rotateLeftRight(node);
        }
      } else if (balanceFactor < -1) {
        if (getBalanceFactor(node.right) > 0) {
          node = rotateRightLeft(node);
        } else {
          node = rotateLeft(node);
        }
      }
      return node;
    }
    return null;
  }
  
  /**
   * Calculates balance factor
   * 
   * @param Node node - node to check balance factor of
   * 
   * @return int - balance factor of provided node
   */
  private int getBalanceFactor(Node node) {
    if (node != null) {
      return getHeight(node.left) - getHeight(node.right);
    }
    return -1;
  }
  
  /**
   * Returns key in root node if root node is not null. 
   * Returns null if root node is null.
   * 
   * @return K - key of root node or null
   */
  @Override
  public K getKeyAtRoot() {
    if (root == null) {
      return null;
    } else {
      return root.key;
    }
  }


  /**
   * Returns the key of the left child of a specified node. If the node is
   * not found it throws KeyNotFoundException. If the left child of the specified node
   * is null, it returns null.
   * 
   * @param K key - key to search for
   * 
   * @return K - key in left child of the key that is found
   * 
   * @throws IllegalNullKeyException - if key provided is null
   * @throws KeyNotFoundException    - if key is not in AVL tree
   */
  @Override
  public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // throws IllegalNullKeyException if provided key is null
    if (key == null) {
      throw new IllegalNullKeyException();
    } else {
      return getKeyOfLeftChildOf(this.root, key);
    }
  }
  
  /**
   * Recursive helper for getKeyOfLeftChildOf()
   * 
   * @param Node node - current node
   * @param K key     - key to search for
   * 
   * @return K - key in left child of the key that is found
   * 
   * @throws KeyNotFoundException - if key is not in AVL tree
   */
  private K getKeyOfLeftChildOf(Node node, K key) throws KeyNotFoundException {
    // if node is null it means that the key was not found so throw KeyNotFoundException
    if (node == null) {
      throw new KeyNotFoundException();
    }

    // if key matches the key provided return node.left key if it is available
    if (node.key.equals(key)) {
      if (node.left == null) {
        return null;
      } else {
        return node.left.key;
      }

    } else if (key.compareTo(node.key) < 0) { // if nodes key is less than provided key, recurse with left child
      return getKeyOfLeftChildOf(node.left, key);
    } else { // else nodes key is greater than provided key, recurse with right child
      return getKeyOfLeftChildOf(node.right, key);
    }
  }

  /**
   * Returns the key of the right child of a specified node. If the node is
   * not found it throws KeyNotFoundException. If the right child of the specified node
   * is null, it returns null.
   * 
   * @param K key - key to search for
   * 
   * @return K - key in right child of the key that is found
   * 
   * @throws IllegalNullKeyException - if key provided is null
   * @throws KeyNotFoundException    - if key is not in AVL tree
   */
  @Override
  public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // throws IllegalNullKeyException if provided key is null
    if (key == null) {
      throw new IllegalNullKeyException();
    } else {
      return getKeyOfRightChildOf(this.root, key);
    }
  }

  /**
   * Recursive helper for getKeyOfRightChildOf()
   * 
   * @param Node node - current node
   * @param K key     - key to search for
   * 
   * @return K - key in right child of the key that is found
   * 
   * @throws KeyNotFoundException - if key is not in AVL tree
   */
  private K getKeyOfRightChildOf(Node node, K key) throws KeyNotFoundException {
    // if node is null it means that the key was not found so throw KeyNotFoundException
    if (node == null) {
      throw new KeyNotFoundException();
    }

    // if key matches the key provided return node.left key if it is available
    if (node.key.equals(key)) {
      if (node.right == null) {
        return null;
      } else {
        return node.right.key;
      }
    } else if (key.compareTo(node.key) < 0) { // if nodes key is less than provided key, recurse with left child
      return getKeyOfRightChildOf(node.left, key);
    } else { // if nodes key is greater than provided key, recurse with right child
      return getKeyOfRightChildOf(node.right, key);
    }
  }

  /**
   * Returns the height of the AVL tree.
   * 
   * @return int - height of tree of provided node
   */
  @Override
  public int getHeight() {
    return getHeight(this.root);
  }

  /**
   * Recursive helper for getHeight()
   * 
   * @param Node node - current node
   * 
   * @return int - height of tree of provided node
   */
  private int getHeight(Node node) {
    // if node is null height=0, so return 0
    if (node == null) {
      return 0;
    }

    // get height of left and right subtrees
    int leftHeight = getHeight(node.left);
    int rightHeight = getHeight(node.right);

    // returns 1 + height of larger subtree as height
    if (leftHeight > rightHeight) {
      return 1 + leftHeight;
    } else {
      return 1 + rightHeight;
    }
  }

  /**
   * Returns empty list if AVL tree is empty. Returns list containing 
   * all AVL tree elements in order if the AVL tree is not empty.
   * 
   * @return List<K> - containing all AVL tree elements in order
   */
  @Override
  public List<K> getInOrderTraversal() {
    // use array list to hold keys because it is easiest solution
    return getInOrderTraversal(this.root, new ArrayList<K>());
  }

  /**
   * Recursive helper method for getInOrderTraversal()
   * 
   * @param Node node    - current node
   * @param List<K> list - to store keys in
   * 
   * @return List<K> - containing all AVL tree elements in order
   */
  private List<K> getInOrderTraversal(Node node, ArrayList<K> list) {
    // if node is null we are done, so ensure node IS NOT null
    if (node != null) {
      getInOrderTraversal(node.left, list);
      list.add(node.key);
      getInOrderTraversal(node.right, list);
    }
    
    return list;
  }

  /**
   * Returns empty list if AVL tree is empty. Returns list containing 
   * all AVL tree elements in pre order if the AVL tree is not empty.
   * 
   * @return List<K> - containing all AVL tree elements in pre order
   */
  @Override
  public List<K> getPreOrderTraversal() {
    // use array list to hold keys because it is easiest solution
    return getPreOrderTraversal(this.root, new ArrayList<K>());
  }

  /**
   * Recursive helper method for getPreOrderTraversal()
   * 
   * @param Node node    - current node
   * @param List<K> list - to store keys in
   * 
   * @return List<K> - containing all AVL tree elements in pre order
   */
  private List<K> getPreOrderTraversal(Node node, ArrayList<K> list) {
    // if node is null we are done, so ensure node IS NOT null
    if (node != null) {
      list.add(node.key); 
      getPreOrderTraversal(node.left, list);
      getPreOrderTraversal(node.right, list);
    }
    
    return list;
  }

  /**
   * Returns empty list if AVL tree is empty. Returns list containing 
   * all AVL tree elements in post order if the AVL tree is not empty.
   * 
   * @return List<K> - containing all AVL tree elements in post order
   */
  @Override
  public List<K> getPostOrderTraversal() {
    // use array list to hold keys because it is easiest solution
    return getPostOrderTraversal(this.root, new ArrayList<K>());
  }
  
  /**
   * Recursive helper method for getPostOrderTraversal()
   * 
   * @param Node node    - current node
   * @param List<K> list - to store keys in
   * 
   * @return List<K> - containing all AVL tree elements in post order
   */
  private List<K> getPostOrderTraversal(Node node, ArrayList<K> list) {
    if (node != null) {
      getPostOrderTraversal(node.left, list);
      getPostOrderTraversal(node.right, list); 
      list.add(node.key); 
    }
    
    return list;
  }

  /**
   * Returns empty list if AVL tree is empty. Returns list containing 
   * all AVL tree elements in level order if the AVL tree is not empty.
   * 
   * @return List<K> - containing all AVL tree elements in level order
   */
  @Override
  public List<K> getLevelOrderTraversal() {
    // use array list to hold keys because it is easiest solution
    return getLevelOrderTraversal(this.root, new ArrayList<K>());
  }

  /**
   * Helper method for getLevelOrderTraversal()
   * 
   * @param Node node    - current node
   * @param List<K> list - to store keys in
   * 
   * @return List<K> - containing all AVL tree elements in level order
   */
  private List<K> getLevelOrderTraversal(Node node, ArrayList<K> list) {
    Node temp = node;
    Queue<Node> queue = new LinkedList<Node>();
    queue.add(temp);
    
    while(queue.size() > 0) {
      temp = queue.remove();
      
      if (temp != null) {
        list.add(temp.key);
        
        if (temp.left != null) {
          queue.add(temp.left);
        }
        if (temp.right != null) {
          queue.add(temp.right);
        }
      }
    }
    
    return list;
  }

  /**
   * Inserts a node with the assigned key/value pair into the AVL tree. If the 
   * key is null it throws IllegalNullKeyException(). If the key already exists
   * in the tree it throws DuplicateKeyException(). Rebalance tree if insert
   * is successful. 
   * 
   * @param key   - key to be added to tree
   * @param value - value to be added to associated key in tree
   * 
   * @throws IllegalNullKeyException - if key is null
   * @throws DuplicateKeyException   - if key already exists in tree
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    // throws IllegalNullKeyException if key is null
    if (key == null) { 
      throw new IllegalNullKeyException();
    } else {
      root = insert(this.root, key, value);
      numKeys++;
    }
    this.root = rebalance(this.root);
  }

  /**
   * Recursive helper for insert()
   * 
   * @param Node node - current node
   * @param K key     - key to be added to tree
   * @param V value   - value to be added to associated key in tree
   * 
   * @return Node - node to be inserted once the correct spot is found
   * 
   * @throws DuplicateKeyException - if key already exists in tree
   */
  private Node insert(Node node, K key, V value) throws DuplicateKeyException {
    // if node is null, create new node and return
    if (node == null) {
      node = new Node(key, value);
      return node;
    }

    // throws DuplicateKeyException if key already exists in tree
    if (node.key.equals(key)) {
      throw new DuplicateKeyException();
    }

    // node is not null and key does not exist in tree so recurse until the correct spot is found
    if (key.compareTo(node.key) < 0) { // if nodes key is less than provided key, recurse with left child
      node.left = insert(node.left, key, value);
    } else { // else nodes key is greater than provided key, recurse with right child
      node.right = insert(node.right, key, value);
    }
    
    return node;
  }

  /**
   * Attempts to remove a node from the tree with the given key. If the node is not found
   * throws KeyNotFoundException. If the key is null throws IllegalNullKeyException.
   * Rebalance tree if removal is successful. 
   * 
   * @param key the key of the node to be deleted
   * 
   * @return boolean - true if node is removed from tree, false if not
   * 
   * @throws IllegalNullKeyExcpetion - if provided key is null
   * @throws KeyNotFoundException    - if key is not in AVL tree
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // throws IllegalNullKeyException if key is null
    if (key == null) {
      throw new IllegalNullKeyException();
    } else {
      root = remove(this.root, key);
      numKeys--;
    }
    
    this.root = rebalance(this.root);
    return true;
  }

  /**
   * Recursive helper for remove()
   * 
   * @param Node node - current node
   * @param K key     - key to be removed from tree
   * 
   * @return Node - to remove
   * 
   * @throws KeyNotFoundException - if key is not in AVL tree
   */
  private Node remove(Node node, K key) throws KeyNotFoundException {
    // if node is null it means that the key was not found so throw KeyNotFoundException
    if (node == null) {
      throw new KeyNotFoundException();
    }

    // key to be removed is found so
    if (node.key.equals(key)) {
      
      // if node to be removed has both left and right child
      if (node.right != null && node.left != null) {
        
        // gives n the key and value of its in order predecessor and returns n if n has two children
        Node inOrderPredecessor = getInOrderPredecessor(node);
        
        node.left = remove(node.left, inOrderPredecessor.key);
        node.key = inOrderPredecessor.key;
        node.value = inOrderPredecessor.value;
        
        return node;
        
      } else if (node.left == null) {  // returns right child
        return node.right; 
        
      } else if (node.right == null) { // returns left child
        return node.left;
        
      } else {
        return null;
        
      }
      
    } else if (node.key.compareTo(key) > 0) {
      node.left = remove(node.left, key);
    } else {
      node.right = remove(node.right, key);
    }
    
    return node;
  }

  /**
   * Return inorder predecessor of provided node
   * 
   * @param Node node - current node
   * 
   * @return Node - inorder predecessor
   */
  private Node getInOrderPredecessor(Node node) {
    Node temp = node.left;
    while (temp.right != null) {
      temp = temp.right;
    }
    return temp;
  }

  /**
   * Attempts to get the value of a node with the provided key. If the key is null
   * throws IllegalNullKepException. If a node with the provided key is not found
   * throws KeyNotFoundException.
   * 
   * @param K key - key to search for
   * 
   * @return V - value of node with provided key
   * 
   * @throws IllegalNullKeyException - if provided key is null
   * @throws KeyNotFoundException    - if key is not in AVL tree
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // throws IllegalNullKeyException if key is null
    if (key == null) {
      throw new IllegalNullKeyException();
    } else {
      return get(this.root, key);
    }
  }
  
  /**
   * Recursive helper for get()
   * 
   * @param Node node - current node
   * @param K key     - key to search for
   * 
   * @return V - value of node with provided key
   * 
   * @throws KeyNotFoundException - if key is not in AVL tree
   */
  private V get(Node node, K key) throws KeyNotFoundException {
    // if node is null it means that the key was not found so throw KeyNotFoundException
    if (node == null) {
      throw new KeyNotFoundException();
    }
    
    // if node is found, return value
    if (key.equals(node.key)) {
      return node.value;
    } else if (key.compareTo(node.key) < 0) { // if nodes key is less than provided key, recurse with left child
      return get(node.left, key);
    } else { // else nodes key is greater than provided key, recurse with left child
      return get(node.right, key);
    }
  }

  /**
   * Attempts to find a node with the provided key and return true or false. If the key is null
   * throws IllegalNullKepException. If a node with the provided key is not found
   * throws KeyNotFoundException.
   * 
   * @param Node node - current node
   * @param K key     - key to search for
   * 
   * @return boolean - true if node with provided key is in tree, false if not
   * 
   */
  @Override
  public boolean contains(K key) throws IllegalNullKeyException {
    // throws IllegalNullKeyException if key is null
    if (key == null) {
      throw new IllegalNullKeyException();
    } else {
      return contains(this.root, key);
    }
  }

  /**
   * Recursive helper for contains()
   * 
   * @param Node node - current node
   * @param K key     - key to search for
   * 
   * @return boolean - true if node with provided key is in tree, false if not
   * 
   */
  private boolean contains(Node node, K key) {
    // if node is null it means that the key was not found so return false
    if (node == null) {
      return false;
    }
    
    // if node is found, return true
    if (key.equals(node.key)) {
      return true;
    } else if (key.compareTo(node.key) < 0) { // if nodes key is less than provided key, recurse with left child
      return contains(node.left, key);
    } else { // else nodes key is greater than provided key, recurse with left child
      return contains(node.right, key);
    }
  }

  /**
   * Return number of keys in AVL tree
   * 
   * @return int - number of keys in the BST
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * Prints the AVL tree 
   */
  @Override
  public void print() {
    print(this.root, 0);
  }

  /**
   * Recursive helper for print()
   * 
   * @param Node node - current node
   * @param int space - spacing for printing tree
   */
  private void print(Node node, int space) {
    int spaceCount = 5;
    
    // end if we reach null spot
    if (node == null) {
      return;
    }
    
    // increase distance between levels
    space += spaceCount;  
  
    // recurse on right child
    print(node.right, space);  
  
    // Print current node after space  
    System.out.print("\n");  
    for (int i = spaceCount; i < space; i++) {
      System.out.print(" ");  
    }
    System.out.print(node.key + "\n");  
  
    // recurse on left child
    print(node.left, space);  
  }

  /**
   * Private class for AVL tree Node
   * 
   * @author samsoncain
   */
  private class Node {
    private K key; // key
    private V value; // data

    private Node left; // left child
    private Node right; // right child

    private int height; // height

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }

  }

}
