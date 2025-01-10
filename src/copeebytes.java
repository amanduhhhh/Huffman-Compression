import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* A File demonstrating write/read of byes. 
 * consider a Buffering this
*/

public class copeebytes {
    public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("C:/Users/xiama/eclipse-workspace/Compression/src/output.mzip");
            out = new FileOutputStream("C:/Users/xiama/eclipse-workspace/Compression/src/goodbye.mzip");
            int c;

            while ((c = in.read()) != -1) {
              System.out.print((c)+ " ");

              out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}