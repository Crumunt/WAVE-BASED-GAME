package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import object.SuperObject;

public class Entity {
	
	//player and enemy values
	public int entityX, entityY;
	public int speed;
	public String name;
	public int life;
	public int maxLife;
	public boolean invincible = false;
	public int damage = 0;
	
	//for player sprite
	BufferedImage up, down, left, right, downIdle, rightIdle, leftIdle, upIdle, subImage,
	downAttack, upAttack, leftAttack, rightAttack;
	
	//for frog sprite
	BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, 
	left2, left3, left4, right1, right2, right3, right4;
	BufferedImage upAttack1, upAttack2, upAttack3, upAttack4, downAttack1, downAttack2, downAttack3, downAttack4, leftAttack1, 
	leftAttack2, leftAttack3, leftAttack4, rightAttack1, rightAttack2, rightAttack3, rightAttack4;
	
	//for golem sprite
	BufferedImage[][] golem;
//	BufferedImage walk1, walk2, walk3, walk4, walk5, walk6, walk7, walk8, walk9,
//	gAttack1, gAttack2, gAttack3, gAttack4, gAttack5, gAttack6, gAttack7, gAttack8;
	public String entityDirection = "down";
	String attackDirection;
	
	//counter attributes for player and enemies
	public int spriteCounter = 0;
	public int enemySpriteCounter = 0;
	public int spriteNum = 1;
	public int spriteIdleNum = 0;
	public int spriteIdleCounter = 1;
	public int sheetCounter = 0;
	public int spriteAttackCounter = 1;
	public int spriteAttackNum = 5;
	public int actionLockCounter = 0;
	public int monsterCount = 10;
	public int invincibleCounter = 0;
	public boolean onPath = false;
	int golemAttackCounter = 1;
	
	//collision area for player to detect hit and attack
	public Rectangle collisionArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	public boolean collisionOn;
	public boolean attacking;
	
	Random random = new Random();
	
	GamePanel gamePanel;
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	
	public void checkCollision() {
		collisionOn = false;
		gamePanel.collisionChecker.checkTile(this);
		gamePanel.collisionChecker.checkObject(this, false);
		gamePanel.collisionChecker.checkPlayer(this);
//		gamePanel.collisionChecker.checkEntity(this, gamePanel.monsterList);
	}
	
