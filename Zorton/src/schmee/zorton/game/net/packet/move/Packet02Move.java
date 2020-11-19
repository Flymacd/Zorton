package schmee.zorton.game.net.packet.move;

import schmee.zorton.game.net.GameClient;
import schmee.zorton.game.net.GameServer;
import schmee.zorton.game.net.packet.Packet;

public class Packet02Move extends Packet {

	private String username;
	private int xVel, yVel, id, e, cbid, x, y;
	
	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.xVel = Integer.parseInt(dataArray[1]);
		this.yVel = Integer.parseInt(dataArray[2]);
		this.id = Integer.parseInt(dataArray[3]);
		this.e = Integer.parseInt(dataArray[4]); // 0 = Cannon, 1 = PTank, 2 = CBall
		this.cbid = Integer.parseInt(dataArray[5]);
		this.x = Integer.parseInt(dataArray[6]);
		this.y = Integer.parseInt(dataArray[7]);
	}
	
	public Packet02Move(String username, int xVel, int yVel, int id, int e, int cbid, int x, int y) {
		super(02);
		this.username = username;
		this.xVel = xVel;
		this.yVel = yVel;
		this.id = id;
		this.e = e;
		this.cbid = cbid;
		this.x = x;
		this.y = y;
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
		return ("02" + this.username + "," + this.xVel + "," + this.yVel + "," + this.id + "," + this.e + "," + this.cbid + "," + this.x + "," + this.y).getBytes();
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
	
	public int getCbid() {
		return cbid;
	}

	public int getE() {
		return e;
	}
	
	public int getxVel() {
		return xVel;
	}
	
	public int getyVel() {
		return yVel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
