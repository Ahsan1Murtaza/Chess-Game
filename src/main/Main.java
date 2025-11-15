package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // ==== TOP-LEVEL FRAME ====
        JFrame frame = new JFrame("CHESS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());


        // ==== LEFT PANEL (Turn Indicator) ====
        JLabel turnLabel = new JLabel("White's Turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        turnLabel.setForeground(Color.WHITE);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setBackground(new Color(60, 60, 60));
        leftPanel.add(turnLabel, BorderLayout.CENTER);


        // ==== TIMER LABELS ====
        JLabel whiteTimerLabel = new JLabel("5:00");
        whiteTimerLabel.setFont(new Font("Ink Free", Font.BOLD, 45));
        whiteTimerLabel.setForeground(Color.WHITE);

        JLabel blackTimerLabel = new JLabel("5:00");
        blackTimerLabel.setFont(new Font("Ink Free", Font.BOLD, 45));
        blackTimerLabel.setForeground(Color.WHITE);


        // ==== RIGHT PANEL (Professional Panel) ====
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(60, 60, 60));
        rightPanel.setPreferredSize(new Dimension(250, 0));

        // Labels for Players
        JLabel blackLabel = new JLabel("BLACK", SwingConstants.CENTER);
        blackLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        blackLabel.setForeground(new Color(200, 200, 200));
        blackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel whiteLabel = new JLabel("WHITE", SwingConstants.CENTER);
        whiteLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        whiteLabel.setForeground(new Color(200, 200, 200));
        whiteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        blackTimerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        whiteTimerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // PANEL BUILD
        rightPanel.add(Box.createVerticalStrut(80)); // top space

        // BLACK SECTION
        rightPanel.add(blackLabel);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(blackTimerLabel);

        rightPanel.add(Box.createVerticalStrut(120)); // space between players

        // WHITE SECTION
        rightPanel.add(whiteLabel);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(whiteTimerLabel);

        rightPanel.add(Box.createVerticalGlue());


        // ==== CENTER (Board Panel) ====
        Board board = new Board(turnLabel, whiteTimerLabel, blackTimerLabel);

        JPanel boardHolder = new JPanel(new GridBagLayout());
        boardHolder.setBackground(Color.DARK_GRAY);
        boardHolder.add(board);


        // ==== ADD TO FRAME ====
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(boardHolder, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
