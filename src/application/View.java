package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class View {
	
    public GameObject gameObject;

	public View(GameObject gO) {
		this.gameObject = gO;
	}

	public void draw(GraphicsContext gc, Image image) {
		gc.drawImage(image, (int) gameObject.getBody().getPosition().getX(), (int) gameObject.getBody().getPosition().getY(),
				gameObject.getBody().getLength(), gameObject.getBody().getWidth());
	}
	
	

}