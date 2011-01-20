package color.exchange.engine.sender;

import java.awt.Color;
import java.io.IOException;

import color.exchange.ui.event.ColorQueue;

/**
 * metoda realizujaca odczyt z kolejki kolorow w nadawcy; paracujaca jako watek
 * w tle
 * 
 */
public class ColorRequestHandler implements Runnable {
    private final ColorQueue queue = ColorQueue.getInstance();
    /**
     * adres odbiorcy
     */
    private final String serverAddress;
    /**
     * port odbiorcy
     */
    private final Integer serverPort;

    public ColorRequestHandler(String serverAddress, Integer serverPort) {
	this.serverAddress = serverAddress;
	this.serverPort = serverPort;
    }

    public void run() {
	// w nieskonczonej petli wywyolujemy metode odczytujaca z kolejki i
	// wysylajaca do odbiorcy

	while (true) {
	    try {
		sendColors();
	    } catch (IOException e) {
		System.out.println(this.getClass() + "   " + e.getClass()
			+ "   " + e.getMessage());
	    }
	}

    }

    /**
     * @throws IOException
     */
    private void sendColors() throws IOException {
	// dopoki jest cos w kolejce to pobieramy z kolejki i wysylamy
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
