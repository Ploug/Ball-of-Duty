package application.gui;

import application.account.Player;
import application.communication.GameClient;
import application.engine.entities.specializations.Specializations;
import application.engine.rendering.TranslatedPoint;
import application.util.Observation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application
{
    public static final int WINDOW_START_WIDTH = 1280;
    public static final int WINDOW_START_HEIGHT = 720;

    public static GameClient gameManager;
    public static Scale scale;
    private Scene sceneMainMenu;
    private Pane gameBox;
    private Stage tStage;
    public Canvas canvas;

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
        final int buttonWidth = 170;
        final int buttonHeight = 40;

        final int mainmenuWidth = 230;
        final int mainmenuHeight = 350;
        final int mainmenuSpacing = 15;

        final String cssURL = "images/GUI.css";

        // Start
        tStage = theStage;
        tStage.setTitle("Ball of Duty");
        tStage.getIcons().add(new Image("images/Logo.png"));
        tStage.setHeight(WINDOW_START_HEIGHT);
        tStage.setWidth(WINDOW_START_WIDTH);
        tStage.centerOnScreen();
        tStage.setResizable(false);

        // Mainmenu
        BorderPane borderMainMenu = new BorderPane();
        borderMainMenu.setId("mainmenu");

        sceneMainMenu = new Scene(borderMainMenu);
        sceneMainMenu.getStylesheets().add((cssURL));
        tStage.setScene(sceneMainMenu);

        // Mainmenu - Menu Guest (MMG)
        HBox hBoxMMG = new HBox();
        BorderPane.setMargin(hBoxMMG, new Insets(200, 0, 0, 340));
        hBoxMMG.setAlignment(Pos.CENTER);
        hBoxMMG.setSpacing(100);
        hBoxMMG.setMaxSize(600, 250);
        borderMainMenu.setCenter(hBoxMMG);

        VBox vBoxMMG1 = new VBox();
        vBoxMMG1.setSpacing(mainmenuSpacing);
        vBoxMMG1.setAlignment(Pos.CENTER);
        hBoxMMG.getChildren().add(vBoxMMG1);

        VBox vBoxMMG2 = new VBox();
        vBoxMMG2.setSpacing(mainmenuSpacing);
        vBoxMMG2.setAlignment(Pos.CENTER);
        hBoxMMG.getChildren().add(vBoxMMG2);

        TextField tfNicknameMMG = new TextField();
        tfNicknameMMG.setPromptText("Nickname");
        vBoxMMG1.getChildren().add(tfNicknameMMG);

        ComboBox<String> comboSpecialization = new ComboBox<String>();
        comboSpecialization.setMinWidth(buttonWidth);
        comboSpecialization.getItems().addAll("Blaster", "Roller", "Heavy");
        comboSpecialization.setValue("Specialization");
        vBoxMMG1.getChildren().add(comboSpecialization);

        Button btnJoinGameMMG = new Button("Join game");
        btnJoinGameMMG.setId("joinbtn");
        btnJoinGameMMG.setMinSize(buttonWidth, 60);
        vBoxMMG1.getChildren().add(btnJoinGameMMG);

        Button btnLoginMMG = new Button("Log in");
        btnLoginMMG.setMinSize(buttonWidth, buttonHeight);
        vBoxMMG2.getChildren().add(btnLoginMMG);

        Button btnCreateAccountMMG = new Button("Create Account");
        btnCreateAccountMMG.setMinSize(buttonWidth, buttonHeight);
        vBoxMMG2.getChildren().add(btnCreateAccountMMG);

        Button btnViewLeaderboardMMG = new Button("Leaderboard");
        btnViewLeaderboardMMG.setMinSize(buttonWidth, buttonHeight);
        vBoxMMG2.getChildren().add(btnViewLeaderboardMMG);

        // Mainmenu - Create Account (CA)
        HBox hBoxCA = new HBox();
        BorderPane.setMargin(hBoxCA, new Insets(200, 0, 0, 340));
        hBoxCA.setAlignment(Pos.CENTER);
        hBoxCA.setSpacing(100);
        hBoxCA.setMaxSize(600, 250);

        VBox vBoxCA1 = new VBox();
        vBoxCA1.setSpacing(mainmenuSpacing);
        vBoxCA1.setAlignment(Pos.CENTER);
        hBoxCA.getChildren().add(vBoxCA1);

        VBox vBoxCA2 = new VBox();
        vBoxCA2.setSpacing(mainmenuSpacing);
        vBoxCA2.setAlignment(Pos.CENTER);
        hBoxCA.getChildren().add(vBoxCA2);

        TextField tfNicknameCA = new TextField();
        tfNicknameCA.setPromptText("Nickname");
        tfNicknameCA.setMinWidth(buttonWidth);
        vBoxCA1.getChildren().add(tfNicknameCA);

        TextField tfUserNameCA = new TextField();
        tfUserNameCA.setPromptText("Username");
        vBoxCA1.getChildren().add(tfUserNameCA);

        PasswordField pfCA = new PasswordField();
        pfCA.setPromptText("Password");
        vBoxCA1.getChildren().add(pfCA);

        PasswordField pfRepeatCA = new PasswordField();
        pfRepeatCA.setPromptText("Repeat password");
        vBoxCA1.getChildren().add(pfRepeatCA);

        Button btnCreateAccountCA = new Button("Create account");
        btnCreateAccountCA.setMinSize(buttonWidth, buttonHeight);
        vBoxCA2.getChildren().add(btnCreateAccountCA);

        Button btnBackCA = new Button("Back");
        btnBackCA.setMinSize(80, buttonHeight);
        vBoxCA2.getChildren().add(btnBackCA);

        // Mainmenu - Log in (LI)
        HBox hBoxLI = new HBox();
        BorderPane.setMargin(hBoxLI, new Insets(200, 0, 0, 340));
        hBoxLI.setAlignment(Pos.CENTER);
        hBoxLI.setSpacing(100);
        hBoxLI.setMaxSize(600, 250);

        VBox vBoxLI1 = new VBox();
        vBoxLI1.setSpacing(mainmenuSpacing);
        vBoxLI1.setAlignment(Pos.CENTER);
        hBoxLI.getChildren().add(vBoxLI1);

        VBox vBoxLI2 = new VBox();
        vBoxLI2.setSpacing(mainmenuSpacing);
        vBoxLI2.setAlignment(Pos.CENTER);
        hBoxLI.getChildren().add(vBoxLI2);

        TextField tfUserNameLI = new TextField();
        tfUserNameLI.setPromptText("Username");
        tfUserNameLI.setMinWidth(buttonWidth);
        vBoxLI1.getChildren().add(tfUserNameLI);

        PasswordField pfLI = new PasswordField();
        pfLI.setPromptText("Password");
        vBoxLI1.getChildren().add(pfLI);

        Button btnLogInLI = new Button("Log in");
        btnLogInLI.setMinSize(buttonWidth, buttonHeight);
        vBoxLI1.getChildren().add(btnLogInLI);

        Button btnBackLI = new Button("Back");
        btnBackLI.setMinSize(80, buttonHeight);
        vBoxLI2.getChildren().add(btnBackLI);

        // Game
        gameBox = new Pane();
        Scene gameScene = new Scene(gameBox);
        gameScene.getStylesheets().add((cssURL));

        canvas = new Canvas(WINDOW_START_WIDTH, WINDOW_START_HEIGHT);
        gameBox.getChildren().add(canvas);

        /*
         * scale = new Scale(); scale.xProperty().bind(gameScene.widthProperty().divide( WINDOW_START_WIDTH));
         * scale.yProperty().bind(gameScene.heightProperty().divide( WINDOW_START_HEIGHT)); scale.setPivotX(0); scale.setPivotY(0);
         * //gameBox.getTransforms().add(scale); //gameBox.setBackground(null); // TODO scaling
         */

        // Game - Pause Menu
        VBox vBoxPmenu = new VBox();
        vBoxPmenu.setId("pauseMenu");
        vBoxPmenu.setMinSize(WINDOW_START_WIDTH, WINDOW_START_HEIGHT);
        vBoxPmenu.setAlignment(Pos.CENTER);
        vBoxPmenu.setSpacing(mainmenuSpacing);
        gameBox.getChildren().add(vBoxPmenu);

        Button gamePauseQuitBtn = new Button("Quit game");
        gamePauseQuitBtn.setMinSize(buttonWidth, buttonHeight);
        vBoxPmenu.getChildren().add(gamePauseQuitBtn);

        Button gamePauseBackbtn = new Button("Back");
        gamePauseBackbtn.setMinSize(buttonWidth, buttonHeight);
        vBoxPmenu.getChildren().add(gamePauseBackbtn);

        // Game - Respawn
        VBox vBoxRespawn = new VBox();
        vBoxRespawn.setId("pauseMenu");
        vBoxRespawn.setMinSize(WINDOW_START_WIDTH, WINDOW_START_HEIGHT);
        vBoxRespawn.setAlignment(Pos.TOP_CENTER);
        gameBox.getChildren().add(vBoxRespawn);

        Label lblDeath = new Label("You Died");
        lblDeath.setId("labelDead");
        VBox.setMargin(lblDeath, new Insets(50, 0, 100, 0));
        vBoxRespawn.getChildren().add(lblDeath);

        VBox vBoxRespawn2 = new VBox();
        vBoxRespawn2.setSpacing(mainmenuSpacing);
        vBoxRespawn2.setAlignment(Pos.CENTER);
        vBoxRespawn.getChildren().add(vBoxRespawn2);

        Button btnRespawn = new Button("Repawn");
        btnJoinGameMMG.setId("joinbtn");
        btnRespawn.setMinSize(buttonWidth, 60);
        btnRespawn.setAlignment(Pos.CENTER);
        vBoxRespawn2.getChildren().add(btnRespawn);

        Button btnRespawnChangeSpec = new Button("Chance Specialization");
        btnRespawn.setMinSize(buttonWidth, buttonHeight);
        btnRespawn.setAlignment(Pos.CENTER);
        vBoxRespawn2.getChildren().add(btnRespawnChangeSpec);

        Button btnRespawnSpec = new Button("Spectate");
        btnRespawn.setMinSize(buttonWidth, buttonHeight);
        btnRespawn.setAlignment(Pos.CENTER);
        vBoxRespawn2.getChildren().add(btnRespawnSpec);

        Button btnRespawnChangeSpecBlaster = new Button("Blaster");
        btnRespawn.setMinSize(buttonWidth, buttonHeight);
        btnRespawn.setAlignment(Pos.CENTER);

        Button btnRespawnChangeSpecRoller = new Button("Roller");
        btnRespawn.setMinSize(buttonWidth, buttonHeight);
        btnRespawn.setAlignment(Pos.CENTER);

        Button btnRespawnChangeSpecHeavy = new Button("Heavy");
        btnRespawn.setMinSize(buttonWidth, buttonHeight);
        btnRespawn.setAlignment(Pos.CENTER);

        Button btnRespawnQuit = new Button("Quit");
        gamePauseBackbtn.setMinSize(80, buttonHeight);
        VBox.setMargin(btnRespawnQuit, new Insets(50, 0, 0, 0));
        vBoxRespawn.getChildren().add(btnRespawnQuit);

        // Leaderboard (LB)
        BorderPane borderLB = new BorderPane();
        Scene sceneLB = new Scene(borderLB);
        sceneLB.getStylesheets().add((cssURL));

        VBox vBoxLB = new VBox();
        borderLB.setLeft(vBoxLB);

        Button btnBackLB = new Button("Back");
        btnBackLB.setMinSize(80, buttonHeight);
        vBoxLB.getChildren().add(btnBackLB);

        // Init game things
        gameManager = new GameClient(getRelativeSceneLocation(tStage));

        tStage.heightProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage)); // This
                                                                                    // only
                                                                                    // happens
                                                                                    // once
                                                                                    // for
                                                                                    // some
                                                                                    // reason
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

        // Actions
        EventHandler<ActionEvent> actionGoToMainMenuGuest = actionEvent ->
        {
            borderMainMenu.setCenter(hBoxMMG);
            hBoxMMG.requestFocus();
            if (tStage.getScene() != sceneMainMenu)
            {
                tStage.setScene(sceneMainMenu);
            }
        };

        EventHandler<ActionEvent> actionGoToLogin = actionEvent ->
        {
            borderMainMenu.setCenter(hBoxLI);
            hBoxLI.requestFocus();
        };

        EventHandler<ActionEvent> actionGoToCreateAccount = actionEvent ->
        {
            borderMainMenu.setCenter(hBoxCA);
            hBoxCA.requestFocus();
        };

        EventHandler<ActionEvent> actionGoToLeaderboard = actionEvent ->
        {
            HighscoreLeaderboard hBoard = gameManager.getHighscoreLeaderboard();
            hBoard.setFocusTraversable(false);
            borderLB.setCenter(hBoard);
            BorderPane.setMargin(hBoard, new Insets(12, 12, 12, 12));
            if (gameManager.getPlayer() != null)
            {
                Player client = gameManager.getPlayer();
                Label you = new Label("YOU:    " + client.getNickname() + " [" + client.getId() + "]    | Score: "
                        + client.getHighscore());
                borderLB.setBottom(you);
            }
            tStage.setScene(sceneLB);
        };

        EventHandler<ActionEvent> actionLogin = actionEvent ->
        {
            // TODO login
        };

        EventHandler<ActionEvent> actionCreateAccount = actionEvent ->
        {
            gameManager.createAccount(tfUserNameCA.getText(), tfNicknameCA.getText(), pfCA.getText().toCharArray(),
                    pfRepeatCA.getText().toCharArray());
            borderMainMenu.setCenter(hBoxMMG);
            hBoxMMG.requestFocus();

        };

        EventHandler<ActionEvent> actionJoinGame = actionEvent ->
        {
            Specializations spec;
            switch (comboSpecialization.getValue())
            {
                case "Blaster":
                    spec = Specializations.BLASTER;
                    break;
                case "Roller":
                    spec = Specializations.ROLLER;
                    break;
                case "Heavy":
                    spec = Specializations.HEAVY;
                    break;
                default:
                    spec = Specializations.BLASTER;
                    break;
            }

            tStage.setScene(gameScene);
            vBoxPmenu.setVisible(false);
            vBoxRespawn.setVisible(false);
            gameManager.joinAsGuest(gameBox, tfNicknameMMG.getText(), spec);
            gameManager.getPlayer().register(Observation.EXTERMINATION, this, (observable, data) -> playerDeath());
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
            canvas.requestFocus();
        };

        EventHandler<ActionEvent> actionRespawn = actionEvent ->
        {
            Specializations spec;
            switch (comboSpecialization.getValue())
            {
                case "Blaster":
                    spec = Specializations.BLASTER;
                    break;
                case "Roller":
                    spec = Specializations.ROLLER;
                    break;
                case "Heavy":
                    spec = Specializations.HEAVY;
                    break;
                default:
                    spec = Specializations.BLASTER;
                    break;
            }
            gameManager.respawn(gameBox, spec);
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
            vBoxRespawn.setVisible(false);
            canvas.requestFocus();
        };

        EventHandler<ActionEvent> actionChangeSpec = actionEvent ->
        {
            vBoxRespawn2.getChildren().clear();
            vBoxRespawn2.getChildren().add(btnRespawnChangeSpecBlaster);
            vBoxRespawn2.getChildren().add(btnRespawnChangeSpecRoller);
            vBoxRespawn2.getChildren().add(btnRespawnChangeSpecHeavy);
        };

        EventHandler<ActionEvent> actionRespawnBlaster = actionEvent ->
        {
            gameManager.respawn(gameBox, Specializations.BLASTER);
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
            vBoxRespawn2.getChildren().clear();
            vBoxRespawn2.getChildren().add(btnRespawn);
            vBoxRespawn2.getChildren().add(btnRespawnChangeSpec);
            vBoxRespawn2.getChildren().add(btnRespawnSpec);
            vBoxRespawn.setVisible(false);
            comboSpecialization.setValue("Blaster");
            canvas.requestFocus();
        };

        EventHandler<ActionEvent> actionRespawnRoller = actionEvent ->
        {
            gameManager.respawn(gameBox, Specializations.ROLLER);
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
            vBoxRespawn2.getChildren().clear();
            vBoxRespawn2.getChildren().add(btnRespawn);
            vBoxRespawn2.getChildren().add(btnRespawnChangeSpec);
            vBoxRespawn2.getChildren().add(btnRespawnSpec);
            vBoxRespawn.setVisible(false);
            comboSpecialization.setValue("Roller");
            canvas.requestFocus();
        };

        EventHandler<ActionEvent> actionRespawnHeavy = actionEvent ->
        {
            gameManager.respawn(gameBox, Specializations.HEAVY);
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
            vBoxRespawn2.getChildren().clear();
            vBoxRespawn2.getChildren().add(btnRespawn);
            vBoxRespawn2.getChildren().add(btnRespawnChangeSpec);
            vBoxRespawn2.getChildren().add(btnRespawnSpec);
            vBoxRespawn.setVisible(false);
            comboSpecialization.setValue("Heavy");
            canvas.requestFocus();
        };

        EventHandler<ActionEvent> actionSpectate = actionEvent ->
        {
            vBoxRespawn.setVisible(false);
        };

        EventHandler<ActionEvent> actionRespawnQuit = actionEvent ->
        {
            gameManager.getPlayer().unregisterAll(this);
            gameManager.quitGame();
            tStage.setScene(sceneMainMenu);
        };

        EventHandler<ActionEvent> actionQuitGame = actionEvent ->
        {
            gameManager.getPlayer().unregisterAll(this);
            gameManager.quitGame();
            tStage.setScene(sceneMainMenu);
        };

        EventHandler<WindowEvent> actionQuit = new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we)
            {
                gameManager.quitGame();
                System.exit(0);
            }
        };

        EventHandler<ActionEvent> actionPauseBack = actionEvent ->
        {
            vBoxPmenu.setVisible(false);
            canvas.requestFocus();
        };

        btnJoinGameMMG.setOnAction(actionJoinGame);
        tfNicknameMMG.setOnAction(actionJoinGame);
        btnLoginMMG.setOnAction(actionGoToLogin);
        btnCreateAccountMMG.setOnAction(actionGoToCreateAccount);
        btnViewLeaderboardMMG.setOnAction(actionGoToLeaderboard);

        btnCreateAccountCA.setOnAction(actionCreateAccount);
        btnBackCA.setOnAction(actionGoToMainMenuGuest);

        btnLogInLI.setOnAction(actionLogin);
        btnBackLI.setOnAction(actionGoToMainMenuGuest);

        btnBackLB.setOnAction(actionGoToMainMenuGuest);

        gamePauseQuitBtn.setOnAction(actionQuitGame);
        gamePauseBackbtn.setOnAction(actionPauseBack);

        btnRespawn.setOnAction(actionRespawn);
        btnRespawnSpec.setOnAction(actionSpectate);
        btnRespawnChangeSpec.setOnAction(actionChangeSpec);
        btnRespawnChangeSpecBlaster.setOnAction(actionRespawnBlaster);
        btnRespawnChangeSpecRoller.setOnAction(actionRespawnRoller);
        btnRespawnChangeSpecHeavy.setOnAction(actionRespawnHeavy);
        btnRespawnQuit.setOnAction(actionRespawnQuit);

        tStage.setOnCloseRequest(actionQuit);

        hBoxMMG.requestFocus();
        tStage.show();
    }

    public void playerDeath()
    {
        gameBox.getChildren().get(2).setVisible(true);
    }
}
