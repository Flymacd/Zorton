package schmee.zorton.game.ai;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.cball.CBall;
import schmee.zorton.game.enums.AI;
import schmee.zorton.game.enums.EType;

public class AIThinking {

	private Game game;
	
	private long tick, tickS;
	private int timeLC = 0, timeRC = 0, timeLP = 0, timeRP = 0;
	
	public AIThinking(Game game) {
		this.game = game;
		tick = System.currentTimeMillis();
		tickS = System.currentTimeMillis();
	}

	public void tick() {
		if (game.ai == AI.easy) {
			easy();
		} else if (game.ai == AI.medium) {
			medium();
		} else if (game.ai == AI.hard) {
			hard();
		}
	}
	
	public void easy() {
		if (System.currentTimeMillis() - tick > 500) {
			int rc = (int) (Math.random() * 3);
			int rp = (int) (Math.random() * 3);
			if (rc == 1) {
				moveC();
			}
			
			if (rp == 1) {
				moveP();
			}
			tick = System.currentTimeMillis();
		}
		
		if (System.currentTimeMillis() - tickS > 500) {
			int rs = (int) (Math.random() * 8);
			if (rs == 3) {
				shoot();
			}
			tickS = System.currentTimeMillis();
		}
	}
	
	public void medium() {
		if (System.currentTimeMillis() - tick > 250) {
			int rc = (int) (Math.random() * 5);
			int rp = (int) (Math.random() * 5);
			
			if (rc > 2) {
				if (rc == 4) {
					moveC();
				} else {
					if (!game.bottom.getCbs().isEmpty()) {
						moveC();
					}
				}
			}
			
			if (rp > 2) {
				if (rp == 4) {
					moveP();
				} else {
					if (!game.bottom.getCbs().isEmpty()) {
						moveP();
					}
				}
			}
			tick = System.currentTimeMillis();
		}
		
		if (System.currentTimeMillis() - tickS > 500) {
			int rs = (int) (Math.random() * 4);
			if (rs < 2) {
				shoot();
			}
			tickS = System.currentTimeMillis();
		}
	}
	
	public void hard() {
		if (System.currentTimeMillis() - tick > 100) {
			int rc = (int) (Math.random() * 5);
			int rp = (int) (Math.random() * 5);
			
			if (rc > 1) {
				int times = 0;
				if (!game.bottom.getCbs().isEmpty()) {
					for (CBall cb : game.bottom.getCbs()) {
						if (cb.getY() >= 60 && cb.getY() <= 600 && Math.abs(cb.getX() - game.top.getC().getX()) <= 75) {
							if (cb.getX() < game.top.getC().getX() + game.top.getC().getWidth() / 2 && game.top.getC().getxVel() < 0) {
								if (game.top.getC().getX() >= 50) {
									times++;
									timeLC = 0;
									break;
								}
							} else if (cb.getX() > game.top.getC().getX() + game.top.getC().getWidth() / 2 && game.top.getC().getxVel() > 0) {
								if (game.top.getC().getX() + game.top.getC().getWidth() <= Game.WIDTH - 50) {
									times++;
									timeRC = 0;
									break;
								}
							}
						}
					}
					if (times > 0) {
						moveC();
					}
				}
			} else {
				if (game.top.getC().getX() > game.bottom.getP().getX() && game.top.getC().getxVel() > 0) { // Changing from Positive to Negative
					if (game.top.getC().getX() >= 50 && timeLC < 1) {
						moveC();
						timeLC++;
						timeRC = 0;
					}
				} else if (game.top.getC().getX() < game.bottom.getP().getX() && game.top.getC().getxVel() < 0) { // Changing from Negative to Positive
					if (game.top.getC().getX() + game.top.getC().getWidth() <= Game.HEIGHT - 50 && timeRC < 1) {
						moveC();
						timeRC++;
						timeLC = 0;
					}
				}
			}
			
			if (rp > 1) {
				int times = 0;
				if (!game.bottom.getCbs().isEmpty()) {
					for (CBall cb : game.bottom.getCbs()) {
						if (cb.getY() >= 20 && cb.getY() <= 500 && Math.abs(cb.getX() - game.top.getP().getX()) <= 75) {
							if (cb.getX() < game.top.getP().getX() + game.top.getP().getWidth() / 2 && game.top.getP().getxVel() < 0) {
								if (game.top.getP().getX() >= 50) {
									times++;
									timeLP = 0;
									break;
								}
							} else if (cb.getX() > game.top.getP().getX() + game.top.getP().getWidth() / 2 && game.top.getP().getxVel() > 0) {
								if (game.top.getP().getX() + game.top.getP().getWidth() <= Game.WIDTH - 50) {
									times++;
									timeRP = 0;
									break;
								}
							}
						}
					}
					if (times > 0) {
						moveP();
					}
				}
				
			} else {
				if (game.top.getP().getX() > game.bottom.getC().getX() && game.top.getP().getxVel() < 0) { // Changing from Negative to Positive
					if (game.top.getP().getX() + game.top.getP().getWidth() <= Game.WIDTH - 50 && timeRP < 1) {
						moveP();
						timeRP++;
						timeLP = 0;
					}
				} else if (game.top.getP().getX() < game.bottom.getC().getX() && game.top.getP().getxVel() > 0) { // Changing from Positive to Negative
					if (game.top.getP().getX() >= 50 && timeLP < 1) {
						moveP();
						timeLP++;
						timeRP = 0;
					}
				}
			}
			
			tick = System.currentTimeMillis();
		}
		
		if (game.top.getC().canFire) {
			shoot();
		}
	}
	
	public void moveC() {
		game.top.getC().setxVel(game.top.getC().getxVel() * -1);
	}
	
	public void moveP() {
		game.top.getP().setxVel(game.top.getP().getxVel() * -1);
	}
	
	public void shoot() {
		if (game.top.getC().canFire) {
			int x1 = game.top.getC().getX() + game.top.getC().getWidth() / 2 - 5;
			int y1 = game.top.getC().getY() + game.top.getC().getHeight() / 2 + 25;
			CBall cb = new CBall(x1, y1, 10, 10, 0, 10, game.top, EType.cball, game);
			game.top.getCbs().add(cb);
			game.top.getC().tick = System.currentTimeMillis();
			game.top.getC().canFire = false;
		}
	}
}
