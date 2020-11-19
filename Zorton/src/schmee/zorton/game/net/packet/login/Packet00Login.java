package schmee.zorton.game.net.packet.login;

import schmee.zorton.game.net.GameClient;
import schmee.zorton.game.net.GameServer;
import schmee.zorton.game.net.packet.Packet;

public class Packet00Login extends Packet {

	private String username;
	private int id;
	
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.id = Integer.parseInt(dataArray[1]);
	}
	
	public Packet00Login(String username, int id) {
		super(00);
		this.username = username;
		this.id = id;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("00" + this.username + "," + this.id).getBytes();
	}
	
	public String getUsername() {
		return this.username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

