package color.exchange.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import color.exchange.engine.ColorChangeExecutor;
import color.exchange.engine.ColorChangeExecutorFactory;
import color.exchange.engine.ColorEngine;
import color.exchange.ui.event.ColorQueue;

/**
 * GUI
 * 
 */
public class ColorValueSliderControl extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ColorEngine engine;

	public ColorEngine getEngine() {
		return engine;
	}

	public void setEngine(ColorEngine engine) {
		this.engine = engine;
	}

	public ColorValueSliderControl(ColorEngine engine) {
		// ustawiamy obiekt odpowiedzialny za wysylanie/odbieranie kolorow
		setEngine(engine);
		getContentPane().add(new TColor(getEngine()));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
	}
}

/**
 * klasa realizujaca reakcje zdarzenie zmiany suwaka -> utworzenie koloru i
 * umieszczenie go w kolejce
 * 
 */
class ColorChangeListener implements ChangeListener {
	public ColorChangeListener(boolean receive, DrawingCanvas canvas) {
		super();
		_receive = receive;
		this.canvas = canvas;
	}

	// flaga do oznaczania czy ma byc reakcja na zmiane suwaka; jesli w stanie
	// nadawania to nie ma reakcji
	private boolean _receive;
	private final DrawingCanvas canvas;

	public void stateChanged(ChangeEvent e) {

		// jesli w stanie to nie nadawania ma reakcji
		if (!_receive) {
			// jesli nastapila zmiana na suwaku nastepuej odczyt wart z
			// wszystkich suwakow, utworzenie koloru i umieszczenie go w kolejce
			Color col = new Color(canvas.getBackground().getRed(), canvas.getBackground().getGreen(),
					canvas.getBackground().getBlue());
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DrawingCanvas canvas = new DrawingCanvas(); // tlo do rysowania koloru
	JLabel rgbValue = new JLabel(""); // pole z wartoscia hex koloru
	JLabel statusValue = new JLabel(""); // pole statusu w jakim jest aplikacja
	// -> wysylanie lub odbieranie

	JSlider sliderR, sliderG, sliderB; // suwaki do zmiany kolorow
	JButton send; // przycisk Wysy³aj -> po nacisnieciu startuje watek
	// nasluchujacy na zmiany suwakow
	JButton receive; // przycisk Odbieraj

	public TColor(final ColorEngine engine) {
		// reakcje zdarzenie zmiany suwaka
		final ColorChangeListener colorChangeListener = new ColorChangeListener(
				false, canvas);

		// ustawianie komponentow GUI
		sliderR = getSlider(0, 255, 0, 50, 5);
		sliderG = getSlider(0, 255, 0, 50, 5);
		sliderB = getSlider(0, 255, 0, 50, 5);
		send = new JButton("Wysy³aj");
		send.setSize(30, 20);
		send.repaint();
		receive = new JButton("Odbieraj");
		receive.setSize(30, 20);
		receive.repaint();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2, 15, 0));

		panel.add(new JLabel("R-G-B (0 - 255)"));
		panel.add(sliderR);
		panel.add(sliderG);
		panel.add(sliderB);
		panel.add(new JLabel("RGB: ", JLabel.RIGHT));

		rgbValue.setBackground(Color.white);
		rgbValue.setForeground(Color.black);
		rgbValue.setOpaque(true);
		panel.add(rgbValue);
		panel.add(send);
		panel.add(receive);
		panel.add(new JLabel("Status: ", JLabel.LEFT));
		panel.add(statusValue);

		// dodajemy do suwakow reakcje zdarzenie zmiany suwaka
		sliderR.addChangeListener(colorChangeListener);
		sliderG.addChangeListener(colorChangeListener);
		sliderB.addChangeListener(colorChangeListener);

		receive.addActionListener(new ActionListener() {
			// obsluga nacisniecia przycisku odbieraj
			public void actionPerformed(ActionEvent event) {
				statusValue.setText("Odbieram");
				colorChangeListener.set_receive(!colorChangeListener
						.is_receive());
				try {
					// obiekt realizuj¹ca zmianê kolorów w wybranych elementach
					// GUI
					
					List<ColorChangeExecutor> executors = new ArrayList<ColorChangeExecutor>();
					executors.add(ColorChangeExecutorFactory.createCanvasColorChangeExecutor(canvas));
					List<JSlider> sliders = new ArrayList<JSlider>();
					sliders.add(sliderR);
					sliders.add(sliderG);
					sliders.add(sliderB);
					executors.add(ColorChangeExecutorFactory.createSlidersColorChangeExecutor(sliders));
					
					// metoda uruchamia: @1 watek realizujacy nasluchiwanie na
					// zmiane kolorow @2 watek reagujacy na odbior zmiany koloru
					engine.receiveColor(executors);

				} catch (IOException e) {
					System.out.println(this.getClass() + "   " + e.getClass()
							+ "   " + e.getMessage());

				} catch (ClassNotFoundException e) {
					System.out.println(this.getClass() + "   " + e.getClass()
							+ "   " + e.getMessage());
				}

			}
		});

		send.addActionListener(new ActionListener() {
			// obsluga nacisniecia przycisku wysylaj
			public void actionPerformed(ActionEvent event) {
				statusValue.setText("Wysy³am");
				try {
					// metoda uruchamia watek realizujacy wysylanie kolorow
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
		send.setEnabled(visible);
		receive.setEnabled(visible);
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

	/**
	 * reakcje zdarzenie zmiany suwaka - zmiana koloru tla (nie ma zwiazku z
	 * kolejka kolorow, zmienia sie tylko te GUI na ktorym zmiana suwaka
	 * nastapila)
	 * 
	 */
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			int r = canvas.getBackground().getRed();
			int g = canvas.getBackground().getGreen();
			int b = canvas.getBackground().getBlue();
			if (slider == sliderR) {
				r = slider.getValue();
			} else if (slider == sliderG) {
				g = slider.getValue();
			} else if (slider == sliderB) {
				b = slider.getValue();
			}
			canvas.setBackground(new Color(r, g, b));
			
			displayRGBColor();
			canvas.repaint();
		}

		public void displayRGBColor() {
			Color.RGBtoHSB(canvas.getBackground().getRed(), canvas.getBackground().getGreen(),
					canvas.getBackground().getBlue(), new float[3]);

			rgbValue.setText(Integer.toString(canvas.getBackground().getRGB() & 0xffffff,
					16));
		}
	}
}