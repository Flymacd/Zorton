package schmee.zorton.game.gfx.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;

public class MainMenu {

	private Game game;
	
	// Buttons
	private Button sp = new Button(250, 320, 200, 50, "SinglePlayer"), 
			mp = new Button(250, 390, 200, 50, "MultiPlayer"), 
			options = new Button(275, 460, 150, 50, "Options"), 
			quit = new Button(300, 530, 100, 50, "Quit");
	public Button[] buttons = {sp, mp, options, quit};
	
	public MainMenu(Game game) {
		this.game = game;
	}
	
	public void tick() {
		
	}

	public void render(Graphics g) {
		Font fnt = new Font("Verdana", 1, 25);
		Font fntT = new Font("Verdana", 1, 50);
		g.setFont(fntT);
		FontMetrics fm = g.getFontMetrics();
	    int x = (Game.WIDTH - fm.stringWidth("ZORTON")) / 2;
	    g.setColor(Color.BLACK);
	    g.drawString("ZORTON", x + 2, 127);
	    g.setColor(Color.LIGHT_GRAY);
	    g.drawString("ZORTON", x, 125);
	    g.setFont(fnt);
		for (Button b : buttons) {
			game.dg.drawButton(b, g);
		}
		
		game.dg.drawCenteredStringShadow("v" + Game.VERSION, 317, 657, g);
		g.setColor(Color.white);
		game.dg.drawCenteredString("v" + Game.VERSION, 315, 655, g);
	}
}
