package application.util;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Resources
{
    public static AudioClip johnCena = new AudioClip(new File("src/sounds/JohnCena.mp3").toURI().toString());
    public static Image blackBall = new Image("images/ball_black.png");
    public static Image blueBall = new Image("images/ball_blue.png");
    public static Image redBall = new Image("images/ball_red.png");
    public static Image silverBall = new Image("images/ball_silver.png");
    public static Image blaster = new Image("images/Blaster.png");
    public static Image crosshair = new Image("images/crosshair.png");
    public static Image frontpage = new Image("images/frontpage.png");
    public static Image gamePic = new Image("images/game.jpg");
    public static Image heavy = new Image("images/Heavy.png");
    public static Image logo = new Image("images/Logo.png");
    public static Image mapField = new Image("images/map_field.png");
    public static Image roller = new Image("images/Roller.png");
    public static Image dirtTexture = new Image("images/texture_dirt.png");
    public static Image wallBox = new Image("images/wall_box.png");
    
    
}
