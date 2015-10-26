package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class View
{

    public GameObject gameObject;

    public View(GameObject gO)
    {
        this.gameObject = gO;
    }


    double centerX;
    double centerY;
    public void draw(GraphicsContext gc, Image image)
    {
        gc.drawImage(image, (int) gameObject.getBody().getPosition().getX(), (int) gameObject.getBody().getPosition().getY(),
                gameObject.getBody().getLength(), gameObject.getBody().getWidth());

        
        /* DEBUG MODE */
        // Velocity vector drawn.
        centerX = gameObject.getBody().getCenter().getX();
        centerY = gameObject.getBody().getCenter().getY();
        gc.setLineWidth(3);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(centerX, centerY,
                centerX + gameObject.getPhysics().getVelocity().getX(),
                centerY + gameObject.getPhysics().getVelocity().getY());
//        Draw orientation vector.
        gc.setStroke(Color.GREEN);
        gc.strokeLine(centerX, centerY,
                centerX + gameObject.getBody().getOrientation().getX(),
                centerY + gameObject.getBody().getOrientation().getY());
                
               
    }

}