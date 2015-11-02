package application.communication;

import javafx.geometry.Point2D;

/**
 * Handles position transfer of objects.
 * @author Gruppe 6
 *
 */
public class ObjectPosition
{
    public final int Id;
    public final Point2D Position;

    /**
     * Creates a ObjectPosition with a id of the object and the position.
     * @param id The id of the object.
     * @param position The position of the object.
     */
    public ObjectPosition(int id, Point2D position)
    {
        this.Id = id;
        this.Position = position;
    }

   

}
