package application.communication;

import application.engine.entities.Bullet;
import application.engine.entities.specializations.Specializations;
import application.engine.factories.EntityFactory;

public class GameObjectDAO
{
    public double x;
    public double y;
    public double height;
    public double width;
    public double velocityX;
    public double velocityY;
    public Bullet.Type bulletType;
    public EntityFactory.EntityType entityType;
    public Specializations specialization;
    public int damage;
    public int ownerId;
    public int objectId;
    public int maxHealth;
    public int healthValue;
    public String nickname;
    public double score;
}
