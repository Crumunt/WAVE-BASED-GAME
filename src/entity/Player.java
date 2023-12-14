package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	
	KeyHandler userAction;
	
	public final int cameraX;
	public final int cameraY;
	int waveKillCounter = 0;
	public int waveCount = 1;
	public int playerKillCount = 0;
	
	public Player(GamePanel gamePanel, KeyHandler userAction) {
		
		super(gamePanel);
		
		this.userAction = userAction;
		
		setDefaultValues();
		getPlayerImage();
		
		//positions character in center of screen
		cameraX = gamePanel.screenWidth / 2 - (gamePanel.tileSize);
		cameraY = gamePanel.screenHeight / 2 - (gamePanel.tileSize);
		
		
		collisionArea = new Rectangle(32, 45, 40, 40);
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		attackArea.width = 50;
		attackArea.height = 50;
		
		
	}
	
	public void setDefaultValues() {
		
		entityX = gamePanel.tileSize * 25;
		entityY = gamePanel.tileSize * 30;
		speed = 4;
		entityDirection = "down";
		maxLife = 50;
		life = maxLife;
		damage = 1;
	}
	
	public void getPlayerImage() {
		
		try {
			
			up = ImageIO.read(getClass().getResourceAsStream("/player/upWalkSheet.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/player/downWalkSheet.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/player/leftWalkSheet.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/player/rightWalkSheet.png"));
			downIdle = ImageIO.read(getClass().getResourceAsStream("/player/downIdleSheet.png"));
			rightIdle = ImageIO.read(getClass().getResourceAsStream("/player/rightIdleSheet.png"));
			upIdle = ImageIO.read(getClass().getResourceAsStream("/player/upIdleSheet.png"));
			leftIdle = ImageIO.read(getClass().getResourceAsStream("/player/leftIdleSheet.png"));
			downAttack = ImageIO.read(getClass().getResourceAsStream("/player/frontAttackSheett.png"));
			upAttack = ImageIO.read(getClass().getResourceAsStream("/player/upAttackSheet.png"));
			rightAttack = ImageIO.read(getClass().getResourceAsStream("/player/rightAttackSheet.png"));
			leftAttack = ImageIO.read(getClass().getResourceAsStream("/player/leftAttackSheet.png"));
			
			
		} catch (IOException e) {
			
		}
		
	}
	
	public void update() {
		
		
		if(userAction.enterPressed == true) {
			attacking = true;
			spriteAttackCounter++;
			
			
			switch(entityDirection) {
			case "up":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 4;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 5;
					
					int currentEntityX = entityX;
					int currentEntityY = entityY;
					int collisionAreaWidth = collisionArea.width;
					int collisionAreaHeight = collisionArea.height;
					
					//adjust collision area for damage checking
					entityY -= attackArea.height;
					
					collisionArea.width = attackArea.width;
					collisionArea.height = attackArea.height;
					
					int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsterList);
					damageEnemy(monsterIndex);
					//return values to default after attacking
					entityX = currentEntityX;
					entityY = currentEntityY;
					collisionArea.width = collisionAreaWidth;
					collisionArea.height = collisionAreaHeight;
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 6;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					attacking = false;
					userAction.idle = true;
					userAction.enterPressed = false;
				}
				break;
			case "down":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 5;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 6;
					
					int currentEntityX = entityX;
					int currentEntityY = entityY;
					int collisionAreaWidth = collisionArea.width;
					int collisionAreaHeight = collisionArea.height;
					
					//adjust collision area for damage checking
					entityY += attackArea.height;
					
					collisionArea.width = attackArea.width;
					collisionArea.height = attackArea.height;
					
					int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsterList);
					damageEnemy(monsterIndex);
					
					//return values to default after attacking
					entityX = currentEntityX;
					entityY = currentEntityY;
					collisionArea.width = collisionAreaWidth;
					collisionArea.height = collisionAreaHeight;
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 7;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					attacking = false;
					userAction.idle = true;
					userAction.enterPressed = false;
				}
				break;
			case "left":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 3;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 2;
					
					int currentEntityX = entityX;
					int currentEntityY = entityY;
					int collisionAreaWidth = collisionArea.width;
					int collisionAreaHeight = collisionArea.height;
					
					//adjust collision area for damage checking
					entityX -= attackArea.width;
					
					collisionArea.width = attackArea.width;
					collisionArea.height = attackArea.height;
					
					int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsterList);
					damageEnemy(monsterIndex);
					
					//return values to default after attacking
					entityX = currentEntityX;
					entityY = currentEntityY;
					collisionArea.width = collisionAreaWidth;
					collisionArea.height = collisionAreaHeight;
					
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 1;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					attacking = false;
					userAction.idle = true;
					userAction.enterPressed = false;
				}
				break;
			case "right":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 10;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 11;
					
					int currentEntityX = entityX;
					int currentEntityY = entityY;
					int collisionAreaWidth = collisionArea.width;
					int collisionAreaHeight = collisionArea.height;
					
					//adjust collision area for damage checking
					entityX += attackArea.width;
					
					collisionArea.width = attackArea.width;
					collisionArea.height = attackArea.height;
					
					int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsterList);
					damageEnemy(monsterIndex);
					
					//return values to default after attacking
					entityX = currentEntityX;
					entityY = currentEntityY;
					collisionArea.width = collisionAreaWidth;
					collisionArea.height = collisionAreaHeight;
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 12;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					attacking = false;
					userAction.idle = true;
					userAction.enterPressed = false;
				}
				break;
			}
		}
		
		else if(userAction.idle == true) {
			
			spriteIdleCounter++;
			
			if(spriteIdleCounter > 12) {
				if(spriteIdleNum == 0) 
					spriteIdleNum = 1;
				else if(spriteIdleNum == 1)
					spriteIdleNum = 2;
				
				else if(spriteIdleNum == 2)
					if(entityDirection == "left" || entityDirection == "right" || entityDirection == "up") {
						spriteIdleNum = 0;
					}
					else spriteIdleNum = 3;
				
				else if(spriteIdleNum == 3) {
					 spriteIdleNum = 4;
				}
				else if(spriteIdleNum == 4)
					spriteIdleNum = 5;
				
				else if(spriteIdleNum == 5) 
					spriteIdleNum = 0;
				
				spriteIdleCounter = 0;
			}
				
		}
		
		//will only update player movements when w a s or d key is pressed
		else if(userAction.upPressed == true || userAction.downPressed == true 
				|| userAction.leftPressed == true || userAction.rightPressed == true) {
			if(userAction.upPressed == true) {
				entityDirection = "up";
			}
			else if(userAction.leftPressed == true) {
				entityDirection = "left";
			}
			else if(userAction.downPressed == true) {
				entityDirection = "down";
			}
			else if(userAction.rightPressed == true) {
				entityDirection = "right";
			}
			
			
			collisionOn = false;
			
			gamePanel.collisionChecker.checkTile(this);
			
			int objIndex = gamePanel.collisionChecker.checkObject(this, true);
			pickObject(objIndex);
			
			int enemyIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsterList);
			monsterDamage(enemyIndex);
			
			if(collisionOn == false) {
				
				switch(entityDirection) {
				case "up":
					//decrement entityY for player to move up
					entityY -= speed;
					break;
				case "down":
					entityY += speed;
					break;
				case "left":
					entityX -= speed;
					break;
				case "right":
					entityX += speed;
					break;
				}
				
				
			}
			
			//increment sprite counter to make moving animation and not only static movements
			spriteCounter++;
			//updates sprite animation type every 12 frames if key is pressed
			if(spriteCounter > 10) {
				if(spriteNum == 1) 
					spriteNum = 2;
				else if(spriteNum == 2)
					spriteNum = 3;
				
				else if(spriteNum == 3) {
					spriteNum = 1;
				}
				
				spriteCounter = 0;
			}
		}
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		
		if(life <= 0) {
			gamePanel.gameState = gamePanel.gameOverState;
		}
		
	}
	
	private void damageEnemy(int i) {
		
		if(i != 999) {
			
			gamePanel.monsterList.get(i).life -= damage;
			gamePanel.monsterList.get(i).invincible = true;
			
			if(gamePanel.monsterList.get(i).life <= 0) {
				gamePanel.monsterList.get(i).life = gamePanel.monsterList.get(i).maxLife;
				gamePanel.monsterList.get(i).invincible = false;
				gamePanel.monsterList.get(i).checkDrop();
				gamePanel.monsterPool.returnMonsterToPool(gamePanel.monsterList.get(i));
				gamePanel.monsterList.remove(i);
				
				waveKillCounter++;
				playerKillCount++;
				
				
				if(gamePanel.monsterList.size() == 0 && waveKillCounter == 10) {
					waveCount++;
					gamePanel.ui.waveMessage("WAVE: " + waveCount);
					waveKillCounter = 0;
					gamePanel.enemySpawnerThread = new Thread(gamePanel.enemySpawnerThread);
					gamePanel.enemySpawnerThread.start();
				}
			}
			
		}
		else return;
		
	}

	public void monsterDamage(int enemyIndex) {
		
		if(enemyIndex != 999) {
			if(invincible == false) {
				Entity monster = gamePanel.monsterList.get(enemyIndex);
				life -= monster.damage;
				invincible = true;
			}
		}
		else return;
		
	}
	
	public void monsterInteraction(boolean interacts) {
		
		if(interacts) {
			if(invincible == false) {
				life -= 2;
				invincible = true;
			}
		}
		else return;
		
	}

	public void pickObject(int i) {
		
		if(i != 999) {
			
			life += 1;
			if(life > maxLife) life = maxLife;
			gamePanel.obj[i] = null;
			
		}
		else return;
		
	}

	public void draw(Graphics2D g2) {
		
		
			switch(entityDirection) {
			case "up":
				if(attacking == false) {
					subImage = up.getSubimage(spriteNum*64, 0, 64, 64);
				}
				if(attacking == true) {
					subImage = upAttack.getSubimage(spriteAttackNum*64, 0, 64, 64);
				}
				if(userAction.idle == true) {
					subImage = upIdle.getSubimage(spriteIdleNum*64, 0, 64, 64);
				}
				break;
			case "down":					
				if(attacking == false) {
					subImage = down.getSubimage(spriteNum*64, 0, 64, 64);
				}
				if(attacking == true) {
					subImage = downAttack.getSubimage(spriteAttackNum*64, 0, 64, 64);
				}
				if(userAction.idle == true) {
					subImage = downIdle.getSubimage(spriteIdleNum*64, 0, 64, 64);
				}
				break;
			case "left":
				if(attacking == false) {
					subImage = left.getSubimage(spriteNum*64, 0, 64, 64);
				}
				if(attacking == true) {
					subImage = leftAttack.getSubimage(spriteAttackNum*64, 0, 64, 64);
				}
				if(userAction.idle == true) {
					subImage = leftIdle.getSubimage(spriteIdleNum*64, 0, 64, 64);
				}
				break;
			case "right":
				if(attacking == false) {
					subImage = right.getSubimage(spriteNum*64, 0, 64, 64);				}
				if(attacking == true) {
					subImage = rightAttack.getSubimage(spriteAttackNum*64, 0, 64, 64);
				}
				if(userAction.idle == true) {
					subImage = rightIdle.getSubimage(spriteIdleNum*64, 0, 64, 64);
				}
				break;
			}
		
			
			
		//player life
		double oneScale = (double)500 / maxLife;
		double hpBarValue = oneScale * life;
		
		g2.setColor(new Color(35,35,35));
		g2.fillRect(10, 20, 500, 20);
		
		g2.setColor(new Color(255, 0, 30));
		g2.fillRect(11, 21, (int)hpBarValue, 20);
			
		//if player has been hit by monster reduce opacity by 70%
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		//load image depending on key pressed and sprite number
		g2.drawImage(subImage, cameraX, cameraY, 128, 128,null);
		
		//reset opacity
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//		g2.drawRect(cameraX, cameraY, collisionArea.width, collisionArea.height);
	}
}
