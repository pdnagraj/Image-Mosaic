package mosaic;

import images.ImageUtils;
import processing.core.PApplet;
import processing.core.PImage;

public class Mosaic {
	private final PApplet pApplet;
	private final PImage image;
	private final TileFactory tileFactory;

	public Mosaic(PApplet pApplet, PImage image, TileFactory tileFactory) {
		this.pApplet = pApplet;
		this.image = image;
		this.tileFactory = tileFactory;
	}
	
	/**
	 * Constructs and returns the mosaic.
	 * 
	 * The mosaic always consists of an integer number of tiles; if the target
	 * image is not exactly as wide or as high as a multiple of the tile width/height,
	 * the mosaic is truncated to be exactly a multiple of the tile width/height.
	 * 
	 * @return the mosaic
	 */
	public PImage buildMosaic() {
		
		
		image.loadPixels();
		
		int mosaicWidth = image.width - image.width % tileFactory.tileWidth;
		int mosaicHeight = image.height -  image.height % tileFactory.tileHeight;
		
		
		PImage img1 = pApplet.createImage(mosaicWidth, mosaicHeight, PImage.RGB);
		PImage c;
		
		for(int i = 0; i<mosaicWidth; i +=tileFactory.tileHeight ){
			for(int j = 0; j<mosaicHeight; j+=tileFactory.tileWidth){
				 c = image.get(i, j, tileFactory.tileWidth, tileFactory.tileHeight);
				 c.loadPixels();
				 img1.copy( tileFactory.getTile(ImageUtils.averageHue(c.pixels, pApplet)),
				 0, 0, tileFactory.tileWidth, tileFactory.tileHeight 
				 ,i,j,tileFactory.tileWidth, tileFactory.tileHeight);
			}
		}
	
		

		img1.updatePixels();
		return img1;
	}
}
