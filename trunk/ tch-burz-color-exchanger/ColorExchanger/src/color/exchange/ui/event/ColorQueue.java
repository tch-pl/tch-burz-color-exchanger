package color.exchange.ui.event;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Klasa stanowi¹ca bufor dla kolorów. Sluzy do przechowywania kolorow odebrane
 * od klienta lub koloow ktore maja byc wyslane Z klasy korzystamy na zasadzie
 * sigletona -> kazdy oddzielnie uruchomiony progam ma tylko jedna wersje
 * obiektu tej klasy. Dzieki temu np. mozemy realizowac odbieranie kolorow od
 * wielu klientow na raz, majac jednoczesnie pewnosc ze kolory w GUI beda
 * zmieniane w kolejnosci w jakiej zostaly odebrane.
 * 
 */
public class ColorQueue {

    /**
     * kolekcja sluzaca do przechowywania obiektow reprezentujacych kolory
     */
    private final ArrayList<Color> queue;

    /**
     * inicjalizacja obiektu
     */
    private static ColorQueue colorQueue = new ColorQueue();

    /**
     * za pomoca tej met. uzyskujemy obiekt i jest to z zawsze ten sam obiekt
     * dla danego programu
     * 
     * @return
     */
    public static ColorQueue getInstance() {
	return colorQueue;
    }

    /**
     * prywatny konstruktor wymusza korzystanie z met getInstance() do uzyskania
     * obiektu tej klasy
     */
    private ColorQueue() {
	// kolekcja uzyta do przechowywania kolorow, przechowuje kolory w
	// kolejnosci w jakiej zostaly one dodane
	queue = new ArrayList<Color>();
    }

    /**
     * metoda zwracjajaca pierwszy kolor z kolekcji lub jesli jest pusta zwraca
     * null, pobrany kolor jest usuwany z kolekcji; metoda jest synchronizowana
     * w celu zabezpieczenia przed jednoczesnym wywolywaniem przez wiele watkow;
     * wywolac synchronizowana metode moze jednoczesnie tylko jeden watek, inne
     * ktore probuja musza zaczekac az metoda zostanie wykonana
     * 
     * @return
     */
    public synchronized Color getColor() {

	if (isEmpty()) {
	    return null;
	}

	synchronized (queue) {
	    Color color = queue.get(0);
	    queue.remove(0);
	    return color;
	}

    }

    /**
     * metoda do sprawdzenia czy kolekcja jest pusta
     * 
     * @return
     */
    public boolean isEmpty() {
	return queue != null && queue.size() > 0 ? false : true;
    }

    /**
     * metoda wstawiajaca kolor do kolekcji³ metoda jest synchronizowana w celu
     * zabezpieczenia przed jednoczesnym wywolywaniem przez wiele watkow;
     * wywolac synchronizowana metode moze jednoczesnie tylko jeden watek, inne
     * ktore probuja musza zaczekac az metoda zostanie wykonana
     * 
     * @param color
     * 
     */
    public synchronized void putColor(Color color) {
	if (queue == null) {
	    return;
	}
	synchronized (queue) {
	    queue.add(color);
	}
    }
}
