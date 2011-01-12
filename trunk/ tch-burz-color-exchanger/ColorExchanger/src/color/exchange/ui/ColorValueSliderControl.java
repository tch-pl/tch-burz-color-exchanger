package color.exchange.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import color.exchange.engine.ColorChangeExecutor;
import color.exchange.engine.ColorEngine;
import color.exchange.ui.event.ColorQueue;

public class ColorValueSliderControl extends JFrame {
    private ColorEngine engine;

    public ColorEngine getEngine() {
	return engine;
    }

    public void setEngine(ColorEngine engine) {
	this.engine = engine;
    }

    public ColorValueSliderControl(ColorEngine engine) {
	setEngine(engine);
	getContentPane().add(new TColor(getEngine()));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(500, 500);
	setVisible(true);
    }
}

class ColorChangeListener implements ChangeListener {
    public ColorChangeListener(boolean receive, DrawingCanvas canvas) {
	super();
	_receive = receive;
	this.canvas = canvas;
    }

    private boolean _receive;
    private final DrawingCanvas canvas;

    public void stateChanged(ChangeEvent e) {
	if (!_receive) {
	    Color col = new Color(canvas.redValue, canvas.greenValue,
		    canvas.blueValue);
	    ColorQueue.getInstance().putColor(col);
	}

    }

    public boolean is_receive() {
	return _receive;
    }

    public void set_receive(boolean receive) {
	_receive = receive;
    }
}

class TColor extends JPanel {
    DrawingCanvas canvas = new DrawingCanvas();
    JLabel rgbValue = new JLabel("");

    JSlider sliderR, sliderG, sliderB;
    JButton send;
    JButton receive;

    public TColor(final ColorEngine engine) {
	final ColorChangeListener colorChangeListener = new ColorChangeListener(
		false, canvas);

	sliderR = getSlider(0, 255, 0, 50, 5);
	sliderG = getSlider(0, 255, 0, 50, 5);
	sliderB = getSlider(0, 255, 0, 50, 5);
	send = new JButton("Wysy³aj");
	send.setSize(30, 20);
	receive = new JButton("Odbieraj");
	receive.setSize(30, 20);
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(6, 2, 15, 0));

	panel.add(new JLabel("R-G-B Sliders (0 - 255)"));
	panel.add(sliderR);
	panel.add(sliderG);
	panel.add(sliderB);
	panel.add(new JLabel("RGB Value: ", JLabel.RIGHT));

	rgbValue.setBackground(Color.white);
	rgbValue.setForeground(Color.black);
	rgbValue.setOpaque(true);
	panel.add(rgbValue);
	panel.add(send);
	panel.add(receive);
	sliderR.addChangeListener(colorChangeListener);
	sliderG.addChangeListener(colorChangeListener);
	sliderB.addChangeListener(colorChangeListener);

	receive.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent event) {
		colorChangeListener.set_receive(Boolean.TRUE);
		blockUI();
		try {
		    ColorChangeExecutor executor = new ColorChangeExecutor(
			    canvas, sliderR, sliderG, sliderB);
		    engine.receiveColor(executor);

		} catch (IOException e) {
		    System.out.println(this.getClass() + "   " + e.getClass()
			    + "   " + e.getMessage());

		} catch (ClassNotFoundException e) {
		    System.out.println(this.getClass() + "   " + e.getClass()
			    + "   " + e.getMessage());
		}

		unblockUI();
	    }
	});

	send.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent event) {
		try {
		    engine.sendColor();
		} catch (IOException e) {
		    System.out.println(this.getClass() + "   " + e.getClass()
			    + "   " + e.getMessage());
		}

	    }
	});
	add(panel, BorderLayout.SOUTH);
	add(canvas);
    }

    private void blockUI() {
	setVisibleUI(false);
    }

    private void unblockUI() {
	setVisibleUI(true);
    }

    private void setVisibleUI(boolean visible) {
	send.setVisible(visible);
	receive.setVisible(visible);
	sliderB.setVisible(visible);
	sliderR.setVisible(visible);
	sliderG.setVisible(visible);
    }

    private JSlider getSlider(int min, int max, int init, int mjrTkSp,
	    int mnrTkSp) {
	JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
	slider.setPaintTicks(true);
	slider.setMajorTickSpacing(mjrTkSp);
	slider.setMinorTickSpacing(mnrTkSp);
	slider.setPaintLabels(true);
	slider.addChangeListener(new SliderListener());
	return slider;
    }

    class SliderListener implements ChangeListener {
	public void stateChanged(ChangeEvent e) {
	    JSlider slider = (JSlider) e.getSource();

	    if (slider == sliderR) {
		canvas.redValue = slider.getValue();
		displayRGBColor();
	    } else if (slider == sliderG) {
		canvas.greenValue = slider.getValue();
		displayRGBColor();
	    } else if (slider == sliderB) {
		canvas.blueValue = slider.getValue();
		displayRGBColor();
	    }
	    canvas.repaint();
	}

	public void displayRGBColor() {
	    canvas.setBackgroundColor();
	    Color.RGBtoHSB(canvas.redValue, canvas.greenValue,
		    canvas.blueValue, canvas.hsbValues);

	    rgbValue.setText(Integer.toString(canvas.color.getRGB() & 0xffffff,
		    16));
	}
    }
}