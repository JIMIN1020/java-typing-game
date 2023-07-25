package TermProject;

import javax.swing.*;

/* -------------- 레벨 업 패널 -------------- */
public class LevelUpPanel extends JPanel {
    public LevelUpPanel() {
        setLayout(null);
        setOpaque(false);

        JLabel title = new JLabel(new ImageIcon("./Images/levelUp.png"));  //레벨업 이미지를 붙여줌
        title.setBounds((1000 - title.getIcon().getIconWidth()) / 2, 100, title.getIcon().getIconWidth(), title.getIcon().getIconHeight());
        add(title);

        setSize(1000, 540);
        setVisible(false);
    }
}
