package ConsoleServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    private static final String ADDRESS = "localhost";
    private static final  int PORT = 8000;

    private static Socket socket;

    private static DataInputStream in;
    private static DataOutputStream out;

    private static Thread threadOut;
    private static Thread threadIn;

    private static BufferedReader bufferedReader;

    public static void main(String[] args) {
        try {
            socket = new Socket(ADDRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            bufferedReader= new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Client start");

                threadOut = new Thread(() -> {
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

                threadIn = new Thread(() -> {
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

        System.out.println("Client close");

    }

}
