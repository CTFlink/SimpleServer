import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient extends Application {
    static int port = 8000;
    static String host = "localhost";

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
                if (tal == 0.0){break;}
                System.out.println("the area is = " + in.readDouble());
            }

            out.writeDouble(5.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private TextArea ta = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
