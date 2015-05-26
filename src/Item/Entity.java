package Item;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity {
	protected int x;
	protected int y;
	protected int nextX;
	protected int nextY;
	protected int width;
	protected int height;
	
	protected int speed;
	protected boolean up;
	protected boolean right;
	protected boolean down;
	protected boolean left;
	
	protected BufferedImage image;
	
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
	
	public void setCenterX(int posX){
		x = posX - width / 2;
	}
	
	public void setCenterY(int posY){
		y = posY - height / 2;
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
	
	public void turnRight(){
		if(this.isMovingLeft()){
			this.setMoveUp();
		}
		else if(this.isMovingUp()){
			this.setMoveRight();
		}
		else if(this.isMovingRight()){
			this.setMoveDown();
		}
		else{
			this.setMoveLeft();
		}
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
	
	public int getSpeed(){
		return speed;
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
	
	public void getNextPosition() {
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
	}
	
	public int calDistanceWithEntity(Entity anEntity){
		int retvalue = Integer.MAX_VALUE;
		
		if(null != anEntity){
			double dis = Math.sqrt((anEntity.getCenterX() - this.getCenterX()) 
											* (anEntity.getCenterX() - this.getCenterX())
											+ (anEntity.getCenterY() - this.getCenterY())
											* (anEntity.getCenterY() - this.getCenterY()));
			
			dis = dis - anEntity.getWidth()/2 - this.getWidth()/2;
			retvalue = (int)dis;
		}
		
		return retvalue;
	}
}
