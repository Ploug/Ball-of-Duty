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
import application.engine.entities.specializations.Blaster;
import application.engine.entities.specializations.Heavy;
import application.engine.entities.specializations.Roller;
import application.engine.entities.specializations.Specializations;
import application.engine.game_object.GameObject;
import application.engine.game_object.Weapon;
import application.util.Vector2;
import application.engine.rendering.TranslatedPoint;
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
    public static GameObject getEntity(GameObjectDTO dto)
    {

        BodyDTO body = dto.getBody();
        TranslatedPoint position = new TranslatedPoint(body.getPosition().getX(), body.getPosition().getY());
        EntityType type = EntityType.fromInteger(dto.getType());
        switch (type)
        {
            case WALL:
            {
                GameObject wall = new Wall(dto.getId(), position, body.getWidth(), body.getHeight(), defaultImages.get(EntityType.WALL));
                return wall;
            }

            case ENEMY_CHARACTER:
            {
                BoDCharacter enemy = null;

                switch (Specializations.fromInteger(dto.getSpecialization()))
                {
                    case BLASTER:
                        enemy = new Blaster(dto.getId(), position, defaultImages.get(EntityType.ENEMY_CHARACTER));
                        break;
                    case ROLLER:
                        enemy = new Roller(dto.getId(), position, defaultImages.get(EntityType.ENEMY_CHARACTER));
                        break;
                    case HEAVY:
                        enemy = new Heavy(dto.getId(), position, defaultImages.get(EntityType.ENEMY_CHARACTER));
                        break;
                    default:
                        break;
                }
                // TODO get velocity from server so we can update the enemies position while inbetween
                // position updates and if enemy has special cosmetics it's passed through here.
                return enemy;
            }

            case BULLET:
            {
                Bullet.Type bType = Bullet.Type.fromInteger(dto.getBulletType());
                return new Bullet(dto.getId(), new TranslatedPoint(dto.getBody().getPosition().getX(), dto.getBody().getPosition().getY()),
                        dto.getBody().getHeight(), new Vector2(dto.getPhysics().getVelX(), dto.getPhysics().getVelY()), 0, bType, (Image)Weapon.getBulletImages().get(bType),
                        -1);
            }

            default:
                break;
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
                BoDCharacter enemy = null;
                TranslatedPoint position = new TranslatedPoint(data.x, data.y);

                switch (data.specialization)
                {
                    case BLASTER:
                        enemy = new Blaster(data.objectId, position, defaultImages.get(EntityType.ENEMY_CHARACTER));
                        break;
                    case ROLLER:
                        enemy = new Roller(data.objectId, position, defaultImages.get(EntityType.ENEMY_CHARACTER));
                        break;
                    case HEAVY:
                        enemy = new Heavy(data.objectId, position, defaultImages.get(EntityType.ENEMY_CHARACTER));
                        break;
                    default:
                        return null;
                }
                enemy.setNickname(data.nickname);
                return enemy;
            }
            case BULLET:
            {
                return new Bullet(data.objectId, new TranslatedPoint(data.x, data.y), data.height, new Vector2(data.velocityX, data.velocityY),
                        data.damage, data.bulletType, (Image)Weapon.getBulletImages().get(data.bulletType), data.ownerId);
            }
            default:
                return null;
        }
    }

}
