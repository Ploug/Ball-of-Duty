package application.engine.game_object;

import application.engine.entities.BoDCharacter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Handles how the game object should be rendered
 * 
 * @author Frederik
 *
 */
public class View
{

    public GameObject gameObject;
    private Image image;

    /**
     * Creates a view for the game object with an image of how the game object should look
     * 
     * @param gameObject
     *            The gameobject the view is given to.
     * @param image
     *            The image defining how the game object should look.
     */
    public View(GameObject gameObject, Image image)
    {
        this.gameObject = gameObject;
        this.image = image;

    }

    /**
     * Draws the game object on a graphics context.
     * 
     * @param gc
     *            The graphics context the game object will be drawn on.
     */
    public void draw(GraphicsContext gc)
    {
        // System.out.println((int) gameObject.getBody().getPosition().getX()+" "+(int) gameObject.getBody().getPosition().getY()+" "+
        // gameObject.getBody().getLength()+" "+ gameObject.getBody().getWidth());
        gc.drawImage(image, (int)gameObject.getBody().getPosition().getX(), (int)gameObject.getBody().getPosition().getY(),
                gameObject.getBody().getHeight(), gameObject.getBody().getWidth());
        // debug info
        // gc.setLineWidth(2);
        // gc.setStroke(Color.BLACK);
        // gc.strokeText(gameObject.getId()+"", gameObject.body.getCenter().getX(),gameObject.body.getCenter().getY());

        if (gameObject.getPhysics() != null && gameObject instanceof BoDCharacter)
        {
            /* DEBUG MODE */
            // Velocity vector drawn.
            double centerX = gameObject.getBody().getCenter().getX();
            double centerY = gameObject.getBody().getCenter().getY();
            gc.setLineWidth(3);
            gc.setStroke(Color.BLACK);
            gc.strokeLine(centerX, centerY, centerX + gameObject.getPhysics().getVelocity().getX(),
                    centerY + gameObject.getPhysics().getVelocity().getY());
            // Draw orientation vector.
            gc.setStroke(Color.GREEN);
            gc.strokeLine(centerX, centerY, centerX + gameObject.getBody().getOrientation().getX(),
                    centerY + gameObject.getBody().getOrientation().getY());
        }

    }

}