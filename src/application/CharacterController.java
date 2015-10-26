package application;

import java.awt.MouseInfo;

import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;

public class CharacterController
{
    private BoDCharacter character;
    private static final Vector2 UP_VECTOR = new Vector2(0, -1);
    private static final Vector2 DOWN_VECTOR = new Vector2(0, 1);
    private static final Vector2 RIGHT_VECTOR = new Vector2(1, 0);
    private static final Vector2 LEFT_VECTOR = new Vector2(-1, 0);
    private KeyHandler keyHandler;
    private Point2D canvasAbsoluteLocation;

    public CharacterController(BoDCharacter inputChar, BorderPane gameBox, Point2D windowAbsoluteLocation)
    {
        this.canvasAbsoluteLocation = windowAbsoluteLocation.add(gameBox.getCenter().getLayoutX(), gameBox.getCenter().getLayoutY());

        character = inputChar;
        keyHandler = new KeyHandler();

        gameBox.setOnKeyPressed(actionEvent ->
        {
            KeyHandler.Action action = keyHandler.getAction(actionEvent.getCode());

            if (action == KeyHandler.Action.MOVE_UP)
            {
                character.physics.addDirection(UP_VECTOR);
            }
            else if (action == KeyHandler.Action.MOVE_DOWN)
            {
                character.physics.addDirection(DOWN_VECTOR);
            }
            else if (action == KeyHandler.Action.MOVE_LEFT)
            {
                character.physics.addDirection(LEFT_VECTOR);
            }
            else if (action == KeyHandler.Action.MOVE_RIGHT)
            {
                character.physics.addDirection(RIGHT_VECTOR);
            }

        });
        gameBox.setOnKeyReleased(actionEvent ->
        {
            KeyHandler.Action action = keyHandler.getAction(actionEvent.getCode());

            if (action == KeyHandler.Action.MOVE_UP)
            {
                character.physics.removeDirection(UP_VECTOR);
            }
            else if (action == KeyHandler.Action.MOVE_DOWN)
            {
                character.physics.removeDirection(DOWN_VECTOR);
            }
            else if (action == KeyHandler.Action.MOVE_LEFT)
            {
                character.physics.removeDirection(LEFT_VECTOR);
            }
            else if (action == KeyHandler.Action.MOVE_RIGHT)
            {
                character.physics.removeDirection(RIGHT_VECTOR);
            }
        });

        character.physics.addCalculation(() ->
        {
            Point2D position = character.body.getCenter();

            double deltaX = MouseInfo.getPointerInfo().getLocation().getX()
                    - (canvasAbsoluteLocation.getX() + gameBox.getCenter().getLayoutX()) - position.getX();
            
            double deltaY = MouseInfo.getPointerInfo().getLocation().getY()
                    - (canvasAbsoluteLocation.getY() + gameBox.getCenter().getLayoutY()) - position.getY();

            character.body.setOrientation(deltaX, deltaY);
        });
    }

    public void setCanvasAbsoluteLocation(Point2D windowAbsoluteLocation)
    {
        // this.canvasAbsoluteLocation = windowAbsoluteLocation.add(gameBox.getCenter().getLayoutX(),gameBox.getCenter().getLayoutY()); //
        // ^ bliver ikke updated ordentlig pt.
        
        this.canvasAbsoluteLocation = windowAbsoluteLocation;
    }
}
