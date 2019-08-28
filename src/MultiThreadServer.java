import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MultiThreadServer extends Application {

    private TextArea ta = new TextArea();

    private int clientNumber = 0;

    public void start(Stage primaryStage) {
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("MultithreadServer");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {

            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("MultiThread Server started at " + new Date() + '\n');

                while (true) {
                    Socket socket = serverSocket.accept();

                    clientNumber++;

                    Platform.runLater(() -> {
                        ta.appendText("Starting thread for client " + clientNumber + " at " + new Date() + '\n');

                        InetAddress inetAddress = socket.getInetAddress();
                        ta.appendText("Client " + clientNumber + "'s host name is " + inetAddress.getHostName() + "\n");
                        ta.appendText("Client " + clientNumber + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
                    });

                    new Thread(new HandleAClient(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    class HandleAClient implements Runnable {
        private Socket socket;

        public HandleAClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try{

                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());

                while (true) {
                    double radius = inputFromClient.readDouble();
                    double area = radius * radius * Math.PI;
                    outputToClient.writeDouble(area);

                    Platform.runLater(() -> {
                        ta.appendText("radius received from client: " + radius + '\n');
                        ta.appendText("Area found: " + area + '\n');
                    });
                    }
                }
            catch(IOException e) {
                e.printStackTrace();
            }
            }
        }
    }