package color.exchange.ui.event;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa stanowi¹ca bufor dla kolorów
 * 
 */
public class ColorQueue {

    private final List<Color> queue;

    private static ColorQueue colorQueue = new ColorQueue();

    public static ColorQueue getInstance() {
	return colorQueue;
    }

    private ColorQueue() {
	queue = new ArrayList<Color>();
    }

    public synchronized Color getColor() {

	if (queue == null || queue.size() == 0) {
	    return null;
	}

	synchronized (queue) {
	    Color color = queue.get(0);
	    queue.remove(0);
	    return color;
	}

    }

    public boolean isEmpty() {
	return queue != null && queue.size() > 0 ? false : true;
    }

    public synchronized void putColor(Color color) {
	if (queue == null) {
	    return;
	}
	synchronized (queue) {
	    queue.add(color);
	}
    }
}
