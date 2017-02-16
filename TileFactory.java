package mosaic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import images.ImageUtils;
import processing.core.PApplet;
import processing.core.PImage;

public class TileFactory {
	public final int tileWidth;
	public final int tileHeight;
	private final PApplet pApplet;
	// you will probably NOT be keeping this array in your final code.
	//private final int[] hues;
	Map<Integer, List<PImage>> huesMap =  new HashMap<Integer, List<PImage>>();
	
	/**
	 * 
	 * @param pApplet a reference to the parent PApplet
	 * @param colors the palette of RGB colors for this TileFactory
	 * @param tileWidth width (in pixels) for each tile
	 * @param tileHeight height (in pixels) for each tile
	 */
	public TileFactory(PApplet pApplet, int[] colors, int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.pApplet = pApplet;
		
//		hues = new int[colors.length];
//		for (int i = 0; i < hues.length; i++) {
//			hues[i] = Math.round(pApplet.hue(colors[i]));
//		} 
		
		for(int i = 0; i<colors.length; i++){
			huesMap.put(Math.round(pApplet.hue(colors[i])), new ArrayList<PImage>());
		}
		
		
		
		
	}
	
	/**
	 * Returns the distance between two hues on the circle [0,256).
	 * @param hue1 
	 * @param hue2
	 * @return the distance between two hues.
	 */
	public static int hueDistance(int hue1, int hue2) {
//		int hueDist = 0;
//		if(Math.abs(hue1-hue2) <= 256/2){
//			hueDist =  Math.abs(hue1-hue2);
//		}
//		
//		else{
//			if(hue1<hue2){
//				hueDist = 256+hue1-hue2;
//			}
//			else{
//				hueDist = 256+hue2-hue1;	
//			}
//			
//		}
		return Math.min(Math.abs(hue1-hue2+256)%256, Math.abs(hue2-hue1+256)%256);
	}
	
	/**
	 * Returns the closest hue from the fixed palette this TileFactory contains.
	 * @param hue
	 * @return the closest hue from the palette
	 */
	
	public Integer closestHue(int hue) {
		Integer lowest = Integer.MAX_VALUE; 
		Integer closestHue = 0; 
		for(Integer i: huesMap.keySet()){
			if(hueDistance(hue, i)<lowest){
				lowest = hueDistance(hue, i);
				closestHue = i;
			}
		}
		
		return closestHue;
	}
	
	/**
	 * Adds an image to this TileFactory for later use.
	 * @param image the image to add
	 */
	public void addImage(PImage image) {
		image.resize(tileWidth, tileHeight);
		image.loadPixels();
		int avgHue = ImageUtils.averageHue(image.pixels, pApplet);
		huesMap.get(closestHue(avgHue)).add(image);
		
	}
	
	/**
	 * Returns the next tile from the list associated with the hue most closely matching the input hue
	 * @param hue the color to match
	 * @return a tile matching hue
	 */
	public PImage getTile(int hue) {
		PImage Temp = huesMap.get(closestHue(hue)).get(0);
		Collections.rotate(huesMap.get(closestHue(hue)), -1);
		return Temp;
	}
}
