package test;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.DebugGraphics;
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
    private static JButton joinGameButton;
    private static JButton quitGameButton;
    private static JFrame a;
    private static ClientMap canvas;
    private static JPanel menuPanel;
    private static JPanel gamePanel;
    private static JPanel cards;
    private static final String GAME_PANEL = "Game Panel";
    private static final String MENU_PANEL = "Menu Panel";

    public static void main(String[] args)
    {
        a = new JFrame();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setTitle("Ball of Duty");
        a.setSize(windowWidth, windowHeight);
        a.setLocationRelativeTo(null);
        a.setVisible(true);

        menuPanel = new JPanel();
        menuPanel.setFocusable(true);

        gamePanel = new JPanel();

        cards = new JPanel(new CardLayout());
        cards.add(gamePanel, GAME_PANEL);
        cards.add(menuPanel, MENU_PANEL);
        a.add(cards);
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, MENU_PANEL);
        // a.setLayout(null);
        joinGameButton = new JButton("Join Game");
        joinGameButton.addActionListener(e ->
        {
            System.out.println("join game called");
            cl.show(cards, GAME_PANEL);
            canvas = gameManager.joinGame();
            gamePanel.add(canvas);
            gamePanel.addKeyListener(gameManager.getCharacterController());
            gamePanel.requestFocus();
            
        });
        quitGameButton = new JButton("Quit Game");
        quitGameButton.addActionListener(e ->
        {
            System.out.println("quit game called");
            cl.show(cards, MENU_PANEL);
            gameManager.quitGame();
            gamePanel.remove(canvas);
        });
        // joinGame.setLocation(windowWidth / 2 - 100, windowHeight / 2 - 80);
        menuPanel.add(joinGameButton);

       

        gamePanel.add(quitGameButton);

        Player clientPlayer = new Player(1337); // får den fra server normalt
        gameManager = new GameManager(clientPlayer);
        
      

    }

}
