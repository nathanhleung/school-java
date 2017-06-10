//Bobby Buhring
//2.27.16
//To utiilize the applet and graphics feature to create a dynamic smiley face

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class ExampleSmiley extends Applet{

	public void paint (Graphics page) {
		
		Dimension appSize = this.getSize();
		int x = (int) appSize.getWidth();
		int y = (int) appSize.getHeight(); 
		int mid = x/2; 
		int top = y/2; 
		//head
		page.setColor(Color.yellow);
		page.fillOval(x/10, y/10, 4*x/5, 4*y/5);
		//eyes
		page.setColor(Color.black);
		page.fillOval( x/2, y/5, 18*x/100, 18*y/100 );
		page.fillOval(2*x/9, y/2-(y/17)-(y/8), 18*x/100, 18*y/100);
		//lines
		page.drawLine(x/10, y/2, 4*x/16, y/2-(y/8) + y/15 );
		page.drawLine(2*x/9 - (x/14) + 22*x/100, y/3 + y/18 , x/2 + x/100, 2*y/7 + y/24);
		page.drawLine( 65*x/100, 25*y/100, 74*x/100, 18*y/100);
		page.drawArc(x/3 , 34*y/100, x/3, y/3, 230, 120);
		page.drawLine(38*x/100, 66*y/100, 41*x/100, 61*y/100);
		page.drawLine(62*x/100, 107*y/200, 70*x/100, 107*y/200);
	}

}
