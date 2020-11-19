package schmee.zorton.game.net.packet.ready;

import schmee.zorton.game.net.GameClient;
import schmee.zorton.game.net.GameServer;
import schmee.zorton.game.net.packet.Packet;

public class Packet03Ready extends Packet {

	private String username;
	private int id, ready;
	
	public Packet03Ready(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.id = Integer.parseInt(dataArray[1]);
		this.ready = Integer.parseInt(dataArray[2]);
	}
	
	public Packet03Ready(String username, int id, int ready) {
		super(03);
		this.username = username;
		this.id = id;
		this.ready = ready;
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
		return ("03" + this.username + "," + this.id + "," + this.ready).getBytes();
	}
	
	public int getReady() {
		return ready;
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

