
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author AmandaXi
 * @version May 2023
 */

public class Encoder{
  private SimplePriorityQueue<BinaryTreeNode<Integer>> freqMap = new SimplePriorityQueue<BinaryTreeNode<Integer>>();
  private SimpleBinaryTree<Integer> codeMap = new SimpleBinaryTree<Integer>();
  private HashMap<Integer, String> charToCode = new HashMap<Integer, String>();


  public void findFreq(String inFile){
    FileInputStream in = null;

    try {
      in = new FileInputStream(inFile);
      int c;

      while ((c = in.read()) != -1) {
        BinaryTreeNode<Integer> newNode = new BinaryTreeNode<Integer>(c, null, null);
        freqMap.increment(newNode);
      }
    }catch(IOException e){
      System.out.println("File read exception");
      
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
    //while loop
    //take 2 nodes and combine them, then make a new node with the priority as sum of their priorities, enqueue that node

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
  public StringBuilder newFile(String inFile){
    StringBuilder data = new StringBuilder("");
    FileInputStream in = null;

    try {
      in = new FileInputStream(inFile);
      int c;

      while ((c = in.read()) != -1) {
        //get location of character value and add the codeValue to string
        data = data.append(charToCode.get(c));
      }
      return data;
    }catch(IOException e){
      System.out.println("File read exception");
      
    } finally {
      try{
        if (in != null) {
          in.close();
        }
      }catch (IOException e){
      }
    }
    return null;
  }

  // print file name, tree, and data to file
  public void printCompress(String inFile, String outFile){
    FileOutputStream out = null;
    //encoded file
    StringBuilder data = newFile(inFile);
    
    try {
      out = new FileOutputStream(outFile);

      //write name of file
      out.write(inFile.getBytes());
      
      //new line 
      out.write(13);
      out.write(10);

      //write binary tree
      String encodedTree=codeMap.encodeTree();
      out.write(encodedTree.getBytes());
      System.out.println(encodedTree);
      
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

      //parse string 8 bits at a time to make a character
      for (int i = 0; i < data.length(); i += 8){
        int binaryString = Integer.parseInt(data.substring(i,i+8),2);
        out.write(binaryString);
      }  
      
    }catch(IOException e){
      System.out.println("File read exception");
      
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