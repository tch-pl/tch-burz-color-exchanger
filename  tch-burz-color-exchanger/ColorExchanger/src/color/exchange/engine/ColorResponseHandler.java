package color.exchange.engine;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import color.exchange.ui.event.ColorQueue;

public class ColorResponseHandler implements Runnable {

    private final Socket client;

    public ColorResponseHandler(Socket client) {
	super();
	this.client = client;
    }

    public void run() {
	ObjectInputStream input;
	try {
	    input = new ObjectInputStream(client.getInputStream());
	    Color color = (Color) input.readObject();
	    ColorQueue queue = ColorQueue.getInstance();
	    queue.putColor(color);
	    input.close();
	    client.close();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
