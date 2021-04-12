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
                System.out.printf("Client [%s] try to connect \n", socket.getInetAddress());
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

        AuthSetvice.disconnect();


    }

    public void subscribe(ClientHandler client){
        users.add(client);
        String log = ("User ["+ client.getNickname()+ "] connected");
        System.out.println(log);
        broadcastContactsList();
    }

    public void unsubscribe(ClientHandler client){
        users.remove(client);
        String log = ("User [" + client.getNickname() + "] disconnected");
        System.out.println(log);
        broadcastContactsList();
    }


    public void broadcastMSG(ClientHandler from, String str){

        for (ClientHandler c: users) {
            if (!c.checkBlackList(from.getNickname())
                    && !from.checkBlackList(c.getNickname())){
                AuthSetvice.historyMsgAdd(c.getNickname(), str);
                c.sendMSG(str);
            }
        }
    }


    public void privateMSG(String nickOut, String nickIn, String str){

        for (ClientHandler c: users) {
            if (c.getNickname().equals(nickOut) || c.getNickname().equals(nickIn)) {
                if (!c.checkBlackList(nickOut)){
                    AuthSetvice.historyMsgAdd(c.getNickname(), nickOut + ": [send for " + nickIn + "] msg: " + str);
                    c.sendMSG(nickOut + ": [send for " + nickIn + "] msg: " + str);
                }
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

    public void broadcastContactsList() {

        StringBuilder strSB = new StringBuilder();
        strSB.append("/clientlist ");
        for (ClientHandler c: users
        ) {
            strSB.append(c.getNickname() + " ");
        }
        String outCmd = strSB.toString();

        for (ClientHandler c: users) {
                c.sendMSG(outCmd);
            }
    }
}