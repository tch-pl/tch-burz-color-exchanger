package color.exchange.engine;

import java.io.IOException;

public class ColorEngine {
    private final TcpColorReceiver receiver;

    public ColorEngine(TcpColorReceiver receiver) {
	super();
	this.receiver = receiver;
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
