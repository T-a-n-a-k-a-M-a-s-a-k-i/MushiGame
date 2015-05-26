package Item;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Func.ConfigSingleton;

public class TileMap {
	private static BufferedImage tileImage;
	
	public TileMap(){
		try {
			if(null == tileImage){
				tileImage = ImageIO.read(getClass().getResourceAsStream("../images/tile.gif"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d){
		if(null != tileImage){
			for(int i = 0; i < ConfigSingleton.getInstance().getCanvasWidth(); i += tileImage.getWidth()){
				for(int j = 0; j < ConfigSingleton.getInstance().getCanvasHeight(); j += tileImage.getHeight()){
					g2d.drawImage(tileImage, i, j, null);
				}
			}
		}
	}
}
