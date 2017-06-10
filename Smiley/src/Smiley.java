/**
 * @author 18nleung
 * 
 * Smiley Face Project
 * 27 Febuary 2017
 * An applet which displays a smiley face.
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Smiley extends Applet {
	// Generated code
	private static final long serialVersionUID = 4115815051186766767L;
	
	public int height;
	public int width;
	
	public void init() {
		setBackground(Color.DARK_GRAY);
	}

	public void paint (Graphics g) {
		Dimension appSize = this.getSize();
		height = (int) appSize.getHeight();
		width = (int) appSize.getWidth();
		makeSun(g);
		makeEarth(g);
	}
	
	public void makeSun(Graphics g) {
		int radiusX = (width * 4 / 5) / 2;
		int radiusY = (height * 4 / 5) / 2;
		g.setColor(Color.YELLOW);
		// Subtract the radii to center the circle
		g.fillOval(width / 2 - radiusX, height / 2 - radiusY, radiusX * 2, radiusY * 2);
		
		// Eyes
		int leftEyeX = width / 2 - radiusX * 3 / 5;
		int rightEyeX = width / 2 + radiusX * 3 / 5;
		g.setColor(Color.BLACK);
		g.fillOval(leftEyeX, height * 1 / 3, radiusX / 5, radiusY / 5);
		g.fillOval(rightEyeX, height * 1 / 3, radiusX / 5, radiusY / 5);
		
		// Mouth
		g.drawArc(width / 2 - radiusX * 1 / 5, height / 2 + radiusY / 4, width / 5, height / 5, -30, -120);
	}
	
	public void makeEarth(Graphics g) {
		int radiusX = (width * 2 / 5) / 2;
		int radiusY = (height * 2 / 5) / 2;
		g.setColor(Color.BLUE);
		int posX = width / 2 + radiusX / 2;
		int posY = height / 2;
		g.fillOval(posX, posY, radiusX * 2, radiusY * 2);
		
		// Eyes
		int leftEyeX = posX + radiusX * 3 / 5;
		int rightEyeX = posX + radiusX * 6 / 5;
		g.setColor(Color.BLACK);
		g.fillOval(leftEyeX, height * 7 / 12, radiusX / 5, radiusY / 5);
		g.fillOval(rightEyeX, height * 7 / 12, radiusX / 5, radiusY / 5);
		
		// Mouth
		g.drawArc(width / 2 + radiusX, height / 2 + radiusY / 2, width / 5, height / 5, -30, -120);
	}
}

