package color.exchange.engine.receiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Klasa przeznaczona do komunikacji TCP/IP nasluchujaca na zasadzie serwera
 * (uzuyto klasy ServerSocket do realizacji tego zadania ). Klasa implementuje
 * interfejs Runnable w celu umozliwienia dzialania w tle jako watek
 * 
 */
public class TcpColorReceiver implements Runnable {
	/**
	 * flaga decydujaca o przerwaniu watku -> true = watek dziala; false = watek
	 * nie dziala
	 */
	private boolean run = true;
	/**
	 * gniazdo nasluchiwania
	 */
	private ServerSocket socket;
	/**
	 * numer poru na ktorym tworzone jest gniazdo
	 */
	private final Integer serverPort;

	/**
	 * KOnstruktor przyjmujacy jako argument numer portu na ktorym bedzie
	 * utworzone gniazdo ServerSocket do nasluchiwania polaczen
	 * 
	 * @param serverPort
	 *            numer portu (nie jest sprawdzana poprawnosc czy wartosc miesci
	 *            sie w zakresie 0 do 65535)
	 */
	public TcpColorReceiver(Integer serverPort) {

		this.serverPort = serverPort;
	}

	public void run() {
		try {
			// utworzenie gniazda nasluchujacego
			socket = new ServerSocket(serverPort);
		} catch (IOException e1) {
			// cos poszlo nie tak
			System.out.println("new Server Socket create error");
			// ustawiamy flage na false - nie chcemy startowac watku bo nei
			// mozna utworzyc gniazda nasluchujacego
			run = false;
		}
		while (run) {

			Socket client;
			try {
				// met. accept wywolana na gniezdzie blokuje biezacy watek
				// dopoki nie zostanie ustanowione polaczenie
				// po ustanownieniu polaczenia tworzone jest gniazdo na ktorym
				// odbywa sie komunikacja z klientem
				client = socket.accept();
				System.out.println("ACCEPTED");
				// odpalany jet nowy watkek do komunikacji z klientem, zeby nie
				// blokowac innych klientow (nasluchiwanie kolejnych polaczen)
				new Thread(new ColorResponseHandler(client)).start();
			} catch (IOException e) {
				// cos poszlo nie tak
				System.out.println("client connection handle error");
				// ale nie rezygnujemy i wracamy do nasluchiwania
			}
		}
	}

	/**
	 * metoda zmieniajaca flage w celu przerwania watku
	 */
	public void stop() {
		run = false;
	}
}
