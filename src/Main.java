
class Main {
  public static void main(String[] args) {
    encode("src/BinaryTreeNode.java", "src/output3.mzip");

 
  }
  public static void encode(String input, String output) {
    Encoder e = new Encoder();
    e.findFreq(input);
    e.createCodeMap();
    e.printCompress(input, output);
  }
}

