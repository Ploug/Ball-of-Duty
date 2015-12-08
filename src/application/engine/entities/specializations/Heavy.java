package application.engine.entities.specializations;

import application.engine.entities.BoDCharacter;
import application.engine.game_object.Body;
import application.engine.game_object.Health;
import application.engine.game_object.View;
import application.engine.game_object.Weapon;
import application.engine.game_object.physics.Physics;
import application.engine.rendering.TranslatedPoint;
import application.util.Resources;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Heavy extends BoDCharacter
{
    private static final int HEALTH = 150;
    private static final double SPEED = 180;
    private static final int DAMAGE = 25;
    private static final double BULLET_SPEED = 410;
    private static final int BULLET_DIAMETER = 15;
    private static final double FIRE_RATE = 7;
    private static final int MAGAZINE_MAX_SIZE = 5;
    private static final int RELOAD_SPEED = 1500;
    private static final double SIZE = 50;

    public Heavy(int id, TranslatedPoint position, Image defaultImage)
    {
        super(id);
        this.body = new Body(this, position, SIZE, SIZE, TYPE);
        this.physics = new Physics(this, SPEED);
        Canvas heavyImage = new Canvas(defaultImage.getWidth(), defaultImage.getHeight());
        heavyImage.getGraphicsContext2D().drawImage(defaultImage, 0, 0);
        double iconWidth = 20;
        double iconHeight = iconWidth/Resources.heavy.getWidth()*Resources.heavy.getHeight();
        heavyImage.getGraphicsContext2D().drawImage(Resources.heavy, heavyImage.getWidth()/2-iconWidth/2, heavyImage.getHeight()/2-iconHeight/2, iconWidth, iconHeight);

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        

        this.view = new View(this, heavyImage.snapshot(sp, null));    
        this.weapon = new Weapon(this, FIRE_RATE, MAGAZINE_MAX_SIZE, DAMAGE, BULLET_SPEED, RELOAD_SPEED, BULLET_DIAMETER);
        this.health = new Health(HEALTH);
    }

    public void updateStats()
    {
        // physics.setTopSpeed(SPEED + (_score * 1));
        weapon.setBulletSpeed(BULLET_SPEED + (_score * 0.05));
        // weapon.setFireRate(FIRE_RATE + (_score * 1));
        weapon.setDamage(DAMAGE + (int)(_score * 0.01));
        weapon.setMagazineMaxSize(MAGAZINE_MAX_SIZE + (int)(_score * 0.01));
        // weapon.setReloadSpeed(RELOAD_SPEED + (int)(_score * 1));
    }
}
