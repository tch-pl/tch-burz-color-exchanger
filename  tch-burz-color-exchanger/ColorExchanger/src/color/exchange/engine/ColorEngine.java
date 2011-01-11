package color.exchange.engine;

import java.awt.Color;
import java.io.IOException;

public interface ColorEngine {

    public void sendColor() throws IOException;

    public void sendColor(Color color) throws IOException;

    public void receiveColor(ColorChangeExecutor executor) throws IOException,
	    ClassNotFoundException;

}
