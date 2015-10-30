package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application
{

    public static GameClient gameManager;
    private static int windowWidth = 1280;
    private static int windowHeight = 720;

    public static void main(String[] args)
    {
        launch(args);
    }

    private Point2D getAbsoluteSceneLocation(Stage stage)
    {
        return new Point2D(stage.getX() + stage.getScene().getX(), stage.getY() + stage.getScene().getY());
    }

    public void start(Stage theStage)
    {
        theStage.setTitle("Ball of Duty");
        theStage.setHeight(windowHeight);
        theStage.setWidth(windowWidth);
        theStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we)
            {
                gameManager.quitGame();
                System.exit(0);
            }
        });

        BorderPane startMenuRoot = new BorderPane();
        Scene startMenu = new Scene(startMenuRoot);
        BorderPane gameBox = new BorderPane();
        Scene gameScene = new Scene(gameBox);

        theStage.setScene(startMenu);

        gameManager = new GameClient(getAbsoluteSceneLocation(theStage));
        theStage.xProperty().addListener(e ->
        {
            gameManager.setSceneAbsoluteLocation(getAbsoluteSceneLocation(theStage));
        });
        theStage.yProperty().addListener(e ->
        {
            gameManager.setSceneAbsoluteLocation(getAbsoluteSceneLocation(theStage));
        });
        gameScene.xProperty().addListener(e ->
        {
            gameManager.setSceneAbsoluteLocation(getAbsoluteSceneLocation(theStage));
        });
        gameScene.yProperty().addListener(e ->
        {
            gameManager.setSceneAbsoluteLocation(getAbsoluteSceneLocation(theStage));
        });
        Image image = new Image("images/frontpage.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        // new Background(images...)
        Background background = new Background(backgroundImage);
        startMenuRoot.setBackground(background);

        theStage.getIcons().add(new Image("images/ball_red.png"));
        VBox buttonBox = new VBox();
        Button joinBtn = new Button("Join game");
        joinBtn.setPrefSize(150, 50);
        joinBtn.setId("join-game");
        joinBtn.setStyle("-fx-font: 22 arial; -fx-base: #ff0717;");
        joinBtn.setOnAction(ActionEvent ->
        {
            theStage.setScene(gameScene);

            gameManager.joinGame(gameBox);
            gameBox.requestFocus();

        });

        buttonBox.getChildren().add(joinBtn);
        startMenuRoot.setLeft(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(400, 0, 0, 180));

        Button quitBtn = new Button("Quit game");
        quitBtn.setPrefSize(80, 40);
        quitBtn.setId("quit-game");

        quitBtn.setOnAction(ActionEvent ->
        {
            gameManager.quitGame();
            theStage.setScene(startMenu);
        });
        Canvas canvas = new Canvas(1100, 660);

        gameBox.setCenter(canvas);
        gameBox.setBottom(quitBtn);
        BorderPane.setAlignment(quitBtn, Pos.BASELINE_LEFT);
        theStage.show();
    }
}
