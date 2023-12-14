package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Potion extends SuperObject {
	
	public OBJ_Potion () {
		
		name = "Potion 3";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/obj/potion3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
