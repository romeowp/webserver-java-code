package nl.sogyo.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket socket;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            System.out.println("***" + line);
            RequestMessage requestMessage = new RequestMessage(line);
            while (!(line = reader.readLine()).isEmpty()) {
                requestMessage.setHeaderParameter(line);
            }
            if(requestMessage.getHTTPMethod() == HttpMethod.POST){
                StringBuilder lastLine = new StringBuilder();
                int remaining = Integer.parseInt(requestMessage.getHeaderParameterValue("Content-Length"));
                while(remaining-- > 0) {
                    lastLine.append((char) reader.read());
                }
                requestMessage.setParameters(lastLine.toString());
            }
            ResponseMessage responseMessage = new ResponseMessage(requestMessage);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(responseMessage.toString());
            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        try {
            ServerSocket socket = new ServerSocket(9090);
            while(true) {
                Socket newConnection = socket.accept();
                Thread t = new Thread(new ConnectionHandler(newConnection));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}