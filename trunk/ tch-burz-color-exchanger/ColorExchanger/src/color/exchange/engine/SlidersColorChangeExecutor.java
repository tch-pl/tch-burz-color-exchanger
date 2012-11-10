package color.exchange.engine;

import java.awt.Color;

import javax.swing.JSlider;

public class SlidersColorChangeExecutor implements ColorChangeExecutor {

	private final JSlider sliderR, sliderG, sliderB;

	/**
	 * parametry konstruktora to elementy GUI, które reaguj¹ na zmianê koloru
	 * 
	 * @param sliderR
	 * @param sliderG
	 * @param sliderB
	 */
	public SlidersColorChangeExecutor(JSlider sliderR, JSlider sliderG,
			JSlider sliderB) {
		this.sliderR = sliderR;
		this.sliderG = sliderG;
		this.sliderB = sliderB;
	}

	/**
	 * metoda ustawia dany kolor na elementach GUI
	 * 
	 * @param color
	 *            kolor ktory ma byc ustawiony w GUI
	 */
	public void executeColorChange(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		System.out.println("R=" + red);
		System.out.println("G=" + green);
		System.out.println("B=" + blue);
		
		System.out.println("Executor sliderR.getValue()=" + sliderR.getValue());
		System.out.println("Executor sliderG.getValue()=" + sliderG.getValue());
		System.out.println("Executor sliderB.getValue()=" + sliderB.getValue());
		
		this.sliderR.setValue(red);
		this.sliderR.repaint();
		this.sliderG.setValue(green);
		this.sliderG.repaint();
		this.sliderB.setValue(blue);
		this.sliderB.repaint();
		
		System.out.println("Executor sliderR.getValue()=" + sliderR.getValue());
		System.out.println("Executor sliderG.getValue()=" + sliderG.getValue());
		System.out.println("Executor sliderB.getValue()=" + sliderB.getValue());
	}

}
