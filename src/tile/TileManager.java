package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gamePanel;
	
	//instantiate tile array to store multiple tile images
	public Tile[] tile;
	
	//tile index array to store which game tile will be placed
	public int mapTileIndex[][];
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatus = new ArrayList<>();
	boolean drawPath = true;
	
	public TileManager(GamePanel gamePanel) {
		
		
		
//		mapTileIndex = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
		//create new instance of 2d array to store world tile index

		this.gamePanel = gamePanel;
		
//		tile = new Tile[10];
//		
//		mapTileIndex = new int[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
		
		//for tile editor
		InputStream is = getClass().getResourceAsStream("/maps/DungeonMapData.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				
				fileNames.add(line);
				collisionStatus.add(br.readLine());
				
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tile = new Tile[fileNames.size()];
		
		is = getClass().getResourceAsStream("/maps/DungeonMap.txt");
		br = new BufferedReader(new InputStreamReader(is));
		
		try {
			String line2 = br.readLine();
			String maxTile[] = line2.split(" ");
			
			
			gamePanel.maxWorldColumn = maxTile.length;
			gamePanel.maxWorldRow = maxTile.length;
			
			mapTileIndex = new int[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		loadTileImage();
		
		loadMap();
	}
	
	public void loadMap() {
		
		try {
			//open map file
			InputStream mapFile = getClass().getResourceAsStream("/maps/DungeonMap.txt");
			
			//read map file
			BufferedReader mapReader = new BufferedReader(new InputStreamReader(mapFile));
			
			int column = 0;
			int row = 0;
			
			while(column < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
				
				//read a line from the file where this will get the numbers defined in the file
				String line = mapReader.readLine();
				
				while(column < gamePanel.maxWorldColumn) {
					
					//split the numbers whenever there is a space encountered
					String numbers[] = line.split(" ");
					
					//parse string numbers into int and store into temporary var
					int num = Integer.parseInt(numbers[column]);
					
					//push temporary value of var num to 2d array
					mapTileIndex[column][row] = num;
					column++;
				}
				
				if(column == gamePanel.maxWorldColumn) {
					column = 0;
					row++;
				}
				
			}
			
			//close reader after using
			mapReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadTileImage() {
	
		
		for(int i = 0; i < fileNames.size(); i++) {
			String fileName;
			boolean collision;
			
			fileName = fileNames.get(i);
			
			if(collisionStatus.get(i).equals("true")) {
				collision = true;
			}
			else collision = false;
			
			
			setup(i, fileName, collision);
		}
		
	}
	
	//optimizing tile draw time
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			
			tile[index] = new Tile();
			tile[index].tileImage = ImageIO.read(getClass().getResourceAsStream("/tile/" + imageName));
			tile[index].tileImage = uTool.scaleImage(tile[index].tileImage, gamePanel.tileSize, gamePanel.tileSize);
			tile[index].collision = collision;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void drawMap(Graphics2D g2) {
		
		int worldColumn = 0;
		int worldRow = 0;
//		int screenX = 0;
//		int screenY = 0;
		
		while(worldColumn < gamePanel.maxWorldColumn && worldRow < gamePanel.maxWorldRow) {
			
			//get index stored in 2d array
			int tileIndex = mapTileIndex[worldColumn][worldRow];
			
			
			int worldX = worldColumn * gamePanel.tileSize;
			int worldY = worldRow * gamePanel.tileSize;
			
			//compute position to place tile based on player position and offset its placement based on camera placement
			int screenX = worldX - gamePanel.player.entityX + gamePanel.player.cameraX;
			int screenY = worldY - gamePanel.player.entityY + gamePanel.player.cameraY;
			
			//will only draw tiles if it is within screen boundaries so that it will not cause performance issues
			//due to drawing of tiles even if player cannot see it
			  if (screenX + gamePanel.tileSize > 0 && screenY + gamePanel.tileSize > 0 &&
	              screenX < gamePanel.screenWidth && screenY < gamePanel.screenHeight) {
				
				//draw tile (grass, earth, water, sand, tree, wall) depending on index
				g2.drawImage(tile[tileIndex].tileImage, screenX, screenY,  null);
			}
			
//			if(worldX + gamePanel.tileSize > gamePanel.player.entityX - gamePanel.player.cameraX &&
//				worldX - gamePanel.tileSize < gamePanel.player.entityX + gamePanel.player.cameraX &&
//				worldY + gamePanel.tileSize > gamePanel.player.entityY - gamePanel.player.cameraY &&
//				worldY - gamePanel.tileSize < gamePanel.player.entityY + gamePanel.player.cameraY ) {
//				
//				g2.drawImage(tile[tileIndex].tileImage, screenX, screenY,  null);
//			}
			
			worldColumn++;
			
//			screenX += gamePanel.tileSize;
			
					
			if(worldColumn == gamePanel.maxWorldColumn) {
				//reset column size and x to 0 after reaching edge of screen
				worldColumn = 0;
//				screenX = 0;

				//increment row to get next indexes in 2d array
				worldRow++;
//				screenY += gamePanel.tileSize;

			}
		}
		
//		if(drawPath == true) {
//			g2.setColor(new Color(255,0,0,70));
//			
//			for(int i = 0; i < gamePanel.pFinder.pathList.size(); i++) {
//				int worldX = gamePanel.pFinder.pathList.get(i).col * gamePanel.tileSize;
//				int worldY = gamePanel.pFinder.pathList.get(i).row * gamePanel.tileSize;
//				int screenX = worldX - gamePanel.player.entityX + gamePanel.player.cameraX;
//				int screenY = worldY - gamePanel.player.entityY + gamePanel.player.cameraY;
//				
//				g2.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
//			}
//		}
	}
}
