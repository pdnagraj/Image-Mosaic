package images;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageUtils {
	public static List<PImage> loadFromDirectory(File directory, PApplet pApplet) {
		List<PImage> list = new ArrayList<>();
		for (File f: directory.listFiles()) {
			PImage image = pApplet.loadImage(f.getAbsolutePath());
			if (image == null) continue;
			if (image.height == -1 || image.width == -1) continue;
			list.add(image);
		}
		return list;
	}
	
	public static int averageHue(int[] pixels, PApplet pApplet) {
		double averageHue = 0.0;
		for (int pixel : pixels) {
			averageHue += pApplet.hue(pixel);
		}
		averageHue /= pixels.length;
		
		return (int) Math.round(averageHue);
	}
}
