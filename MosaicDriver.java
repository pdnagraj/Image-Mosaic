package mosaic;

import java.io.File;

import images.ImageUtils;
import processing.core.PApplet;
import processing.core.PImage;

public class MosaicDriver extends PApplet {
	private Mosaic mosaic;
	private static final int WIDTH = 40;
	private static final int HEIGHT = 30;
	private static final String RESOURCE_DIR = "test" + File.separator + "resources" + File.separator;
	PImage imageMosaic;
	
	public static void main(String[] args) {
		PApplet.main("mosaic.MosaicDriver");
	}
	
	public void settings() {
		size(1152, 720);
	}

	public void setup() {
		// first, choose the set of colors (the "palette") for the mosaic
		int[] palette = {color(255, 0, 0), color(0, 255, 0), color(0, 0, 255)};
		
		// then instantiate a TileFactor with this palette
		TileFactory tileFactory = new TileFactory(this, palette, WIDTH, HEIGHT);
		
		// load the test images
		for (String color: new String[] {"red", "green", "blue"}) {
			for (PImage image: ImageUtils.loadFromDirectory(new File(RESOURCE_DIR + color), this)) {				
				tileFactory.addImage(image);
			}
		}
		
		// load the target image
		File file = new File(RESOURCE_DIR + "nature.jpg");
		PImage image = loadImage(file.getAbsolutePath());
		
		// create a Mosaic with this target image and the previously initialized TileFactory
		mosaic = new Mosaic(this, image, tileFactory);
		imageMosaic = mosaic.buildMosaic();
	}
	
	public void draw() {
		// draw the imageMosaic 
		image(imageMosaic, 0, 0, width, height);
	}
	
	public void mousePressed() {
		exit();
	}
}
