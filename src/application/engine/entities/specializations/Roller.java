package application.engine.entities.specializations;

import application.engine.entities.BoDCharacter;
import application.engine.game_object.Body;
import application.engine.game_object.Health;
import application.engine.game_object.View;
import application.engine.game_object.Weapon;
import application.engine.game_object.physics.Physics;
import application.engine.rendering.TranslatedPoint;
import javafx.scene.image.Image;

public class Roller extends BoDCharacter
{
    private static final int HEALTH = 100;
    private static final double SPEED = 230;
    private static final int DAMAGE = 5;
    private static final double BULLET_SPEED = 480;
    private static final int BULLET_DIAMETER = 15;
    private static final double FIRE_RATE = 20;
    private static final int MAGAZINE_MAX_SIZE = 15;
    private static final int RELOAD_SPEED = 1000;
    private static final double SIZE = 50;

    public Roller(int id, TranslatedPoint position, Image image)
    {
        super(id);
        this.body = new Body(this, position, SIZE, SIZE, TYPE);
        this.physics = new Physics(this, SPEED);
        this.view = new View(this, image);
        this.weapon = new Weapon(this, FIRE_RATE, MAGAZINE_MAX_SIZE, DAMAGE, BULLET_SPEED, RELOAD_SPEED, BULLET_DIAMETER);
        this.health = new Health(HEALTH);
    }

    public void updateStats()
    {
        physics.setTopSpeed(SPEED + (_score * 0.1));
        weapon.setBulletSpeed(BULLET_SPEED + (_score * 0.05));
        weapon.setFireRate(FIRE_RATE + (_score * 0.01));
        // weapon.setDamage(DAMAGE + (int)(_score * 1));
        weapon.setMagazineMaxSize(MAGAZINE_MAX_SIZE + (int)(_score * 0.02));
        // weapon.setReloadSpeed(RELOAD_SPEED + (int)(_score * 1));
    }
}
