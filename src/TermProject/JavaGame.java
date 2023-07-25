package TermProject;
import javax.swing.*;

/*
    < ------- 게임 화면 틀을 구성하는 메인 JFrame ------- >
    - UserPanel과 GamePanel을 가지고 있음.
*/

public class JavaGame extends JFrame
{
    private UserPanel userPanel;   // 유저 정보 패널
    private GamePanel gamePanel;   // 게임 패널

    public JavaGame()
    {
        super("Java를 잡아먹자!");
        setLayout(null);

        /* -------------- userPanel setting -------------- */
        userPanel = new UserPanel();
        userPanel.setBounds(0, 0, 200, 700);


        /* -------------- gamePanel setting -------------- */
        gamePanel = new GamePanel();
        gamePanel.setBounds(200, 0, 1000, 700);


        /* -------------- Frame에 붙이기 -------------- */
        add(userPanel);
        add(gamePanel);


        /* -------------- JFrame setting -------------- */
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}