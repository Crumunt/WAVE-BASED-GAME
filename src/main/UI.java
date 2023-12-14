package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
	
	GamePanel gamePanel;
	Font waveFont = new Font("Times New Roman", Font.BOLD, 80);
	String wave;
	boolean messageOn = false;
	private int messageCounter = 0;
	Graphics2D g2;
	
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void waveMessage(String waveCount) {
		wave = waveCount;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		if(messageOn == true) {
			g2.setFont(waveFont);
			g2.setColor(Color.WHITE);
			
			g2.drawString(wave, 490, 700);
			
			messageCounter++;
			if(messageCounter == 120) {
				messageOn = false;
				messageCounter = 0;
			}
			
		}
		
		
		if(gamePanel.gameState == gamePanel.pauseState) {
			drawPauseState();
		}
		
		if(gamePanel.gameState == gamePanel.gameOverState) {
			drawGameOverState();
		}
	}
	
	private void drawGameOverState() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Times New Roman", Font.BOLD, 80));
		String text = "GAME OVER";
		int x = getXForCenteredText(text);
		int y = gamePanel.screenHeight / 2;
		
		g2.drawString(text, x, y);
		
		drawScore();
	}

	private void drawScore() {
		
		g2.setColor(Color.BLUE);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50));
		String text = "Kill Count: " + gamePanel.player.playerKillCount;
		int x = getXForCenteredText(text);
		int y = gamePanel.screenHeight / 2 + 100;
		
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50));
		
		int scoreMultiplier = 1;
		
		if(gamePanel.player.waveCount < 3) {
			scoreMultiplier = gamePanel.player.waveCount * 6;
		}
		else if(gamePanel.player.waveCount > 3 && gamePanel.player.waveCount < 12) {
			scoreMultiplier = gamePanel.player.waveCount * 13;
		}
		else {
			scoreMultiplier = gamePanel.player.waveCount * 17;
		}
		
		text = "Score: " + gamePanel.player.playerKillCount * scoreMultiplier;
		x = getXForCenteredText(text);
		y = gamePanel.screenHeight / 2 + 200;
		
		g2.drawString(text, x, y);
		
	}

	public void drawPauseState() {
		
		g2.setFont(new Font("Times New Roman", Font.BOLD, 80));
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gamePanel.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}

	private int getXForCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gamePanel.screenWidth / 2 - length / 2;
		
		return x;
	}
	
}
