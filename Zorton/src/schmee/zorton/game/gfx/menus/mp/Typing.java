package schmee.zorton.game.gfx.menus.mp;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;
import schmee.zorton.game.input.textfield.TextField;

public class Typing {
	
	private Game game;
	private Button ready = new Button(300, 460, 100, 50, "Host"), 
			back = new Button(300, 530, 100, 50, "Back"); 
	public Button[] buttons = {ready, back};
	
	private TextField name = new TextField(205, 300, 300, 50, "NAME"),
			ip = new TextField(205, 370, 300, 50, "IP");
	public TextField[] tfs = {name, ip};
	
	public Typing(Game game) {
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Verdana", 1, 25);
		Font fntT = new Font("Verdana", 1, 50);
		g.setFont(fntT);
		
		
		if (game.hosting) {
			FontMetrics fm = g.getFontMetrics();
		    int x = (Game.WIDTH - fm.stringWidth("ENTER A NAME")) / 2;
		    g.setColor(Color.BLACK);
		    g.drawString("ENTER A NAME", x + 2, 127);
		    g.setColor(Color.LIGHT_GRAY);
		    g.drawString("ENTER A NAME", x, 125);
		    g.setFont(fnt);
		    // NAME
		    game.dg.drawTextField(name, g);
		    ready.setString("Host");
		    ready.setX(300);
		    ready.setWidth(100);
		} else if (!game.hosting) {
			fntT = new Font("Verdana", 1, 45);
			g.setFont(fntT);
			FontMetrics fm = g.getFontMetrics();
			int x = (Game.WIDTH - fm.stringWidth("ENTER CONNECTION INFO")) / 2;
		    g.setColor(Color.BLACK);
		    g.drawString("ENTER CONNECTION INFO", x + 2, 127);
		    g.setColor(Color.LIGHT_GRAY);
		    g.drawString("ENTER CONNECTION INFO", x, 125);
		    g.setFont(fnt);
		    // IP AND NAME
		    for (TextField t : tfs) {
		    	game.dg.drawTextField(t, g);
		    }
		    ready.setString("Join");
		}
		
		for (Button b : buttons) {
			game.dg.drawButton(b, g);
		}
		
		game.dg.drawCenteredStringShadow("v" + Game.VERSION, 317, 657, g);
		g.setColor(Color.white);
		game.dg.drawCenteredString("v" + Game.VERSION, 315, 655, g);
	}

}
