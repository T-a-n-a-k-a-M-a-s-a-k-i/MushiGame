import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class GameMediator {
	public static int CONFIG_APPLENUM = 5;
	
	private ArrayList<Apple> apples;
	private ArrayList<Tree> trees;
	private Caterpillar mushi;
	private TileMap tilemap;
	private HUD hud;
	
	public GameMediator(){
		hud = new HUD();
		
		tilemap = new TileMap();
		placeTrees();
		
		mushi = new Caterpillar();
		
		placeApples();
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
	}

	private void placeApples(){
		apples = new ArrayList<Apple>();
		
		Apple apple;
		for(int i = 0; i < CONFIG_APPLENUM; i++){
			apple = new Apple();
			randomPlace(apple);
			apples.add(apple);
		}
	}
	
	public void update(){
		mushi.update();
	};
	
	public void draw(Graphics2D g2d){
		hud.draw(g2d);
		
		tilemap.draw(g2d);
		
		for(Tree t : trees){
			t.draw(g2d);
		}
		
		for(Apple apple : apples){
			apple.draw(g2d);
		}
		
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
		
		return valid;
	}


}
