package application.engine.entities;

import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.Health;
import application.engine.game_object.View;
import application.engine.game_object.Weapon;
import application.engine.game_object.physics.Physics;
import application.engine.rendering.TranslatedPoint;
import javafx.scene.image.Image;

/**
 * Characters in the game consists of the balls that players control.
 * 
 * @author Gruppe6
 *
 */
public abstract class BoDCharacter extends GameObject
{

    public static final Body.Geometry TYPE = Body.Geometry.CIRCLE;

    protected double _score = 0;
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
    public BoDCharacter(int id)
    {
        super(id);
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
        updateStats();
    }

    public String toString()
    {
        return String.format("%s | Score: %.2f", _nickname, _score);
    }

   
    public abstract void updateStats();

}