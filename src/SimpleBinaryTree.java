/**
 * @author AmandaXi
 * @version April 2023
 */

class SimpleBinaryTree<T extends Comparable<T>> {
  public BinaryTreeNode<T> root;

  /**
   * Displays binary tree in ascending order
   */
  public void displayInOrder() {
    recursiveDisplayInOrder(root);
  }

  /**
   * Recurses through tree from least to greatest
   * 
   * @param currentNode Node to traverse
   */
  private void recursiveDisplayInOrder(BinaryTreeNode<T> currentNode) {
    //  1. recurse left, 2. display current item, 3. recurse right
    // just make sure the currentNode is not null!
    if (currentNode == null) {
      return;
    }
    // base case
    if (currentNode.isLeaf()) {
      System.out.println(currentNode.getItem());
      return;
    }
    recursiveDisplayInOrder(currentNode.getLeft());
    System.out.println(currentNode.getItem());
    recursiveDisplayInOrder(currentNode.getRight());
    return;
  }

  public void setRoot(BinaryTreeNode<T> root){
    this.root = root;
  }

  /**
   * Calls helper method to add an item of type T to tree
   * 
   * @param item Item to add
   */
  public void add(T item) {
    if (root == null) {
      root = new BinaryTreeNode<T>(item, null, null);
      return;
    }
    recursiveAdd(root, item);
  }

  /**
   * Recursively compares to add an item of type E to tree
   * 
   * @param currentNode Node to traverse
   * @param item Item to add
   */
  private void recursiveAdd(BinaryTreeNode<T> currentNode, T item) {
    boolean greater;
    if (currentNode == null) {
      return;
    }
    // check if greater or less than current node
    if (item.compareTo(currentNode.getItem()) >= 0) {
      greater = true;
    } else {
      greater = false;
    }

    BinaryTreeNode<T> newNode = new BinaryTreeNode<T>(item, null, null);

    // check if left or right node is null
    if (greater) {
      if (currentNode.getRight() == null) {
        currentNode.setRight(newNode);
      } else {
        recursiveAdd(currentNode.getRight(), item);
      }
    } else {
      if (currentNode.getLeft() == null) {
        currentNode.setLeft(newNode);
      } else {
        recursiveAdd(currentNode.getLeft(), item);
      }
    }
    return;
  }

  /*
   * Calls helper method to find if item T is contained in tree
   * 
   * @param item Item to find
   * @return Boolean representing whether value was found 
   */
  public boolean contains(T item) {
    return recursiveSearch(root, item);
  }

  /*
   * Recurses through tree to find value
   * 
   * @param currentNode Node to traverse
   * @param item Item to find
   * @return Boolean representing whether value was found 
   */
  private boolean recursiveSearch(BinaryTreeNode<T> currentNode, T item) {
    // base case - leaf or node equals item
    if (currentNode == null) {
      return false;
    }
    if (currentNode.getItem().equals(item)) {
      return true;
    }
    if (currentNode.isLeaf()) {
      return false;
    }

    //go left if less
    if ((currentNode.getItem().compareTo(item))<0){
      return recursiveSearch(currentNode.getLeft(), item);
    //go right if greater
    }else if ((currentNode.getItem().compareTo(item))>0){
      return recursiveSearch(currentNode.getRight(), item); 
    }
    return false;

  }


  // ************ METHODS TO BE COMPLETED ABOVE ************

  /*
   * size
   * returns the number of items in the tree
   * 
   * @return an integer representing the number of items stored in the tree
   */
  public int size() {
    return sizeRecursive(root);
  }

  // recursive helper method for size()
  private int sizeRecursive(BinaryTreeNode<T> currentNode) {
    if (currentNode == null) {
      return 0;
    } else {
      int numberOfChildNodes = 0;
      numberOfChildNodes += sizeRecursive(currentNode.getLeft());
      numberOfChildNodes += sizeRecursive(currentNode.getRight());
      return numberOfChildNodes + 1; // add the current node to the count
    }
  }
  
  public String encodeTree(){
    return recursiveTreeEncoder(root);
  }
  
  public String recursiveTreeEncoder(BinaryTreeNode<T> current) {
    // base case: leaf
    if (current.isLeaf()) {
      return ""+ current.getItem() ;
    }
    return "(" + recursiveTreeEncoder(current.getLeft()) + ")" + 
    "(" + recursiveTreeEncoder(current.getRight()) + ")";
  }

  /**
   * isEmpty
   * Determines if the binary tree is empty, no data exists
   * 
   * @return true if the binary tree contains no data, otherwise false
   */
  public Boolean isEmpty() {
    return (root == null);
  }

} // end of BinaryTree

