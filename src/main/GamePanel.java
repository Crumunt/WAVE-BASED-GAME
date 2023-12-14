package main;

// package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.EnemySpawner;
import entity.Entity;
import entity.MonsterPool;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	
	private static final long serialVersionUID = 1L;
	//screen settings
	final int originalTileSize = 16;
	final int scale = 3;
	
	//64 * 2
	public final int tileSize = originalTileSize * scale; // would scale sprites to 48px
	public final int spriteSize = 64;
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	
//	tileSize * maxScreenCol
	public final int screenWidth = 1280;
//	tileSize * maxScreenRow
	public final int screenHeight = 800;
	
	//word map size
	public int maxWorldColumn;
	public int maxWorldRow;
	public final int worldWidth = tileSize * maxWorldColumn;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//ENTITIES
//	public Entity[] monster = new Entity[100];
	public ArrayList<Entity> monsterList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	
	//FPS
	int FPS = 60;
	
	KeyHandler userAction = new KeyHandler(this);
	
	//imports from other packages
	public TileManager tileManager = new TileManager(this);
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this, userAction);
	public SuperObject obj[] = new SuperObject[10];
	public PathFinder pFinder = new PathFinder(this);
	public MonsterPool monsterPool = new MonsterPool(10, this);
	public EnemySpawner enemySpawner = new EnemySpawner(this, monsterPool);
	public UI ui = new UI(this);
	
	//GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int gameOverState = 3;
	
	
	//THREADS
	Thread gameThread;
	public Thread enemySpawnerThread = new Thread(enemySpawner);
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //enabling will render game better
		this.addKeyListener(userAction); //adds keyHandler class to detect user action
		this.setFocusable(true);
				
	}
	
	public void setUpGame() {
		
		ui.waveMessage("WAVE: " + player.waveCount);
		enemySpawnerThread.start();
		aSetter.setObject();		
		gameState = playState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		
		gameThread.start();
	}

	@Override
	public void run() {
		
		//1 second == 1 billion nanoseconds
		
		//set draw interval to only update 60 frames per second
		double drawInterval = 1000000000/FPS;
		
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		//run this thread as long as it is not equal to null
		while(gameThread != null) {
			
			
			//in charge of updating information such as sprite movement
			update();
			
			//draw the updated information on the screen
			repaint();
			
			try {
				
				//gets remaining time until next draw time will occur
				double remainingTime = nextDrawTime - System.nanoTime();
				
				remainingTime = remainingTime / 1000000; //converts nanoseconds to miliseconds
				
				//in the event that remaining time goes below 0 set remainingTime to 0 so that thread won't need
				//to sleep
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				//limits the number of time graphics update by making thread sleep
				Thread.sleep((long) remainingTime);
				
				//update when next draw update will happen
				nextDrawTime += drawInterval;
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	public void update() {
		
		if(gameState == playState) {
			player.update();
			
			for(int i = 0; i < monsterList.size(); i++) {
				if(monsterList.get(i) != null) {
					monsterList.get(i).update();
				}
			}
		}
		if(gameState == pauseState) {
			//do nothing
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//converts graphics into Graphics2D they're similar but Graphics2D has more functions
		Graphics2D g2 = (Graphics2D)g;
		
		tileManager.drawMap(g2);
		
		entityList.add(player);
		
		for(int i = 0; i < monsterList.size(); i++) {
			if(monsterList.get(i) != null) {
				entityList.add(monsterList.get(i));
			}
		}
		
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);;
			}
		}
		
		Collections.sort(entityList, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				int result = Integer.compare(e1.entityY, e2.entityY);
				return result;
			}
			
		});
		
		for(int i = 0; i < entityList.size(); i++) {
			entityList.get(i).draw(g2);
		}
		
		
		entityList.clear();
		
		ui.draw(g2);
		
		//dispose used graphics for next use
		//best practice to save memory
		g2.dispose();
		
	}

}
