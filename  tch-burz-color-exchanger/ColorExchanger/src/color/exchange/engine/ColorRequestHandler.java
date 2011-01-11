package color.exchange.engine;

import java.awt.Color;
import java.io.IOException;

import color.exchange.ui.event.ColorQueue;

public class ColorRequestHandler implements Runnable {
    ColorQueue queue = ColorQueue.getInstance();

    public void run() {
	while (true) {
	    try {
		sendColors();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

    }

    private void sendColors() throws IOException {

	while (!queue.isEmpty()) {
	    Color col = queue.getColor();
	    TcpColorSender sender = new TcpColorSender();
	    sender.start();
	    if (col != null) {
		sender.sendColor(col.getRed(), col.getGreen(), col.getBlue());
	    }
	}
    }
}
