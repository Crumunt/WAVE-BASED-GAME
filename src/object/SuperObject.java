package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int objX, objY;
	public Rectangle collisionArea = new Rectangle(0,0,48,48);
	public int collisionAreaDefaultX = 0;
	public int collisionAreaDefaultY = 0;
	
	
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		//compute position to place tile based on player position and offset its placement based on camera placement
		int screenX = objX - gamePanel.player.entityX + gamePanel.player.cameraX;
		int screenY = objY - gamePanel.player.entityY + gamePanel.player.cameraY;
		
		//will only draw tiles if it is within screen boundaries so that it will not cause performance issues
		//due to drawing of tiles even if player cannot see it
		  if (screenX + gamePanel.tileSize > 0 && screenY + gamePanel.tileSize > 0 &&
              screenX < gamePanel.screenWidth && screenY < gamePanel.screenHeight) {
			
			//draw tile (grass, earth, water, sand, tree, wall) depending on index
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize,  null);
		}
	}
}
