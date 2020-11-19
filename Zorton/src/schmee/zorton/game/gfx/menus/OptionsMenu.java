package schmee.zorton.game.gfx.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;

public class OptionsMenu {

	private Game game;
	private Button back = new Button(300, 530, 100, 50, "Back"),
			showFps = new Button(150, 175, 150, 50, "Show FPS");
	public Button[] buttons = {back, showFps};
	
	public OptionsMenu(Game game) {
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Verdana", 1, 25);
		Font fntT = new Font("Verdana", 1, 50);
		g.setFont(fntT);
		FontMetrics fm = g.getFontMetrics();
	    int x = (Game.WIDTH - fm.stringWidth("OPTIONS")) / 2;
	    g.setColor(Color.BLACK);
	    g.drawString("OPTIONS", x + 2, 127);
	    g.setColor(Color.LIGHT_GRAY);
	    g.drawString("OPTIONS", x, 125);
	    g.setFont(fnt);
		for (Button b : buttons) {
			game.dg.drawButton(b, g);
		}
		
		
		g.setColor(Color.red);
		game.dg.drawCenteredString(String.valueOf(game.options.showFps).toUpperCase(), 125, 0, g);
		
		game.dg.drawCenteredStringShadow("v" + Game.VERSION, 317, 657, g);
		g.setColor(Color.white);
		game.dg.drawCenteredString("v" + Game.VERSION, 315, 655, g);
	}
	
}
