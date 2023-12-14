package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.GamePanel;

public class MonsterPool {
	
	public List<Entity> pool;
	public List<Entity> specialPool;
	private int poolSize;
	Random random;
	GamePanel gamePanel;
	
	public MonsterPool(int poolSize, GamePanel gamePanel) {
		pool = new ArrayList<Entity>();
		specialPool = new ArrayList<Entity>();
		this.poolSize = poolSize;
		this.gamePanel = gamePanel;
		this.random = new Random();
		initializePool();
	}
	
	public void initializePool() {
		
		for(int i = 0; i < poolSize; i++) {
			
			Entity monster = new MON_Frog(gamePanel);
			pool.add(monster);
			
			Entity specialMonster = new MON_Golem(gamePanel);
			specialPool.add(specialMonster);
		}
		
	}
	
	public synchronized Entity getMonsterFromPool(List<Entity> poolList) {
		if(poolList.isEmpty()) {
			if(poolList.equals(pool))
				return new MON_Frog(gamePanel);
			else
				return new MON_Golem(gamePanel);
		}
		
		else {
			if(poolList.equals(pool))
				return pool.remove(pool.size() - 1);
			else
				return specialPool.remove(specialPool.size() - 1);
		}
	
	}
	
	public synchronized void returnMonsterToPool(Entity monster) {
		
		if(monster.name == "Frog") {
			if(pool.size() < poolSize) {
				pool.add(monster);
			}
		}else {
			if(specialPool.size() < poolSize) {
				specialPool.add(monster);
			}
		}
		
	}
	
	
}
