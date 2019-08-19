import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    static int port = 1;
    static String host = "192.168.86.25";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            while(true) {
                System.out.println("Write radius: ");
                double tal = scanner.nextDouble();
                out.writeDouble(tal);
                if (tal == 0.0){break;};
                System.out.println("the area is = " + in.readDouble());
            }

            out.writeDouble(5.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
