import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Tree extends Entity implements PassiveObject {
	public final static int TREE_WIDTH = 16;
	public final static int TREE_HEIGHT = 16;
	
	public Tree(){
		super();
		loadImage();
	}
	
	public Tree(int posx, int posy){
		super(posx, posy);
		
		loadImage();
	}

	private void loadImage(){
		try {
			BufferedImage tileSet = ImageIO.read(getClass().getResourceAsStream("images/tileset.gif"));
			BufferedImage img = tileSet.getSubimage(0, 16, TREE_WIDTH, TREE_HEIGHT);
			setImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void collisionCheck(ActiveObject anActiveObject) {
	}
}
