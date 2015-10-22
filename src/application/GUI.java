package application;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application
{

    public static GameClient gameManager;
    private static int windowWidth = 800;
    private static int windowHeight = 600;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage theStage)
    {
       gameManager = new GameClient();

       
        theStage.setTitle("Ball of Duty");
        theStage.setHeight(windowHeight);
        theStage.setWidth(windowWidth);

        BorderPane startMenuRoot = new BorderPane();
        Scene startMenu = new Scene(startMenuRoot);
        BorderPane gameBox = new BorderPane();
        Scene gameScene = new Scene(gameBox);

        theStage.setScene(startMenu);

        VBox buttonBox = new VBox();
        Button joinBtn = new Button("Join game");
        joinBtn.setPrefSize(200, 100);
        joinBtn.setId("join-game");
        joinBtn.setOnAction(ActionEvent ->
        {
               gameManager.joinGame(gameBox);
               theStage.setScene(gameScene);

        });

        buttonBox.getChildren().add(joinBtn);
        startMenuRoot.setCenter(buttonBox);
        buttonBox.setAlignment(Pos.CENTER);

        Button quitBtn = new Button("Quit game");
        quitBtn.setPrefSize(80, 40);
        quitBtn.setId("join-game");
        quitBtn.setOnAction(ActionEvent ->
        {

            theStage.setScene(startMenu);
        });
        Canvas canvas = new Canvas(600, 500);

        gameBox.setCenter(canvas);
        gameBox.setBottom(quitBtn);
        BorderPane.setAlignment(quitBtn, Pos.BASELINE_RIGHT);
        theStage.show();
    }
}
