package ConsoleServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private static ServerSocket serverSocket;
    private static Socket socket;

    private static DataInputStream in;
    private static DataOutputStream out;

    private static BufferedReader bufferedReader;

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(8000);
            System.out.println("Server start");

            socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            bufferedReader= new BufferedReader(new InputStreamReader(System.in));

            Thread threadOut = new Thread(() -> {
                while (true) {
                    try {
                        String outMSG = bufferedReader.readLine();
                        out.writeUTF(outMSG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadOut.start();

            Thread threadIn = new Thread(() -> {
                while (true) {
                    try {
                        String inMSG = in.readUTF();
                        System.out.println(inMSG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadIn.start();

            try {
                threadOut.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                threadIn.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Server close");

    }
}
