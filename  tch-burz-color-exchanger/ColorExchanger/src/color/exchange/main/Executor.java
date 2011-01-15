package color.exchange.main;

import java.util.HashMap;
import java.util.Map;

import color.exchange.engine.ColorEngine;
import color.exchange.ui.ColorValueSliderControl;

public class Executor {

    /**
     * @param args
     */
    public static void main(String[] args) {

	ColorEngine engine = new ColorEngine(resolveConfig(args));
	new ColorValueSliderControl(engine);
    }

    /**
     * @param args
     *            parametry z linii polecen
     * @return
     */
    private static Map<String, Object> resolveConfig(String[] args) {
	Map<String, Object> config = new HashMap<String, Object>();
	String server = null;
	Integer port = null;
	if (args == null || args.length == 0) {
	    server = ColorEngine.DEFAULT_SERVER_ADDRESS;
	    port = ColorEngine.DEFAULT_SERVER_PORT;
	} else {
	    server = args[0];
	    port = Integer.valueOf(args[1]);
	}

	config.put(ColorEngine.SERVER_ADDRESS_KEY, server);
	config.put(ColorEngine.SERVER_PORT_KEY, port);

	return config;

    }
}
