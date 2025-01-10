import java.util.Scanner;
class Main {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out.println("For your sanity and mine, please keep your files to 1mb or less");
    System.out.print("Please enter the path of file to zip: ");
    String input = s.nextLine();
    String output = input+".mzip";
    
      encode(input, output); 
  }
  public static void encode(String input, String output) {
    Encoder e = new Encoder();
    System.out.println("Working...");
    e.findFreq(input);
    e.createCodeMap();
    e.printCompress(input, output);
  }
}

