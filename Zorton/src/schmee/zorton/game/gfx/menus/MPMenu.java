package schmee.zorton.game.gfx.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;

public class MPMenu {
	
	private Game game;
	private Button host = new Button(300, 320, 100, 50, "Host"), 
			join = new Button(300, 390, 100, 50, "Join"), 
			back = new Button(300, 460, 100, 50, "Back"); 
	public Button[] buttons = {host, join, back};
	
	public MPMenu(Game game) {
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Verdana", 1, 25);
		Font fntT = new Font("Verdana", 1, 50);
		g.setFont(fntT);
		FontMetrics fm = g.getFontMetrics();
	    int x = (Game.WIDTH - fm.stringWidth("MULTIPLAYER")) / 2;
	    g.setColor(Color.BLACK);
	    g.drawString("MULTIPLAYER", x + 2, 127);
	    g.setColor(Color.LIGHT_GRAY);
	    g.drawString("MULTIPLAYER", x, 125);
	    g.setFont(fnt);
		for (Button b : buttons) {
			game.dg.drawButton(b, g);
		}
		
		game.dg.drawCenteredStringShadow("v" + Game.VERSION, 317, 657, g);
		g.setColor(Color.white);
		game.dg.drawCenteredString("v" + Game.VERSION, 315, 655, g);
	}

}
