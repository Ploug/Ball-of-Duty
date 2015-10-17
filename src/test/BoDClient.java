package test;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import application.*;

public class BoDClient
{

    public static IGame game;
    public static GameManager gameManager;
    private static BoDServer server;
    private static int windowWidth = 800;
    private static int windowHeight = 600;
    private static JButton joinGame;
    private static JFrame a;
    private static Canvas canvas;
    private static JPanel buttonPanel;
    private static JPanel drawPanel;

    public static void main(String[] args)
    {
        a = new JFrame();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setTitle("Ball of Duty");
        a.setSize(windowWidth, windowHeight);
        a.setLocationRelativeTo(null);
        a.setVisible(true);
//        a.setLayout(null);
        joinGame = new JButton("Join Game");
        joinGame.addActionListener(e ->
        {
            joinGame();
        });
//        joinGame.setLocation(windowWidth / 2 - 100, windowHeight / 2 - 80);
        buttonPanel = new JPanel();
        drawPanel = new JPanel();
        buttonPanel.add(joinGame);
        a.add(buttonPanel, BorderLayout.CENTER);
//        a.add(drawPanel);
        canvas = new Canvas();
        drawPanel.add(canvas);
       
        gameManager = GameManager.getInstance();
        
        Player clientPlayer = new Player(true, 1337);
        
        clientPlayer.setCharacter(new BoDCharacter(canvas.getGraphics()));
        gameManager.setClientPlayer(clientPlayer);
        
        a.addKeyListener(clientPlayer.getKeyListener());
        buttonPanel.addKeyListener(clientPlayer.getKeyListener());
        joinGame.addKeyListener(clientPlayer.getKeyListener());
        buttonPanel.setFocusable(true);

    }

    public static void joinGame()
    {
//        ServerMap sm = new ServerMap();
       

//        gameManager.setClientMap(sm);

    }

    public static void quitGame()
    {

    }

}
