package application.engine.factories;

import java.util.HashMap;
import java.util.Map;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import application.engine.entities.BoDCharacter;
import application.engine.entities.Wall;
import application.engine.game_object.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * Entities that are original build server side is build here.
 * 
 * @author Gruppe6
 *
 */
public class EntityFactory
{
    /**
     * The type of entity.
     * 
     * @author Gruppe6
     *
     */
    public enum EntityType
    {
        ENEMY_CHARACTER, WALL
    }

    private static Map<EntityType, Image> defaultImages;

    static
    {
        defaultImages = new HashMap<>();
        defaultImages.put(EntityType.ENEMY_CHARACTER, new Image("images/ball_red.png"));
        defaultImages.put(EntityType.WALL, new Image("images/wall_box.png"));
    }

    /**
     * Returns an entity of the inputted type created from information in a DTO, often because it is created on the server.
     * 
     * @param dto
     *            The DTO.
     * @param type
     *            The type of the entity.
     * @return A game object based on the DTO.
     */
    public static GameObject getEntity(GameObjectDTO dto, EntityType type)
    {

        BodyDTO body = dto.getBody();
        Point2D position = new Point2D(body.getPosition().getX(), body.getPosition().getY());
        switch (type)
        {
            case WALL:
                GameObject wall = new Wall(dto.getId(), position, body.getWidth(), body.getHeight(), defaultImages.get(EntityType.WALL));
                return wall;
            case ENEMY_CHARACTER:
                GameObject enemy = new BoDCharacter(dto.getId(), position, body.getWidth(), body.getHeight(), 0,
                        defaultImages.get(EntityType.ENEMY_CHARACTER));
                // TODO get velocity from server so we can update the enemies position while inbetween
                // position updates and if enemy has special cosmetics it's passed through here.
                return enemy;
        }
        return null;
    }

    /**
     * Creates a default dummy game object with an id and type.
     * 
     * @param id
     *            The id of the dummy object.
     * @param type
     *            The type of the dummy object.
     * @return A dummy object with default values a type and an id..
     */
    public static GameObject getDefaultEntity(int id, EntityType type)
    {
        switch (type)
        {
            case WALL:
                return new Wall(id);
            case ENEMY_CHARACTER:
                GameObject enemy = new BoDCharacter(id, new Point2D(0, 0), 50, 50, 0, defaultImages.get(EntityType.ENEMY_CHARACTER));
                return enemy;
        }
        return null;

    }

}
