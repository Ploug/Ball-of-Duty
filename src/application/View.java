package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class View {
	
    public GameObject gameObject;

	public View(GameObject gO) {
		this.gameObject = gO;
	}

	public void draw(GraphicsContext gc) {
		Paint oldPaint = gc.getFill();
		gc.setFill(Color.GREEN);

		gc.fillOval((int) gameObject.getBody().getPosition().getX(), (int) gameObject.getBody().getPosition().getY(),
				40, 40);

		gc.setFill(oldPaint);
	}
	
	

}