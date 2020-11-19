package schmee.zorton.game.gfx.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;

public class EndGame {

	private Game game;
	
	public Button rematch = new Button(265, 460, 175, 50, "Rematch");
	public Button mainMenu = new Button(265, 530, 175, 50, "Main Menu");
	
	public EndGame(Game game) {
		this.game = game;
	}
	
	@SuppressWarnings({ "unlikely-arg-type", "rawtypes", "unchecked" })
	public void tick() {
		game.started = false;
		game.bothready = false;
		List<Integer> toRemoveTop = new ArrayList();
		List<Integer> toRemoveBottom = new ArrayList();
		for (int cb = 0; cb < game.top.getCbs().size(); cb++) {
			toRemoveTop.add(cb);
		}
		for (int cb = 0; cb < game.bottom.getCbs().size(); cb++) {
			toRemoveBottom.add(cb);
		}
		
		game.top.getCbs().removeAll(toRemoveTop);
		game.bottom.getCbs().removeAll(toRemoveBottom);
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Verdana", 1, 25);
		Font fntT = new Font("Verdana", 1, 50);
		g.setFont(fntT);
		
	    
		if (game.top.win) {
			FontMetrics fm = g.getFontMetrics();
		    int x = (Game.WIDTH - fm.stringWidth("YOU LOST!")) / 2;
			g.setColor(Color.BLACK);
		    g.drawString("YOU LOST!", x + 2, 127);
		    g.setColor(Color.LIGHT_GRAY);
		    g.drawString("YOU LOST!", x, 125);
		} else if (game.bottom.win) {
			FontMetrics fm = g.getFontMetrics();
		    int x = (Game.WIDTH - fm.stringWidth("YOU WON!")) / 2;
			g.setColor(Color.BLACK);
		    g.drawString("YOU WON!", x + 2, 127);
		    g.setColor(Color.LIGHT_GRAY);
		    g.drawString("YOU WON!", x, 125);
		}
		g.setFont(fnt);
		game.dg.drawButton(mainMenu, g);
		game.dg.drawButton(rematch, g);
	}
}
