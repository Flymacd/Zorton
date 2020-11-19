package schmee.zorton.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import schmee.zorton.game.Game;
import schmee.zorton.game.net.packet.Packet;
import schmee.zorton.game.net.packet.Packet.PacketTypes;
import schmee.zorton.game.net.packet.disconnect.Packet01Disconnect;
import schmee.zorton.game.net.packet.end.Packet04End;
import schmee.zorton.game.net.packet.login.Packet00Login;
import schmee.zorton.game.net.packet.move.Packet02Move;
import schmee.zorton.game.net.packet.ready.Packet03Ready;
import schmee.zorton.game.net.packet.update.Packet05Update;
import schmee.zorton.game.team.mp.PlayerMP;


public class GameServer extends Thread {
	
	private DatagramSocket socket;
	private Game game;
	private int serverPort = 1331;
	public List<PlayerMP> players = new ArrayList<PlayerMP>();
	
	
	public GameServer(Game game) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(serverPort); // TODO: Add Port inside paranthesis
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			if (socket != null) {
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			}			
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String msg = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(msg.substring(0, 2));
		Packet packet = null;
		switch (type) {
			default:
			case INVALID:
				break;
				
			case LOGIN:
				packet = new Packet00Login(data);
				System.out.println("[" + address.getHostAddress() + " : " + port + "] " + ((Packet00Login)packet).getUsername() + " has connected.");
				PlayerMP player = null;
				if (((Packet00Login)packet).getId() == 1) {
					player = new PlayerMP(((Packet00Login)packet).getUsername(), address, port, ((Packet00Login) packet).getId(), game.bottom);
				} else {
					player = new PlayerMP(((Packet00Login)packet).getUsername(), address, port, ((Packet00Login) packet).getId(), game.top);
				}
				
				if (player != null) {
					this.addConnection(player, ((Packet00Login)packet));
				}
				break;
				
			case MOVE:
				packet = new Packet02Move(data);
				this.handleMove((Packet02Move)packet);
				break;
				
			case READY:
				packet = new Packet03Ready(data);
				this.handleReady((Packet03Ready)packet);
				break;
				
			case END:
				packet = new Packet04End(data);
				this.handleEnd((Packet04End)packet);
				break;
				
			case UPDATE:
				packet = new Packet05Update(data);
				this.handleUpdate((Packet05Update)packet);
				break;
				
			case DISCONNECT:
				packet = new Packet01Disconnect(data);
				System.out.println("[" + address.getHostAddress() + " : " + port + "] " + ((Packet01Disconnect)packet).getUsername() + " has disconnected.");
				this.removeConnection((Packet01Disconnect)packet);
				break;
		}
	}

	

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		
		for (PlayerMP p : this.players) {
			
			if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
				if (p.ipAddress == null) {
					p.ipAddress = player.ipAddress;
				}
				
				if (p.port == -1) {
					p.port = player.port;
				}
				
				alreadyConnected = true;
			} else {
				sendDataToAllClients(packet.getData());
				
				for (PlayerMP pm : this.players) {
					packet = new Packet00Login(pm.getUsername(), pm.id);
					sendData(packet.getData(), player.ipAddress, player.port);
				}
				break;
			}
		}
		
		if (!alreadyConnected) {
			this.players.add(player);
		}
		
	}
	
	private void handleUpdate(Packet05Update packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			sendDataToAllClients(packet.getData());
		}
	}
	
	public void removeConnection(Packet01Disconnect packet) {
		this.players.remove(getPlayerMPIndex(packet.getUsername()));
		if (this.players.size() == 0) {
			this.socket = null;
		}
		sendDataToAllClients(packet.getData());
	}
	
	private void handleReady(Packet03Ready packet) {
		sendDataToAllClients(packet.getData());
	}
	
	private void handleEnd(Packet04End packet) {
		sendDataToAllClients(packet.getData());
	}
	
	public PlayerMP getPlayerMP(String username) {
		for (PlayerMP p : this.players) {
			if (p.getUsername().equalsIgnoreCase(username)) {
				return p;
			}
		}
		return null;
	}
	
	public int getPlayerMPIndex(String username) {
		int index = 0;
		for (PlayerMP p : this.players) {
			if (p.getUsername().equalsIgnoreCase(username)) {
				break;
			}
			index++;
		}
		return index;
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : players) {
			sendData(data, p.ipAddress, p.port);
		}
	}
	
	private void handleMove(Packet02Move packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			sendDataToAllClients(packet.getData());
		}
	}
	
	public void closeServer() {
		socket.close();
	}
}
