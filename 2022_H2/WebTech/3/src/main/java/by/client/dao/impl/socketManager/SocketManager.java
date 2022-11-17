package main.java.by.client.dao.impl.socketManager;

import main.java.by.client.entity.StudentRequest;
import main.java.by.client.entity.StudentResponse;
import main.java.by.client.entity.request.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketManager {
    private final String ip;
    private final int port;

    public SocketManager(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public StudentResponse sendRequest(Object body, RequestType type) {
        Socket client = null;
        try {
            try {
                client = new Socket(this.ip, this.port);

                ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(client.getInputStream());

                StudentRequest req = new StudentRequest();
                req.setBody(body);
                req.setRequestType(type);

                os.writeObject(req);
                os.flush();

                return (StudentResponse) is.readObject();

            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("Error client: %s%n", e.getMessage());
            } finally {
                if ((client != null) && !client.isClosed()) {
                    client.close();
                }
            }

        } catch (IOException e) {
            System.out.printf("Error client: %s%n", e.getMessage());
        }

        return null;
    }
}
