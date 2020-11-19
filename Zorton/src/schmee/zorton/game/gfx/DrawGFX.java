package schmee.zorton.game.gfx;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.cannons.Cannon;
import schmee.zorton.game.entities.cball.CBall;
import schmee.zorton.game.entities.ptank.PTank;
import schmee.zorton.game.input.buttons.Button;
import schmee.zorton.game.input.textfield.TextField;

public class DrawGFX {
	
	private Game game;
	
	public DrawGFX(Game game) {
		this.game = game;
	}
	
	public void drawCannon(Cannon c, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(c.getX() - 1, c.getY() - 1, c.getWidth() + 2, c.getHeight() + 2);
		
		
		if (game.top.getId() == c.getTeam().getId()) { // TOP
			g.setColor(Color.red);
			g.fillOval(c.getX() + c.getWidth() / 2 - 5, c.getY() + c.getHeight() / 2, 10, 25);
			g.fillOval(c.getX(), c.getY(), c.getWidth(), c.getHeight());
			g.setColor(Color.BLACK);
			g.drawOval(c.getX() + c.getWidth() / 2 - 6, c.getY() + c.getHeight() / 2, 11, 26);
		} else { // BOTTOM
			g.setColor(Color.blue);
			g.fillOval(c.getX() + c.getWidth() / 2 - 5, c.getY() - c.getHeight() / 2, 10, 25);
			g.fillOval(c.getX(), c.getY(), c.getWidth(), c.getHeight());
			g.setColor(Color.BLACK);
			g.drawOval(c.getX() + c.getWidth() / 2 - 6, c.getY() - c.getHeight() / 2, 11, 26);
		}
	}
	
	public void drawTextField(TextField t, Graphics g) {
		g.setColor(Color.black);
		g.drawRect(t.getX() - 1, t.getY() - 1, t.getWidth() + 2, t.getHeight() + 2);
		drawCenteredStringShadow(t, g);
		
		if (t.isHovered()) {
			g.setColor(getColor(34, 139, 34));
		} else if (t.isSelected()) {
			g.setColor(getColor(34, 34, 139));
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		
		drawCenteredString(t, g);
		g.drawRect(t.getX(), t.getY(), t.getWidth(), t.getHeight());
	}
	
	public void drawCenteredString(TextField t, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
	    int x1 = t.getX() + (t.getWidth() - fm.stringWidth(t.getString())) / 2 - 1;
	    int y1 = t.getY() + (fm.getAscent() + (t.getHeight() - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(t.getString(), x1, y1);
	}
	
	public void drawCenteredStringShadow(TextField t, Graphics g) {
		g.setColor(Color.BLACK);
		FontMetrics fm = g.getFontMetrics();
	    int x1 = t.getX() + (t.getWidth() - fm.stringWidth(t.getString())) / 2 - 1;
	    int y1 = t.getY() + (fm.getAscent() + (t.getHeight() - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(t.getString(), x1 + 2, y1 + 2);
	}
	
	public void drawPTank(PTank p, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(p.getX() - 1, p.getY() - 1, p.getWidth() + 2, p.getHeight() + 2);
		
		if (game.top.getId() == p.getTeam().getId()) { // TOP
			g.setColor(Color.red);
		} else { // BOTTOM
			g.setColor(Color.blue);
		}

		g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
		
		g.setColor(Color.BLACK);
		g.drawString("P", p.getX() + p.getWidth() / 2 - g.getFontMetrics().stringWidth("P") / 2, p.getY() + p.getHeight() / 2 + g.getFontMetrics().getHeight() / 3);
	}
	
	public void drawCannonBall(CBall cb, Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(cb.getX() - 1, cb.getY() - 1, cb.getWidth() + 2, cb.getHeight() + 2);
		
		
		if (game.top.getId() == cb.getTeam().getId()) { // TOP
			g.setColor(Color.red);
		} else { // BOTTOM
			g.setColor(Color.blue);
		}
		
		g.fillOval(cb.getX(), cb.getY(), cb.getWidth(), cb.getHeight());
	}
	
	
	public void drawString(String s, int x, int y, Color color, Graphics g) {
		g.setColor(Color.black);
		g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2 + 2, y - g.getFontMetrics().getHeight() / 3 + 2);
		
		g.setColor(color);
		g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y - g.getFontMetrics().getHeight() / 3);
	}
	
	public void drawButton(Button b, Graphics g) {
		if (b.isHovered()) {
			g.setColor(getColor(34, 139, 34));
		} else {
			g.setColor(Color.black);
		}
		
		g.fillRect(b.getX() - 1, b.getY() - 1, b.getWidth() + 2, b.getHeight() + 2);
		
		g.setColor(Color.white);
		g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		
		if (b.getString() != null) {
			drawCenteredStringShadow(b, g);
			
			if (b.isHovered()) {
				g.setColor(getColor(34, 139, 34));
			} else {
				g.setColor(Color.LIGHT_GRAY);
			}
			
			drawCenteredString(b, g);
		}
	}
	
	public void drawGreyedButton(Button b, Graphics g) {
		g.setColor(Color.black);
		g.fillRect(b.getX() - 1, b.getY() - 1, b.getWidth() + 2, b.getHeight() + 2);
		
		g.setColor(Color.gray);
		g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		
		if (b.getString() != null) {
			drawCenteredStringShadow(b, g);
			
			g.setColor(Color.DARK_GRAY);
			
			drawCenteredString(b, g);
		}
	}
	
	public void drawCenteredString(String s, int x, int y, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
	    int x1 = x + (Game.WIDTH - fm.stringWidth(s)) / 2 - 1;
	    int y1 = y + (fm.getAscent() + (400 - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(s, x1, y1);
	}
	
	public void drawCenteredStringShadow(String s, int x, int y, Graphics g) {
		g.setColor(Color.BLACK);
		FontMetrics fm = g.getFontMetrics();
	    int x1 = x + (Game.WIDTH - fm.stringWidth(s)) / 2 - 1;
	    int y1 = y + (fm.getAscent() + (400 - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(s, x1, y1);
	}
	
	public void drawCenteredString(Button b, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
	    int x = b.getX() + (b.getWidth() - fm.stringWidth(b.getString())) / 2 - 1;
	    int y = b.getY() + (fm.getAscent() + (b.getHeight() - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(b.getString(), x, y);
	}
	
	public void drawCenteredStringShadow(Button b, Graphics g) {
		g.setColor(Color.black);
		FontMetrics fm = g.getFontMetrics();
	    int x = b.getX() + (b.getWidth() - fm.stringWidth(b.getString())) / 2 - 1;
	    int y = b.getY() + (fm.getAscent() + (b.getHeight() - (fm.getAscent() + fm.getDescent())) / 2) - 1;
	    g.drawString(b.getString(), x + 2, y + 2);
	}
	
	public Color getColor(int r, int g, int b) {
		return new Color(r, g, b);
	}

	
	public void drawTracks(Cannon c, PTank p, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, c.getY() + 6, Game.WIDTH + 60, c.getHeight() - 12);
		g.fillRect(0, p.getY() + 6, Game.WIDTH + 60, p.getHeight() - 12);
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(-10, c.getY() + 6, Game.WIDTH + 100, c.getHeight() - 12);
		g.drawRect(-10, p.getY() + 6, Game.WIDTH + 100, p.getHeight() - 12);
	}
}
