package application.gui;

import java.util.Optional;

import application.account.Player;
import application.communication.GameClient;
import application.engine.entities.specializations.Specializations;
import application.engine.rendering.TranslatedPoint;
import application.util.Observation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application
{
    private static final int WINDOW_START_WIDTH = 1350;
    private static final int WINDOW_START_HEIGHT = 760;
    public static final int CANVAS_START_WIDTH = 1100;
    public static final int CANVAS_START_HEIGHT = 680;

    public static GameClient gameManager;
    public static Scale scale;
    private Scene sceneMainMenu;
    private BorderPane gameBox;
    private Stage tStage;
    public Canvas canvas;

    private EventHandler<ActionEvent> actionGoToLogin;
    private EventHandler<ActionEvent> actionGoToCreateAccount;
    private EventHandler<ActionEvent> actionGoToLeaderboard;
    private EventHandler<ActionEvent> actionGoToMainMenuGuest;
    private EventHandler<ActionEvent> actionCreateAccount;
    private EventHandler<ActionEvent> actionLogin;
    private EventHandler<ActionEvent> actionJoinGame;
    private EventHandler<ActionEvent> actionQuitGame;
    private EventHandler<WindowEvent> actionQuit;

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Gets the scenes relative location. The relative location is based on how the scene's is located relative to the operating system.
     * 
     * @param The
     *            stage of which scene to get the relative location.
     * @return The relative location of the scene. The relative location is based on how the scene's is located relative to the operating system.
     */
    private TranslatedPoint getRelativeSceneLocation(Stage stage)
    {
        return new TranslatedPoint(stage.getX() + stage.getScene().getX(), stage.getY() + stage.getScene().getY());
    }

    @Override
    public void start(Stage theStage)
    {
        // Sizes
        final int buttonWidth = 210;
        final int buttonHeight = 40;

        final int mainmenuWidth = 230;
        final int mainmenuHeight = 350;
        final int mainmenuSpacing = 8;

        // A. Start
        tStage = theStage;
        tStage.setTitle("Ball of Duty");
        tStage.getIcons().add(new Image("images/ball_red.png")); // TODO get image from image map or something
        tStage.setHeight(WINDOW_START_HEIGHT);
        tStage.setWidth(WINDOW_START_WIDTH);
        tStage.centerOnScreen();
        tStage.setResizable(false);

        // 1. Startmenu
        BorderPane borderMainMenu = new BorderPane();
        sceneMainMenu = new Scene(borderMainMenu);
        sceneMainMenu.getStylesheets().add(("images/GUICSS.css"));

        Image image = new Image("images/frontpage.png");// TODO get image from image map or something
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        borderMainMenu.setBackground(background);

        // 1.1 MainMenuGuest (MMG)
        VBox vBoxMMG = new VBox();
        BorderPane.setMargin(vBoxMMG, new Insets(350, 0, 0, 150));
        vBoxMMG.setSpacing(mainmenuSpacing);
        vBoxMMG.setId("mainmenu");
        vBoxMMG.setMinWidth(mainmenuWidth);
        vBoxMMG.setMaxHeight(mainmenuHeight);

        Label lblNicknameMMG = new Label("Nickname:");
        TextField tfNicknameMMG = new TextField();
        Button btnJoinGameMMG = new Button("Join game");
        Button btnLoginMMG = new Button("Log in");
        Button btnCreateAccountMMG = new Button("Create Account");
        Button btnViewLeaderboardMMG = new Button("Leaderboard");

        btnJoinGameMMG.setMinWidth(mainmenuWidth);
        btnJoinGameMMG.setMinHeight(buttonHeight);
        btnLoginMMG.setMinWidth(mainmenuWidth);
        btnLoginMMG.setMinHeight(buttonHeight);
        btnCreateAccountMMG.setMinWidth(mainmenuWidth);
        btnCreateAccountMMG.setMinHeight(buttonHeight);
        btnViewLeaderboardMMG.setMinWidth(mainmenuWidth);
        btnViewLeaderboardMMG.setMinHeight(buttonHeight);

        HBox hBoxSpecialization = new HBox();
        hBoxSpecialization.setSpacing(15);

        ToggleGroup tGroupspecialization = new ToggleGroup();

        RadioButton rbBlaster = new RadioButton("Blaster");
        RadioButton rbRoller = new RadioButton("Roller");
        RadioButton rbHeavy = new RadioButton("Heavy");

        rbBlaster.setToggleGroup(tGroupspecialization);
        rbRoller.setToggleGroup(tGroupspecialization);
        rbHeavy.setToggleGroup(tGroupspecialization);
        rbBlaster.setSelected(true);

        hBoxSpecialization.getChildren().add(rbBlaster);
        hBoxSpecialization.getChildren().add(rbRoller);
        hBoxSpecialization.getChildren().add(rbHeavy);

        vBoxMMG.getChildren().add(lblNicknameMMG);
        vBoxMMG.getChildren().add(tfNicknameMMG);
        vBoxMMG.getChildren().add(hBoxSpecialization);
        vBoxMMG.getChildren().add(btnJoinGameMMG);
        vBoxMMG.getChildren().add(btnLoginMMG);
        vBoxMMG.getChildren().add(btnCreateAccountMMG);
        vBoxMMG.getChildren().add(btnViewLeaderboardMMG);

        // 1.2 Create Account (CA)
        VBox vBoxCA = new VBox();
        BorderPane.setMargin(vBoxCA, new Insets(350, 0, 0, 150));
        vBoxCA.setSpacing(mainmenuSpacing);
        vBoxCA.setId("mainmenu");
        vBoxCA.setMinWidth(mainmenuWidth);
        vBoxCA.setMaxHeight(mainmenuHeight);

        Label lblNicknameCA = new Label("Nickname:");
        TextField tfNicknameCA = new TextField();
        Label lblUserNameCA = new Label("Name:");
        TextField tfUserNameCA = new TextField();
        Label lblPasswordCA = new Label("Password:");
        Label lblPasswordRepeatCA = new Label("Repeat password:");
        PasswordField pfCA = new PasswordField();
        PasswordField pfRepeatCA = new PasswordField();
        Button btnCreateAccountCA = new Button("Create account");
        Button btnBackCA = new Button("Start menu");

        btnCreateAccountCA.setMinWidth(mainmenuWidth);
        btnCreateAccountCA.setMinHeight(buttonHeight);
        btnBackCA.setMinWidth(mainmenuWidth);
        btnBackCA.setMinHeight(buttonHeight);

        vBoxCA.getChildren().add(lblNicknameCA);
        vBoxCA.getChildren().add(tfNicknameCA);
        vBoxCA.getChildren().add(lblUserNameCA);
        vBoxCA.getChildren().add(tfUserNameCA);
        vBoxCA.getChildren().add(lblPasswordCA);
        vBoxCA.getChildren().add(pfCA);
        vBoxCA.getChildren().add(lblPasswordRepeatCA);
        vBoxCA.getChildren().add(pfRepeatCA);
        vBoxCA.getChildren().add(btnCreateAccountCA);
        vBoxCA.getChildren().add(btnBackCA);

        // 1.3 Log in (LI)
        VBox vBoxLI = new VBox();
        BorderPane.setMargin(vBoxLI, new Insets(350, 0, 0, 150));
        vBoxLI.setSpacing(mainmenuSpacing);
        vBoxLI.setId("mainmenu");
        vBoxLI.setMinWidth(mainmenuWidth);
        vBoxLI.setMaxHeight(mainmenuHeight);

        Button btnLogInLI = new Button("Log in");
        Label lblUserNameLI = new Label("Name:");
        TextField tfUserNameLI = new TextField();
        Label lblPasswordLI = new Label("Password:");
        PasswordField pfLI = new PasswordField();
        Button btnBackLI = new Button("Start Menu");

        btnLogInLI.setMinWidth(mainmenuWidth);
        btnLogInLI.setMinHeight(buttonHeight);
        btnBackLI.setMinWidth(mainmenuWidth);
        btnBackLI.setMinHeight(buttonHeight);

        vBoxLI.getChildren().add(lblUserNameLI);
        vBoxLI.getChildren().add(tfUserNameLI);
        vBoxLI.getChildren().add(lblPasswordLI);
        vBoxLI.getChildren().add(pfLI);
        vBoxLI.getChildren().add(btnLogInLI);
        vBoxLI.getChildren().add(btnBackLI);

        // 2. Game
        BorderPane gameBox = new BorderPane();
        Scene gameScene = new Scene(gameBox);
        gameScene.getStylesheets().add(("application/gui/GUICSS.css"));

        canvas = new Canvas(CANVAS_START_WIDTH, CANVAS_START_HEIGHT);

        Button quitBtn = new Button("Quit game");

        quitBtn.setMinWidth(mainmenuWidth);
        quitBtn.setMinHeight(buttonHeight);

        gameBox.setCenter(canvas);
        gameBox.setBottom(quitBtn);
        BorderPane.setAlignment(canvas, Pos.TOP_LEFT);
        BorderPane.setAlignment(quitBtn, Pos.TOP_LEFT);

        /*
         * scale = new Scale(); scale.xProperty().bind(gameScene.widthProperty().divide(WINDOW_START_WIDTH));
         * scale.yProperty().bind(gameScene.heightProperty().divide(WINDOW_START_HEIGHT)); scale.setPivotX(0); scale.setPivotY(0);
         * //gameBox.getTransforms().add(scale); //gameBox.setBackground(null); // TODO scaling
         */

        // 3 Leaderboard (LB)
        BorderPane borderLB = new BorderPane();
        Scene sceneLB = new Scene(borderLB);
        sceneLB.getStylesheets().add(("application/gui/GUICSS.css"));

        VBox vBoxLB = new VBox();

        Button btnBackLB = new Button("Back");

        btnBackLB.setMinWidth(mainmenuWidth);
        btnBackLB.setMinHeight(buttonHeight);

        vBoxLB.getChildren().add(btnBackLB);

        borderLB.setLeft(vBoxLB);

        Label lblTopTextLB = new Label("Only shows scores higher than 100!");

        borderLB.setTop(lblTopTextLB);

        // Init game things
        tStage.setScene(sceneMainMenu);
        gameManager = new GameClient(getRelativeSceneLocation(tStage));

        tStage.heightProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage)); // This only happens once for some reason
        });
        tStage.widthProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
        });
        tStage.xProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
        });
        tStage.yProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
        });
        gameScene.xProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
        });
        gameScene.yProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
        });

        // Start things
        borderMainMenu.setLeft(vBoxMMG);
        tStage.setScene(sceneMainMenu);
        tStage.show();

        // Actions
        actionGoToMainMenuGuest = actionEvent ->
        {
            borderMainMenu.setLeft(vBoxMMG);
            if (tStage.getScene() != sceneMainMenu)
            {
                tStage.setScene(sceneMainMenu);
            }
        };

        actionGoToLogin = actionEvent ->
        {
            borderMainMenu.setLeft(vBoxLI);
        };

        actionGoToCreateAccount = actionEvent ->
        {
            borderMainMenu.setLeft(vBoxCA);
        };

        actionGoToLeaderboard = actionEvent ->
        {
            HighscoreLeaderboard hBoard = gameManager.getHighscoreLeaderboard();
            hBoard.setFocusTraversable(false);
            borderLB.setCenter(hBoard);
            BorderPane.setMargin(hBoard, new Insets(12, 12, 12, 12));
            if (gameManager.getPlayer() != null)
            {
                Player client = gameManager.getPlayer();
                Label you = new Label("YOU:    " + client.getNickname() + " [" + client.getId() + "]    | Score: " + client.getHighscore());
                borderLB.setBottom(you);
            }
            tStage.setScene(sceneLB);
        };

        actionLogin = actionEvent ->
        {
            // TODO login
        };

        actionCreateAccount = actionEvent ->
        {
            gameManager.createAccount(tfUserNameCA.getText(), tfNicknameCA.getText(), pfCA.getText().toCharArray(),
                    pfRepeatCA.getText().toCharArray());
            borderMainMenu.setLeft(vBoxMMG);

        };

        actionJoinGame = actionEvent ->
        {
            Specializations spec;
            if (rbRoller.isSelected())
            {
                spec = Specializations.ROLLER;
            }
            else if (rbHeavy.isSelected())
            {
                spec = Specializations.HEAVY;
            }
            else // Blaster is default, if something goes wrong with radio
                 // buttons
            {
                spec = Specializations.BLASTER;
            }

            tStage.setScene(gameScene);
            gameManager.joinAsGuest(gameBox, tfNicknameMMG.getText(), spec);
            gameManager.getPlayer().register(Observation.EXTERMINATION, this, (observable, data) -> playerDeath());
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
            gameBox.requestFocus();
        };

        actionQuitGame = actionEvent ->
        {
            gameManager.quitGame();
            tStage.setScene(sceneMainMenu);
        };

        actionQuit = new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we)
            {
                gameManager.quitGame();
                System.exit(0);
            }
        };

        btnJoinGameMMG.setOnAction(actionJoinGame);
        btnLoginMMG.setOnAction(actionGoToLogin);
        btnCreateAccountMMG.setOnAction(actionGoToCreateAccount);
        btnViewLeaderboardMMG.setOnAction(actionGoToLeaderboard);

        btnCreateAccountCA.setOnAction(actionCreateAccount);
        btnBackCA.setOnAction(actionGoToMainMenuGuest);

        btnLogInLI.setOnAction(actionLogin);
        btnBackLI.setOnAction(actionGoToMainMenuGuest);

        btnBackLB.setOnAction(actionGoToMainMenuGuest);

        quitBtn.setOnAction(actionQuitGame);

        tStage.setOnCloseRequest(actionQuit);
    }

    public void playerDeath()
    {
        Platform.runLater(() -> // Run later to modify GUI from
                                // outside GUI-thread
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Game over");
            alert.setHeaderText(null);
            alert.setContentText("You have died...\nDo you want to respawn?");

            ButtonType respawnBlaster = new ButtonType("Respawn as blaster");
            ButtonType respawnRoller = new ButtonType("Respawn as roller");
            ButtonType respawnHeavy = new ButtonType("Respawn as heavy");
            ButtonType spectate = new ButtonType("Spectate");
            ButtonType quit = new ButtonType("Quit");

            alert.getButtonTypes().setAll(respawnBlaster, respawnRoller, respawnHeavy, spectate, quit);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == respawnBlaster)
            {
                System.out.println("Trying to respawn...");
                gameManager.respawn(gameBox, Specializations.BLASTER);
            }
            else if (result.get() == respawnRoller)
            {
                System.out.println("Trying to respawn...");
                gameManager.respawn(gameBox, Specializations.ROLLER);
            }
            else if (result.get() == respawnHeavy)
            {
                System.out.println("Trying to respawn...");
                gameManager.respawn(gameBox, Specializations.HEAVY);
            }
            else if (result.get() == spectate)
            {
                System.out.println("Spectating...");
            }
            else if (result.get() == quit)
            {
                System.out.println("Trying to quit...");
                gameManager.quitGame();
                tStage.setScene(sceneMainMenu);
            }
        });
    }
}
