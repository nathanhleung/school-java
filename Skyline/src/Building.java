/**
 * Skyline Project: Building
 * This class contains the code to draw buildings in our skyline
 * @author 18nleung
 * @version 20 April 2017
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

public class Building {
	private int[][] coordinates;
	private Color color;
	private int windowsPerRow;
	private Random rand;
	
	/**
	 * Constructs a new instance of Building which can be then painted
	 * @param leftX the x-coordinate of the left side of the building
	 * @param width the width of the building
	 * @param height the height of the building
	 * @param color the color of the building
	 * @param appletSize the size of the applet (for calculations involving height)
	 */
	public Building(int leftX, int width, int height, Color color, Dimension appletSize) {
		rand = new Random();
		int rightX = leftX + width;
		
		int appletHeight = appletSize.height;
		// Correct height to accomodate the coordinate system of the applet
		// (where y=0 is at the top)
		int correctedHeight = appletHeight - height;
		
		// Put in a coordinates array so 
		// we can do transformations (maybe)
		coordinates = new int[][]{
			{leftX, appletHeight},
			{rightX, appletHeight},
			{rightX, correctedHeight},
			{leftX, correctedHeight},
		};
		
		this.color = color;
	}
	
	/**
	 * Draws a building
	 * @param page the current graphics context
	 */
	public void draw(Graphics page) {
		// Save current color so we can reset the context after we're done
		Color c = page.getColor();
		
		// Make original points
		int[] xpoints = new int[4];
		int[] ypoints = new int[4];
		for (int i = 0; i < 4; i++) {
			xpoints[i] = coordinates[i][0];
			ypoints[i] = coordinates[i][1];
		}
		
		// Make it 3D - color will be darker behind the face of bldg
		page.setColor(this.color.darker());
		final int DEPTH = 25;
		for (int i = 0; i < 4; i++) {
			xpoints[i] -= DEPTH;
			ypoints[i] += DEPTH;
		}
		for (int i = 0; i < DEPTH; i++) {
			// If we're on the face of the building make the color lighter
			if (i == DEPTH - 1) {
				page.setColor(this.color);
			}
			Polygon polygon = new Polygon(xpoints, ypoints, 4);
			page.fillPolygon(polygon);	
			for (int j = 0; j < 4; j++) {
				xpoints[j] += 1;
				ypoints[j] += 1;
			}
		}
		this.drawWindows(page, xpoints, ypoints);
		page.setColor(c);
	}
	
	/**
	 * Draws 25x25 windows within the bounds of the provided coordinates
	 * @param page the current graphics context
	 * @param xpoints the x-coordinates of the boundaries in which the windows should be drawn
	 * @param ypoints the y-coordinates of the boundaries in which the windows should be drawn
	 */
	public void drawWindows(Graphics page, int[] xpoints, int[] ypoints) {
		// Subtract x coordinates to get width
		int width = Math.abs(xpoints[2] - xpoints[0]);
		// Subtract y coordinates to get height
		int height = Math.abs(ypoints[2] - ypoints[0]);
		
		// Windows are 25x25, plus 10x10 padding
		int windowsPerRow = width / 45;
		int windowRows = height / 45;
		int remainderLateral = width % 45;
		int remainderBase = height % 45;
		
		Color c = page.getColor();
		for (int i = 1; i < windowRows; i++) {
			int y = ypoints[0] - 45 * i - remainderBase;
			for (int j = 0; j < windowsPerRow; j++) {
				// Initial padding (10) plus remainder
				int x = 10 + remainderLateral / 2 + xpoints[0] + 45 * j;
				// Randomize whether the window is lighted or unlighted
				Color windowColor = rand.nextInt(2) == 1 ? Color.BLACK : Color.YELLOW;
				page.setColor(windowColor);
				page.fillRect(x, y, 25, 25);
			}
		}
		// Return color to original
		page.setColor(c);
	}
}
