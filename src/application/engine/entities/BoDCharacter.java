package application.engine.entities;

import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.Health;
import application.engine.game_object.View;
import application.engine.game_object.Weapon;
import application.engine.game_object.physics.Physics;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * Characters in the game consists of the balls that players control.
 * 
 * @author Gruppe6
 *
 */
public class BoDCharacter extends GameObject
{

    public static final Body.Geometry TYPE = Body.Geometry.CIRCLE;

    private double _score = 0;
    private String _nickname = "Anon";

    /**
     * Creates a character with certain speed, width, length, position and image.
     * 
     * @param id
     *            Id of the character
     * @param position
     *            Starting position of the character.
     * @param width
     *            Width of the character.
     * @param height
     *            Height of the character.
     * @param speed
     *            Movement speed of the character.
     */
    public BoDCharacter(int id, Point2D position, double width, double height, int speed, Image image)
    {
        super(id);
        this.body = new Body(this, position, width, height, TYPE);
        this.physics = new Physics(this, speed);
        this.view = new View(this, image);
        this.weapon = new Weapon(this, 5, 20, 9); // Default weapon would be created per spec
        this.health = new Health(100); // Default health should be created perspec.
    }
    
    public double getScore()
    {
        return _score;
    }
    
    public String getNickname()
    {
        return _nickname;
    }
    
    public void setNickname(String nickname)
    {
        this._nickname = nickname;
    }
    
    public void setScore(double score)
    {
        this._score = score;
    }
    
    /**
     * Adds the given double (score) to the current score (this.score).
     * @param score
     **/
    public void addScore(double score)
    {
        this._score += score;
    }
    
    public String toString()
    {
        return _nickname+" | "+_score;
    }

}
