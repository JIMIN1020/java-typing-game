package TermProject;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/*
    < ------- GamePanel 상단 status bar ------- >
    - 'Score', 'Life' JLabel
    - Life 하트를 그리는 HeartPanel
        -> 하트 색을 변경하는 repaintHeart()
        -> 하트 색을 리셋하는 repaintAll()
*/

public class StatusPanel extends JPanel
{
    public static JLabel lb_score;
    public JLabel lb_life;

    public HeartPanel heart1, heart2, heart3;
    public static HeartPanel[] hearts;

    public StatusPanel()
    {
        setLayout(null);

        /* -------------- Score JLabel -------------- */
        lb_score = new JLabel("Score:  " + GameInfo.score);  //게임 정보의 스코어를 불러와 라벨로 보여줌
        lb_score.setBounds(105, 15, 300, 30);


        /* -------------- Life hearts -------------- */
        lb_life = new JLabel("Life: ");
        lb_life.setBounds(680, 15, 80, 30);

        heart1 = new HeartPanel();  //하트1
        heart1.setBounds(750, 15, 30, 30);
        heart1.setOpaque(false);
        heart2 = new HeartPanel();  //하트2
        heart2.setBounds(790, 15, 30, 30);
        heart2.setOpaque(false);
        heart3 = new HeartPanel();  //하트3
        heart3.setBounds(830, 15, 30, 30);
        heart3.setOpaque(false);

        hearts = new HeartPanel[3];
        hearts[0] = heart1;
        hearts[1] = heart2;
        hearts[2] = heart3;

        /* -------------- Font 적용 -------------- */
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("./Text/font.ttf"));  //폰트를 파일로 불러와 폰트로 적용

            Font sizedFont = font.deriveFont(23f);  //폰트 사이즈 적용
            lb_score.setFont(sizedFont);
            lb_life.setFont(sizedFont);
        } catch (IOException | FontFormatException e) { }


        /* -------------- Panel에 붙이기 -------------- */
        add(lb_score);
        add(lb_life);
        add(heart1);
        add(heart2);
        add(heart3);

        setVisible(true);
    }

    /* -------------- JLabel 업데이트 하는 method -------------- */
    public static void update() {
        lb_score.setText("Score:  " + GameInfo.score);
    }

    /* -------------- Score, Life 박스 그리기 -------------- */
    public void paintComponent(Graphics g) {
        ImageIcon scoreImg = new ImageIcon("./Images/box.png");  //score 라벨 뒤의 박스를 그려줌
        g.drawImage(scoreImg.getImage(), 70, 5, null);
        ImageIcon lifeImg = new ImageIcon("./Images/box.png");  //life 라벨 뒤의 박스를 그려줌
        g.drawImage(lifeImg.getImage(), 645, 4, null);
    }

    /* -------------- 하트 색 변경 method -------------- */
    public static void repaintHeart(int n, Color color) {
        hearts[n].color = color;
        hearts[n].repaint();
    }

    /* -------------- 하트 색 리셋 method -------------- */
    public void repaintAll(Color color) {
        for (HeartPanel h: hearts) {
            h.color = color;
            h.repaint();
        }
    }
}
