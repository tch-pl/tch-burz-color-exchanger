package color.exchange.engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpColorReceiver implements Runnable {
    private boolean run = true;
    private ServerSocket socket;
    private final Integer serverPort;

    public TcpColorReceiver(Integer serverPort) {
	this.serverPort = serverPort;
    }

    public void run() {
	try {
	    socket = new ServerSocket(serverPort);
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
