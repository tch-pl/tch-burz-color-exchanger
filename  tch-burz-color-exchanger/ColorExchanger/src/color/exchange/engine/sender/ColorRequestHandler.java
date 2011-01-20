package color.exchange.engine.sender;

import java.awt.Color;
import java.io.IOException;

import color.exchange.ui.event.ColorQueue;

/**
 * metoda realizujaca odczyt
 * 
 */
public class ColorRequestHandler implements Runnable {
    private final ColorQueue queue = ColorQueue.getInstance();
    private final String serverAddress;
    private final Integer serverPort;

    public ColorRequestHandler(String serverAddress, Integer serverPort) {
	this.serverAddress = serverAddress;
	this.serverPort = serverPort;
    }

    public void run() {
	while (true) {
	    try {
		sendColors();
	    } catch (IOException e) {
		System.out.println(this.getClass() + "   " + e.getClass()
			+ "   " + e.getMessage());
	    }
	}

    }

    private void sendColors() throws IOException {

	while (!queue.isEmpty()) {
	    Color col = queue.getColor();
	    TcpColorSender sender = new TcpColorSender();
	    sender.start(serverAddress, serverPort);
	    if (col != null) {
		sender.sendColor(col.getRed(), col.getGreen(), col.getBlue());
	    }
	}
    }
}
