import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity {
	protected int x;
	protected int y;
	protected int nextX;
	protected int nextY;
	protected int width;
	protected int height;
	protected BufferedImage image;
	protected GameMediator mediator;
	
	protected int speed;
	protected boolean up;
	protected boolean right;
	protected boolean down;
	protected boolean left;
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getCenterX(){
		return x + width / 2;
	}
	
	public int getCenterY(){
		return y + height / 2;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setMoveUp() {
		stop();
		up = true;
	}

	public void setMoveRight() {
		stop();
		right = true;
	}

	public void setMoveDown() {
		stop();
		down = true;
	}

	public void setMoveLeft() {
		stop();
		left = true;
	}
	
	public void stop(){
		up = false;
		right = false;
		down = false;
		left = false;
	}

	public boolean isMovingUp() {
		return up;
	}

	public boolean isMovingRight() {
		return right;
	}

	public boolean isMovingDown() {
		return down;
	}

	public boolean isMovingLeft() {
		return left;
	}
	
	public void setSpeed(int spd){
		speed = spd;
	}
	
	public void setMediator(GameMediator m){
		mediator = m;
	}
	
	protected void setImage(BufferedImage img){
		if(null != img){
			image = img;
			width = img.getWidth();
			height = img.getHeight();
		}
	}
	
	public void setPosition(int posx, int posy){
		x = posx;
		y = posy;
		nextX = x;
		nextY = y;
	}
	
	public Entity(){}
	
	public Entity(int posx, int posy){
		x = posx;
		y = posy;
		nextX = x;
		nextY = y;
	}

	public void update(){
		getNextPosition();
		setPosition(nextX, nextY);
	}
	
	private void getNextPosition() {
		if (up) {
			nextX = x;
			nextY = y - speed;
		}
		else if(right){
			nextX = x + speed;
			nextY = y;
		}
		else if(down){
			nextX = x;
			nextY = y + speed;
		}
		else if(left){
			nextX = x - speed;
			nextY = y;
		}
	}
	
	public void draw(Graphics2D g2d){
		g2d.drawImage(
				image,
				x,
				y,
				null
			);
	};

}
