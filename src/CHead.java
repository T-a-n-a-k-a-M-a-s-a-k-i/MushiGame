import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class CHead extends Entity implements ActiveObject {
	private final static String IMG_TOP = "images/head-back.gif";
	private final static String IMG_RIGHT = "images/head-right.gif";
	private final static String IMG_DOWN = "images/head-front.gif";
	private final static String IMG_LEFT = "images/head-left.gif";
	
	public CHead(){
		super();
		loadImage(IMG_DOWN);
	}
	
	public CHead(int posx, int posy){
		super(posx, posy);
		loadImage(IMG_DOWN);
	}

	@Override
	public void update(){
		mediator.collisionCheckAll(this);
		super.getNextPosition();
		setPosition(nextX, nextY);
	}
	
	public void setMoveUp() {
		super.setMoveUp();
		loadImage(IMG_TOP);
	}

	public void setMoveRight() {
		super.setMoveRight();
		loadImage(IMG_RIGHT);
	}

	public void setMoveDown() {
		super.setMoveDown();
		loadImage(IMG_DOWN);
	}

	public void setMoveLeft() {
		super.setMoveLeft();
		loadImage(IMG_LEFT);
	}
	
	@Override
	public void hitJoint() {
		mediator.noticeToGameOver();
	}

	@Override
	public void hitTree() {
		mediator.noticeToGameOver();
	}

	@Override
	public void hitApple() {
		mediator.addJoint();
	}
	
	private void loadImage(String path){
		try {
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream(path));
			setImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
