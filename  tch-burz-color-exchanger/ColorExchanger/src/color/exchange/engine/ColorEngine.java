package color.exchange.engine;

import java.io.IOException;
import java.util.Map;

import color.exchange.engine.receiver.ColorChanger;
import color.exchange.engine.receiver.TcpColorReceiver;
import color.exchange.engine.sender.ColorRequestHandler;

/**
 * klasa uzywana jest przez gui do realizacji wybranych zadan: nasluchiwania
 * informacji o zmianie koloru lub wysylania informacji o zmianie koloru
 * 
 */
public class ColorEngine {

    // =========== WARTOSCI DLA DOMYSLNEJ KONFIGURACJI =========== //
    public static String SERVER_ADDRESS_KEY = "DEFAULT_SERVER_ADDRESS_KEY";
    public static String DEFAULT_SERVER_ADDRESS = "localhost";

    public static String SERVER_PORT_KEY = "DEFAULT_SERVER_PORT_KEY";
    public static Integer DEFAULT_SERVER_PORT = 61101;
    // ======================================================= //

    // deklaracja obiektu realizujacego odbior
    private final TcpColorReceiver receiver;
    // deklaracja obiektu realizujacego nadawanie
    private final ColorRequestHandler sender;

    private String serverAddress = null;
    private Integer serverPort = null;

    /**
     * dane z konfiguracji sluza do inicjalizacji serverAddress i serverPort
     * 
     * @param config
     *            konfiguracja
     */
    public ColorEngine(Map<String, Object> config) {
	super();
	serverAddress = (String) config.get(SERVER_ADDRESS_KEY);
	serverPort = (Integer) config.get(SERVER_PORT_KEY);

	// tworzymy obiekt realizujacy nasluchujacy informacji o zmianie koloru
	receiver = new TcpColorReceiver(serverPort);
	// tworzymy obiekt realizujacy wysylajacy informacje o zmianie koloru
	sender = new ColorRequestHandler(serverAddress, serverPort);
    }

    /**
     * metoda uruchamia watek realizujacy wysylanie kolorow
     * 
     * @throws IOException
     */
    public void sendColor() throws IOException {
	new Thread(sender).start();
    }

    /**
     * metoda uruchamia:
     * 
     * @1 watek realizujacy nasluchiwanie na zmiane kolorow
     * @2 watek reagujacy na odbior zmiany koloru
     * 
     * @param executor
     *            obiekt odpowiedzialny za zmiane koloru w interfejsie
     *            uzytkownika
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receiveColor(ColorChangeExecutor executor) throws IOException,
	    ClassNotFoundException {
	new Thread(receiver).start();
	new Thread(new ColorChanger(executor)).start();
    }

}
