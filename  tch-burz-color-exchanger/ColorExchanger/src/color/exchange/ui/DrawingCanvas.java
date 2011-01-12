package color.exchange.ui;

import java.awt.Canvas;
import java.awt.Color;

public class DrawingCanvas extends Canvas {
    public Color color;
    public int redValue, greenValue, blueValue;
    int alphaValue = 255;
    float[] hsbValues = new float[3];

    public DrawingCanvas() {
	setSize(150, 150);
	color = new Color(0, 0, 0);
	setBackgroundColor();
    }

    public void setBackgroundColor() {
	color = new Color(redValue, greenValue, blueValue, alphaValue);
	setBackground(color);
    }
}