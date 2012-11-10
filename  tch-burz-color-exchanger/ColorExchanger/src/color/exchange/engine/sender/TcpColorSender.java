package color.exchange.engine.sender;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * klasa do komunikacji odbiorca koloru przez gniazdo TCP
 * 
 */
public class TcpColorSender {
	/**
	 * gniazdo do komunikacji
	 */
	private Socket socket;

	/**
	 * @param address
	 *            adres ip maszyny na ktorej dziala program odbierajcy kolory
	 * @param port
	 *            port
	 * @throws IOException
	 */
	public void start(String address, Integer port) throws IOException {
		socket = new Socket(address, port);
	}

	/**
	 * metoda przesylajaca kolor do obdiorcy
	 * 
	 * @param r
	 *            wart r koloru
	 * @param g
	 *            wart g koloru
	 * @param b
	 *            wart b koloru
	 * @throws IOException
	 */
	public void sendColor(int r, int g, int b) throws IOException {
		// tworzymy strumien do przeslania koloru (korzystajac z gniazda)
		ObjectOutputStream output = new ObjectOutputStream(
				socket.getOutputStream());
		// zapisujemy obiekt do strumienia
		output.writeObject(new Color(r, g, b));

		// zamykamy strumien
		output.close();
		// zamykamy gniazdo
		socket.close();
	}
}
