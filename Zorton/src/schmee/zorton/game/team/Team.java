package schmee.zorton.game.team;

import java.awt.Graphics;
import java.util.ArrayList;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.cannons.Cannon;
import schmee.zorton.game.entities.cball.CBall;
import schmee.zorton.game.entities.ptank.PTank;

public class Team {
	
	private Cannon c;
	private PTank p;
	private ArrayList<CBall> cbs;
	private Game game;
	private int id;
	public boolean win = false, ready = false;
	
	public Team(Cannon c, PTank p, Game game, int id) {
		this.c = c;
		this.p = p;
		cbs = new ArrayList<CBall>();
		this.game = game;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cannon getC() {
		return c;
	}

	public void setC(Cannon c) {
		this.c = c;
	}

	public PTank getP() {
		return p;
	}

	public void setP(PTank p) {
		this.p = p;
	}

	public ArrayList<CBall> getCbs() {
		return cbs;
	}

	public void setCbs(ArrayList<CBall> cbs) {
		this.cbs = cbs;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public void render(Graphics g) {
		game.dg.drawCannon(c, g);
		game.dg.drawPTank(p, g);
		ArrayList<CBall> cbstr = new ArrayList<CBall>();
		for (CBall cb : cbs) { 
			if (cb.getY() > 0 && cb.getY() + cb.getHeight() < 880) {
				cbstr.add(cb);
			}
		}
		
		for (CBall cb : cbstr) {
			game.dg.drawCannonBall(cb, g);
		}
		
	}
	
	public void tick() {
		c.tick();
		p.tick();
		for (int i = 0; i < cbs.size(); i++) {
			CBall cb = cbs.get(i);
			if (cb.getY() > 0 && cb.getY() + cb.getHeight() < 880) {
				cb.tick();
			}
		}
	}
	
}
