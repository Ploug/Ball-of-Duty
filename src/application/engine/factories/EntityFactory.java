package application.engine.factories;

import java.util.HashMap;
import java.util.Map;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import application.communication.GameObjectDAO;
import application.engine.entities.BoDCharacter;
import application.engine.entities.Bullet;
import application.engine.entities.Wall;
import application.engine.entities.Bullet.Type;
import application.engine.game_object.GameObject;
import application.engine.game_object.Weapon;
import application.util.Vector2;
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
        ENEMY_CHARACTER, WALL, BULLET;
        private static Map<Integer, EntityType> values = new HashMap<>();

        static
        {

            values.put(BULLET.ordinal(), BULLET);
            values.put(WALL.ordinal(), WALL);
            values.put(ENEMY_CHARACTER.ordinal(), ENEMY_CHARACTER);
        }

        /**
         * Returns a entity type based on an int.
         * 
         * @param x
         *            The integer the entity type is based on.
         * @return A entity type based on an int.
         */
        public static EntityType fromInteger(int x)
        {

            return values.get(x);
        }
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
                BoDCharacter enemy = new BoDCharacter(dto.getId(), position, body.getWidth(), body.getHeight(), 0,
                        defaultImages.get(EntityType.ENEMY_CHARACTER));
                
                // TODO get velocity from server so we can update the enemies position while inbetween
                // position updates and if enemy has special cosmetics it's passed through here.
                return enemy;
        }
        return null;
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
    public static GameObject getEntity(GameObjectDAO data, EntityType type)
    {
        switch (type)
        {
            case ENEMY_CHARACTER:
            {
                BoDCharacter enemy = new BoDCharacter(data.objectId, new Point2D(data.x,data.y), data.width, data.height, 0,
                        defaultImages.get(EntityType.ENEMY_CHARACTER));
                enemy.setNickname(data.nickname);
                return enemy;
            }
            case BULLET:
            {
                return new Bullet(data.objectId, new Point2D(data.x, data.y), data.height, new Vector2(data.velocityX, data.velocityY), data.damage,
                        data.bulletType, (Image)Weapon.getBulletImages().get(data.bulletType), data.ownerId);
            }

            default:
                return null;
        }
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
