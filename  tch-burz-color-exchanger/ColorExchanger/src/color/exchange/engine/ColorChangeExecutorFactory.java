package color.exchange.engine;

import java.awt.Canvas;
import java.util.List;

import javax.swing.JSlider;

public class ColorChangeExecutorFactory {
	public static ColorChangeExecutor createSlidersColorChangeExecutor(List<JSlider> sliders) {
		if (sliders == null || sliders.isEmpty() || sliders.size() < 3) {
			String reason = sliders == null ? "NULL" : "wrong size";
			String error = new String("Incorrect slider list definiton found! Slider list is  " + reason);
			throw new IllegalArgumentException(error); 
		}
		
		return new SlidersColorChangeExecutor(sliders.get(0), sliders.get(1), sliders.get(2));
	}
	
	public static ColorChangeExecutor createCanvasColorChangeExecutor(Canvas canvas) {
		if (canvas == null) {
			String error = new String("Incorrect canvas definiton found! canvas is  NULL");
			throw new IllegalArgumentException(error); 
		}
		
		return new CanvasColorChangeExecutor(canvas);
	}
	
}
