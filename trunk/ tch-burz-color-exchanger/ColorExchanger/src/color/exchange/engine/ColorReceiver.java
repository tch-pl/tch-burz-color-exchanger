package color.exchange.engine;

import java.awt.Color;
import java.io.IOException;

public interface ColorReceiver {
    public void start() throws IOException;

    public Color receiveColor() throws IOException, ClassNotFoundException;

}
