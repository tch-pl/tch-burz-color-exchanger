package color.exchange.main;

import color.exchange.engine.ColorEngine;
import color.exchange.engine.TcpColorReceiver;
import color.exchange.ui.ColorValueSliderControl;

public class Executor {

    /**
     * @param args
     */
    public static void main(String[] args) {
	TcpColorReceiver receiver = new TcpColorReceiver();
	ColorEngine engine = new ColorEngine(receiver);

	new ColorValueSliderControl(engine);
    }

}
