package color.exchange.main;

import color.exchange.engine.ColorEngine;
import color.exchange.engine.ColorEngineImpl;
import color.exchange.engine.ColorSender;
import color.exchange.engine.TcpColorReceiver;
import color.exchange.engine.TcpColorSender;
import color.exchange.ui.ColorValueSliderControl;

public class Executor {

    /**
     * @param args
     */
    public static void main(String[] args) {
	ColorSender sender = new TcpColorSender();
	TcpColorReceiver receiver = new TcpColorReceiver();

	ColorEngine engine = new ColorEngineImpl(sender, receiver);

	new ColorValueSliderControl(engine);
    }

}
