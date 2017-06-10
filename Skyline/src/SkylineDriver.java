/**
 * Skyline Project: Driver
 * This class runs the code to generate our skyline
 * @author 18nleung
 * @version 20 April 2017
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SkylineDriver extends Applet {
	private BufferedImage lala;
	private Clip stars;
	
	/**
	 * Runs on applet startup, used to load assets
	 */
	public void init() {
		// Load Image
		try {
			URL path = SkylineDriver.class.getResource("lalaland.png");
		    lala = ImageIO.read(new File(path.getFile()));
		} catch (IOException e) {
			System.out.println(e);
		}
		playSong();
	}
	
	/**
	 * Plays the special song, synchronized so only one
	 * instance of the playing can occur at any time
	 */
	public synchronized void playSong() {
		// From http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java?rq=1
		new Thread(new Runnable() {
			public void run() {
				try {
					stars = AudioSystem.getClip();
					AudioInputStream audioIn =
						AudioSystem.getAudioInputStream(
							SkylineDriver.class.getResourceAsStream("CityOfStars.wav")
						);
					stars.open(audioIn);
					stars.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
			}
		}).start();
	}
	
	/**
	 * Draws the background of the skyline (including moon, etc)
	 * @param page the current Graphics context
	 */
	public void drawBg(Graphics page) {
		// Nice dark indigo
		Color bgColor = new Color(15, 0, 45);
		setBackground(bgColor);
		
		Color c = page.getColor();
		
		int width = this.getSize().width;
		int height = this.getSize().height;
		// Make a crescent moon
		page.setColor(Color.YELLOW);
		page.fillOval(width - 200, 30, 150, 150);
		page.setColor(bgColor);
		page.fillOval(width - 250, 50, 150, 150);
		
		// Make 500 random stars
		Random rand = new Random();
		page.setColor(Color.WHITE);
		for (int i = 0; i < 500; i++) {
			int starX = rand.nextInt(width);
			int starY = rand.nextInt(height);
			page.fillRect(starX, starY, 5, 5);
		}
		page.setColor(c);
	}
	
	/**
	 * Draws the title of the skyline
	 * @param page the current graphics context
	 */
	public void drawTitle(Graphics page) {
		Color c = page.getColor();
		page.setColor(Color.WHITE);
		Font subtitles = new Font("Franklin Gothic Medium", Font.BOLD, 20);
		Font titles = new Font("Broadway", Font.BOLD, 80);
		page.setFont(subtitles);
		page.drawString("Original Motion Picture Soundtrack", 30, 70);
		page.setFont(titles);
		page.drawString("La La Land", 30, 140);
		page.setFont(subtitles);
		page.drawString("\"City of Stars\"", 30, 170);
		page.setColor(c);
		
		// Draw image after title is done
		// Random x position each time
		int height = this.getSize().height;
		int width = this.getSize().width;
		Random rand = new Random();
		int imgX = rand.nextInt(width - 200) + 50;
		page.drawImage(lala, imgX, height - 400, this);
	}
	
	/**
	 * Paints the skyline
	 * @param page the current Graphics context
	 */
	public void paint(Graphics page) {
		Dimension appletSize = this.getSize();
		int height = appletSize.height;
		
		this.drawBg(page);
		
		Random rand = new Random();

		// If we're past the width of the applet, this becomes true
		boolean shouldStop = false;
		int prevRightX = 0;
		for (int i = 0; i < 10 && !shouldStop; i++) {
			// Random number between 100 and 200
			int nextWidth = rand.nextInt(100) + 100;
			// Random number up to 80% of window height
			int heightRange = (int) Math.round(height * 0.2);
			int heightMax = (int) Math.round(height * 0.7);
			int nextHeight = rand.nextInt(heightRange) + heightMax;
			
			// Extra addend is for padding
			int nextLeftX = prevRightX + 40;
			
			if (nextLeftX + nextWidth > appletSize.width - 10) {
				shouldStop = true;
			} else {
				// From http://stackoverflow.com/questions/4246351/creating-random-colour-in-java
				float r = rand.nextFloat();
				float g = rand.nextFloat();
				float b = rand.nextFloat();
				Color randomColor = new Color(r, g, b);
				// 50 + so there is a padding of 50 on the left side of each building
				Building building = new Building(
								nextLeftX,
								nextWidth,
								nextHeight,
								randomColor,
								appletSize
							);
				building.draw(page);
				prevRightX = nextLeftX + nextWidth;
			}
		}
		// Draw title last so it's on top
		this.drawTitle(page);
	}
}
