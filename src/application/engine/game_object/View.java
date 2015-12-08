package application.engine.game_object;

import application.engine.entities.BoDCharacter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Handles how the game object should be rendered
 * 
 * @author Gruppe 6
 *
 */
public class View
{

    private GameObject gameObject;
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
        gc.drawImage(image, (int)gameObject.getBody().getPosition().getTranslatedX(), (int)gameObject.getBody().getPosition().getTranslatedY(),
                gameObject.getBody().getHeight(), gameObject.getBody().getWidth());
        
        // debug info
        // gc.setLineWidth(2);
        // gc.setStroke(Color.BLACK);
        // gc.strokeText(gameObject.getId()+"", gameObject.body.getCenter().getX(),gameObject.body.getCenter().getY());

        if (gameObject instanceof BoDCharacter)
        {
            BoDCharacter character = (BoDCharacter)gameObject;
            double centerX = gameObject.getBody().getCenter().getTranslatedX();
            double centerY = gameObject.getBody().getCenter().getTranslatedY();
            if (gameObject.getPhysics() != null)
            {
                gc.setLineWidth(3);
                /*
                 * DEBUG MODE // Velocity vector drawn. gc.setStroke(Color.BLACK); gc.strokeLine(centerX, centerY, centerX +
                 * gameObject.getPhysics().getVelocity().getX(), centerY + gameObject.getPhysics().getVelocity().getY());
                 */
                // Draw orientation vector.
                gc.setStroke(Color.BLACK);
                gc.strokeLine(centerX, centerY, centerX + gameObject.getBody().getOrientation().getX(),
                        centerY + gameObject.getBody().getOrientation().getY());
            }
            if (gameObject.getHealth() != null)
            {
                int healthBoxHeight = 8;
                int healthValueBoxWidth = (int)(0.4 * gameObject.getHealth().getValue());
                int maxHealthBoxWidth = (int)(0.4 * gameObject.getHealth().getMax());
                int healthBoxX = (int)(centerX - maxHealthBoxWidth / 2);
                int healthBoxY = (int)(gameObject.getBody().getPosition().getTranslatedY() - healthBoxHeight - 2);

                gc.setFill(Color.RED);
                gc.fillRect(healthBoxX, healthBoxY, maxHealthBoxWidth, healthBoxHeight);
                gc.setFill(Color.GREENYELLOW);
                gc.fillRect(healthBoxX, healthBoxY, healthValueBoxWidth, healthBoxHeight);
            }
            double bottomX = gameObject.getBody().getCenter().getTranslatedX();
            double bottomY = gameObject.getBody().getCenter().getTranslatedY() + (gameObject.getBody().getHeight() / 2) + 15;
            gc.setLineWidth(1);
            gc.setFont(Font.font("Verdana", 12));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText(character.getNickname(), bottomX, bottomY, 200);
            gc.setTextAlign(TextAlignment.LEFT);

        }
    }
}
