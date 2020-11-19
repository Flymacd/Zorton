package schmee.zorton.game.team.mp;

import java.net.InetAddress;

import schmee.zorton.game.team.Team;

public class PlayerMP {
	
	public InetAddress ipAddress;
	public int port, id;
	private String username;
	public Team team;
	
	public PlayerMP(String username, InetAddress ipAddress, int port, int id, Team team) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.id = id;
		this.username = username;
		this.team = team;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
