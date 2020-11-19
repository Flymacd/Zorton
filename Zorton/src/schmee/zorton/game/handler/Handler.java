package schmee.zorton.game.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.cball.CBall;
import schmee.zorton.game.enums.State;
import schmee.zorton.game.gfx.menus.EndGame;
import schmee.zorton.game.gfx.menus.HoverMenu;
import schmee.zorton.game.gfx.menus.MPMenu;
import schmee.zorton.game.gfx.menus.MainMenu;
import schmee.zorton.game.gfx.menus.OptionsMenu;
import schmee.zorton.game.gfx.menus.SPMenu;
import schmee.zorton.game.gfx.menus.mp.Lobby;
import schmee.zorton.game.gfx.menus.mp.Typing;

public class Handler {
	
	private Game game;
	
	public MainMenu mm;
	public EndGame eg;
	public OptionsMenu om;
	public SPMenu sp;
	public MPMenu mp;
	public HoverMenu hm;
	public Lobby l;
	public Typing t;
	
	public long tick = 0, uTick = 0;
	
	public boolean shooting = false;
	
	private String toSay = "5";
	
	public Handler(Game game) {
		this.game = game;
		mm = new MainMenu(game);
		eg = new EndGame(game);
		om = new OptionsMenu(game);
		sp = new SPMenu(game);
		mp = new MPMenu(game);
		hm = new HoverMenu(game);
		l = new Lobby(game);
		t = new Typing(game);
		uTick = System.currentTimeMillis();
	}

	public void tick() {
		if (game.state == State.game) { 
			if (game.started) {
				game.top.tick();
				game.bottom.tick();
				game.top.ready = false;
				game.bottom.ready = false;
				ballCheck();
				if (!game.multiplayer) {
					game.ait.tick();
				}
			} else {
				if (game.multiplayer) {
					if (System.currentTimeMillis() - tick >= 5500) {
						game.started = true;
						game.top.getC().setxVel(3);
						game.top.getP().setxVel(3);
						game.bottom.getC().setxVel(3);
						game.bottom.getP().setxVel(3);
						game.top.ready = false;
						game.bottom.ready = false;
						game.bothready = false;
						toSay = "5";
					} else if (System.currentTimeMillis() - tick >= 5000) {
						toSay = "Start!";
					} else if (System.currentTimeMillis() - tick >= 4000) {
						toSay = "1";
					} else if (System.currentTimeMillis() - tick >= 3000) {
						toSay = "2";
					} else if (System.currentTimeMillis() - tick >= 2000) {
						toSay = "3";
					} else if (System.currentTimeMillis() - tick >= 1000) {
						toSay = "4";
					} else if (System.currentTimeMillis() - tick >= 0) {
						toSay = "5";
					}
				}
			}
		} else if (game.state == State.end) {
			eg.tick();
		} else if (game.state == State.menu) {
			mm.tick();
		} else if (game.state == State.options) {
			om.tick();
		} else if (game.state == State.sp) {
			sp.tick();
		} else if (game.state == State.mp) {
			mp.tick();
		}
	}
	
	public void render(Graphics g) {
		if (game.state == State.game) {
			game.dg.drawTracks(game.top.getC(), game.top.getP(), g);
			game.dg.drawTracks(game.bottom.getC(), game.bottom.getP(), g);
			game.top.render(g);
			game.bottom.render(g);
			hm.render(g);
			if (!game.started && game.multiplayer) {
				Font fnt = new Font("Verdana", 1, 25);
				Font fntT = new Font("Verdana", 1, 50);
				g.setFont(fntT);
				FontMetrics fm = g.getFontMetrics();
			    int x = (Game.WIDTH - fm.stringWidth(toSay)) / 2;
			    g.setColor(Color.BLACK);
			    g.drawString(toSay, x + 2, 402);
			    g.setColor(Color.LIGHT_GRAY);
			    g.drawString(toSay, x, 400);
			    g.setFont(fnt);
			} else if (!game.started && !game.multiplayer) {
				toSay = "Press \"Space\" to Start";
				Font fnt = new Font("Verdana", 1, 25);
				Font fntT = new Font("Verdana", 1, 50);
				g.setFont(fntT);
				FontMetrics fm = g.getFontMetrics();
			    int x = (Game.WIDTH - fm.stringWidth(toSay)) / 2;
			    g.setColor(Color.BLACK);
			    g.drawString(toSay, x + 2, 402);
			    g.setColor(Color.LIGHT_GRAY);
			    g.drawString(toSay, x, 400);
			    g.setFont(fnt);
			}
		} else if (game.state == State.end) {
			eg.render(g);
		} else if (game.state == State.menu) {
			mm.render(g);
		} else if (game.state == State.options) {
			om.render(g);
		} else if (game.state == State.sp) {
			sp.render(g);
		} else if (game.state == State.mp) {
			mp.render(g);
		} else if (game.state == State.lobby) {
			l.render(g);
		} else if (game.state == State.typing) {
			t.render(g);
		}
	}
	
	public boolean isColliding(int x, int y, int width, int height, int nx, int ny, int nwidth, int nheight) {
		if (x >= nx && x + width <= nx + nwidth) {
			if (y >= ny && y + height <= ny + nheight) {
				return true;
			}
		}
		
		return false;
	}
	
	public void ballCheck() {
		for (CBall cb : game.top.getCbs()) {
			if (cb.getY() + cb.getHeight() < 0 && cb.getY() >= Game.HEIGHT) {
				game.top.getCbs().remove(cb);
			}
		}
		
		for (CBall cb : game.bottom.getCbs()) {
			if (cb.getY() + cb.getHeight() < 0 && cb.getY() >= Game.HEIGHT) {
				game.bottom.getCbs().remove(cb);
			}
		}
	}
}
