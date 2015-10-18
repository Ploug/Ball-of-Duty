package application;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BoDClient
{
    public static IGame game;
    public static GameManager gameManager;
    private static BoDServer server;
    private static int windowWidth = 800;
    private static int windowHeight = 600;
    private static JButton joinGameButton;

    public static void main(String[] args)
    {
        JFrame a = new JFrame();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setTitle("Ball of Duty");
        a.setSize(windowWidth, windowHeight);
        a.setLocationRelativeTo(null);
        a.setVisible(true);
        a.setLayout(null);
        joinGameButton = new JButton("Join Game");
        joinGameButton.addActionListener(e ->
        {
            joinGame();
        });
        joinGameButton.setSize(200, 80);
        joinGameButton.setLocation(windowWidth / 2 - 100, windowHeight / 2 - 80); // Bad magic numbers. Will be removed later... hopefully
        a.add(joinGameButton);

    }

    public static void joinGame()
    {
        System.out.println("Game started");
    }

    public static void quitGame()
    {

    }

}
