import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        int port = 1;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server kører!");
            Socket socket = server.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while(true) {
                double tallet = in.readDouble();
                System.out.println(tallet);
                double aNumber = tallet * tallet * Math.PI;
                System.out.println(aNumber);
                out.writeDouble(aNumber);
            }

        }catch (IOException e)
        {e.printStackTrace();}
    }
}
