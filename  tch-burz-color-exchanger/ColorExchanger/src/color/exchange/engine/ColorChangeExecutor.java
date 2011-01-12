package color.exchange.engine;

import java.awt.Color;

import javax.swing.JSlider;

import color.exchange.ui.DrawingCanvas;

/**
 * klasa realizuj¹ca zmianê kolorów w wybranych elementach GUI
 * 
 */
public class ColorChangeExecutor {
    private final DrawingCanvas canvas;
    private final JSlider sliderR, sliderG, sliderB;

    /**
     * parametry konstruktora to elementy GUI, które reaguj¹ na zmianê koloru
     * 
     * @param canvas
     * @param sliderR
     * @param sliderG
     * @param sliderB
     */
    public ColorChangeExecutor(DrawingCanvas canvas, JSlider sliderR,
	    JSlider sliderG, JSlider sliderB) {
	super();
	this.canvas = canvas;
	this.sliderR = sliderR;
	this.sliderG = sliderG;
	this.sliderB = sliderB;
    }

    public void executeColorChange(Color color) {
	int red = color.getRed();
	int green = color.getGreen();
	int blue = color.getBlue();

	this.canvas.redValue = red;
	this.canvas.greenValue = green;
	this.canvas.blueValue = blue;
	this.canvas.setBackgroundColor();
	this.canvas.repaint();
	this.sliderR.setValue(red);
	this.sliderR.repaint();
	this.sliderG.setValue(green);
	this.sliderG.repaint();
	this.sliderB.setValue(blue);
	this.sliderB.repaint();
    }
}
