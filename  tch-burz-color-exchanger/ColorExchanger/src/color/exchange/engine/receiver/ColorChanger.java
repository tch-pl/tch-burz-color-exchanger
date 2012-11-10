package color.exchange.engine.receiver;

import java.awt.Color;
import java.util.List;

import color.exchange.engine.ColorChangeExecutor;
import color.exchange.ui.event.ColorQueue;

/**
 * Klasa przeznaczona do czytajaca z kolejki kolory przyslanych od klienta ;
 * dziala w tle jako watek
 * 
 */
public class ColorChanger implements Runnable {
	private boolean run = true;

	private final List<ColorChangeExecutor> executors;

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public void run() {
		// tworzymy obiekt kolejki kolorow
		ColorQueue queue = ColorQueue.getInstance();
		// w pelti czytamy kolory z kolejki i przkazujemy kolor do obiektu
		// zmieniajacego kolor w gui
		while (run) {
			Color color = queue.getColor();
			if (color != null) {
				for (ColorChangeExecutor executor : executors)
					executor.executeColorChange(color);
			}
		}

	}

	public ColorChanger(List<ColorChangeExecutor> executors) {
		super();
		this.executors = executors;
	}
}
