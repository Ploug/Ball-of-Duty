package application.engine.entities.specializations;

import application.engine.entities.BoDCharacter;
import application.engine.game_object.*;
import application.engine.game_object.physics.Physics;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Heavy extends BoDCharacter
{
    private static final int HEALTH = 150;
    private static final double SPEED = 180;
    private static final int DAMAGE = 25;
    private static final double BULLET_SPEED = 360;
    private static final int BULLET_SIZE = 15;
    private static final double FIRE_RATE = 7;
    private static final int MAGAZINE_MAX_SIZE = 5;
    private static final int RELOAD_SPEED = 1500;
    private static final double SIZE = 50;
   
 
    public Heavy(int id, Point2D position, Image image)
    {
        super(id);
        this.body = new Body(this, position, SIZE, SIZE, TYPE);
        this.physics = new Physics(this, SPEED);
        this.view = new View(this, image);
        this.weapon = new Weapon(this, FIRE_RATE, MAGAZINE_MAX_SIZE, DAMAGE, BULLET_SPEED, RELOAD_SPEED, BULLET_SIZE);
        this.health = new Health(HEALTH);
    }
    
    public void updateStats()
    {
        physics.setTopSpeed( SPEED + (_score * 1));
        weapon.setBulletSpeed( BULLET_SPEED + (_score * 1));
        weapon.setFireRate( FIRE_RATE + (_score * 1));
        weapon.setDamage( DAMAGE + (int) (_score * 1));
        weapon.setMagazineMaxSize( MAGAZINE_MAX_SIZE + (int) (_score * 1));
        weapon.setReloadSpeed( RELOAD_SPEED + (int) (_score * 1));
    }
}