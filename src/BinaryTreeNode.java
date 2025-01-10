// **********************************
// Binary Tree Node Class
// **********************************

/**
 * BinaryTreeNode
 * A Node class for a Binary Tree
 * 
 * @author AmandaXi
 * @version 1.0 2025
 */
public class BinaryTreeNode<E extends Comparable> implements Comparable<BinaryTreeNode<E>>{
  private E item;
  private BinaryTreeNode<E> left;
  private BinaryTreeNode<E> right;

  BinaryTreeNode(E i, BinaryTreeNode<E> l, BinaryTreeNode<E> r) {
    this.item = i;
    this.left = l;
    this.right = r;
  }

  public void setLeft(BinaryTreeNode<E> n) {
    this.left = n;
  }

  public void setRight(BinaryTreeNode<E> n) {
    this.right = n;
  }

  public BinaryTreeNode<E> getLeft() {
    return this.left;
  }

  public BinaryTreeNode<E> getRight() {
    return this.right;
  }

  public void setItem(E d) {
    this.item = d;
  }

  public E getItem() {
    return this.item;
  }

  @Override
  public int compareTo(BinaryTreeNode<E> other){
    return (this.getItem().compareTo(other.getItem()));
  }

  @Override 
  public boolean equals(Object other){
    if (other instanceof BinaryTreeNode<?>){
      return this.getItem().equals(((BinaryTreeNode<?>)other).getItem());
    }
    return false;
  }

  @Override
  public String toString(){
    return (this.getItem()+"");
  }
  
  /**
   * isLeaf
   * determines if the current node is a leaf
   * 
   * @returns true if the current node has no children, otherwise false
   */
  public boolean isLeaf() {
    if (this.left == null && this.right == null) {
      return true;
    }
    return false;
  }
}