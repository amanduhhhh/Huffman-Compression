/**
 * @author AmandaXi
 * @version April 2023
 */

// sorted from lowest to highest value
class SimplePriorityQueue<E> {
  private int size = 0;
  private PriorityNode<E> head;
  private PriorityNode<E> tail;
  
  public int getSize() {
    return this.size;
  }

  /**
   * Adds an item of type E to queue
   * 
   * @param item Item to add
   * @param value Priority value of item
   */
  public void enqueue(E item, int value) {
    if (head == null) {
      head = new PriorityNode<E>(item, null, null, value);
      tail = head;
      size++;
      return;
    }

    PriorityNode<E> tempNode = tail;

    // traverse from tail until lesser/equal value is found
    while ((tempNode != null)) {
      //insert new item after 
      if (tempNode.getPriority() <= value) {
        PriorityNode<E> next = tempNode.getNext();
        PriorityNode<E> newNode = new PriorityNode<E>(item, tempNode, next, value);
        
        if (tempNode == tail){
          tail = newNode;
        }else{
          next.setPrev(newNode);
        }
        
        tempNode.setNext(newNode);
        size++;
        return;
      }
      // increment index
      tempNode = tempNode.getPrev();
    }

    // add to beginning if no lesser value found
    head.setPrev(new PriorityNode<E>(item, null, head, value));
    head = head.getPrev();
    size++;
    return;

  }

  // increases the priority of the item
  public void increment(E item) {
    PriorityNode<E> incrementItem = findItem(item);
    if (incrementItem == null){
      enqueue(item, 1);
      return;
    }
    
    // increment the item
    incrementItem.setPriority(incrementItem.getPriority()+1);
    
    // remove it: update the next and prev
    PriorityNode<E> prev = incrementItem.getPrev();
    PriorityNode<E> next = incrementItem.getNext();
    if (prev!= null){
      prev.setNext(next);
    }else{
      head = next; 
    }
    if (next!=null){
      next.setPrev(prev);
    }else{
      tail = prev;
    }
    
    // starting from the next node, find next largest and place before
    PriorityNode<E> tempNode = next;
    while (tempNode!=null) {
      if (tempNode.getPriority() >= incrementItem.getPriority()) {
        incrementItem.setNext(tempNode);
        PriorityNode<E> tempPrev = tempNode.getPrev();
        tempNode.setPrev(incrementItem);
       
        if (tempPrev!=null) {
          incrementItem.setPrev(tempPrev);
          tempPrev.setNext(incrementItem);
        }else {
          head=incrementItem;
        }
        return;
      }
      tempNode = tempNode.getNext();
    }
    
    //otherwise, insert at end as tail
    incrementItem.setPrev(tail);
    incrementItem.setNext(null);
    if (tail!=null) {
      tail.setNext(incrementItem);
      tail = incrementItem;
    }else {
      head=incrementItem;
      tail=head;
    }
    

    
//      enqueue(incrementItem.getItem(), incrementItem.getPriority());
  }

  public int peekPriority(){
    if (head==null){
      return -1;
    }else{
      return head.getPriority();
    }
  }

  private PriorityNode<E> findItem(E item){
    //iterate from both ends
    PriorityNode<E> leftNode = head; 
    PriorityNode<E> rightNode = tail; 
    //iterate from both left and right
    while ((leftNode != null) && (rightNode != null)) {
      if (rightNode.getNext()==leftNode) {
        return null;
      }else if (leftNode.getItem().equals(item)) {
        return leftNode;
      }else if (rightNode.getItem().equals(item)){
        return rightNode;
      }
      leftNode = leftNode.getNext();
      rightNode = rightNode.getPrev();
    }

    return null;
    
//    //start from head
//    PriorityNode<E> currentNode = head; 
//    while (currentNode!=null){
//      if (item.equals(currentNode.getItem())){
//        return currentNode;
//      }
//      currentNode = currentNode.getNext();
//    }
//    return null;
  }
  
  /**
   * Removes item from head and returns
   * 
   * @return Item removed
   */
  public E dequeue() {
    if (head == null) {
      return null;
    }
    E item = head.getItem();
    head = head.getNext();

    if (head != null) {
      head.setPrev(null);
    }else {
      tail=null;
    }
    size--;
    return item;
  }

  public void printArray() {
    PriorityNode<E> tempNode = head;

    System.out.println("");
    while (tempNode != null) {
      System.out.println("Byte: " + tempNode.getItem() + " frequency: " + tempNode.getPriority());
      tempNode = tempNode.getNext();

    }
  }

  public int size(){
    int size = 0;
    PriorityNode<E> tempNode = head;

    while (tempNode != null) {
      size++;
      tempNode = tempNode.getNext();
    }

    return size;
  }

  // ********************** A Node in the queue *********
  private class PriorityNode<T> {
    private T item;
    private PriorityNode<T> next, prev;
    private int priority;
    private String code; 

    public PriorityNode(T item) {
      this.item = item;
      this.next = null;
    }

    public PriorityNode(T item, PriorityNode<T> prev, PriorityNode<T> next, int priority) {
      this.item = item;
      this.prev = prev;
      this.next = next;
      this.priority = priority;
    }

    public PriorityNode<T> getNext() {
      return this.next;
    }

    public PriorityNode<T> getPrev() {
      return this.prev;
    }

    public int getPriority() {
      return this.priority;
    }

    public void setPriority(int p){
      this.priority = p;
    }

    public void setNext(PriorityNode<T> next) {
      this.next = next;
    }

    public void setPrev(PriorityNode<T> prev) {
      this.prev = prev;
    }

    public T getItem() {
      return this.item;
    }

    public void setCode(String code){
      this.code = code;
    }

    public String getCode(){
      return this.code;
    }

  }

} // end of priorityqueue