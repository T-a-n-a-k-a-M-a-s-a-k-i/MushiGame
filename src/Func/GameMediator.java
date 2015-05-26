package Func;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import Item.Apple;
import Item.Banana;
import Item.Entity;
import Item.Caterpillar;
import Item.Joint;
import Item.TileMap;
import Item.Tree;

public class GameMediator {
	private static GameMediator instance = null;
	
	private ArrayList<Tree> trees;
	private Caterpillar mushi;
	private Banana banana;
	private TileMap tilemap;
	private Canvas canvas;
	private Apple apple;
	
	public void setCanvas(Canvas c){
		canvas = c;
	}
	
	public static GameMediator getInstance(){
		if(null == instance){
			instance = new GameMediator();
		}
		
		return instance;
	}
	
	public GameMediator(){
		tilemap = new TileMap();
		
		trees = new ArrayList<Tree>();
		placeTrees();
		
		mushi = new Caterpillar();
		randomPlace(mushi);
		mushi.born();
		
		apple = new Apple();
		randomPlace(apple);
		
		banana = new Banana();
		randomPlace(banana);
	}
	
	private void placeTrees(){
		ConfigSingleton singleton = ConfigSingleton.getInstance();
		
		Tree t;
		for(int i = 0; i < singleton.getCanvasHeight(); i += singleton.getEntityHeight()){
			if(0 == i || singleton.getEntityHeight() >= (singleton.getCanvasHeight() - i)){
				for(int j = 0; j < singleton.getCanvasWidth(); j += singleton.getEntityWidth()){
					t = new Tree(j, i);
					trees.add(t);
				}
			}
			else{
				t = new Tree(0, i);
				trees.add(t);
				
				t = new Tree(singleton.getCanvasWidth() - singleton.getEntityWidth(), i);
				trees.add(t);
			}
		}
		
		for(int i = 0; i < ConfigSingleton.getInstance().getObstacleTreeNumber(); i++){
			Tree obstacleTree = new Tree();
			trees.add(obstacleTree);
			randomPlace(obstacleTree);
		}
	}
	
	public void update(){
		mushi.update();
	}

	public void collisionCheckAll(ActiveObject anActiveObject){
		boolean doHit = false;
		
		if(false == doHit){
			for(Tree tree : trees){
				doHit = tree.collisionCheck(anActiveObject);
				if(doHit) break;
			}
		}
		
		if(false == doHit){
			Joint joint = null;
			
			for(int i = 1; i < mushi.getJoints().size(); i++){
				joint = mushi.getJoints().get(i);
				doHit = joint.collisionCheck(anActiveObject);
				if(doHit) break;
			}
		}
		
		if(false == doHit){
			doHit = apple.collisionCheck(anActiveObject);
		}
		
		if(false == doHit){
			doHit = banana.collisionCheck(anActiveObject);
		}
	}
	
	public void draw(Graphics2D g2d){
		tilemap.draw(g2d);
		
		for(Tree t : trees){
			t.draw(g2d);
		}
		
		apple.draw(g2d);
		banana.draw(g2d);
		mushi.draw(g2d);
	}
	
	public void randomPlace(Entity anEntity){
		do{
			int[] pos = newRandomPosition(anEntity);
			anEntity.setPosition(pos[0], pos[1]);
		}while(false == this.checkPositionValid(anEntity));
	}
	
	public int[] newRandomPosition(Entity item){
		int[] pos = {0, 0};
		
		Random ran = new Random();
		
		ConfigSingleton singleton = ConfigSingleton.getInstance();
		
		int maxX = singleton.getCanvasWidth() 
				- 2 * singleton.getEntityWidth() - item.getWidth();
		int minX = singleton.getEntityWidth();
		pos[0] = minX +  ran.nextInt(maxX);
		
		int maxY = singleton.getCanvasHeight() 
				- 2 * singleton.getEntityHeight() - item.getHeight();
		int minY = singleton.getEntityHeight();
		pos[1] = minY +  ran.nextInt(maxY);
		
		return pos;
	}
	
	private boolean checkPositionValid(Entity anEntity){
		boolean retvalue = true;
		
		final int minDis = ConfigSingleton.getInstance().getObstacleMinDistance();
		
		if(true == retvalue && null != trees){
			for(Tree tree : trees){
				if(!tree.equals(anEntity)){
					if(minDis > tree.calDistanceWithEntity(anEntity)){
						retvalue = false;
						break;
					}
				}
			}
		}
		
		if(true == retvalue && null != mushi){
			for(Joint joint : mushi.getJoints()){
				if(!joint.equals(anEntity)){
					if(minDis > joint.calDistanceWithEntity(anEntity)){
						retvalue = false;
						break;
					}
				}
			}
		}
		
		if(true == retvalue && null != mushi 
				&& (!mushi.equals(anEntity))){
			if(minDis > mushi.calDistanceWithEntity(anEntity)){
				retvalue = false;
			}
		}
		
		if(true == retvalue && null != apple 
				&& (!apple.equals(anEntity))){
			if(minDis > apple.calDistanceWithEntity(anEntity)){
				retvalue = false;
			}
		}
		
		if(true == retvalue && null != banana 
				&& (!banana.equals(anEntity))){
			if(minDis > banana.calDistanceWithEntity(anEntity)){
				retvalue = false;
			}
		}
		
		return retvalue;
	}
	
	public void noticeToGameOver(){
		this.canvas.noticeToGameOver(mushi.getScore());
	}
}
