package schmee.zorton.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import schmee.zorton.game.ai.AIThinking;
import schmee.zorton.game.entities.cannons.Cannon;
import schmee.zorton.game.entities.ptank.PTank;
import schmee.zorton.game.enums.AI;
import schmee.zorton.game.enums.EType;
import schmee.zorton.game.enums.State;
import schmee.zorton.game.gfx.DrawGFX;
import schmee.zorton.game.handler.Handler;
import schmee.zorton.game.input.Keyboard;
import schmee.zorton.game.input.Mouse;
import schmee.zorton.game.net.GameClient;
import schmee.zorton.game.net.GameServer;
import schmee.zorton.game.net.packet.Packet.PacketTypes;
import schmee.zorton.game.net.packet.login.Packet00Login;
import schmee.zorton.game.options.Options;
import schmee.zorton.game.team.Team;
import schmee.zorton.game.team.mp.PlayerMP;


@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 700, HEIGHT = 860;
	public static final double VERSION = 0.03;
	public static final String NAME = "Zorton " + " | " + "v" + VERSION;
	public static Game game;
	
	
	public JFrame frame;
	
	public boolean running = false;
	
	public boolean started = false, gameOver = true, colliding = false, multiplayer = false, hosting = false, bothready = false;
	
	public Team top, bottom;
	
	// MultiPlayer
	public GameServer server;
	public GameClient client;
	
	public PlayerMP player;
	
	public long tick = 0;
	
	// Game State
	public State state = State.menu;
	
	// AI Level
	public AI ai = AI.easy;
	public AIThinking ait;
	
	// Handler
	public Handler handler;
	
	// DrawGFX
	public DrawGFX dg;
	
	// Mouse
	public Mouse mouse;
	
	// Keyboard
	public Keyboard key;
	
	// Options
	public Options options;
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		
		frame.requestFocus();
		
		// Initialize
		init();
	}
	
	public void init() {
		bottom = new Team(new Cannon(WIDTH / 2 - 30 / 2, 710, 30, 30, 0, 0, bottom, EType.cannon, game), new PTank(WIDTH / 2 - 60 / 2, 820, 60, 30, 0, 0, bottom, EType.ptank, game), this, 1);
		top = new Team(new Cannon(WIDTH / 2 - 30 / 2, 120, 30, 30, 0, 0, top, EType.cannon, game), new PTank(WIDTH / 2 - 60 / 2, 20, 60, 30, 0, 0, top, EType.ptank, game), this, 2);
		key = new Keyboard(this);
		ait = new AIThinking(this);
		mouse = new Mouse(this);
		handler = new Handler(this);
		options = new Options();
		dg = new DrawGFX(this);
		
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try { 
				Thread.sleep(2);;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}
			
			if (System.currentTimeMillis() - lastTimer > 1000) {
				if (options.showFps) {
					frame.setTitle(NAME + " | FPS: " + frames + " | UPS: " + ticks); 
				}
				lastTimer = System.currentTimeMillis();
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick() {
		key.tick();
		handler.tick();
		mouse.tick();
		if (multiplayer) {
			if (!bothready) {
				if (bottom.ready && top.ready) {
					handler.tick = System.currentTimeMillis();
					bothready = true;
				}
			} else if (bothready && !started) {
				state = State.game;
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Font fnt = new Font("Verdana", 1, 25);
		
		
		Graphics g = bs.getDrawGraphics();
		g.setFont(fnt);
		g.setColor(Color.DARK_GRAY);
		
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		handler.render(g);
//		
		
//		g.setColor(Color.LIGHT_GRAY);
//		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		
		g.dispose();
		bs.show();
	}
	
	public void host(String name) {
		server = new GameServer(this);
		server.start();
		
		client = new GameClient(this, "127.0.0.1");
		client.start();
		
		player = new PlayerMP(name, null, -1, 0, bottom);
		frame.setTitle(NAME + " | " + player.getUsername());
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), 0);
		if (server != null) {
			server.addConnection((PlayerMP) player, loginPacket);
		}
		loginPacket.type = PacketTypes.LOGIN;
		loginPacket.writeData(client);
		state = State.lobby;
	}
	
	public void join(String name, String ip) {
		hosting = false;
		client = new GameClient(this, ip);
		client.start();
		
		player = new PlayerMP(name, null, -1, 1, bottom);
		frame.setTitle(NAME + " | " + player.getUsername());
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), 1);
		if (server != null) {
			server.addConnection((PlayerMP) player, loginPacket);
		}
		client.players.add(player);
		loginPacket.type = PacketTypes.LOGIN;
		loginPacket.writeData(client);
		state = State.lobby;
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

}
