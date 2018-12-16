
import java.awt.image.BufferedImage;

import javax.imageio.IIOImage;

public interface Effect {
	public BufferedImage applyEffect(IIOImage BufferedImage);
}
