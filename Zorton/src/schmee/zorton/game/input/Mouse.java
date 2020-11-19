package schmee.zorton.game.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.cannons.Cannon;
import schmee.zorton.game.entities.cball.CBall;
import schmee.zorton.game.entities.ptank.PTank;
import schmee.zorton.game.enums.AI;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.enums.State;
import schmee.zorton.game.input.buttons.Button;
import schmee.zorton.game.input.textfield.TextField;
import schmee.zorton.game.net.packet.Packet.PacketTypes;
import schmee.zorton.game.net.packet.disconnect.Packet01Disconnect;
import schmee.zorton.game.net.packet.ready.Packet03Ready;
import schmee.zorton.game.team.Team;


public class Mouse extends MouseAdapter {
	  
	private Game game;
  
	// Declaring
	public int mouseX = 0, mouseY = 0;
	public boolean canClick = false;
	
	public long tick = 0;
	
	
  
	public Mouse(Game game) {
		this.game = game;
		game.addMouseListener(this);
		tick = System.currentTimeMillis();
	}
  
	public void tick() {
		// Mouse Cursor
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		Point c = game.getLocationOnScreen();
		mouseX = (int) b.getX() - (int) c.getX();
		mouseY = (int) b.getY() - (int) c.getY();
		// Mouse Cursor End
	
		if (!canClick) {
			if (System.currentTimeMillis() - tick >= 250) {
				canClick = true;
			}
		}
		
		
		// Hover Buttons
		if (game.state == State.menu) {
			for (Button b1 : game.handler.mm.buttons) {
				if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					b1.setHovered(true);
				} else {
					b1.setHovered(false);
				}
			}
		} else if (game.state == State.options) {
			for (Button b1 : game.handler.om.buttons) {
				if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					b1.setHovered(true);
				} else {
					b1.setHovered(false);
				}
			}
		} else if (game.state == State.sp) {
			for (Button b1 : game.handler.sp.buttons) {
				if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					b1.setHovered(true);
				} else {
					b1.setHovered(false);
				}
			}
		} else if (game.state == State.mp) {
			for (Button b1 : game.handler.mp.buttons) {
				if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					b1.setHovered(true);
				} else {
					b1.setHovered(false);
				}
			}
		} else if (game.state == State.end) {
			Button b1 = game.handler.eg.mainMenu;
			if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
				b1.setHovered(true);
			} else {
				b1.setHovered(false);
			}
			
			b1 = game.handler.eg.rematch;
			if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
				b1.setHovered(true);
			} else {
				b1.setHovered(false);
			}
		} else if (game.state == State.game) {
			if (game.key.hoverMenu) {
				Button b1 = game.handler.hm.mainMenu;
				if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					b1.setHovered(true);
				} else {
					b1.setHovered(false);
				}
			}
		} else if (game.state == State.lobby) {
			for (Button b1 : game.handler.l.buttons) {
				if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					b1.setHovered(true);
				} else {
					b1.setHovered(false);
				}
			}
		} else if (game.state == State.typing) {
			for (Button b1 : game.handler.t.buttons) {
				if (mouseOver(mouseX, mouseY, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					b1.setHovered(true);
				} else {
					b1.setHovered(false);
				}
			}
			for (TextField t : game.handler.t.tfs) {
				if (mouseOver(mouseX, mouseY, t.getX(), t.getY(), t.getWidth(), t.getHeight())) {
					t.setHovered(true);
				} else {
					t.setHovered(false);
				}
			}
		}
	}
  
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		// Click Buttons
		if (canClick) {
			if (game.state == State.menu) {
				for (Button b1 : game.handler.mm.buttons) {
					if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
						if (b1 == game.handler.mm.buttons[0]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.state = State.sp;
						} else if (b1 == game.handler.mm.buttons[1]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.multiplayer = true;
							game.state = State.mp;
						} else if (b1 == game.handler.mm.buttons[2]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.state = State.options;
						} else {
							System.exit(0);
						}
					}
				}
			} else if (game.state == State.options) {
				for (Button b1 : game.handler.om.buttons) {
					if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
						if (b1 == game.handler.om.buttons[0]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.state = State.menu;
						} else if (b1 == game.handler.om.buttons[1]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.options.showFps = !game.options.showFps;
						}
					}
				}
			} else if (game.state == State.sp) {
				for (Button b1 : game.handler.sp.buttons) {
					if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
						if (b1 == game.handler.sp.buttons[0]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.ai = AI.easy;
							game.state = State.game;
						} else if (b1 == game.handler.sp.buttons[1]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.ai = AI.medium;
							game.state = State.game;
						} else if (b1 == game.handler.sp.buttons[2]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.ai = AI.hard;
							game.state = State.game;
						} else if (b1 == game.handler.sp.buttons[3]) {
							// TODO : ZOLAI
							// tick = System.currentTimeMillis();
							// canClick = false;
							// game.ai = AI.zolai;
							// game.state = State.game;
						} else {
							tick = System.currentTimeMillis();
							canClick = false;
							game.state = State.menu;
						}
					}
				}
			} else if (game.state == State.mp) {
				for (Button b1 : game.handler.mp.buttons) {
					if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
						if (b1 == game.handler.mp.buttons[0]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.hosting = true;
							game.state = State.typing;
						} else if (b1 == game.handler.mp.buttons[1]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.state = State.typing;
						} else {
							tick = System.currentTimeMillis();
							canClick = false;
							game.multiplayer = false;
							game.state = State.menu;
						}
					}
				}
			} else if (game.state == State.end) {
				Button b1 = game.handler.eg.mainMenu;
				if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					tick = System.currentTimeMillis();
					canClick = false;
					game.started = false;
					game.gameOver = false;
					game.bottom = new Team(new Cannon(Game.WIDTH / 2 - 30 / 2, 710, 30, 30, 0, 0, game.bottom, EType.cannon, game), new PTank(Game.WIDTH / 2 - 60 / 2, 820, 60, 30, 0, 0, game.bottom, EType.ptank, game), game, 1);
					game.top = new Team(new Cannon(Game.WIDTH / 2 - 30 / 2, 120, 30, 30, 0, 0, game.top, EType.cannon, game), new PTank(Game.WIDTH / 2 - 60 / 2, 20, 60, 30, 0, 0, game.top, EType.ptank, game), game, 2);
					game.key.hoverMenu = false;
					if (game.multiplayer) {
						Packet01Disconnect packet = new Packet01Disconnect(game.player.getUsername());
						packet.type = PacketTypes.DISCONNECT;
						packet.writeData(game.client);
						game.server = null;
						game.client = null;
						game.bothready = false;
						game.multiplayer = false;
					}
					game.state = State.menu;
				}
				
				b1 = game.handler.eg.rematch;
				if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
					tick = System.currentTimeMillis();
					canClick = false;
					game.started = false;
					game.gameOver = false;
					boolean ready = false;
					if (game.multiplayer) {
						if (checkIfReady()) {
							ready = true;
						}
					}
					game.bottom = new Team(new Cannon(Game.WIDTH / 2 - 30 / 2, 710, 30, 30, 0, 0, game.bottom, EType.cannon, game), new PTank(Game.WIDTH / 2 - 60 / 2, 820, 60, 30, 0, 0, game.bottom, EType.ptank, game), game, 1);
					game.top = new Team(new Cannon(Game.WIDTH / 2 - 30 / 2, 120, 30, 30, 0, 0, game.top, EType.cannon, game), new PTank(Game.WIDTH / 2 - 60 / 2, 20, 60, 30, 0, 0, game.top, EType.ptank, game), game, 2);
					
					if (ready) {
						game.top.ready = true;
					}
					
					game.key.hoverMenu = false;
					if (game.multiplayer) {
						game.state = State.lobby;
					} else {
						game.state = State.sp;
					}
				}
			} else if (game.state == State.game) {
				if (game.key.hoverMenu) {
					Button b1 = game.handler.hm.mainMenu;
					if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
						tick = System.currentTimeMillis();
						canClick = false;
						game.started = false;
						game.bottom = new Team(new Cannon(Game.WIDTH / 2 - 30 / 2, 710, 30, 30, 0, 0, game.bottom, EType.cannon, game), new PTank(Game.WIDTH / 2 - 60 / 2, 820, 60, 30, 0, 0, game.bottom, EType.ptank, game), game, 1);
						game.top = new Team(new Cannon(Game.WIDTH / 2 - 30 / 2, 120, 30, 30, 0, 0, game.top, EType.cannon, game), new PTank(Game.WIDTH / 2 - 60 / 2, 20, 60, 30, 0, 0, game.top, EType.ptank, game), game, 2);
						game.key.hoverMenu = false;
						if (game.multiplayer) {
							Packet01Disconnect packet = new Packet01Disconnect(game.player.getUsername());
							packet.type = PacketTypes.DISCONNECT;
							packet.writeData(game.client);
							game.server = null;
							game.client = null;
							game.bothready = false;
							game.multiplayer = false;
						}
						game.state = State.menu;
					}
				}
			} else if (game.state == State.lobby) {
				for (Button b1 : game.handler.l.buttons) {
					if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
						if (b1 == game.handler.l.buttons[0]) {
							tick = System.currentTimeMillis();
							canClick = false;
							game.bottom.ready = !game.bottom.ready;
							// Multiplayer Ready
							int ready = 0;
							if (game.bottom.ready) {
								ready = 1;
							}
							int id = 0;
							if (!game.hosting) {
								id = 1;
							}
							Packet03Ready packet = new Packet03Ready(game.player.getUsername(), id, ready);
							packet.type = PacketTypes.READY;
							removeAllCBs();
							packet.writeData(game.client);
						} else {
							tick = System.currentTimeMillis();
							canClick = false;
							game.multiplayer = false;
							game.bottom.ready = false;
							Packet01Disconnect packet = new Packet01Disconnect(game.player.getUsername());
							packet.type = PacketTypes.DISCONNECT;
							packet.writeData(game.client);
							if (game.hosting) {
								game.server.closeServer();
							}
							game.hosting = false;
							game.server = null;
							game.client = null;
							game.bothready = false;
							game.state = State.menu;
						}
					}
				}
			} else if (game.state == State.typing) {
				for (TextField t : game.handler.t.tfs) {
					if (mouseOver(mx, my, t.getX(), t.getY(), t.getWidth(), t.getHeight())) {
						if (game.hosting) {
							if (t == game.handler.t.tfs[0]) {
								game.key.typeString = "";
								t.setSelected(!t.isSelected());
								if (t.isSelected()) {
									t.setString("");
								}
							}
						} else {
							if (t == game.handler.t.tfs[0]) {
								game.handler.t.tfs[1].setSelected(false);
								game.key.typeString = "";
								t.setSelected(!t.isSelected());
								if (t.isSelected()) {
									t.setString("");
								}
							} else {
								game.handler.t.tfs[0].setSelected(false);
								game.key.typeString = "";
								t.setSelected(!t.isSelected());
								if (t.isSelected()) {
									t.setString("");
								}
							}
						}
					}
				}
				for (Button b1 : game.handler.t.buttons) {
					if (mouseOver(mx, my, b1.getX(), b1.getY(), b1.getWidth(), b1.getHeight())) {
						if (b1 == game.handler.t.buttons[0]) {
							tick = System.currentTimeMillis();
							canClick = false;
							if (game.hosting) {
								String s = game.handler.t.tfs[0].getString();
								for (TextField t : game.handler.t.tfs) {
									t.setSelected(false);
								}
								game.host(s);
							} else if (!game.hosting) {
								String s = game.handler.t.tfs[0].getString();
								String s2 = game.handler.t.tfs[1].getString();
								for (TextField t : game.handler.t.tfs) {
									t.setSelected(false);
								}
								game.join(s, s2);
							}
						} else {
							tick = System.currentTimeMillis();
							canClick = false;
							game.multiplayer = false;
							game.hosting = false;
							game.bothready = false;
							game.state = State.mp;
						}
					}
				}
			}
		}
	}
	
	public boolean checkIfReady() {
		if (game.top.ready) {
			return true;
		}
		return false;
	}

	public void mouseReleased(MouseEvent e) {

	}
	
  
	public void removeAllCBs() {
		for (CBall cb : game.bottom.getCbs()) {
			game.bottom.getCbs().remove(cb);
		}
		
		for (CBall cb : game.top.getCbs()) {
			game.top.getCbs().remove(cb);
		}
	}
	
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else return false;
		} else return false;
	}
}
// FLYNN IS BIG GAY