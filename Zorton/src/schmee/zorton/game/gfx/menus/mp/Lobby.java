package schmee.zorton.game.gfx.menus.mp;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;
import schmee.zorton.game.team.mp.PlayerMP;

public class Lobby {
	
	
	private Game game;
	private Button ready = new Button(300, 460, 100, 50, "Ready"), 
			leave = new Button(300, 530, 100, 50, "Leave"); 
	public Button[] buttons = {ready, leave};
	
	public Lobby(Game game) {
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Verdana", 1, 25);
		Font fntT = new Font("Verdana", 1, 50);
		g.setFont(fntT);
		FontMetrics fm = g.getFontMetrics();
	    int x = (Game.WIDTH - fm.stringWidth("LOBBY")) / 2;
	    g.setColor(Color.BLACK);
	    g.drawString("LOBBY", x + 2, 127);
	    g.setColor(Color.LIGHT_GRAY);
	    g.drawString("LOBBY", x, 125);
	    g.setFont(fnt);
		for (Button b : buttons) {
			game.dg.drawButton(b, g);
		}
		
		// CHECKMARKS
		g.setFont(new Font("sans-serif", 1, 40));
		int botx = 525, boty = 255, topx = 525, topy = 325;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		g.setColor(Color.black);
		g.drawString("\u2713", topx + 2, topy + 2);
		g.drawString("\u2713", botx + 2, boty + 2);
		if (game.bottom.ready) {
			g.setColor(game.dg.getColor(34, 139, 34));
		} else {
			g.setColor(game.dg.getColor(155, 24, 24));
		}
		g.drawString("\u2713", botx, boty);
		
		if (game.top.ready) {
			g.setColor(game.dg.getColor(34, 139, 34));
		} else {
			g.setColor(game.dg.getColor(155, 24, 24));
		}
		g.drawString("\u2713", topx, topy);
		// Names
		if (game.hosting) {
			for (int i = 0; i < game.server.players.size(); i++) {
				PlayerMP p = game.server.players.get(i);
				if (game.bottom.ready) {
					if (i == 0) {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), game.dg.getColor(34, 139, 34), g);
					} else {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), Color.gray, g);
					}
				} else if (game.top.ready) {
					if (i == 1) {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), game.dg.getColor(34, 139, 34), g);
					} else {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), Color.gray, g);
					}
				} else {
					game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), Color.gray, g);
				}
				
			}
		} else if (!game.hosting) {
			for (int i = 0; i < game.client.players.size(); i++) {
				PlayerMP p = game.client.players.get(i);
				if (game.bottom.ready) {
					if (i == 0) {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), game.dg.getColor(34, 139, 34), g);
					} else {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), Color.gray, g);
					}
				} else if (game.top.ready) {
					if (i == 1) {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), game.dg.getColor(34, 139, 34), g);
					} else {
						game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), Color.gray, g);
					}
				} else {
					game.dg.drawString(p.getUsername(), 225, 275 + (i * 70), Color.gray, g);
				}
			}
		}
		
		g.setFont(fnt);
		
		game.dg.drawCenteredStringShadow("v" + Game.VERSION, 317, 657, g);
		g.setColor(Color.white);
		game.dg.drawCenteredString("v" + Game.VERSION, 315, 655, g);
	}

}
