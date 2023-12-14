package main;

import java.util.ArrayList;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity) {
	
		int entityLeftWorldX = entity.entityX + entity.collisionArea.x;
		int entityRightWorldX = entity.entityX + entity.collisionArea.x + entity.collisionArea.width;
		int entityTopWorldY = entity.entityY + entity.collisionArea.y;
		int entityBottomWorldY = entity.entityY + entity.collisionArea.y + entity.collisionArea.height;
		
		int entityLeftColumn = entityLeftWorldX / gamePanel.tileSize;
		int entityRightColumn = entityRightWorldX / gamePanel.tileSize;
		int entityTopRow = entityTopWorldY / gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.entityDirection) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileIndex[entityLeftColumn][entityTopRow];
			tileNum2 = gamePanel.tileManager.mapTileIndex[entityRightColumn][entityTopRow];
			
			if(gamePanel.tileManager.tile[tileNum1].collision == true || 
			   gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileIndex[entityLeftColumn][entityBottomRow];
			tileNum2 = gamePanel.tileManager.mapTileIndex[entityRightColumn][entityBottomRow];
			
			if(gamePanel.tileManager.tile[tileNum1].collision == true || 
			   gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftColumn = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileIndex[entityLeftColumn][entityTopRow];
			tileNum2 = gamePanel.tileManager.mapTileIndex[entityLeftColumn][entityBottomRow];
			
			if(gamePanel.tileManager.tile[tileNum1].collision == true || 
			   gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightColumn = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileIndex[entityRightColumn][entityTopRow];
			tileNum2 = gamePanel.tileManager.mapTileIndex[entityRightColumn][entityBottomRow];
			
			if(gamePanel.tileManager.tile[tileNum1].collision == true || 
			   gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
		
	}
	
	
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gamePanel.obj.length; i++) {
			
			if(gamePanel.obj[i] != null) {
				
				//get entity's solid area position
				entity.collisionArea.x = entity.entityX + entity.collisionArea.x;
				entity.collisionArea.y = entity.entityY + entity.collisionArea.y;
				
				//get object's solid area position
				gamePanel.obj[i].collisionArea.x = gamePanel.obj[i].objX + gamePanel.obj[i].collisionArea.x;
				gamePanel.obj[i].collisionArea.y = gamePanel.obj[i].objY + gamePanel.obj[i].collisionArea.y;
				
				
				switch(entity.entityDirection) {
				
				case "up":
					entity.collisionArea.y -= entity.speed;
					if(entity.collisionArea.intersects(gamePanel.obj[i].collisionArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.collisionArea.y += entity.speed;
					if(entity.collisionArea.intersects(gamePanel.obj[i].collisionArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.collisionArea.x -= entity.speed;
					if(entity.collisionArea.intersects(gamePanel.obj[i].collisionArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.collisionArea.x += entity.speed;
					if(entity.collisionArea.intersects(gamePanel.obj[i].collisionArea)) {
						if(gamePanel.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				
				}
				
				//reset collision area for player and items
				entity.collisionArea.x = entity.collisionAreaDefaultX;
				entity.collisionArea.y = entity.collisionAreaDefaultY;
				
				gamePanel.obj[i].collisionArea.x = gamePanel.obj[i].collisionAreaDefaultX;
				gamePanel.obj[i].collisionArea.y = gamePanel.obj[i].collisionAreaDefaultY;
			}
			
		}
		
		return index;
	}
	
	public boolean checkEnemyAttack(Entity entity, Entity target) {

		
		if(entity.collisionArea.intersects(target.collisionArea)) {
//				entity.collisionOn = true;
				return true;
		}
		
		return false;
	}
	
	public int checkEntity(Entity entity, ArrayList<Entity> target) {
		
		int index = 999;
		
		for(int i = 0; i < target.size(); i++) {
			
			if(target.get(i) != null) {
				
				//get entity's solid area position
				entity.collisionArea.x = entity.entityX + entity.collisionArea.x + 10;
				entity.collisionArea.y = entity.entityY + entity.collisionArea.y + 10;
				
				//get object's solid area position
				target.get(i).collisionArea.x = target.get(i).entityX + target.get(i).collisionArea.x;
				target.get(i).collisionArea.y = target.get(i).entityY + target.get(i).collisionArea.y;
				
				
				switch(entity.entityDirection) {
				case "up":
					entity.collisionArea.y -= entity.speed;
					break;
				case "down":
					entity.collisionArea.y += entity.speed;
					break;
				case "left":
					entity.collisionArea.x -= entity.speed;
					break;
				case "right":
					entity.collisionArea.x += entity.speed;
					break;
				
				}
				
				if(entity.collisionArea.intersects(target.get(i).collisionArea)) {
					if(target.get(i) != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				
				//reset collision area for player and items
				entity.collisionArea.x = entity.collisionAreaDefaultX;
				entity.collisionArea.y = entity.collisionAreaDefaultY;
				
				target.get(i).collisionArea.x = target.get(i).collisionAreaDefaultX;
				target.get(i).collisionArea.y = target.get(i).collisionAreaDefaultY;
				
			}
			
		}
		
		return index;
	}
	
	
	public void checkPlayer(Entity entity) {
		
		entity.collisionArea.x = entity.entityX + entity.collisionArea.x;
		entity.collisionArea.y = entity.entityY + entity.collisionArea.y;
		
		//get object's solid area position
		gamePanel.player.collisionArea.x = gamePanel.player.entityX + gamePanel.player.collisionArea.x;
		gamePanel.player.collisionArea.y = gamePanel.player.entityY + gamePanel.player.collisionArea.y;
		
		
		switch(entity.entityDirection) {
		case "up":
			entity.collisionArea.y -= entity.speed;
			break;
		case "down":
			entity.collisionArea.y += entity.speed;
			break;
		case "left":
			entity.collisionArea.x -= entity.speed;
			break;
		case "right":
			entity.collisionArea.x += entity.speed;
			break;
		
		}
		
		if(entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
			entity.collisionOn = true;
		}
		
		//reset collision area for player and items
		entity.collisionArea.x = entity.collisionAreaDefaultX;
		entity.collisionArea.y = entity.collisionAreaDefaultY;
		
		gamePanel.player.collisionArea.x = gamePanel.player.collisionAreaDefaultX;
		gamePanel.player.collisionArea.y = gamePanel.player.collisionAreaDefaultY;
		
		
	}
	
}
