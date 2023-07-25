package TermProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
< ------- 타자게임 입력창과 WordRain Thread를 연계하여 처리하는 class ------- >
    - JTextField에 입력한 단어를 엔터키 입력 시 화면의 WordRain Thread와 대조하여 처리하도록 구현
        -> 모든 클래스에서 접근하여 사용할 수 있도록
*/

public class InputPanel extends JPanel {
    public static JTextField tf_userInput;
    public SoundEffect GameSe = new SoundEffect(); //입력 효과음 객체 생성.

    public InputPanel() {
        setLayout(null);

        /* -------------- 타자 입력 창 -------------- */
        tf_userInput = new JTextField("엔터를 눌러 단어를 입력하세요.", 30);
        tf_userInput.setBounds(350, 17, 230, 30);
        tf_userInput.setForeground(Color.GRAY);

        // placeHolder 구현
        tf_userInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                tf_userInput.setText("");
                tf_userInput.setForeground(Color.BLACK);
            }
        });

        add(tf_userInput); //텍스트필드 추가
        setVisible(true);


        /* -------------- 타자 입력창 처리 -------------- */
        tf_userInput.setFocusable(true);
        tf_userInput.requestFocus();

        tf_userInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            /*-------------- 키 이벤트 중 엔터키 입력할 때의 처리 --------------*/
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { //엔터를 누르면
                    for (int i = 0; i < (GameInfo.currentWords).size(); i++) { //화면에 있는 단어가 있는지 검사한다.
                        if (InputPanel.tf_userInput.getText().equals(GameInfo.currentWords.get(i).getText())) {
                            GameSe.play("./Audio/beep.wav", false); //효과음 추가.
                            GameInfo.score += 10;
                            StatusPanel.update();  // 스코어 refresh

                            // Red Label 이라면? Black으로 바꾸고 넘어감
                            if (GameInfo.currentWords.get(i).red) {
                                GameInfo.currentWords.get(i).red = false;
                                GameInfo.currentWords.get(i).setForeground(Color.BLACK);
                            }
                            // 아니라면? 없애기
                            else {
                                GameInfo.currentWords.get(i).setVisible(false);
                                GameInfo.currentWords.remove(GameInfo.currentWords.get(i));
                                GameInfo.currentThreads.get(i).interrupt();
                            }
                            break;
                        }
                    }
                    tf_userInput.setText(""); // 엔터를 누를 시 입력 칸을 비워준다.
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }

    public void paintComponent(Graphics g) {
        ImageIcon ipImg = new ImageIcon("./Images/input.png"); //입력창 이미지
        g.drawImage(ipImg.getImage(), 320, 0, null);
    }
}
