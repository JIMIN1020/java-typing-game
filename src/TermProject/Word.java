package TermProject;

import javax.swing.*;
import java.awt.*;

/*
    < ------- 게임에 사용되는 단어, 퀴즈 JLabel을 구현한 class ------- >
    - 멤버 필드로 boolean type의 'red'를 가지고 있음.
        -> 30% 확률로 true가 되어 빨간색 단어를 식별하는 역할

    - 생성자는 총 3개.
        1) 일반 단어 생성자  | word(String word)
        2) 교수님 퀴즈 생성자 | word(boolean red)
        3) 퀴즈 정답 생성자  | word(String answer, boolean red)
*/

public class Word extends JLabel {

    public boolean red;

    /* -------------- 일반 단어 생성자 -------------- */
    public Word(String word) {
        super(word);

        // 30% 확률로 red 라벨로 만들기
        double random = Math.random();
        if(random <= 0.3) {
            red = true;
            setForeground(Color.RED);
        }
        else red = false;

        // 랜덤 x좌표 지정
        int x = (int) (Math.random() * 800 + 50);
        int y = 10;

        // 위치, 포지션 지정
        setBounds(x, y, 100, 80);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);

        // 랜덤 이미지 설정
        int j = (int) (Math.random() * 5);
        setIcon(new ImageIcon(WordStorage.getStorage().Images[j]));
    }

    /* -------------- 교수님 퀴즈용 생성자 -------------- */
    public Word(boolean red) {
        this.red = red;

        int count = GameInfo.level;	// HashMap에서 문제 찾기용 카운트

        // n번째(=현재 level에 해당하는) 순서 키 찾아서 값 넣기
        for (String key: WordStorage.getStorage().quiz.keySet()) {
            count--;
            if (count == 0) {
                setText(key);
                GameInfo.currentWords.add(new Word(WordStorage.getStorage().quiz.get(key), false)); // 현재 단어리스트에 정답 추가
                break;
            }
        }

        // 위치, 포지션, 아이콘 지정
        setBounds(380, 10, 210, 170);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        setIcon(new ImageIcon("./Images/prof.png"));
    }

    /* -------------- 퀴즈 정답용 생성자 -------------- */
    public Word(String answer, boolean red) {
        super(answer);
        this.red = red;
        setVisible(false);
    }
}
