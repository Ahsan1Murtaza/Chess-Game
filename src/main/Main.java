package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {


        // Create turn label
        JLabel turnLabel = new JLabel("White's Turn");
        turnLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        turnLabel.setForeground(Color.WHITE);


        // Create timer label
        JLabel whiteTimerLabel = new JLabel("5:00");
        whiteTimerLabel.setFont(new Font("Ink Free", Font.BOLD, 50));
        whiteTimerLabel.setForeground(Color.WHITE);
        JLabel blackTimerLabel = new JLabel("5:00");
        blackTimerLabel.setFont(new Font("Ink Free", Font.BOLD, 50));
        blackTimerLabel.setForeground(Color.WHITE);

        // Panel for turn label and timer
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.BLACK);
        topPanel.add(turnLabel);

        // Right panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 150));
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setPreferredSize(new Dimension(200, 200));
        rightPanel.add(blackTimerLabel);
        rightPanel.add(whiteTimerLabel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JFrame frame = new JFrame();
        frame.setTitle("CHESS");
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1920, 1080));
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board(turnLabel, whiteTimerLabel, blackTimerLabel);


        panel.add(board, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH); // Add top panel to main panel
        panel.add(rightPanel, BorderLayout.EAST);

        frame.add(panel); // Add everything to frame


        frame.setVisible(true);
    }
};