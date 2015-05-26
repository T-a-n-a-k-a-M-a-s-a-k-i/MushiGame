package Item;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Func.ActiveObject;
import Func.ConfigSingleton;
import Func.GameMediator;
import Func.PassiveObject;

public class Tree extends Entity implements PassiveObject {
	private static BufferedImage treeImage = null;
	
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
			if(null == treeImage){
				treeImage = ImageIO.read(getClass().getResourceAsStream("../images/tree.gif"));
			}
			
			setImage(treeImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean collisionCheck(ActiveObject anActiveObject) {
		Entity anEntity = (Entity)anActiveObject;
		
		if(this.calDistanceWithEntity(anEntity) < ConfigSingleton.getInstance().getCollisionDetectionDistance()){
			anActiveObject.hitTree();
			GameMediator.getInstance().randomPlace(this);
			return true;
		}
		
		return false;
	}
}
