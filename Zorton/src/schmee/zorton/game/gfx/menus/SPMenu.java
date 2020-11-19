package schmee.zorton.game.gfx.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;

public class SPMenu {

	private Game game;
	private Button easy = new Button(300, 320, 100, 50, "Easy"), 
			medium = new Button(287, 390, 125, 50, "Medium"), 
			hard = new Button(300, 460, 100, 50, "Hard"), 
			zolai = new Button(287, 530, 125, 50, "Extreme"),
			back = new Button(300, 600, 100, 50, "Back");
	public Button[] buttons = {easy, medium, hard, zolai, back};
	
	public SPMenu(Game game) {
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Verdana", 1, 25);
		Font fntT = new Font("Verdana", 1, 50);
		g.setFont(fntT);
		FontMetrics fm = g.getFontMetrics();
	    int x = (Game.WIDTH - fm.stringWidth("SINGLEPLAYER")) / 2;
	    g.setColor(Color.BLACK);
	    g.drawString("SINGLEPLAYER", x + 2, 127);
	    g.setColor(Color.LIGHT_GRAY);
	    g.drawString("SINGLEPLAYER", x, 125);
	    g.setFont(fnt);
		for (Button b : buttons) {
			if (b.getString().equalsIgnoreCase("extreme")) {
				game.dg.drawGreyedButton(b, g);
			} else {
				game.dg.drawButton(b, g);
			}
		}
		
		game.dg.drawCenteredStringShadow("v" + Game.VERSION, 317, 657, g);
		g.setColor(Color.white);
		game.dg.drawCenteredString("v" + Game.VERSION, 315, 655, g);
	}
	
}
