package color.exchange.engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpColorReceiver implements Runnable {
    private boolean run = true;
    private ServerSocket socket;

    public void run() {
	try {
	    socket = new ServerSocket(61101);
	} catch (IOException e1) {
	    System.out.println("new Server Socket create error");
	}
	while (run) {

	    Socket client;
	    try {
		client = socket.accept();
		new Thread(new ColorResponseHandler(client)).start();
	    } catch (IOException e) {
		System.out.println("client connection handle error");
	    }
	}
    }

    public void stop() {
	run = false;
    }
}
