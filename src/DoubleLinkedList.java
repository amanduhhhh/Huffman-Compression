/**
 * @author AmandaXi
 * @version 2025
 */

class DoubleLinkedList<E> {
  private int size = 0;	
  private DoubleNode<E> head;
  private DoubleNode<E> tail;

  /**
   * Adds an item of type E to linked list
   * @param item Item to add 
   */
  public void add(E item) {
    if (head == null) {
      head = new DoubleNode<E>(item, null, null);
      tail = head;
      size++;
      return;
    }

    tail.setNext(new DoubleNode<E>(item, tail, null));
    tail = tail.getNext();
    size++;
    return;

  }

  /**
   * Gets an item from linked list
   * @param index Index of item to get
   * @return Item in index position or null if non-existant
   */
  public E get(int index) {
    if (head == null || index >= size) {
      return null;
    }

    int avg = size / 2;

    //iterate from head
    DoubleNode<E> tempNode;
    if (index < avg) {
      tempNode = head;
      for (int i = 0; i < index; i++) {
        if (tempNode.getNext() != null) {
          tempNode = tempNode.getNext();
        }else {
          return null;
        }
      }

    //iterate from tail
    } else {
      tempNode = tail;
      for (int i = 0; i < (size-index-1); i++) {
        if (tempNode.getPrev() != null) {
          tempNode = tempNode.getPrev();
        } else {
          return null;
        }
      }
    }

    return tempNode.getItem();

  }

  /**
   * Gets the index of an item E
   * @param item Item to find in linked list
   * @return Index of item in linked list, -1 if not found
   */
  public int indexOf(E item) {
    int index = 0;
    DoubleNode<E> leftNode = head;
    DoubleNode<E> rightNode = tail;
    if (head == null) {
      return -1;
    }

    //iterate from both left and right
    while ((leftNode != null) && (rightNode != null)) {
      if (rightNode.getNext()==leftNode) {
        return -1;
      }else if (leftNode.getItem().equals(item)) {
        return index;
      }else if (rightNode.getItem().equals(item)){
        return (size-index-1);
      }
      leftNode = leftNode.getNext();
      rightNode = rightNode.getPrev();
      index++;
    }

    return -1;
  }

  /**
   * Removes item with index
   * @param index Index to remove 
   * @return Item removed
   */
  public E remove(int index) {
    E item;
    int avg = size / 2;

    //if item is head replace it
    if (index == 0) {
      item = head.getItem();
      head = head.getNext();
      return (item);
    }
    
    // return if index not in list
    if (index>size){
      return null;
    }

    DoubleNode<E> tempNode;
    
    //iterate from head
    if (index < avg) {
      tempNode = head;
      for (int i = 0; i < index; i++) {
        if (tempNode.getNext() != null) {
          tempNode = tempNode.getNext();
        } else {
          return null;
        }
      }
    //iterate from tail
    } else {
      tempNode = tail;
 
      for (int i = 0; i < (size-index-1); i++) {
        if (tempNode.getPrev() != null) {
          tempNode = tempNode.getPrev();
        } else {
          return null;
        }
      }
    }

    //set previous and next values of adjacent nodes
    item = tempNode.getItem();
    DoubleNode<E> prev  = tempNode.getPrev();
    DoubleNode<E> next = tempNode.getNext();
    prev.setNext(next);

    //check if next exists, if so, update prev
    if (next!=null){
      next.setPrev(prev);
    }
    
    size--;
    return item;
  }

  /**
   * Removes item with item value
   * @param item Item to remove 
   * @return Boolean representing whether item was successfully removed
   */
  public boolean remove(E item) {
    //find index
    int index = indexOf(item);
    if (index == -1) {
      return false;
    }

    //remove index
    remove(index);
    return true;
  }

  /**
   * Clears linked list
   */
  public void clear() {
    head = null;
    tail = head;
  }

//  /**
//   * Gets size of linked list
//   * @return Number of items in linked list
//   */
//  public int size() {
//    int size = 0;
//    DoubleNode<E> tempNode = head;
//    while (tempNode != null) {
//      tempNode = tempNode.getNext();
//      size++;
//    }
//
//    return size;
//  }

  public void printArray() {
    DoubleNode<E> tempNode = head;

    int size = 0;
    while (tempNode != null) {
      System.out.println(size + ". " + tempNode.getItem());
      tempNode = tempNode.getNext();
      size++;

    }
  }

  // ********************** A Node in the linked list *********
  private class DoubleNode<T> {
    private T item;
    private DoubleNode<T> next;
    private DoubleNode<T> prev;

    public DoubleNode(T item) {
      this.item = item;
      this.next = null;
    }

    public DoubleNode(DoubleNode<T> prev, T item) {
      this.item = item;
      this.prev = prev;
    }

    public DoubleNode(T item, DoubleNode<T> next) {
      this.item = item;
      this.next = next;
    }

    public DoubleNode(T item, DoubleNode<T> prev, DoubleNode<T> next) {
      this.item = item;
      this.prev = prev;
      this.next = next;
    }

    public DoubleNode<T> getNext() {
      return this.next;
    }

    public DoubleNode<T> getPrev() {
      return this.prev;
    }

    public void setNext(DoubleNode<T> next) {
      this.next = next;
    }

    public void setPrev(DoubleNode<T> prev) {
      this.prev = prev;
    }

    public T getItem() {
      return this.item;
    }

  }

} // end of doublelinkedlist