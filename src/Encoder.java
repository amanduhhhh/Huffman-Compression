
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author AmandaXi
 * @version May 2023
 */

public class Encoder{
  private HashMap<Integer, Integer> freqHash = new HashMap<Integer, Integer>();
  private SimplePriorityQueue<BinaryTreeNode<Integer>> freqMap = new SimplePriorityQueue<BinaryTreeNode<Integer>>();
  private SimpleBinaryTree<Integer> codeMap = new SimpleBinaryTree<Integer>();
  private HashMap<Integer, String> charToCode = new HashMap<Integer, String>();


  public void findFreq(String inFile){
    FileInputStream in = null;

    try {
      in = new FileInputStream(inFile);
      int c;
    
      while ((c = in.read()) != -1) {
//        BinaryTreeNode<Integer> newNode = new BinaryTreeNode<Integer>(c, null, null);
        Integer frequency = freqHash.get(c);
        freqHash.put(c, frequency!=null ? frequency+1:1);
      }
      
      in.close();
      System.out.println("Frequency map complete");
    }catch(IOException e){
      System.out.println("File read exception");
      return;
      
    } finally {
      try{
        if (in != null) {
          in.close();
        }
      }catch (IOException e){
      }
    }
  }
  

  public void createCodeMap(){
    // create priority queue from hashmap
    freqHash.forEach((character, frequency) -> freqMap.enqueue(new BinaryTreeNode<Integer>(character, null, null), frequency));
    BinaryTreeNode<Integer> newNode = null;
    // special case: one character
    if (freqMap.getSize()==1) {
      charToCode.put(freqMap.dequeue().getItem(), "0");
      return;
    }
    while (freqMap.getSize()>1){
      //get first smallest two nodes
      int priority1 = freqMap.peekPriority();
      BinaryTreeNode<Integer> node1 = freqMap.dequeue();
    
      // otherwise, make a parent for node1 and node2 with combined priority
      int priority2 = freqMap.peekPriority();
      BinaryTreeNode<Integer> node2 = freqMap.dequeue();

      //create parent
      newNode = new BinaryTreeNode<Integer>(null, node1, node2);

      //insert parent back into queue
      freqMap.enqueue(newNode, (priority1+priority2));
      
    }
    //create a tree and make the root the topmost node
    codeMap.setRoot(newNode);
    mapCharacters();
    System.out.println("Binary tree complete");
  }

  //method to map all char to values
  //go through binary tree to find 
  public void mapCharacters(){
    recursiveMap(codeMap.root, "");
  }
  //recursive helper method
  private void recursiveMap(BinaryTreeNode<Integer> currentNode, String code){
    if (currentNode==null){
      return;
    }else if (currentNode.isLeaf()){
      charToCode.put(currentNode.getItem(),code);
      return;
    }
    recursiveMap(currentNode.getLeft(), code+"0");
    recursiveMap(currentNode.getRight(), code+"1");
  }

  // creates a compressed version of file
  public StringBuilder newFile(String inFile) {
    StringBuilder data = new StringBuilder("");
    FileInputStream in = null;

    try {
      in = new FileInputStream(inFile);
      int c;
  
      while ((c = in.read()) != -1) {
        //get location of character value and add the codeValue to string
        data = data.append(charToCode.get(c));
      }
      in.close();
      System.out.println("Data strung... hang tight!");
      return data;
    }catch(IOException e){
      System.out.println("File read exception");
      return null;
    } finally {
      try{
        if (in != null) {
          in.close();
        }
      }catch (IOException e){
      }
    }
  }

  // print file name, tree, and data to file
  public void printCompress(String inFile, String outFile){
    FileOutputStream out = null;
    
    try {
      //encoded file
      StringBuilder data = newFile(inFile);
      if (data==null) {
        throw new IOException();
      }
      
      out = new FileOutputStream(outFile);
  
      //write name of file
      out.write(inFile.getBytes());
      
      //new line 
      out.write(13);
      out.write(10);
  
      //write binary tree
      
      // special case:no nodes
      if (codeMap.root==null) {
        out.write("0".getBytes());
        System.out.println(0);
      }else {
        String encodedTree=codeMap.encodeTree();
        out.write(encodedTree.getBytes());
        System.out.println(encodedTree);
      }
      
      //new line 
      out.write(13);
      out.write(10);
  
      //write number of extra bits
      int extra = (8-(data.length()%8));
      out.write(String.valueOf(extra).getBytes());
      
      //newline
      out.write(13);
      out.write(10);
  
      //add extra bits to end of data
      data = data.append("0".repeat(extra));  
  
      System.out.println("Preliminary data written! Final stretch");
      //parse string 8 bits at a time to make a character
      int length=data.length();
      for (int i = 0; i < length; i += 8) {
        int byteValue = 0;

        // Process 8 bits directly
        for (int j = 0; j < 8 && (i + j) < length; j++) {
            byteValue = (byteValue << 1) | (data.charAt(i + j) - '0');
        }
        // Write the byte to the file
        out.write(byteValue);
    }
      out.close();
      System.out.println("File successfully zipped to: " + outFile);
    }catch(IOException e){
      System.out.println("File not found. Please ensure your path exists, and your input has no quotes!");
      return;
    } finally {
      try{
        if (out != null) {
          out.close();
        }
      }catch (IOException e){
      }
    }
     
    
  }

  
}