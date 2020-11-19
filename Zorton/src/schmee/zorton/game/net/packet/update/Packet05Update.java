package schmee.zorton.game.net.packet.update;

import schmee.zorton.game.net.GameClient;
import schmee.zorton.game.net.GameServer;
import schmee.zorton.game.net.packet.Packet;

public class Packet05Update extends Packet {

	private String username;
	private int id, x, y, cbid;
	
	public Packet05Update(byte[] data) {
		super(05);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.id = Integer.parseInt(dataArray[1]);
		this.x = Integer.parseInt(dataArray[2]);
		this.y = Integer.parseInt(dataArray[3]);
	}
	
	public Packet05Update(String username, int id, int x, int y, int cbid) {
		super(05);
		this.username = username;
		this.id = id;
		this.x = x;
		this.y = y;
		this.cbid = cbid;
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
		return ("05" + this.username + "," + this.id + "," + this.x + "," + this.y + "," + this.cbid).getBytes();
	}
	
	public int getCbid() {
		return cbid;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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

