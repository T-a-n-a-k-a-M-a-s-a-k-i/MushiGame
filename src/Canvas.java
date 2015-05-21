import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements Runnable, KeyListener {
	public static final int WIDTH = 320;		//20
	public static final int HEIGHT = 240;		//15
	public static final int HEIGHT2 = HEIGHT + 16;
	public static final int SCALE = 2;

	private final int FPS = 30;
	private final int TARGET_TIME = 1000 / FPS;

	private GameMediator aGameMediator;
	private GameWindow aGameWindow;
	
	private BufferedImage img;
	private boolean running;
	private Graphics2D g2d;
	private Thread thread;
	
	public Canvas() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public Canvas(GameWindow aGameWindow) {
		this.aGameWindow = aGameWindow;
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
		setFocusable(true);
		requestFocus();
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

		long start;
		long elapsed;
		long wait;

		while (running) {
			start = System.nanoTime();
			
			Keys.update();
			drawToScreen();
			aGameMediator.update();
			aGameMediator.draw(g2d);
			
			elapsed = System.nanoTime() - start;

			wait = TARGET_TIME - elapsed / 1000000;
			if (wait < 0)
				wait = TARGET_TIME;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		running = true;
		img = new BufferedImage(WIDTH, HEIGHT2, 1);
		g2d = (Graphics2D) img.getGraphics();
		aGameMediator = new GameMediator(this);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT2 * SCALE, null);
		g2.dispose();
	}
	
	public void noticeToGameOver(int score){
		running = false;
		aGameWindow.setGameOverComponents(score);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}