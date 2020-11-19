package schmee.zorton.game.gfx.menus;

import java.awt.Graphics;

import schmee.zorton.game.Game;
import schmee.zorton.game.input.buttons.Button;

public class HoverMenu {
	
	private Game game;
	
	public Button mainMenu = new Button(265, 390, 175, 50, "Main Menu");
	
	public HoverMenu(Game game) {
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if (game.key.hoverMenu) {
			game.dg.drawButton(mainMenu, g);
		}
	}

}
