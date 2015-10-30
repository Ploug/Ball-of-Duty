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
//        System.out.println((int) gameObject.getBody().getPosition().getX()+" "+(int) gameObject.getBody().getPosition().getY()+"  "+
//                gameObject.getBody().getLength()+"  "+ gameObject.getBody().getWidth());
        gc.drawImage(image, (int) gameObject.body.getPosition().getX(), (int) gameObject.body.getPosition().getY(),
                gameObject.body.getHeight(), gameObject.body.getWidth());
        // debug info
//        gc.setLineWidth(2);
//        gc.setStroke(Color.BLACK);
//        gc.strokeText(gameObject.getId()+"", gameObject.body.getCenter().getX(),gameObject.body.getCenter().getY());
        
        if(gameObject.physics != null && gameObject instanceof BoDCharacter)
        {
            /* DEBUG MODE */
            // Velocity vector drawn.
            centerX = gameObject.body.getCenter().getX();
            centerY = gameObject.body.getCenter().getY();
            gc.setLineWidth(3);
            gc.setStroke(Color.BLACK);
            gc.strokeLine(centerX, centerY,
                    centerX + gameObject.physics.getVelocity().getX(),
                    centerY + gameObject.physics.getVelocity().getY());
//            Draw orientation vector.
            gc.setStroke(Color.GREEN);
            gc.strokeLine(centerX, centerY,
                    centerX + gameObject.body.getOrientation().getX(),
                    centerY + gameObject.body.getOrientation().getY());
        }
        
                
               
    }

}