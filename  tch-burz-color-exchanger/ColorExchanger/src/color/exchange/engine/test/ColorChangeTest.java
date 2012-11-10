package color.exchange.engine.test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSlider;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import color.exchange.engine.ColorChangeExecutor;
import color.exchange.engine.ColorChangeExecutorFactory;
import color.exchange.ui.DrawingCanvas;

public class ColorChangeTest {
	private DrawingCanvas canvas;
	private JSlider sliderR, sliderG, sliderB;
	private List<JSlider> sliders;
	private ColorChangeExecutor canvasColorChangeExecutor;
	private ColorChangeExecutor sliderColorChangeExecutor;

	@Parameters({ "color_red", "color_green", "color_blue" })
	@Test
	public void executorCreateTest(Integer r, Integer g, Integer b) {

		
		canvasColorChangeExecutor.executeColorChange(new Color(r, g, b));
		sliderColorChangeExecutor.executeColorChange(new Color(r, g, b));

		Assert.assertEquals(canvas.getBackground().getRed(), 0);
		Assert.assertEquals(canvas.getBackground().getGreen(), 0);
		Assert.assertEquals(canvas.getBackground().getBlue(), 0);
		Assert.assertEquals(sliderR.getValue(), 0);
		Assert.assertEquals(sliderG.getValue(), 0);
		Assert.assertEquals(sliderB.getValue(), 0);

		canvasColorChangeExecutor.executeColorChange(new Color(0, 0, 0));
		sliderColorChangeExecutor.executeColorChange(new Color(0, 0, 0));

		Assert.assertEquals(canvas.getBackground().getRed(), 0);
		Assert.assertEquals(canvas.getBackground().getGreen(), 0);
		Assert.assertEquals(canvas.getBackground().getBlue(), 0);
		Assert.assertEquals(sliderR.getValue(), 0);
		Assert.assertEquals(sliderG.getValue(), 0);
		Assert.assertEquals(sliderB.getValue(), 0);

	}

	@Test(dataProvider = "color_generator")
	public void executeColorChangeTest(Integer r, Integer g, Integer b) {
		Color color = new Color(r.intValue(), g.intValue(), b.intValue());
		canvasColorChangeExecutor.executeColorChange(color);
		Assert.assertEquals(canvas.getBackground().getRed(), r.intValue());
		Assert.assertEquals(canvas.getBackground().getGreen(), g.intValue());
		Assert.assertEquals(canvas.getBackground().getBlue(), b.intValue());
		
		sliderColorChangeExecutor.executeColorChange(color);
		System.out.println("sliderR.getValue()=" + sliderR.getValue());
		System.out.println("sliderG.getValue()=" + sliderG.getValue());
		System.out.println("sliderB.getValue()=" + sliderB.getValue());
		
		Assert.assertEquals(sliderR.getValue(), r.intValue());
		Assert.assertEquals(sliderG.getValue(), g.intValue());
		Assert.assertEquals(sliderB.getValue(), b.intValue());

	}

	@BeforeMethod
	public void beforeMethod() {
		// create test resources
		sliderR = new JSlider();
		sliderG = new JSlider();
		sliderB = new JSlider();
		sliders = new ArrayList<JSlider>();
		sliders.add(sliderR);
		sliders.add(sliderG);
		sliders.add(sliderB);
		canvas = new DrawingCanvas();
		canvasColorChangeExecutor = ColorChangeExecutorFactory
				.createCanvasColorChangeExecutor(canvas);
		sliderColorChangeExecutor = ColorChangeExecutorFactory
				.createSlidersColorChangeExecutor(sliders);

	}

	@AfterMethod
	public void afterMethod() {
	}

	@DataProvider(name = "color_generator")
	public Object[][] colorGenerate() {
		Integer r = (int) (Math.random() * 100);
		Integer g = (int) (Math.random() * 100);
		Integer b = (int) (Math.random() * 100);
		Integer rgb[] = {r, g, b};

		return new Object[][] { rgb };
	}

}
