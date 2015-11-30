package application.engine.entities;

import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.View;
import application.engine.rendering.TranslatedPoint;
import javafx.scene.image.Image;

/**
 * Walls are the main component of level design, they are solid rectangles that cannot be killed.
 * 
 * @author Frederik
 *
 */
public class Wall extends GameObject
{

    public static final Body.Geometry TYPE = Body.Geometry.RECTANGLE;

   /**
    * Creates a wall with an id.
    * @param id The id of the wall.
    */
    public Wall(int id)
    {
        super(id);

    }

    /**
     * Creates a wall with the needed information.
     * 
     * @param id
     *            The id of the game object, used mainly by the Client Map to identify objects.
     * @param position
     *            The position of the wall.
     * @param height
     *            The height of the wall.
     * @param width
     *            The width of the wall.
     */
    public Wall(int id, TranslatedPoint position, double height, double width, Image image)
    {
        super(id);
        this.body = new Body(this, position, height, width, TYPE);
        this.view = new View(this, image);
    }
}
