package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerChat {
    private Vector<ClientHandler> users;

    public ServerChat() {

        users = new Vector<>();
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            AuthSetvice.connect();
            serverSocket = new ServerSocket(8002);
            System.out.println("Server start");

            while (true){
                socket = serverSocket.accept();
                System.out.println("Client connected " + socket.getInetAddress());

             new ClientHandler(this, socket);

            }

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AuthSetvice.dicconnect();


    }

    public void subscribe(ClientHandler client){
        users.add(client);
    }

    public void unsubscribe(ClientHandler client){
        users.remove(client);
    }

    public void broadcastMSG(String str){
        for (ClientHandler c: users
             ) {
            c.sendMSG(str);
        }
    }

    public void privateMSG(String nickOut, String nickIn, String str){
        for (ClientHandler c: users
        ) {
            if (c.getNickname().equals(nickOut) ||
                    c.getNickname().equals(nickIn)){
                c.sendMSG(nickOut + ": [send for " + nickIn + "] msg: "+str);
            }

        }
    }

    public boolean checkNick(String nick){
        for (ClientHandler c: users
        ) {
            if(nick.equals(c.getNickname())){
                return true;
            }
        }
        return false;
    }


}