package Func;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements Runnable, KeyListener {
	private GameMediator aGameMediator;
	private GameWindow aGameWindow;
	
	private boolean acceptKeyPressed = true;
	private BufferedImage img;
	private boolean running;
	private Graphics2D g2d;
	private Thread thread;

	public Canvas() {
		ConfigSingleton singleton = ConfigSingleton.getInstance();
		int width = singleton.getCanvasWidth() * singleton.getScale();
		int height = singleton.getCanvasHeight() * singleton.getScale();

		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
	}

	public Canvas(GameWindow aGameWindow) {
		this();
		this.aGameWindow = aGameWindow;
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		init();
		
		while (running) {
			long start = System.nanoTime();

			drawToScreen();
			Keys.update();
			aGameMediator.update();
			aGameMediator.draw(g2d);

			long elapsed = System.nanoTime() - start;
			int targetTime = ConfigSingleton.getInstance().getTargetTime();
			long wait = targetTime - elapsed / 1000000;
			
			if (wait < 0)
				wait = targetTime;
			
			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		img = new BufferedImage(ConfigSingleton.getInstance().getCanvasWidth(), 
											ConfigSingleton.getInstance().getCanvasHeight(), 1);
		g2d = (Graphics2D) img.getGraphics();
		running = true;
		
		aGameMediator = GameMediator.getInstance();
		aGameMediator.initMediator();
		aGameMediator.setCanvas(this);
	}

	private void drawToScreen() {
		ConfigSingleton singleton = ConfigSingleton.getInstance();
		int width = singleton.getCanvasWidth() * singleton.getScale();
		int height = singleton.getCanvasHeight() * singleton.getScale();
		
		Graphics g2 = getGraphics();
		g2.drawImage(img, 0, 0, width, height, null);
		g2.dispose();
	}

	public void noticeToGameOver(int score) {
		aGameWindow.setGameOverComponents(score);
		running = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(acceptKeyPressed){
			Keys.keySet(e.getKeyCode(), true);
			Keys.setKeyStateNew(e.getKeyCode(), true);
		}
		acceptKeyPressed = false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
		acceptKeyPressed = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}