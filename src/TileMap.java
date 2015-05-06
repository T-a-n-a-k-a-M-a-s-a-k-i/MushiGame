import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class TileMap {
	private static BufferedImage tileImage;
	
	public TileMap(){
		try {
			if(null == tileImage){
				BufferedImage tileSet = ImageIO.read(getClass().getResourceAsStream("images/tileset.gif"));
				tileImage = tileSet.getSubimage(16, 0, 16, 16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d){
		if(null != tileImage){
			for(int i = 0; i < Canvas.WIDTH; i += tileImage.getWidth()){
				for(int j = 0; j < Canvas.HEIGHT; j += tileImage.getHeight()){
					g2d.drawImage(tileImage, i, j, null);
				}
			}
		}
	}
}
