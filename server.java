import java.net.*;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Font;
import java.io.*;

public class server {
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //declare component
   


    public server() {
        try {
            server = new ServerSocket(777);
            System.out.println("Server is ready to accept the connection");
            System.out.println("waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startReading() {
        // theread
        Runnable r1 = () -> {
            System.out.println("reader started");
             try {
            while (true) {
               
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client has terminated the chat");
                        break;
                    }
                    System.out.println("Client :" + msg);
                } 
            }
            catch (Exception e) {
                   // e.printStackTrace();
                   System.out.println("connection closed");
                }

        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("writer started...");
              try {
            while (!socket.isClosed()) {
              
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")){
                        socket.close();
                        break;
                    }

                }
            }
             catch (Exception e) {
                  //  e.printStackTrace();
                  System.out.println("connection closed");
                }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is server");
        new server();
    }
}
