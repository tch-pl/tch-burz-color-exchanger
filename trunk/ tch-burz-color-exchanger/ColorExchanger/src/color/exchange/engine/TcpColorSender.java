package color.exchange.engine;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpColorSender {
    private Socket socket;

    public void start(String address, Integer port) throws IOException {
	socket = new Socket(address, port);
    }

    public void sendColor(int r, int g, int b) throws IOException {
	ObjectOutputStream output = new ObjectOutputStream(socket
		.getOutputStream());
	output.writeObject(new Color(r, g, b));
	output.close();
	socket.close();
    }
}
