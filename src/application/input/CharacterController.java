package application.input;

import java.awt.MouseInfo;
import java.util.Observable;
import java.util.Observer;

import application.engine.entities.BoDCharacter;
import application.util.Observation;
import application.util.Vector2;
import application.engine.rendering.TranslatedPoint;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

/**
 * Controls a character with input from peripherals such as keyboard and mouse.
 * 
 * @author gruppe6
 *
 */
public class CharacterController
{
    private BoDCharacter character;
    private static final Vector2 UP_VECTOR = new Vector2(0, -1);
    private static final Vector2 DOWN_VECTOR = new Vector2(0, 1);
    private static final Vector2 RIGHT_VECTOR = new Vector2(1, 0);
    private static final Vector2 LEFT_VECTOR = new Vector2(-1, 0);
    private KeyHandler keyHandler;
    private TranslatedPoint canvasRelativeLocation;
    private BorderPane gameBox;

    /**
     * Creates a controller defining the character to control, the gamebox on which it is being controlled, and the windows relative
     * location. The action a input starts is defined by the key handler.
     * 
     * @param inputChar
     *            The character to control.
     * @param gameBox
     *            The game box on which the character is being controlled.
     * @param windowRelativeLocation
     *            The relative location is based on how the scene's is located relative to the operating system.
     */
    public CharacterController(BoDCharacter inputChar, BorderPane gameBox, TranslatedPoint windowRelativeLocation)
    {
        this.gameBox = gameBox;
        this.canvasRelativeLocation = new TranslatedPoint(windowRelativeLocation.getX(), windowRelativeLocation.getY());
        this.canvasRelativeLocation.add(gameBox.getCenter().getLayoutX(), gameBox.getCenter().getLayoutY());
        character = inputChar;
        character.register(Observation.EXTERMINATION, this, (observable, data) -> characterDeath());
        keyHandler = new KeyHandler();

        gameBox.setOnKeyPressed(actionEvent ->
        {
            KeyHandler.Action action = keyHandler.getAction(actionEvent.getCode());

            if(action == null)
            {
                return;
            }
            switch (action)
            {
                case MOVE_UP:
                {
                    character.getPhysics().addDirection(UP_VECTOR);
                    break;
                }
                case MOVE_DOWN:
                {
                    character.getPhysics().addDirection(DOWN_VECTOR);
                    break;
                }
                case MOVE_LEFT:
                {
                    character.getPhysics().addDirection(LEFT_VECTOR);
                    break;
                }
                case MOVE_RIGHT:
                {
                    character.getPhysics().addDirection(RIGHT_VECTOR);
                    break;
                }
                case BLINK:
                {
                    // character.getBody().setCenter(getMousePoint());
                    break;
                }
                case RELOAD:
                {
                    character.getWeapon().reload();
                    break;
                }
                default:
                    break;
            }

        });
        gameBox.setOnKeyReleased(actionEvent ->
        {
            KeyHandler.Action action = keyHandler.getAction(actionEvent.getCode());
            if(action == null)
            {
                return;
            }   
            switch (action)
            {
                case MOVE_UP:
                {
                    character.getPhysics().removeDirection(UP_VECTOR);
                    break;
                }
                case MOVE_DOWN:
                {
                    character.getPhysics().removeDirection(DOWN_VECTOR);
                    break;
                }
                case MOVE_LEFT:
                {
                    character.getPhysics().removeDirection(LEFT_VECTOR);
                    break;
                }
                case MOVE_RIGHT:
                {
                    character.getPhysics().removeDirection(RIGHT_VECTOR);
                    break;
                }
                default:
                    break;
            }
        });

        gameBox.setOnMousePressed(actionEvent ->
        {
            if (actionEvent.getButton() == MouseButton.PRIMARY)
            {
                character.getWeapon().startShooting();
            }

        });
        gameBox.setOnMouseReleased(actionEvent ->
        {
            if (actionEvent.getButton() == MouseButton.PRIMARY)
            {
                character.getWeapon().stopShooting();
            }

        });

        character.getPhysics().addMethod(() ->
        {
            TranslatedPoint position = character.getBody().getCenter();
            double deltaX = getMousePoint().getX() - position.getX();
            double deltaY = getMousePoint().getY() - position.getY();
            character.getBody().setOrientation(new Vector2(deltaX, deltaY));
        });
    }

    /**
     * The current mouse position, relative to the canvas location. (Canvas is where the game is drawn).
     * 
     * @return
     */
    public TranslatedPoint getMousePoint()
    {
        return new TranslatedPoint(
                MouseInfo.getPointerInfo().getLocation().getX() - (canvasRelativeLocation.getTranslatedX() + gameBox.getCenter().getLayoutX()),
                MouseInfo.getPointerInfo().getLocation().getY() - (canvasRelativeLocation.getTranslatedY() + gameBox.getCenter().getLayoutY()));
    }

    public void characterDeath()
    {
        character.getWeapon().stopShooting();
    }

    /**
     * Sets the relative location of the canvas compared to the operating system..
     * 
     * @param windowRelativeLocation
     *            The relative location is based on how the scene's is located relative to the operating system.
     */
    public void setCanvasRelativeLocation(TranslatedPoint canvasRelativeLocation)
    {

        this.canvasRelativeLocation = canvasRelativeLocation;
    }

}
