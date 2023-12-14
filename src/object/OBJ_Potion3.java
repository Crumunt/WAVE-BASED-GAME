package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Potion3 extends SuperObject {

	public OBJ_Potion3() {
		
		name = "Potion";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/obj/potion.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
