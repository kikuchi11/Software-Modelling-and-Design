
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;

public class ImageDocument implements Effect {

	public IIOImage BufferedImage;
	public Effect[] transforms;
	public String outputFile;
	
	public ImageDocument(BufferedImage img) {
		
	}
	
	public boolean addTransform(Effect t) {
		
	}
	
	public void renderImage(String outFile) {
		
	}
	
}
