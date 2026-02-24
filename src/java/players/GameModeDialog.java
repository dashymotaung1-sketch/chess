package players;

import javax.swing.*;
import java.awt.*;

public class GameModeDialog extends JDialog {
    private boolean twoPlayerMode = true;
    private boolean playerIsWhite = true;
    private String difficulty = "Normal";

    public GameModeDialog(JFrame parent) {
        super(parent, "Chess Settings", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JRadioButton pvpBtn = new JRadioButton("Player vs Player", true);
        JRadioButton pvcBtn = new JRadioButton("Player vs Computer");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(pvpBtn); modeGroup.add(pvcBtn);

        String[] levels = {"Easy", "Normal", "Hard"};
        JComboBox<String> levelBox = new JComboBox<>(levels);
        levelBox.setSelectedItem("Normal");
        levelBox.setEnabled(false); // Only active if vs Computer

        pvcBtn.addActionListener(e -> levelBox.setEnabled(true));
        pvpBtn.addActionListener(e -> levelBox.setEnabled(false));

        JButton startBtn = new JButton("Start Game");
        startBtn.addActionListener(e -> {
            twoPlayerMode = pvpBtn.isSelected();
            difficulty = (String) levelBox.getSelectedItem();
            dispose();
        });

        gbc.gridy = 0; add(pvpBtn, gbc);
        gbc.gridy = 1; add(pvcBtn, gbc);
        gbc.gridy = 2; add(new JLabel("Difficulty:"), gbc);
        gbc.gridy = 3; add(levelBox, gbc);
        gbc.gridy = 4; add(startBtn, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isTwoPlayerMode() { return twoPlayerMode; }
    public boolean isPlayerWhite() { return playerIsWhite; }
    public String getDifficulty() { return difficulty; }
}