package schmee.zorton.game.entities.ptank;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.Entity;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.team.Team;

public class PTank extends Entity {

	private boolean alive = true;
	
	public PTank(int x, int y, int width, int height, int xVel, int yVel, Team team, EType e, Game game) {
		super(x, y, width, height, xVel, yVel, team, e, game);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	@Override
	public void tick() {
		x += xVel;
		
		if (x <= 0) {
			xVel *= -1;
		} else if (x + width >= 710) {
			xVel *= -1;
		}
	}

}