	public void update() {
		
		if(attacking == false) {
			setAction();
			
			checkCollision();
			
			if(collisionOn == false) {
				
				switch(entityDirection) {
				case "up":
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

			enemySpriteCounter++;

			if(enemySpriteCounter > 10) {
				if(spriteNum == 1) 
					spriteNum = 2;
				else if(spriteNum == 2)
					spriteNum = 3;
				else if(spriteNum == 3) 
					spriteNum = 4;
				else if(spriteNum == 4) {
					if(this.name == "Golem") {
						spriteNum = 5;
					}else spriteNum = 1;
				}
				else if(spriteNum == 5)
					spriteNum = 6;
				else if(spriteNum == 6)
					spriteNum = 7;
				else if(spriteNum == 7)
					spriteNum = 8;
				else if(spriteNum == 8)
					spriteNum = 1;
				
				
				enemySpriteCounter = 0;
			}
			
			if(invincible == true) {
				invincibleCounter++;
				if(invincibleCounter > 60) {
					invincible = false;
					invincibleCounter = 0;
				}
			}
		}
		
		else if(attacking == true) {
			attacking();
		}
	}
	
	public void checkDrop() {
		
	}
	
	public void dropItem(SuperObject droppedItem) {
		
		for(int i = 0; i < gamePanel.obj.length; i++) {
			if(gamePanel.obj[i] == null) {
				gamePanel.obj[i] = droppedItem;
				gamePanel.obj[i].objX = entityX;
				gamePanel.obj[i].objY = entityY;
			}
		}
		
	}
	
	public void setAction() {}
	
	public int getXDistance(Entity target) {
		int xDistance = Math.abs(entityX - target.entityX);
		return xDistance;
	}
	
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(entityY - target.entityY);
		return yDistance;
	}
	
	//checks whether player is within range of monster then hadouken by chance
	public void checkAttackorNot(int rate, int straight, int horizontal) {
		
		boolean targetInRange = false;
		int xDistance = getXDistance(gamePanel.player);
		int yDistance = getYDistance(gamePanel.player);
		
		switch(entityDirection) {
		case "up":
			if(gamePanel.player.entityY < entityY && yDistance < straight && xDistance < horizontal) {
				targetInRange = true;
			}
			break;
		case "down":
			if(gamePanel.player.entityY > entityY && yDistance < straight && xDistance < horizontal) {
				targetInRange = true;
			}
			break;
		case "left":
			if(gamePanel.player.entityY < entityX && xDistance < straight && yDistance < horizontal) {
				targetInRange = true;
			}
			break;
		case "right":
			if(gamePanel.player.entityY > entityY && xDistance < straight && yDistance < horizontal) {
				targetInRange = true;
			}
			break;
		}
		
		if(targetInRange == true) {
			int i = random.nextInt(rate);
			if(i == 0) {
				attacking = true;
			}
		}
		
	}
	
	public void attacking() {
		spriteAttackCounter++;
		
		
		if(this.name == "Frog") {
			switch(entityDirection) {
			case "up":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 1;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 2;
					
					attackAction();
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 3;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					spriteAttackNum = 1;
					attacking = false;
				}
				break;
			case "down":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 1;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 2;
					
					attackAction();
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 3;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					spriteAttackNum = 1;
					attacking = false;
				}
				break;
			case "left":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 1;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 2;
					
					attackAction();
					
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 3;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					spriteAttackNum = 1;
					attacking = false;
				}
				break;
			case "right":
				if(spriteAttackCounter < 5) {
					spriteAttackNum = 1;
				}
				if(spriteAttackCounter > 5 && spriteAttackCounter <= 15) {
					spriteAttackNum = 2;
					
					
					attackAction();
					
				}
				if(spriteAttackCounter > 15 && spriteAttackCounter <= 20) {
					spriteAttackNum = 3;
				}
				if(spriteAttackCounter > 20) {
					spriteAttackCounter = 0;
					spriteAttackNum = 1;
					attacking = false;
				}
				break;
			}
			
		}
		else if(this.name == "Golem") {
			if(spriteAttackCounter <= 25) {
				spriteAttackNum = 1;
			}
			if(spriteAttackCounter > 25 && spriteAttackCounter <= 35) {
				spriteAttackNum = 2;
			}
			if(spriteAttackCounter > 35 && spriteAttackCounter <= 40) {
				spriteAttackNum = 3;
			}
			if(spriteAttackCounter > 45 && spriteAttackCounter <= 55) {
				spriteAttackNum = 4;
			}
			if(spriteAttackCounter > 55 && spriteAttackCounter <= 65) {
				spriteAttackNum = 5;	
				if(golemAttackCounter == 2) {
					attackAction();
				}
			}
			if(spriteAttackCounter > 65 && spriteAttackCounter <= 75) {
				spriteAttackNum = 6;
				if(golemAttackCounter == 1) {
					attackAction();
				}
			}
			if(spriteAttackCounter > 75 && spriteAttackCounter <= 85) {
				spriteAttackNum = 7;
			}
			if(spriteAttackCounter > 85 && spriteAttackCounter <= 95) {
				spriteAttackNum = 8;
			}
			if(spriteAttackCounter > 95) {
				spriteAttackCounter = 0;
				spriteAttackNum = 1;
				golemAttackCounter = random.nextInt(2) + 1;
				attacking = false;
			}
		}
	}
	
	public void attackAction() {
		
		int currentEntityX = entityX;
		int currentEntityY = entityY;
		int collisionAreaWidth = collisionArea.width;
		int collisionAreaHeight = collisionArea.height;
		
		switch(entityDirection) {
		case "up":
			entityY -= attackArea.height;
			break;
		case "down":
			entityY += attackArea.height;
			break;
		case "left":
			//adjust collision area for damage checking
			entityX -= attackArea.width;
			break;
		case "right":
			
			//adjust collision area for damage checking
			entityX += attackArea.width;
		
			
			break;
		}
		
		collisionArea.width = attackArea.width;
		collisionArea.height = attackArea.height;
		
		gamePanel.player.monsterInteraction(gamePanel.collisionChecker.checkEnemyAttack(this, gamePanel.player));
		
		//return values to default after attacking
		entityX = currentEntityX;
		entityY = currentEntityY;
		collisionArea.width = collisionAreaWidth;
		collisionArea.height = collisionAreaHeight;
		
	}
	
	public BufferedImage setup(String imagePath, int scaleSize) {
		
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			if(this.name == "Golem") {
				image = uTool.scaleImage(image, scaleSize, scaleSize);
			}
			else if(this.name == "Frog") {
				image = uTool.scaleImage(image, scaleSize, scaleSize);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return image;
	}


	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		//compute position to place tile based on player position and offset its placement based on camera placement
		int screenX = entityX - gamePanel.player.entityX + gamePanel.player.cameraX;
		int screenY = entityY - gamePanel.player.entityY + gamePanel.player.cameraY;
		
		//will only draw tiles if it is within screen boundaries so that it will not cause performance issues
		//due to drawing of tiles even if player cannot see it
		if (screenX + gamePanel.tileSize > 0 && screenY + gamePanel.tileSize > 0 &&
	              screenX < gamePanel.screenWidth && screenY < gamePanel.screenHeight) {
			
			  
			int tempScreenX = screenX;
		  	int tempScreenY = screenY;
			
				  switch(entityDirection) {
					case "up":
						if(attacking == true) {
							if(spriteAttackNum == 1) {
								image = upAttack1;
							}
							if(spriteAttackNum == 2) {
								image = upAttack2;
							}
							if(spriteAttackNum == 3) {
								image = upAttack3;
							}
							if(spriteAttackNum == 4) {
								image = upAttack4;
							}
						}
						else {
							if(spriteNum == 1) {
								image = up;
							}
							if(spriteNum == 2) {
								image = up2;
							}
							if(spriteNum == 3) {
								image = up3;
							}
							if(spriteNum == 4) {
								image = up4;
							}
						}
						break;
					case "down":	
						if(attacking == true) {
							if(spriteAttackNum == 1) {
								image = downAttack1;
							}
							if(spriteAttackNum == 2) {
								image = downAttack2;
							}
							if(spriteAttackNum == 3) {
								image = downAttack3;
							}
							if(spriteAttackNum == 4) {
								image = downAttack4;
							}
						}
						else {
							if(spriteNum == 1) {
								image = down;
							}
							if(spriteNum == 2) {
								image = down2;
							}
							if(spriteNum == 3) {
								image = down3;
							}
							if(spriteNum == 4) {
								image = down4;
							}
						}
						break;
					case "left":
						if(attacking == true) {
							if(spriteAttackNum == 1) {
								image = leftAttack1;
							}
							if(spriteAttackNum == 2) {
								image = leftAttack2;
							}
							if(spriteAttackNum == 3) {
								image = leftAttack3;
							}
							if(spriteAttackNum == 4) {
								image = leftAttack4;
							}
						}
						else {
							if(spriteNum == 1) {
								image = left;
							}
							if(spriteNum == 2) {
								image = left2;
							}
							if(spriteNum == 3) {
								image = left3;
							}
							if(spriteNum == 4) {
								image = left4;
							}
						}
						break;
					case "right":
						if(attacking == true) {
							if(spriteAttackNum == 1) {
								image = rightAttack1;
							}
							if(spriteAttackNum == 2) {
								image = rightAttack2;
							}
							if(spriteAttackNum == 3) {
								image = rightAttack3;
							}
							if(spriteAttackNum == 4) {
								image = rightAttack4;
							}
						}
						else {
							if(spriteNum == 1) {
								image = right;
							}
							if(spriteNum == 2) {
								image = right2;
							}
							if(spriteNum == 3) {
								image = right3;
							}
							if(spriteNum == 4) {
								image = right4;
							}
						}
						break;
					}
			  if(this.name == "Golem") {
				  
					switch(entityDirection) {
					case "up":
					case "down":
					case "left":
					case "right":
						if(attacking == true) {
							if(golemAttackCounter == 1) {
								tempScreenX = screenX - gamePanel.tileSize;
								tempScreenY = screenY - gamePanel.tileSize;
								if(spriteAttackNum == 1) {
									image = golem[1][0];
								}
								if(spriteAttackNum == 2) {
									image = golem[1][1];
								}
								if(spriteAttackNum == 3) {
									image = golem[1][2];
								}
								if(spriteAttackNum == 4) {
									image = golem[1][3];
								}
								if(spriteAttackNum == 5) {
									image = golem[1][4];
								}
								if(spriteAttackNum == 6) {
									image = golem[1][5];
								}
								if(spriteAttackNum == 7) {
									image = golem[1][6];
								}
								if(spriteAttackNum == 8) {
									image = golem[1][7];
								}
								
							}
							else if(golemAttackCounter == 2) {
								tempScreenX = screenX - gamePanel.tileSize * 2;
								tempScreenY = screenY - gamePanel.tileSize * 5;
								if(spriteAttackNum == 1) {
									image = golem[2][0];
								}
								if(spriteAttackNum == 2) {
									image = golem[2][1];
								}
								if(spriteAttackNum == 3) {
									image = golem[2][2];
								}
								if(spriteAttackNum == 4) {
									image = golem[2][3];
								}
								if(spriteAttackNum == 5) {
									image = golem[2][4];
								}
								if(spriteAttackNum == 6) {
									image = golem[2][5];
								}
								if(spriteAttackNum == 7) {
									image = golem[2][6];
								}
								if(spriteAttackNum == 8) {
									image = golem[2][7];
								}
							}
						}
						else {
							if(spriteNum == 1) {
								image = golem[0][0];
							}
							if(spriteNum == 2) {
								image = golem[0][1];
							}
							if(spriteNum == 3) {
								image = golem[0][2];
							}
							if(spriteNum == 4) {
								image = golem[0][3];
							}
							if(spriteNum == 5) {
								image = golem[0][4];
							}
							if(spriteNum == 6) {
								image = golem[0][5];
							}
							if(spriteNum == 7) {
								image = golem[0][6];
							}
							if(spriteNum == 8) {
								image = golem[0][7];
							}
						}
						break;
					}
			  }
			
		  if(invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			}
			  
			//draw tile (grass, earth, water, sand, tree, wall) depending on index
			g2.drawImage(image, tempScreenX, tempScreenY,  null);
			
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			
//			g2.fillRect(gamePanel.player.cameraX, gamePanel.player.cameraY - 100, 128, 128);
		}
	}
	
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (entityX + collisionArea.x) / gamePanel.tileSize;
		int startRow = (entityY + collisionArea.y) / gamePanel.tileSize;
		
		gamePanel.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if(gamePanel.pFinder.search() == true) {
			
			//next X and Y of entity
			int nextX = gamePanel.pFinder.pathList.get(0).col * gamePanel.tileSize;
			int nextY = gamePanel.pFinder.pathList.get(0).row * gamePanel.tileSize;
			
			//get monsters solid area position
			int enLeftX = entityX + collisionArea.x;
			int enRightX = entityX + collisionArea.x + collisionArea.width;
			int enTopY = entityY + collisionArea.y;
			int enBottomY = entityY + collisionArea.y + collisionArea.height;
			
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
				entityDirection = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
				entityDirection = "down";
			}
			else if(enTopY >= nextY && enBottomY < nextY + gamePanel.tileSize) {
				//entity go either left or right
				if(enLeftX > nextX) {
					entityDirection = "left";
				}
				if(enLeftX < nextX) {
					entityDirection = "right";
				}
			}
			else if(enTopY > nextY && enLeftX > nextX) {
				//either go up or left
				entityDirection = "up";
				checkCollision();
				if(collisionOn == true) {
					entityDirection = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX) {
				//either go up or right
				entityDirection = "up";
				checkCollision();
				if(collisionOn == true) {
					entityDirection = "right";
				}
			}
			else if(enTopY < nextX && enLeftX > nextX) {
				//either go down or left
				entityDirection = "down";
				checkCollision();
				if(collisionOn == true) {
					entityDirection = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				//either go down or right
				entityDirection = "down";
				checkCollision();
				if(collisionOn == true) {
					entityDirection = "right";
				}
			}
			
		}
		
	}
	
}
