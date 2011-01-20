package color.exchange.main;

import java.util.HashMap;
import java.util.Map;

import color.exchange.engine.ColorEngine;
import color.exchange.ui.ColorValueSliderControl;

public class Executor {

    /**
     * @param args
     *            parametry z linii polecen sluzace do konfiguracji nadawcy;
     *            pierwszy parametr to adres ip aplikacji nasluchujacej, drugi
     *            parametr to port na ktorym slucha
     */
    public static void main(String[] args) {
	/**
	 * uruchomienie programu:
	 */
	/**
	 * 1. tworzymy obiekt realizujacy nadawanie i odbieranie kolorow przez
	 * siec jako argument wywolania konstruktora podajemy konfiguracje
	 * odczytana z linii polecen badz z domyslnych wartosci (@see: met.
	 * resolveConfig)
	 */
	ColorEngine engine = new ColorEngine(resolveConfig(args));
	/**
	 * 2. Tworzymy interfejs uzytkownika, podajemy jako arg. wywolania
	 * konstruktora obiekt realizujacy nadawanie i odbieranie kolorow
	 * */

	new ColorValueSliderControl(engine);
    }

    /**
     * Metoda tworzy konfiguracje na podstawie argumentow z linii polecen; lub
     * jesli nie ma parametro w linni polecen ustawiane sa domyslne wartosci
     * 
     * @param args
     *            parametry z linii polecen
     * @return mapa zawierajaca: adres aplikacji nasluchujacej oraz port
     *         nasluchujacy
     */
    private static Map<String, Object> resolveConfig(String[] args) {
	Map<String, Object> config = new HashMap<String, Object>();
	String server = null;
	Integer port = null;
	if (args == null || args.length < 2) {
	    // jesli nie podano parametrow w linii polecen pobierane sa domyslne
	    // wartosci

	    server = ColorEngine.DEFAULT_SERVER_ADDRESS; // domyslna wartosc dla
	    // adresu -> localhost
	    port = ColorEngine.DEFAULT_SERVER_PORT; // domyslna wartosc portu ->
	    // 61101
	} else {
	    // jesli parametry podano to jako pierwszy powinien byc podany adres
	    // ip
	    server = args[0];
	    // jako drugi podany poiwnien byc port
	    port = Integer.valueOf(args[1]);
	}

	config.put(ColorEngine.SERVER_ADDRESS_KEY, server);
	config.put(ColorEngine.SERVER_PORT_KEY, port);

	return config;

    }
}
