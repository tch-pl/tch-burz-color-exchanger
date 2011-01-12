package color.exchange.engine;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpColorSender {
    private Socket socket;

    public void start() throws IOException {
	socket = new Socket("localhost", 61101);
    }

    public void sendColor(int r, int g, int b) throws IOException {
	ObjectOutputStream output = new ObjectOutputStream(socket
		.getOutputStream());
	output.writeObject(new Color(r, g, b));
	output.close();
	socket.close();
    }
}
