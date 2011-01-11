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
    public ColorChangeListener(ColorEngine engine, boolean receive,
	    DrawingCanvas canvas) {
	super();
	this.engine = engine;
	_receive = receive;
	this.canvas = canvas;
    }

    private final ColorEngine engine;
    private boolean _receive;
    private final DrawingCanvas canvas;

    public void stateChanged(ChangeEvent e) {
	if (!_receive) {
	    Color col = new Color(canvas.redValue, canvas.greenValue,
		    canvas.blueValue);
	    ColorQueue.getInstance().putColor(col);
	    // engine.sendColor(canvas.redValue, canvas.greenValue,
	    // canvas.blueValue);
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
    private final ColorEngine engine;

    public TColor(final ColorEngine engine) {
	final Boolean _receive = Boolean.FALSE;
	this.engine = engine;
	final ColorChangeListener colorChangeListener = new ColorChangeListener(
		engine, false, canvas);

	sliderR = getSlider(0, 255, 0, 50, 5);
	sliderG = getSlider(0, 255, 0, 50, 5);
	sliderB = getSlider(0, 255, 0, 50, 5);
	send = new JButton("send");
	receive = new JButton("receive");
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

	    public void actionPerformed(ActionEvent e) {
		colorChangeListener.set_receive(Boolean.TRUE);
		blockUI();
		try {
		    ColorChangeExecutor executor = new ColorChangeExecutor(
			    canvas, sliderR, sliderG, sliderB);
		    engine.receiveColor(executor);

		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}

		unblockUI();
	    }
	});

	send.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		try {
		    engine.sendColor();
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
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