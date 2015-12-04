package application.util;

import java.io.File;

import javafx.scene.media.AudioClip;

public class Resources
{
    public static AudioClip johnCena = new AudioClip(new File("src/sounds/JohnCena.mp3").toURI().toString());
}
