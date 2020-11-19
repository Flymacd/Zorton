package schmee.zorton.game.net.packet.end;

import schmee.zorton.game.net.GameClient;
import schmee.zorton.game.net.GameServer;
import schmee.zorton.game.net.packet.Packet;

public class Packet04End extends Packet {

	private String username;
	private int id, end;
	
	public Packet04End(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.id = Integer.parseInt(dataArray[1]);
		this.end = Integer.parseInt(dataArray[2]);
	}
	
	public Packet04End(String username, int id, int end) {
		super(03);
		this.username = username;
		this.id = id;
		this.end = end;
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
		return ("03" + this.username + "," + this.id + "," + this.end).getBytes();
	}
	
	public int getEnd() {
		return end;
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

