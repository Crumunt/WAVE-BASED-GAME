package entity;

import java.util.Random;

import main.GamePanel;

public class EnemySpawner implements Runnable {
	
	private GamePanel gamePanel;
	private MonsterPool monsterPool;
	
	
	public EnemySpawner(GamePanel gamePanel, MonsterPool monsterPool) {
		this.gamePanel = gamePanel;
		this.monsterPool = monsterPool;
	}


	@Override
	public void run() {
		
		
		Random random = new Random();
		
		for(int i = 0; i < 10; i++) {
			Entity monster = new Entity(gamePanel);
			if(gamePanel.player.waveCount == 1) {
				monster = monsterPool.getMonsterFromPool(monsterPool.pool);
				monster.maxLife = 40;
			}
			if(gamePanel.player.waveCount >= 2) {
				if(random.nextInt(100) + 1 >= 80) {
					monster = monsterPool.getMonsterFromPool(monsterPool.specialPool);
					monster.maxLife = gamePanel.player.waveCount * 40;
				}else {
					monster = monsterPool.getMonsterFromPool(monsterPool.pool);
					monster.maxLife = gamePanel.player.waveCount * 32;
				}
				monster.damage++;
			}
			
			monster.entityX = gamePanel.tileSize * 26;
			monster.entityY = gamePanel.tileSize * 18;
			
			gamePanel.monsterList.add(monster);
			
			try {

				if(monster.name == "Golem") {
					Thread.sleep(5000);
				}
				else Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
		}
		
	}
	
}
