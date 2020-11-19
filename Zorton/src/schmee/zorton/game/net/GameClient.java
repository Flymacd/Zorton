package schmee.zorton.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import schmee.zorton.game.Game;
import schmee.zorton.game.entities.cball.CBall;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.net.packet.Packet;
import schmee.zorton.game.net.packet.Packet.PacketTypes;
import schmee.zorton.game.net.packet.disconnect.Packet01Disconnect;
import schmee.zorton.game.net.packet.end.Packet04End;
import schmee.zorton.game.net.packet.login.Packet00Login;
import schmee.zorton.game.net.packet.move.Packet02Move;
import schmee.zorton.game.net.packet.ready.Packet03Ready;
import schmee.zorton.game.net.packet.update.Packet05Update;
import schmee.zorton.game.team.mp.PlayerMP;

public class GameClient extends Thread {
	
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;
	public List<PlayerMP> players = new ArrayList<PlayerMP>();
	
	public GameClient(Game game, String ipAddress) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(); // TODO: Add Port inside parenthesis
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	//catch these hands
	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			
//			String msg = new String(packet.getData());
//			System.out.println("SERVER > " + msg);
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
				PlayerMP player = new PlayerMP(((Packet00Login)packet).getUsername(), address, port, ((Packet00Login) packet).getId(), game.top);
				handleLogin((Packet00Login)packet, address, port, player);
				break;
				
			case MOVE:
				packet = new Packet02Move(data);
				handleMove((Packet02Move)packet);
				break;
				
			case READY:
				packet = new Packet03Ready(data);
				handleReady((Packet03Ready)packet);
				break;
				
			case END:
				packet = new Packet04End(data);
				handleEnd((Packet04End)packet);
				break;
				
			case UPDATE:
				packet = new Packet05Update(data);
				handleUpdate((Packet05Update)packet);
				break;
				
			case DISCONNECT:
				packet = new Packet01Disconnect(data);
				System.out.println("[" + address.getHostAddress() + " : " + port + "] " + ((Packet01Disconnect)packet).getUsername() + " has left the game.");
				//game.level.removePlayerMP(((Packet01Disconnect)packet).getUsername());
				break;
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleUpdate(Packet05Update packet) {
		if (!packet.getUsername().equalsIgnoreCase(game.player.getUsername())) {
			if (packet.getId() == 0) {
				game.top.getC().setX(packet.getX());
			} else if (packet.getId() == 1) {
				game.top.getP().setX(packet.getX());
			} else if (packet.getId() == 2) {
				for (int i = 0; i < game.top.getCbs().size(); i++) {
					if (game.top.getCbs().size() == 0) {
						return;
					} else {
						CBall cb = game.top.getCbs().get(packet.getCbid());
						cb.setX(packet.getX());
					}
				}
			}
		}
	}
	
	private void handleReady(Packet03Ready packet) {
		boolean ready = false;
		if (packet.getReady() == 1) {
			ready = true;
		}
		
		
		if (!game.hosting) {
			if (packet.getId() == 0) {
				game.top.ready = ready;
			}
		} else {
			if (packet.getId() == 1) {
				game.top.ready = ready;
			}
		}
		
	}
	
	private void handleEnd(Packet04End packet) {
		if ((packet.getId() == 1 && game.hosting) || (packet.getId() == 0 && !game.hosting)) {
			if (packet.getEnd() == 2) {
				game.bottom.win = true;
				game.gameOver = true;
				game.state = schmee.zorton.game.enums.State.end;
			} else if (packet.getEnd() == 1) {
				game.top.win = true;
				game.gameOver = true;
				game.state = schmee.zorton.game.enums.State.end;
			}
		} else if ((packet.getId() == 0 && game.hosting) || (packet.getId() == 1 && !game.hosting)) {
			if (packet.getEnd() == 1) {
				game.bottom.win = true;
				game.gameOver = true;
				game.state = schmee.zorton.game.enums.State.end;
			} else if (packet.getEnd() == 2) {
				game.top.win = true;
				game.gameOver = true;
				game.state = schmee.zorton.game.enums.State.end;
			}
		}
	}
	
	private void handleLogin(Packet00Login packet, InetAddress address, int port, PlayerMP player) {
		players.add(player);
		System.out.println("[" + address.getHostAddress() + " : " + port + "] " + packet.getUsername() + " has joined the game.");
	}
	
	private void handleMove(Packet02Move packet) {
		if ((packet.getId() == 1 && game.hosting) || (packet.getId() == 0 && !game.hosting)) {
			if (packet.getE() == 0) { // Cannon
				game.top.getC().setX(packet.getX());
				game.top.getC().setxVel(packet.getxVel());
			} else if (packet.getE() == 1) { // PTank
				game.top.getP().setX(packet.getX());
				game.top.getP().setxVel(packet.getxVel());
			} else if (packet.getE() == 2) { // CBall
				game.handler.shooting = true;
				int x1 = packet.getX();
				int y1 = game.top.getC().getY() + game.top.getC().getHeight() / 2 + 25;
				game.top.getCbs().add(new CBall(x1, y1, 10, 10, 0, packet.getyVel() * -1, game.top, EType.cball, game));
				game.handler.shooting = false;
			}
		}
		//game.level.movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getNumSteps(), packet.isMoving(), packet.getMovingDir());
	}

}
