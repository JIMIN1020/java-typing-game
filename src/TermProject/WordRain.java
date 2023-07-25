package TermProject;
import java.awt.*;

/*
    < ------- 화면에 붙인 단어를 일정 간격으로 내려오게 하는 Thread ------- >
    - 멤버 필드로는 화면에 붙인 JLabel 'word'와 최대 y좌표 값인 'maxY'가 있음.

    - 생성자는 총 2개
        1) 일반 생성자 | WordRain(JLabel word)
        2) 교수님 퀴즈용 생성자 | WordRain(JLabel word, int y)

    - 단어의 y좌표를 10씩 증가시키고, 단어가 입력되거나, Life=0이거나, maxY에 도달하면 종료함.
    - 실행될 때 wordThreads(현재 쓰레드 리스트)에 추가되고, 종료될 때 제거됨.
*/

class WordRain extends Thread
{
    public Word word;
    public int maxY;
    public SoundEffect attackSe = new SoundEffect();

    // 일반 생성자
    public WordRain(Word word) {
        this.word = word;
        this.maxY = 480;
    }

    // 교수님 퀴즈용 생성자
    public WordRain(Word word, int y) {
        this.word = word;
        this.maxY = y;
    }

    public void run() {
        GameInfo.currentWords.add(word);    // 현재 단어 리스트에 추가
        GameInfo.currentThreads.add(this);  // 실행 중인 쓰레드 목록에 추가

        int x = word.getX();    // 현재 x좌표
        int y = word.getY();    // 현재 y좌표

        // stop 사인이 올 때 while loop 탈출
        while (!GameInfo.stop) {
            try {
                // y좌표 +10 하고 위치 재지정
                y += 10;
                word.setLocation(x, y);

                // 바닥(maxY)에 닿으면?
                if (word.getY() >= maxY) {
                    word.setVisible(false);

                    // life -1 후 하트 색 바꾸기
                    GameInfo.life -= 1;
                    StatusPanel.repaintHeart(GameInfo.life, Color.GRAY);
                    attackSe.play("./Audio/attacked.wav", false);  // 효과음

                    // life == 0 인 경우 stop 사인
                    if (GameInfo.life == 0) {
                        StatusPanel.repaintHeart(0, Color.GRAY);    // 마지막 하트 색 변경 안된 경우 고려
                        GameInfo.stop = true;
                    }

                    break;  // loop 탈출 -> run() 종료
                }

                Thread.sleep(GameInfo.speed); // 레벨 별 speed 만큼 sleep
            }
            // interrupt 날아오면 쓰레드 종료
            catch (InterruptedException e) {
                break;
            }
        }
        GameInfo.currentWords.remove(word);        // 현재 단어 리스트에서 제거
        GameInfo.currentThreads.remove(this);   // 현재 쓰레드 리스트에서 제거
    }
}