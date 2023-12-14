package entity;

import main.GamePanel;
import object.OBJ_Potion;

public class MON_Frog extends Entity {

	public MON_Frog(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Frog";
		speed = 2;
		maxLife = gamePanel.player.waveCount * 12;
		life = maxLife;
		damage = 1;
		
		collisionArea.x = 15;
		collisionArea.y = 17;
		collisionArea.width = 40;
		collisionArea.height = 38;
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		attackArea.width = 80;
		attackArea.height = 80;
		
		getImage();
		
	}
	
	public void getImage() {
		up = setup("/monster/backwalk1", 120);
		up2 = setup("/monster/backwalk2", 120);
		up3 = setup("/monster/backwalk3", 120);
		up4 = setup("/monster/backwalk4", 120);
		down = setup("/monster/frontwalk1", 120);
		down2 = setup("/monster/frontwalk2", 120);
		down3 = setup("/monster/frontwalk3", 120);
		down4 = setup("/monster/frontwalk4", 120);
		left = setup("/monster/leftwalk1", 120);
		left2 = setup("/monster/leftwalk2", 120);
		left3 = setup("/monster/leftwalk3", 120);
		left4 = setup("/monster/leftwalk4", 120);
		right = setup("/monster/rightwalk1", 120);
		right2 = setup("/monster/rightwalk2", 120);
		right3 = setup("/monster/rightwalk3", 120);
		right4 = setup("/monster/rightwalk4", 120);
		
		//attack image
		upAttack1 = setup("/monster/backattack1", 120);
		upAttack2 = setup("/monster/backattack2", 120);
		upAttack3 = setup("/monster/backattack3", 120);
		upAttack4 = setup("/monster/backattack4", 120);
		downAttack1 = setup("/monster/frontattack1", 120);
		downAttack2 = setup("/monster/frontattack2", 120);
		downAttack3 = setup("/monster/frontattack3", 120);
		downAttack4 = setup("/monster/frontattack4", 120);
		leftAttack1 = setup("/monster/leftattack1", 120);
		leftAttack2 = setup("/monster/leftattack2", 120);
		leftAttack3 = setup("/monster/leftattack3", 120);
		leftAttack4 = setup("/monster/leftattack4", 120);
		rightAttack1 = setup("/monster/rightattack1", 120);
		rightAttack2 = setup("/monster/rightattack2", 120);
		rightAttack3 = setup("/monster/rightattack3", 120);
		rightAttack4 = setup("/monster/rightattack4", 120);
	}
	
	public void setAction() {
		
		int goalCol = (gamePanel.player.entityX + gamePanel.player.collisionArea.x) / gamePanel.tileSize;
		int goalRow = (gamePanel.player.entityY + gamePanel.player.collisionArea.y) / gamePanel.tileSize;
		
		searchPath(goalCol, goalRow);
		
		if(attacking == false) {
			checkAttackorNot(50, gamePanel.tileSize*2, gamePanel.tileSize*3);
		}
		
	}
	
	public void checkDrop() {
		
		int i = random.nextInt(100) + 1;
		
		if(i >= 6 && i <= 12) {
			dropItem(new OBJ_Potion());
		}
		
	}
	
	

}
