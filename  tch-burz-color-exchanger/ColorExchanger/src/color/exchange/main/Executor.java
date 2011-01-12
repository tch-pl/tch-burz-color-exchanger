package color.exchange.main;

import color.exchange.engine.ColorEngine;
import color.exchange.ui.ColorValueSliderControl;

public class Executor {

    /**
     * @param args
     */
    public static void main(String[] args) {
	ColorEngine engine = new ColorEngine();
	new ColorValueSliderControl(engine);
    }

}
