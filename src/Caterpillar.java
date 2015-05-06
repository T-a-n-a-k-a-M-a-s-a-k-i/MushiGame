import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Queue;

public class Caterpillar {
	public static int CONFIG_START_X = 160;
	public static int CONFIG_START_Y = 120;
	public static int CONFIG_JOINTNUM = 5;
	
//	private GameMediator mediator;
	private Hashtable<Integer, Integer> turns;
	private ArrayList<CJoint> joints;
	private CHead head;
	
	public Caterpillar(){
		turns = new Hashtable<Integer, Integer>();
		
		head = new CHead(CONFIG_START_X, CONFIG_START_Y);
		head.setMoveLeft();
		head.setSpeed(1);
		
		joints = new ArrayList<CJoint>();
		CJoint joint;
		for(int i = 0; i < CONFIG_JOINTNUM; i++){
			joint = new CJoint();
			joint.setMoveLeft();
			joint.setSpeed(1);
			joints.add(joint);
		}
		
		initJointsPosition();
	}
	
	public void setMediator(GameMediator m){
//		mediator = m;
		
		head.setMediator(m);
		
		for(CJoint joint : joints){
			joint.setMediator(m);
		}
	}
	
	public boolean isMovingUp() {
		return head.isMovingUp();
	}

	public boolean isMovingRight() {
		return head.isMovingRight();
	}

	public boolean isMovingDown() {
		return head.isMovingDown();
	}

	public boolean isMovingLeft() {
		return head.isMovingLeft();
	}
	
	public void update(){
		updateDirection();
		head.update();
		
		for(CJoint joint : joints){
			joint.update();
		}
	}
	
	public void draw(Graphics2D g2d){
		ArrayList<Entity> ordered = reorder();
		
		for(Entity next : ordered){
			next.draw(g2d);
		}
	}
	
	private ArrayList<Entity> reorder(){
		ArrayList<Entity> ordered = new ArrayList<Entity>();
		
		Entity prev;
		int idx = 0;
		
		prev = head;
		ordered.add(prev);
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
		updateJointsDirection();
		
		if(Keys.isDown(Keys.UP) && !isMovingUp() && !isMovingDown()){
			head.setMoveUp();
			turns.put(0, Keys.UP);
		}
		else if(Keys.isDown(Keys.RIGHT) && !isMovingRight() && !isMovingLeft()){
			head.setMoveRight();
			turns.put(0, Keys.RIGHT);
		}
		else if(Keys.isDown(Keys.DOWN) && !isMovingDown()&& !isMovingUp()){
			head.setMoveDown();
			turns.put(0, Keys.DOWN);
		}
		else if(Keys.isDown(Keys.LEFT) && !isMovingLeft()&& !isMovingRight()){
			head.setMoveLeft();
			turns.put(0, Keys.LEFT);
		}
	}
	
	private void updateJointsDirection(){
		Hashtable<Integer, Integer> turnsNext = new Hashtable<Integer, Integer>();
		
		for(int idx : turns.keySet()){
			int turn = turns.get(idx);
			
			Entity prev = (0 == idx ? head : joints.get(idx - 1));
			Entity joint = joints.get(idx);
			boolean turned = false;
			
			if(Keys.UP == turn){
				if(prev.getCenterX() == joint.getCenterX()){
					joint.setMoveUp();
					turned = true;
				}
			}
			else if(Keys.RIGHT == turn){
				if(prev.getCenterY() == joint.getCenterY()){
					joint.setMoveRight();
					turned = true;
				}
			}
			else if(Keys.DOWN == turn){
				if(prev.getCenterX() == joint.getCenterX()){
					joint.setMoveDown();
					turned = true;
				}
			}
			else{
				if(prev.getCenterY() == joint.getCenterY()){
					joint.setMoveLeft();
					turned = true;
				}
			}
			
			if(turned){
				if(idx + 1 < joints.size()){
					turnsNext.put(idx + 1, turn);
				}
			}
			else{
				turnsNext.put(idx, turn);
			}
			
		}

		turns = turnsNext;
	}
	
	private void initJointsPosition(){
		if(0 < joints.size()){
			Entity prev = head;
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

}
