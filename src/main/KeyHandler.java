package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {

	public boolean upPressed, leftPressed, downPressed, rightPressed, idle = true, enterPressed;
	GamePanel gamePanel;
	
	public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
			idle = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
			idle = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
			idle = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
			idle = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			idle = false;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			if(gamePanel.gameState == gamePanel.playState) {
				gamePanel.gameState = gamePanel.pauseState;
			}
			else if(gamePanel.gameState == gamePanel.pauseState) {
				gamePanel.gameState = gamePanel.playState;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
			idle = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
			idle = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
			idle = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
			idle = true;
		}


	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
