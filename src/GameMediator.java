import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class GameMediator {
	public static int CONFIG_APPLENUM = 5;
	public static int CONFIG_TREENUM = 10;
	
	private Apple apple = new Apple();
	private ArrayList<Tree> trees;
	private Caterpillar mushi = new Caterpillar();
	private TileMap tilemap;
	private HUD hud;
	private Canvas canvas;
	
	public GameMediator(){
		hud = new HUD();
		
		tilemap = new TileMap();
		placeTrees();
		
		mushi = new Caterpillar();
        mushi.setMediator(this);
		
		placeApples();
	}
	
	public GameMediator(Canvas canvas){
		hud = new HUD();
		
		tilemap = new TileMap();
		placeTrees();
		
		mushi = new Caterpillar();
        mushi.setMediator(this);
		
		placeApples();
		
		this.canvas = canvas;
	}
	
	private void placeTrees(){
		trees = new ArrayList<Tree>();
		
		Tree t;
		for(int i = 0; i < Canvas.HEIGHT; i += Tree.TREE_HEIGHT){
			if(0 == i || Tree.TREE_HEIGHT >= (Canvas.HEIGHT - i)){
				for(int j = 0; j < Canvas.WIDTH; j += Tree.TREE_WIDTH){
					t = new Tree(j, i);
					trees.add(t);
				}
			}
			else{
				t = new Tree(0, i);
				trees.add(t);
				
				t = new Tree(Canvas.WIDTH - Tree.TREE_WIDTH, i);
				trees.add(t);
			}
		}
		for(int i = 0; i < CONFIG_TREENUM; i++){
			Tree obstacleTree = new Tree();
			randomPlace(obstacleTree);
			trees.add(obstacleTree);
		}
	}

	public void placeApples(){
		apple = new Apple();
		randomPlace(apple);
	}
	
	public void update(){
		mushi.update();
	}

	public void collisionCheckAll(ActiveObject anActiveObject){
		for(Tree tree : trees){
			tree.collisionCheck(anActiveObject);
		}
		for(CJoint joint : mushi.getJoints()){
			joint.collisionCheck(anActiveObject);
		}
		apple.collisionCheck(anActiveObject);
	}

	public void addJoint(){
		mushi.addJoint();
	}
	
	public void draw(Graphics2D g2d){
		hud.draw(g2d);
		
		tilemap.draw(g2d);
		
		for(Tree t : trees){
			t.draw(g2d);
		}
		apple.draw(g2d);
		mushi.draw(g2d);
	}
	
	public void randomPlace(Entity item){
		int[] pos = newRandomPosition(item);
		item.setPosition(pos[0], pos[1]);
	}
	
	/* question */
	public int[] newRandomPosition(Entity item){
		int[] pos = {0, 0};
		
		Random ran = new Random();
		
		int maxX = Canvas.WIDTH - 2 * Tree.TREE_WIDTH - item.width;
		int minX = Tree.TREE_WIDTH;
		pos[0] = minX +  ran.nextInt(maxX);
		
		int maxY = Canvas.HEIGHT - 2 * Tree.TREE_HEIGHT - item.height;
		int minY = Tree.TREE_HEIGHT;
		pos[1] = minY +  ran.nextInt(maxY);
		
		return pos;
	}
	
	public boolean checkPositionValid(Entity item){
		boolean valid = true;
		//for(Tree tree : trees){
			//if((Math.abs(tree.getCenterX()-item.getCenterX())<tree.getWidth()/2+item.getWidth()/2) && (Math.abs(tree.getCenterY()-item.getCenterY())<tree.getHeight()/2+item.getHeight()/2))
				//valid = false;
		//}
		//if((Math.abs(apple.getCenterX()-item.getCenterX())<apple.getWidth()/2+item.getWidth()/2) && (Math.abs(apple.getCenterY()-item.getCenterY())<apple.getHeight()/2+item.getHeight()/2))
			//valid = false;
		//for(CJoint joint : mushi.getJoints()){
			//if((Math.abs(joint.getCenterX()-item.getCenterX())<joint.getWidth()/2+item.getWidth()/2) && (Math.abs(joint.getCenterY()-item.getCenterY())<joint.getHeight()/2+item.getHeight()/2))
				//valid = false;
		//}
		return valid;
	}
	
	public void noticeToGameOver(){
		//hard coding
		this.canvas.noticeToGameOver(mushi.getJoints().size()-5);
	}
}
