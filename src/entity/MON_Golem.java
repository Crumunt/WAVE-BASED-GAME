package entity;

import java.awt.image.BufferedImage;

import main.GamePanel;
import object.OBJ_Potion;

public class MON_Golem extends Entity {
	
	
	
	public MON_Golem(GamePanel gamePanel) {
		super(gamePanel);
		
		golem = new BufferedImage[3][9];
		
		name = "Golem";
		speed = 1;
		maxLife = gamePanel.player.waveCount * 20;
		life = maxLife;
		damage = 3;
		
		collisionArea.x = 93;
		collisionArea.y = 59;
		collisionArea.width = 25;
		collisionArea.height = 125;
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		attackArea.width = collisionArea.width * 3;
		attackArea.height = collisionArea.height * 3;
		
		getImage();
		
	}
	
	public void getImage() {
		
		for(int i = 0; i < 9; i++) {
			golem[0][i] = setup("/monster/bosswalk" + (i+1), 200);
		}
		
		for(int i = 0; i < 8; i++) {
			golem[1][i] = setup("/monster/bossattack" + (i+1), 340);
			golem[2][i] = setup("/monster/2ndattck" + (i+1), 360);
		}
	}
	
	public void setAction() {
		
		int goalCol = (gamePanel.player.entityX + gamePanel.player.collisionArea.x) / gamePanel.tileSize;
		int goalRow = (gamePanel.player.entityY + gamePanel.player.collisionArea.y) / gamePanel.tileSize;
		
		searchPath(goalCol, goalRow);
		
		if(attacking == false) {
			checkAttackorNot(80, gamePanel.tileSize*5, gamePanel.tileSize * 8);
		}
		
	}
	
public void checkDrop() {
		
		int i = random.nextInt(100) + 1;
		
		if(i >= 0 && i <= 5) {
			dropItem(new OBJ_Potion());
		}
		
	}
	
}
