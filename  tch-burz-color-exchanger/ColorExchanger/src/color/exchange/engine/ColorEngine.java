package color.exchange.engine;

import java.io.IOException;

public class ColorEngine {
    private final TcpColorReceiver receiver;

    public ColorEngine() {
	super();
	receiver = new TcpColorReceiver();
    }

    public void sendColor() throws IOException {
	new Thread(new ColorRequestHandler()).start();
    }

    public void receiveColor(ColorChangeExecutor executor) throws IOException,
	    ClassNotFoundException {
	new Thread(receiver).start();
	new Thread(new ColorChanger(executor)).start();
    }

}
