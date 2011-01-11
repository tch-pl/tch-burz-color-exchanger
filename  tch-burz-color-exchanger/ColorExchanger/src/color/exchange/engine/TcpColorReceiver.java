package color.exchange.engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpColorReceiver implements Runnable {
    private final boolean run = true;
    private ServerSocket socket;

    public void run() {
	while (run) {
	    try {
		socket = new ServerSocket(61101);
		Socket client = socket.accept();
		new Thread(new ColorResponseHandler(client)).run();
		socket.close();
	    } catch (IOException e) {
		e.printStackTrace();
		return;
	    }

	}
    }
}
