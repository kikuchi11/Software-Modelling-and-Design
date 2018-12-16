import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;


public class ImageLoader {
	String fileName;
	
	public ImageLoader(String fileName) {
		this.fileName = fileName;
	}
	
	public ImageDocument loadImage() {
		
		
	}
	
	public static void writeImage(Image img, String outputFile) {
		
	}
	
	Image img = Image.IO.read(fileName)
	doc = new ImageDocument(img);
}
