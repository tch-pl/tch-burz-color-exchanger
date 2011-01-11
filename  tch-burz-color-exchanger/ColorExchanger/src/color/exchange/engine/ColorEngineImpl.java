package color.exchange.engine;

import java.awt.Color;
import java.io.IOException;

public class ColorEngineImpl implements ColorEngine {
    private final ColorSender sender;
    private final TcpColorReceiver receiver;

    public ColorEngineImpl(ColorSender sender, TcpColorReceiver receiver) {
	super();
	this.sender = sender;
	this.receiver = receiver;
    }

    public void sendColor(Color color) throws IOException {
	// int r = color.getRed();
	// int g = color.getGreen();
	// int b = color.getBlue();
	// sender.start();
	// sender.sendColor(r, g, b);
	new Thread(new ColorRequestHandler()).start();
    }

    public void sendColor() throws IOException {
	new Thread(new ColorRequestHandler()).start();
	// sender.start();
	// sender.sendColor(r, g, b);
    }

    public void receiveColor(ColorChangeExecutor executor) throws IOException,
	    ClassNotFoundException {
	new Thread(receiver).start();
	new Thread(new ColorChanger(executor)).start();
    }

}
