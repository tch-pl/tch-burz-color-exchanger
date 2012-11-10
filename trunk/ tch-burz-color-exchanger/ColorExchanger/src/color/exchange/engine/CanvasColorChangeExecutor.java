package color.exchange.engine;

import java.awt.Canvas;
import java.awt.Color;

/**
 * klasa realizuj¹ca zmianê kolorów w wybranych elementach GUI
 * 
 */
public class CanvasColorChangeExecutor implements ColorChangeExecutor{
	private final Canvas canvas;

	/**
	 * parametry konstruktora to elementy GUI, które reaguj¹ na zmianê koloru
	 * 
	 * @param canvas
	 */
	public CanvasColorChangeExecutor(Canvas canvas) {
		super();
		this.canvas = canvas;
	}

	/**
	 * metoda ustawia dany kolor na elementach GUI
	 * 
	 * @param color
	 *            kolor ktory ma byc ustawiony w GUI
	 */
	public void executeColorChange(Color color) {
		this.canvas.setBackground(color);
		this.canvas.repaint();

	}
}
