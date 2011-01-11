package color.exchange.engine;

import java.awt.Color;

import color.exchange.ui.event.ColorQueue;

public class ColorChanger implements Runnable {
    private boolean run = true;
    private final ColorChangeExecutor executor;

    public boolean isRun() {
	return run;
    }

    public void setRun(boolean run) {
	this.run = run;
    }

    public void run() {
	ColorQueue queue = ColorQueue.getInstance();
	while (run) {
	    Color color = queue.getColor();
	    if (color != null) {
		executor.executeColorChange(color);
	    }
	}

    }

    public ColorChanger(ColorChangeExecutor executor) {
	super();
	this.executor = executor;
    }
}
