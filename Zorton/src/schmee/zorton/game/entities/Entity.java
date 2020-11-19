package schmee.zorton.game.entities;

import schmee.zorton.game.Game;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.team.Team;

public abstract class Entity {
	
	protected int x, y, width, height, xVel, yVel;
	protected Team team;
	protected EType e;
	protected Game game;
	
	
	public Entity(int x, int y, int width, int height, int xVel, int yVel, Team team, EType e, Game game) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xVel = xVel;
		this.yVel = yVel;
		this.team = team;
		this.e = e;
		this.game = game;
	}
	
	public EType getE() {
		return e;
	}

	public void setE(EType e) {
		this.e = e;
	}

	public int getxVel() {
		return xVel;
	}

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	public int getyVel() {
		return yVel;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public abstract void tick();
	
}
