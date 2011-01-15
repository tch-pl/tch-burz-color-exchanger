package color.exchange.engine;

import java.io.IOException;
import java.util.Map;

public class ColorEngine {
    public static String SERVER_ADDRESS_KEY = "DEFAULT_SERVER_ADDRESS_KEY";
    public static String DEFAULT_SERVER_ADDRESS = "localhost";

    public static String SERVER_PORT_KEY = "DEFAULT_SERVER_PORT_KEY";
    public static Integer DEFAULT_SERVER_PORT = 61101;

    private final TcpColorReceiver receiver;
    private final ColorRequestHandler sender;

    private String serverAddress = null;
    private Integer serverPort = null;

    public ColorEngine(Map<String, Object> config) {
	super();
	serverAddress = (String) config.get(SERVER_ADDRESS_KEY);
	serverPort = (Integer) config.get(SERVER_PORT_KEY);

	receiver = new TcpColorReceiver(serverPort);
	sender = new ColorRequestHandler(serverAddress, serverPort);
    }

    public void sendColor() throws IOException {
	new Thread(sender).start();
    }

    public void receiveColor(ColorChangeExecutor executor) throws IOException,
	    ClassNotFoundException {
	new Thread(receiver).start();
	new Thread(new ColorChanger(executor)).start();
    }

    public void stopReceiveColor(ColorChangeExecutor executor) {
	receiver.stop();
    }

    public void stopSendColor(ColorChangeExecutor executor) {
	receiver.stop();
    }
}
