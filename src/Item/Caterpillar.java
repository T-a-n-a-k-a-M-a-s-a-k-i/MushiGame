package Item;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import Func.ActiveObject;
import Func.ConfigSingleton;
import Func.GameMediator;
import Func.Keys;

public class Caterpillar extends Entity implements ActiveObject {
	private final static String IMG_TOP = "../images/head-back.gif";
	private final static String IMG_RIGHT = "../images/head-right.gif";
	private final static String IMG_DOWN = "../images/head-front.gif";
	private final static String IMG_LEFT = "../images/head-left.gif";
	
	private static BufferedImage upImage = null;
	private static BufferedImage rightImage = null;
	private static BufferedImage downImage = null;
	private static BufferedImage leftImage = null;
	
	private Hashtable<Integer, Integer> turns;
	private ArrayList<Joint> joints;
	private int turnCount;
	private int score;
	
	public ArrayList<Joint> getJoints(){
		return joints;
	}
	
	public int getScore(){
		return score;
	}
	
	public Caterpillar(){
		super();
		
		score = 0;
		turnCount = 0;
		joints = new ArrayList<Joint>();
		turns = new Hashtable<Integer, Integer>();
		
		loadImage();
	}
	
	private void loadImage(){
		try {
			if(null == upImage){
				upImage = ImageIO.read(getClass().getResourceAsStream(IMG_TOP));
			}
			if(null == rightImage){
				rightImage = ImageIO.read(getClass().getResourceAsStream(IMG_RIGHT));
			}
			if(null == downImage){
				downImage = ImageIO.read(getClass().getResourceAsStream(IMG_DOWN));
			}
			if(null == leftImage){
				leftImage = ImageIO.read(getClass().getResourceAsStream(IMG_LEFT));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void born(){
		Joint joint;
		for(int i = 0; i < ConfigSingleton.getInstance().getInitialJointNumber(); i++){
			joint = new Joint();
			joint.setMoveLeft();
			joints.add(joint);
		}
		
		this.setMoveLeft();
		this.setSpeed(ConfigSingleton.getInstance().getSpeedInitial());
		initJointsPosition();
	}
	
	/* WARNING: the update of joints' position has several bugs when speed up */
	@Override
	public void update(){
		updateDirection();
		
		super.getNextPosition();
		setPosition(nextX, nextY);
		GameMediator.getInstance().collisionCheckAll(this);
		
		updateJointsPosition();
	}
	
	@Override
	public void setSpeed(int spd){
		super.setSpeed(spd);
		
		for(Joint joint: joints){
			joint.setSpeed(spd);
		}
	}
	
	@Override
	public void setMoveUp() {
		super.setMoveUp();
		setImage(upImage);
	}

	@Override
	public void setMoveRight() {
		super.setMoveRight();
		setImage(rightImage);
	}

	@Override
	public void setMoveDown() {
		super.setMoveDown();
		setImage(downImage);
	}

	@Override
	public void setMoveLeft() {
		super.setMoveLeft();
		setImage(leftImage);
	}
	
	@Override
	public void draw(Graphics2D g2d){
		if(this.isMovingUp()){
			super.draw(g2d);
			drawJoints(g2d);
		}
		else{
			drawJoints(g2d);
			super.draw(g2d);
		}
	}
	
	private void drawJoints(Graphics2D g2d){
		ArrayList<Entity> ordered = reorder();
		for(Entity next : ordered){
			next.draw(g2d);
		}
	}
	
	private ArrayList<Entity> reorder(){
		ArrayList<Entity> ordered = new ArrayList<Entity>();
		
		Entity prev;
		int idx = 0;
		
		prev = this;
		for(Entity next : joints){
			if(prev.isMovingUp()){
				ordered.add(next);
				idx += 1;
			}
			else{
				ordered.add(idx, next);
			}
			
			prev = next;
		}
		
		return ordered;
	}

	private void updateDirection(){
		 if(Keys.isDown(Keys.SPACE) && Keys.getKeyStateNew(Keys.SPACE) && (!turns.containsValue(0))){
			 turns.put(++turnCount, 0);
			 this.turnRight();
		}
	}
	
	private void updateJointsPosition(){
		Hashtable<Integer, Integer> turnsNext = new Hashtable<Integer, Integer>();

		boolean turned = false;
		Entity prev = this;
		Entity joint = null;
		
		for(int i = 0; i < joints.size(); i++){
			joint = joints.get(i);
			turned = false;
			
			if(turns.containsValue(i)){
				int tc = -1;
				for(int j = 1; j <= turnCount; j++){
					if(turns.containsKey(j) && i == turns.get(j)){
						tc = j;
						break;
					}
				}
				
				if(tc > 0){
					if(!turns.containsValue(i+1)){
						turned = turnJoint(prev, joint);
						
						if(turned){
							joint.turnRight();
							turns.remove(tc, i);
							turns.put(tc, i + 1);
						}
					}
					
					if(!turned){
						turnsNext.put(tc, i);
					}
				}
			}
			
			if(!turned){
				joint.update();
			}
			
			prev = joint;
		}
		
		turns = turnsNext;
	}
	
	private boolean turnJoint(Entity prev, Entity joint){
		boolean retvalue = false;
		
		int moveDistance = joint.getSpeed();
		int alignDistance, leftDistance;
		
		if(prev.isMovingUp()){
			alignDistance = Math.abs(prev.getCenterX() - joint.getCenterX());
			leftDistance = moveDistance - alignDistance;
			
			if(0 <= leftDistance){
				joint.setCenterX(prev.getCenterX());
				joint.setCenterY(joint.getCenterY() - leftDistance);
				retvalue = true;
			}
		}
		else if(prev.isMovingRight()){
			alignDistance = Math.abs(prev.getCenterY() - joint.getCenterY());
			leftDistance = moveDistance - alignDistance;
			
			if(0 <= leftDistance){
				joint.setCenterX(joint.getCenterX() + leftDistance);
				joint.setCenterY(prev.getCenterY());
				retvalue = true;
			}
		}
		else if(prev.isMovingDown()){
			alignDistance = Math.abs(prev.getCenterX() - joint.getCenterX());
			leftDistance = moveDistance - alignDistance;
			
			if(0 <= leftDistance){
				joint.setCenterX(prev.getCenterX());
				joint.setCenterY(joint.getCenterY() + leftDistance);
				retvalue = true;
			}
		}
		else{
			alignDistance = Math.abs(prev.getCenterY() - joint.getCenterY());
			leftDistance = moveDistance - alignDistance;
			
			if(0 <= leftDistance){
				joint.setCenterX(joint.getCenterX() - leftDistance);
				joint.setCenterY(prev.getCenterY());
				retvalue = true;
			}
		}
		
		return retvalue;
	}
	
	private void initJointsPosition(){
		if(0 < joints.size()){
			Entity prev = this;
			Entity next;
			
			for(int i = 0; i < joints.size(); i++){
				next = joints.get(i);
				adjustPosition(prev, next);
				prev = next;
			}
		}
	}
	
	private void adjustPosition(Entity prev, Entity next){
		final int DISTENCE = 9;
		int connectorX = prev.getCenterX();
		int connectorY = prev.getCenterY();
		
		if(next.isMovingUp()){
			connectorY += DISTENCE;
		}
		else if(next.isMovingRight()){
			connectorX -= DISTENCE;
		}
		else if(next.isMovingDown()){
			connectorY -= DISTENCE;
		}
		else{
			connectorX += DISTENCE;
		}
		
		int posx = connectorX - next.getWidth() / 2;
		int posy = connectorY - next.getHeight() / 2;
		
		next.setPosition(posx, posy);
	}

	@Override
	public void hitJoint() {
		GameMediator.getInstance().noticeToGameOver();
	}

	@Override
	public void hitTree() {
		GameMediator.getInstance().noticeToGameOver();
	}
	
	@Override
	public void hitApple() {
		score++;
		this.speedUp();
		this.addJoint();
	}
	
	@Override
	public void hitBanana() {
		speedDown();
	}
	
	private void addJoint(){
		Joint newJoint = new Joint();
		Joint endJoint = joints.get(joints.size()-1);
		
		if (endJoint.isMovingUp())
			newJoint.setMoveUp();
		else if (endJoint.isMovingDown())
			newJoint.setMoveDown();
		else if (endJoint.isMovingLeft())
			newJoint.setMoveLeft();
		else if (endJoint.isMovingRight())
			newJoint.setMoveRight();
		
		newJoint.setSpeed(this.speed);
		joints.add(newJoint);
		initJointsPosition();
	}
	
	private void speedUp(){
		int spd = this.getSpeed();
		spd += ConfigSingleton.getInstance().getSpeedIncrement();
		this.setSpeed(spd);
	}
	
	private void speedDown(){
		if(ConfigSingleton.getInstance().getSpeedInitial() < this.getSpeed()){
			int spd = this.getSpeed();
			spd -= ConfigSingleton.getInstance().getSpeedIncrement();
			this.setSpeed(spd);
		}
	}
}
