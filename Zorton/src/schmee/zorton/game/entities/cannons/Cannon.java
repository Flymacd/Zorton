package schmee.zorton.game.entities.cannons;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.Entity;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.team.Team;

public class Cannon extends Entity {
	
	private boolean disabled = false;
	public boolean canFire = false;
	public long tick = 0;
	
	
	public Cannon(int x, int y, int width, int height, int xVel, int yVel, Team team, EType e, Game game) {
		super(x, y, width, height, xVel, yVel, team, e, game);
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		tick = System.currentTimeMillis();
		this.disabled = disabled;
	}

	@Override
	public void tick() {
		if (!disabled) {
			x += xVel;
			if (x <= 0) {
				xVel *= -1;
			} else if (x + width >= 710) {
				xVel *= -1;
			}
			
			if (!canFire) {
				if (System.currentTimeMillis() - tick >= 500) {
					canFire = true;
				}
			}
		} else {
			canFire = false;
			if (System.currentTimeMillis() - tick >= 1500) {
				canFire = true;
				disabled = false;
			}
		}
	}
	
}
