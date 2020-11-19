package schmee.zorton.game.input;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.cball.CBall;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.enums.State;
import schmee.zorton.game.input.textfield.TextField;
import schmee.zorton.game.net.packet.Packet.PacketTypes;
import schmee.zorton.game.net.packet.move.Packet02Move;

public class Keyboard implements KeyListener{
	
	private Game game;
	private boolean canEscape = false, ctrl = false, canTab = false;
	public boolean hoverMenu = false;
	public long tick = 0;
	
	public boolean spacebar = false;
	
	public String typeString = "";
	
	public Keyboard(Game game) {
		this.game = game;
		game.addKeyListener(this);
		game.setFocusTraversalKeysEnabled(false);
		tick = System.currentTimeMillis();
	}
	
	public void tick() {
		if (game.state == State.game) { 
			if (!canEscape) {
				if (System.currentTimeMillis() - tick >= 250) {
					canEscape = true;
				}
			}
			
			if (spacebar) {
				if (game.bottom.getC().canFire) {
					game.handler.shooting = true;
					int x1 = game.bottom.getC().getX() + game.bottom.getC().getWidth() / 2 - 5;
					int y1 = game.bottom.getC().getY() + game.bottom.getC().getHeight() / 2 - 25;
					CBall cb = new CBall(x1, y1, 10, 10, 0, -10, game.bottom, EType.cball, game);
					game.bottom.getCbs().add(cb);
					game.bottom.getC().tick = System.currentTimeMillis();
					game.bottom.getC().canFire = false;
					// MultiPlayer Shoot
					if (game.multiplayer) {
						int id = 0;
						if (!game.hosting) {
							id = 1;
						}
						Packet02Move packet = new Packet02Move(game.player.getUsername(), 0, -10, id, 2, game.bottom.getCbs().size(), cb.getX(), cb.getY());
						packet.type = PacketTypes.MOVE;
						packet.writeData(game.client);
					}
					game.handler.shooting = false;
				}
			}
		} else if (game.state == State.typing) {
			boolean selected = false;
			for (TextField t : game.handler.t.tfs) {
				if (t.isSelected()) {
					selected = true;
				}
			}
			
			if (!canTab) {
				if (System.currentTimeMillis() - tick >= 250) {
					canTab = true;
				}
			}
			
			if (!selected) {
				typeString = "";
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			ctrl = true;
		}
		if (game.state == State.game) { 
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				// Hover Menu
				if (canEscape) {
					hoverMenu = !hoverMenu;
				}
			}
			if (game.started) {
				// TODO: MultiPlayer Move
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (game.bottom.getP().getxVel() > 0) {
						game.bottom.getP().setxVel(game.bottom.getP().getxVel() * -1);
						// MultiPlayer Move
						if (game.multiplayer) {
							int id = 0;
							if (!game.hosting) {
								id = 1;
							}
							Packet02Move packet = new Packet02Move(game.player.getUsername(), game.bottom.getP().getxVel(), 0, id, 1, -1, game.bottom.getP().getX(), 0);
							packet.type = PacketTypes.MOVE;
							packet.writeData(game.client);
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (game.bottom.getP().getxVel() < 0) {
						game.bottom.getP().setxVel(game.bottom.getP().getxVel() * -1);
						// MultiPlayer Move
						if (game.multiplayer) {
							int id = 0;
							if (!game.hosting) {
								id = 1;
							}
							Packet02Move packet = new Packet02Move(game.player.getUsername(), game.bottom.getP().getxVel(), 0, id, 1, -1, game.bottom.getP().getX(), 0);
							packet.type = PacketTypes.MOVE;
							packet.writeData(game.client);
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					if (game.bottom.getC().getxVel() > 0) {
						game.bottom.getC().setxVel(game.bottom.getC().getxVel() * -1);
						// MultiPlayer Move
						if (game.multiplayer) {
							int id = 0;
							if (!game.hosting) {
								id = 1;
							}
							Packet02Move packet = new Packet02Move(game.player.getUsername(), game.bottom.getC().getxVel(), 0, id, 0, -1, game.bottom.getC().getX(), 0);
							packet.type = PacketTypes.MOVE;
							packet.writeData(game.client);
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					if (game.bottom.getC().getxVel() < 0) {
						game.bottom.getC().setxVel(game.bottom.getC().getxVel() * -1);
						// MultiPlayer Move
						if (game.multiplayer) {
							int id = 0;
							if (!game.hosting) {
								id = 1;
							}
							Packet02Move packet = new Packet02Move(game.player.getUsername(), game.bottom.getC().getxVel(), 0, id, 0, -1, game.bottom.getC().getX(), 0);
							packet.type = PacketTypes.MOVE;
							packet.writeData(game.client);
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					// Fire Cannon
					spacebar = true;
					if (game.bottom.getC().canFire) {
						game.handler.shooting = true;
						int x1 = game.bottom.getC().getX() + game.bottom.getC().getWidth() / 2 - 5;
						int y1 = game.bottom.getC().getY() + game.bottom.getC().getHeight() / 2 - 25;
						CBall cb = new CBall(x1, y1, 10, 10, 0, -10, game.bottom, EType.cball, game);
						game.bottom.getCbs().add(cb);
						game.bottom.getC().tick = System.currentTimeMillis();
						game.bottom.getC().canFire = false;
						// MultiPlayer Shoot
						if (game.multiplayer) {
							int id = 0;
							if (!game.hosting) {
								id = 1;
							}
							Packet02Move packet = new Packet02Move(game.player.getUsername(), 0, -10, id, 2, game.bottom.getCbs().size(), cb.getX(), cb.getY());
							packet.type = PacketTypes.MOVE;
							packet.writeData(game.client);
						}
						game.handler.shooting = false;
					}
				}
			} else if (!game.started) {
				if (!game.multiplayer) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						game.started = true;
						
						int xVelCT = 3, xVelCB = 3, xVelPT = 3, xVelPB = 3;
						int rct = (int) (Math.random() * 3), rcb = (int) (Math.random() * 3), rpt = (int) (Math.random() * 3), rpb = (int) (Math.random() * 3);
						if (rct >= 1) {
							xVelCT *= -1;
						}
						if (rcb >= 1) {
							xVelCB *= -1;
						}
						if (rpt >= 1) {
							xVelPT *= -1;
						}
						if (rpb >= 1) {
							xVelPB *= -1;
						}
						
						
						game.top.getC().setxVel(xVelCT);
						game.top.getP().setxVel(xVelPT);
						game.bottom.getC().setxVel(xVelCB);
						game.bottom.getP().setxVel(xVelPB);
						game.tick = System.currentTimeMillis();
						game.bottom.getC().tick = System.currentTimeMillis();
					}
				}
			}
		} else if (game.state == State.typing) {
			
			if (e.getKeyCode() == KeyEvent.VK_V && ctrl) {
				Clipboard systemClipboard = getSystemClipboard();
		        DataFlavor dataFlavor = DataFlavor.stringFlavor;

		        if (systemClipboard.isDataFlavorAvailable(dataFlavor)) {
		            Object text = "";
					try {
						text = systemClipboard.getData(dataFlavor);
					} catch (UnsupportedFlavorException | IOException e1) {
						e1.printStackTrace();
					}
		            typeString += text;
		        }
			} else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
				ctrl = true;
			} else if (e.getKeyCode() == KeyEvent.VK_TAB) {
				if (!game.hosting) {
					if (canTab) {
						if (game.handler.t.tfs[0].isSelected() && !game.handler.t.tfs[1].isSelected()) {
							typeString = "";
							game.handler.t.tfs[0].setSelected(false);
							game.handler.t.tfs[1].setSelected(true);
							canTab = false;
							tick = System.currentTimeMillis();
						} else if (game.handler.t.tfs[1].isSelected() && !game.handler.t.tfs[0].isSelected()) {
							typeString = "";
							game.handler.t.tfs[1].setSelected(false);
							game.handler.t.tfs[0].setSelected(true);
							canTab = false;
							tick = System.currentTimeMillis();
						}
					}
				}
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				typeString += " ";
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				// ACCEPT
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
			} else if (e.getKeyCode() == KeyEvent.VK_PERIOD) { 
				typeString += ".";
			} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				typeString += "";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
				typeString += "0";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				typeString += "1";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				typeString += "2";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				typeString += "3";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
				typeString += "4";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
				typeString += "5";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
				typeString += "6";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
				typeString += "7";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				typeString += "8";
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
				typeString += "9";
			} else if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
				typeString += KeyEvent.getKeyText(e.getKeyCode());
			} else {
				removeLastChar();
			}
			
			
			
			for (TextField t : game.handler.t.tfs) {
				if (game.hosting) {
					if (t == game.handler.t.tfs[0]) {
						if (t.isSelected()) {
							t.setString(typeString);
						}
					}
				} else {
					if (t == game.handler.t.tfs[0]) {
						if (t.isSelected()) {
							t.setString(typeString);
						}
					} else if (t == game.handler.t.tfs[1]) {
						if (t.isSelected()) {
							t.setString(typeString);
						}
					}
				}
			}
		}
		
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			ctrl = false;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spacebar = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	private void removeLastChar() {
		if (typeString != null && typeString.length() > 0) {
			typeString = typeString.substring(0, typeString.length() - 1);
		}
	}
	
	private static Clipboard getSystemClipboard() {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

        return systemClipboard;
    }
}
