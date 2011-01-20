package color.exchange.engine.receiver;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import color.exchange.ui.event.ColorQueue;

/**
 * klasa realizujaca polaczenie TCP z klientem. Klasa implementuje interfejs
 * Runnable w celu umozliwienia dzialania w tle jako watek
 * 
 */
public class ColorResponseHandler implements Runnable {

    /**
     * gniazdo komunikacji z klientem
     */
    private final Socket client;

    /**
     * @param client
     *            gniazdo komunikacji z klientem
     */
    public ColorResponseHandler(Socket client) {
	super();
	this.client = client;
    }

    public void run() {
	ObjectInputStream input;
	try {
	    // tworzymy strumien do odczytu danych od klienta
	    input = new ObjectInputStream(client.getInputStream());
	    // odczytujemy jaki kolor przyslal klient -> dane przyslane
	    // bezposrednio
	    // sa zamieniane na obiekt klasy Color (wszystko dzieki klasie
	    // ObjectInputStream)
	    Color color = (Color) input.readObject();
	    // dodajemy odebrany kolor do kolejki z kolorami, dane z kolejki sa
	    // odczytywane przez oddzielny watek zmieniajacy kolory w GUI
	    ColorQueue queue = ColorQueue.getInstance();
	    queue.putColor(color);
	    // zamykamy strumien
	    input.close();
	    // zamykamy gniazdo
	    client.close();
	} catch (IOException e) {
	    // cos poszlo nie tak
	    System.out.println(this.getClass() + "   " + e.getClass() + "   "
		    + e.getMessage());
	} catch (ClassNotFoundException e) {
	    // cos poszlo nie tak
	    System.out.println(this.getClass() + "   " + e.getClass() + "   "
		    + e.getMessage());
	}

    }
}
