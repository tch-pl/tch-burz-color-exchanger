package color.exchange.engine;

import java.io.IOException;

public interface ColorSender {
    public void start() throws IOException;

    public void sendColor(int r, int g, int b) throws IOException;

}
