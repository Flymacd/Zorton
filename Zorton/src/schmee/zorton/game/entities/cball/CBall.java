package schmee.zorton.game.entities.cball;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.Entity;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.enums.State;
import schmee.zorton.game.net.packet.end.Packet04End;
import schmee.zorton.game.team.Team;

public class CBall extends Entity {

	
	public CBall(int x, int y, int width, int height, int xVel, int yVel, Team team, EType e, Game game) {
		super(x, y, width, height, xVel, yVel, team, e, game);
	}

	@Override
	public void tick() {
		y += yVel;
		
		if (game.bottom == team) { // BOTTOM
			
			if (game.handler.isColliding(x, y, width, height, game.top.getC().getX(), game.top.getC().getY(), game.top.getC().getWidth(), game.top.getC().getHeight())) {
				game.colliding = true;
				game.top.getC().setDisabled(true);
				game.bottom.getCbs().remove(this);
				game.colliding = false;
			} else if (game.handler.isColliding(x, y, width, height, game.top.getP().getX(), game.top.getP().getY(), game.top.getP().getWidth(), game.top.getP().getHeight())) {
				game.colliding = true;
				game.gameOver = true;
				game.bottom.win = true;
				game.bottom.getCbs().remove(this);
				if (game.multiplayer) {
					int id = 0;
					if (!game.hosting) {
						id = 1;
					}
					Packet04End packet = new Packet04End(game.player.getUsername(), id, 1);
					packet.writeData(game.client);
				}
				game.state = State.end;
				game.colliding = false;
			}
			
			if (y + height <= 0) {
				game.bottom.getCbs().remove(this);
			} else if (y >= Game.HEIGHT) {
				game.bottom.getCbs().remove(this);
			}
		} else { // TOP
			
			if (game.handler.isColliding(x, y, width, height, game.bottom.getC().getX(), game.bottom.getC().getY(), game.bottom.getC().getWidth(), game.bottom.getC().getHeight())) {
				game.colliding = true;
				game.bottom.getC().setDisabled(true);
				game.top.getCbs().remove(this);
				game.colliding = false;
			} else if (game.handler.isColliding(x, y, width, height, game.bottom.getP().getX(), game.bottom.getP().getY(), game.bottom.getP().getWidth(), game.bottom.getP().getHeight())) {
				game.colliding = true;
				game.gameOver = true;
				game.top.win = true;
				if (game.multiplayer) {
					int id = 0;
					if (!game.hosting) {
						id = 1;
					}
					Packet04End packet = new Packet04End(game.player.getUsername(), id, 2);
					packet.writeData(game.client);
				}
				game.state = State.end;
				game.top.getCbs().remove(this);
				game.colliding = false;
			}
			
			if (y + height <= 0) {
				game.top.getCbs().remove(this);
			} else if (y >= Game.HEIGHT) {
				game.top.getCbs().remove(this);
			}
		}
		
	}

}
