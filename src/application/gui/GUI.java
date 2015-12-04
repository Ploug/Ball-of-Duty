package application.gui;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import application.account.Player;
import application.communication.GameClient;
import application.engine.entities.specializations.Specializations;
import application.engine.rendering.ClientMap;
import application.engine.rendering.TranslatedPoint;
import application.util.Observation;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.media.AudioClip;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application
{

    public static GameClient gameManager;
    private static final int WINDOW_START_WIDTH = 1350;
    private static final int WINDOW_START_HEIGHT = 760;
    private BorderPane gameBox;
    private Stage tStage;
    private Scene startMenu;
    private Scene gameScene;
    private RadioButton chooseBlaster;
    private RadioButton chooseHeavy;
    private RadioButton chooseRoller;
    public static final int CANVAS_START_WIDTH = 1100;
    public static final int CANVAS_START_HEIGHT = 680;
    public static Scale scale;

    private AudioClip johnCena = new AudioClip(new File("src/sounds/JohnCena.mp3").toURI().toString());
    Canvas canvas;

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Gets the scenes relative location. The relative location is based on how the scene's is located relative to the operating system.
     * 
     * @param The
     *            stage of which scene to get the relative location.
     * @return The relative location of the scene. The relative location is based on how the scene's is located relative to the operating
     *         system.
     */
    private TranslatedPoint getRelativeSceneLocation(Stage stage)
    {
        return new TranslatedPoint(stage.getX() + stage.getScene().getX(), stage.getY() + stage.getScene().getY());
    }

    @Override
    public void start(Stage theStage)
    {
        this.tStage = theStage;
        theStage.setTitle("Ball of Duty");
        theStage.setHeight(WINDOW_START_HEIGHT);
        theStage.setWidth(WINDOW_START_WIDTH);
        theStage.centerOnScreen();
        theStage.setResizable(false);
        theStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we)
            {
                gameManager.quitGame();
                System.exit(0);
            }
        });

        BorderPane startMenuRoot = new BorderPane();
        startMenu = new Scene(startMenuRoot);

        gameBox = new BorderPane();
        gameScene = new Scene(gameBox);

        BorderPane createAccountRoot = new BorderPane();
        BorderPane lohInRoot = new BorderPane();

        theStage.setScene(startMenu);

        gameManager = new GameClient(getRelativeSceneLocation(theStage));

        scale = new Scale();
        scale.xProperty().bind(gameScene.widthProperty().divide(WINDOW_START_WIDTH));
        scale.yProperty().bind(gameScene.heightProperty().divide(WINDOW_START_HEIGHT));
        scale.setPivotX(0);
        scale.setPivotY(0);
        // gameBox.getTransforms().add(scale);
        // gameBox.setBackground(null);
        // TODO scaling

        theStage.heightProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(theStage)); // This
                                                                                      // only
                                                                                      // happens
                                                                                      // once
                                                                                      // for
                                                                                      // some
                                                                                      // reason.
        });
        theStage.widthProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(theStage));
        });
        theStage.xProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(theStage));
        });
        theStage.yProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(theStage));
        });
        gameScene.xProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(theStage));
        });
        gameScene.yProperty().addListener(e ->
        {
            gameManager.setSceneRelativeLocation(getRelativeSceneLocation(theStage));
        });
        Image image = new Image("images/frontpage.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                backgroundSize);
        // new Background(images...)
        Background background = new Background(backgroundImage);
        startMenuRoot.setBackground(background);

        chooseBlaster = new RadioButton("Blaster");
        chooseRoller = new RadioButton("Roller");
        chooseHeavy = new RadioButton("Heavy");

        final ToggleGroup specializationGroup = new ToggleGroup();

        chooseBlaster.setToggleGroup(specializationGroup);
        chooseRoller.setToggleGroup(specializationGroup);
        chooseHeavy.setToggleGroup(specializationGroup);

        chooseBlaster.setSelected(true);

        HBox specializationBox = new HBox();
        specializationBox.setSpacing(9);
        specializationBox.getChildren().add(chooseBlaster);
        specializationBox.getChildren().add(chooseRoller);
        specializationBox.getChildren().add(chooseHeavy);

        Label lblNickname = new Label("Nickname:");
        TextField tfNickname = new TextField();
        Button loginStart = new Button("Log in");
        Button createStart = new Button("Create Account");
        theStage.getIcons().add(new Image("images/ball_red.png"));
        VBox mainButtonBox = new VBox();
        mainButtonBox.setSpacing(5);
        Button joinBtn = new Button("Join game");

        Button viewLB = new Button("Leaderboard");
        viewLB.setPrefSize(150, 50);
        viewLB.setId("viewLB");
        viewLB.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");

        BorderPane lbBorder = new BorderPane();
        VBox lbBox = new VBox();
        Scene lbScene = new Scene(lbBorder);

        Button lbBack = new Button("Start menu");
        lbBox.getChildren().add(lbBack);
        lbBorder.setLeft(lbBox);
        lbBack.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");

        Label topText = new Label("Only shows scores higher than 100!");
        topText.setStyle("-fx-font-size: 20pt;-fx-font-family: Segoe UI Semibold;");

        viewLB.setOnAction(ActionEvent ->
        {
            HighscoreLeaderboard hBoard = gameManager.getHighscoreLeaderboard();
            hBoard.setFocusTraversable(false);
            lbBorder.setCenter(hBoard);
            if (gameManager.getPlayer() != null)
            {
                Player client = gameManager.getPlayer();
                Label you = new Label("YOU:    " + client.getNickname() + " [" + client.getId() + "]    | Score: " + client.getHighscore());

                you.setStyle("-fx-font-size: 15pt;-fx-font-family: Segoe UI Semibold;");
                lbBorder.setBottom(you);
            }

            BorderPane.setMargin(hBoard, new Insets(12, 12, 12, 12));

            lbBorder.setTop(topText);
            theStage.setScene(lbScene);

            lbBack.setOnAction(ActionEvent1 ->
            {
                theStage.setScene(startMenu);
                startMenuRoot.setLeft(mainButtonBox);

                BorderPane.setMargin(mainButtonBox, new Insets(350, 0, 0, 150));
            });
        });

        joinBtn.setPrefSize(150, 50);
        tfNickname.setPrefSize(150, 20);
        joinBtn.setId("joinBtn");
        joinBtn.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
        lblNickname.setId("lblNickname");
        lblNickname.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
        createStart.setId("CreateStart");
        createStart.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
        loginStart.setId("loginStart");
        loginStart.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");

        joinBtn.setOnAction(ActionEvent ->
        {
            joinGame(tfNickname.getText());

        });
        tfNickname.setOnAction(ActionEvent ->
        {
            joinGame(tfNickname.getText());
        });

        mainButtonBox.getChildren().add(lblNickname);
        mainButtonBox.getChildren().add(tfNickname);
        mainButtonBox.getChildren().add(specializationBox);
        mainButtonBox.getChildren().add(joinBtn);
        mainButtonBox.getChildren().add(loginStart);
        mainButtonBox.getChildren().add(createStart);
        mainButtonBox.getChildren().add(viewLB);
        startMenuRoot.setLeft(mainButtonBox);

        createStart.setOnAction(ActionEvent ->
        {
            VBox createAccountButtonBox = new VBox();
            Label lblNickname2 = new Label("Nickname:");
            TextField tfNickname2 = new TextField();
            Label lblUserName = new Label("Name:");
            TextField tfUserName = new TextField();
            Label lblPassword = new Label("Password:");
            Label lblPassword2 = new Label("Repeat password:");
            PasswordField pf = new PasswordField();
            PasswordField pf2 = new PasswordField();
            Button createBtn = new Button("Create account");
            Button back = new Button("Start menu");

            createBtn.setId("join-yyyyy");
            createBtn.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
            lblUserName.setId("join-game");
            lblUserName.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
            lblPassword.setId("join-game");
            lblPassword.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
            lblPassword2.setId("join-game");
            lblPassword2.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
            back.setId("join-game");
            back.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");

            createAccountButtonBox.getChildren().add(lblNickname2);
            createAccountButtonBox.getChildren().add(tfNickname2);
            createAccountButtonBox.getChildren().add(lblUserName);
            createAccountButtonBox.getChildren().add(tfUserName);
            createAccountButtonBox.getChildren().add(lblPassword);
            createAccountButtonBox.getChildren().add(pf);
            createAccountButtonBox.getChildren().add(lblPassword2);
            createAccountButtonBox.getChildren().add(pf2);
            createAccountButtonBox.getChildren().add(createBtn);
            createAccountButtonBox.getChildren().add(back);

            startMenuRoot.setLeft(createAccountButtonBox);
            BorderPane.setMargin(createAccountButtonBox, new Insets(350, 0, 0, 150));

            createBtn.setOnAction(ActionEvent1 ->
            {
                gameManager.createAccount(tfUserName.getText(), tfNickname2.getText(), pf.getText().toCharArray(), pf2.getText().toCharArray());
                startMenuRoot.setLeft(mainButtonBox);
                BorderPane.setMargin(mainButtonBox, new Insets(350, 0, 0, 150));
            });

            back.setOnAction(ActionEvent1 ->
            {
                startMenuRoot.setLeft(mainButtonBox);
                BorderPane.setMargin(mainButtonBox, new Insets(350, 0, 0, 150));
            });
        });

        loginStart.setOnAction(ActionEvent ->
        {
            VBox loginButtonBox = new VBox();
            Button logInBtn = new Button("Log in");
            Label lblUserName2 = new Label("Name:");
            TextField tfUserName2 = new TextField();
            Label lblPassword2 = new Label("Password:");
            PasswordField pf3 = new PasswordField();
            Button back2 = new Button("Start Menu");

            logInBtn.setId("join-game");
            logInBtn.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");
            back2.setId("join-game");
            back2.setStyle("-fx-font: 20 arial; -fx-base: #ff0717;");

            loginButtonBox.getChildren().add(lblUserName2);
            loginButtonBox.getChildren().add(tfUserName2);
            loginButtonBox.getChildren().add(lblPassword2);
            loginButtonBox.getChildren().add(pf3);
            loginButtonBox.getChildren().add(logInBtn);
            loginButtonBox.getChildren().add(back2);

            startMenuRoot.setLeft(loginButtonBox);
            BorderPane.setMargin(loginButtonBox, new Insets(350, 0, 0, 150));

            logInBtn.setOnAction(ActionEvent1 ->
            {
                // TODO login
            });

            back2.setOnAction(ActionEvent1 ->
            {
                startMenuRoot.setLeft(mainButtonBox);
                BorderPane.setMargin(mainButtonBox, new Insets(350, 0, 0, 150));
            });
        });

        BorderPane.setMargin(mainButtonBox, new Insets(350, 0, 0, 150));

        Button quitBtn = new Button("Quit game");
        quitBtn.setPrefSize(80, 40);
        quitBtn.setId("quit-game");

        quitBtn.setOnAction(ActionEvent ->
        {
            quitGame();
        });

        canvas = new Canvas(CANVAS_START_WIDTH, CANVAS_START_HEIGHT);
        gameBox.setCenter(canvas);
        BorderPane.setAlignment(canvas, Pos.TOP_LEFT);

        gameBox.setBottom(quitBtn);
        BorderPane.setAlignment(quitBtn, Pos.TOP_LEFT);
        theStage.show();
    }

    public void joinGame(String nickname)
    {
        if (nickname.toLowerCase().contains("john")&&nickname.toLowerCase().contains("cena"))
        {
            johnCena.play();
        }
        Specializations spec;
        if (chooseRoller.isSelected())
        {
            spec = Specializations.ROLLER;
        }
        else if (chooseHeavy.isSelected())
        {
            spec = Specializations.HEAVY;
        }
        else // Blaster is default, if something goes wrong with radio
             // buttons
        {
            spec = Specializations.BLASTER;
        }
        tStage.setScene(gameScene);
        gameManager.joinAsGuest(gameBox, nickname, spec);
        gameManager.getPlayer().register(Observation.EXTERMINATION, this, (observable, data) -> playerDeath());
        gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
        gameBox.requestFocus();
    }

    public void quitGame()
    {
        gameManager.getPlayer().unregisterAll(this);
        gameManager.quitGame();
        tStage.setScene(startMenu);
    }

    public void playerDeath()
    {
        Platform.runLater(new Runnable()

        {
            @Override
            public void run()
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
                    gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
                }
                else if (result.get() == respawnRoller)
                {
                    System.out.println("Trying to respawn...");
                    gameManager.respawn(gameBox, Specializations.ROLLER);
                    gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
                }
                else if (result.get() == respawnHeavy)
                {
                    System.out.println("Trying to respawn...");
                    gameManager.respawn(gameBox, Specializations.HEAVY);
                    gameManager.setSceneRelativeLocation(getRelativeSceneLocation(tStage));
                }
                else if (result.get() == spectate)
                {
                    System.out.println("Spectating...");
                }
                else if (result.get() == quit)
                {
                    quitGame();
                }
            }
        });
    }
}
