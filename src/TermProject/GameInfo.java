package TermProject;

import java.util.ArrayList;

/*
    < ------- 게임에 사용되는 각종 정보들을 저장한 class ------- >
    - static 변수, static method만 제공하는 utility class로 구현
        -> 모든 클래스에서 접근하여 사용할 수 있도록
        -> 인스턴스화 할 수 없음
*/

public class GameInfo
{
    public static int level = 1;    // 현재 레벨 (=주차)
    public static int score = 0;    // 게임 스코어
    public static int life = 3;     // 생명 (= 기회)

    public static boolean stop = false; // flag 변수 (전체 쓰레드 중단)
    public static boolean back = false; // flag 변수 (back 버튼 여부)

    public static int speed = 1000;     // 단어 내려오는 속도 (초기 = 1초)
    public static int interval = 3000;  // 단어 붙이는 간격 (초기 = 3초)

    public static ArrayList<Word> currentWords = new ArrayList<>(); 	// 현재 쓰레드 진행 중인 단어들
    public static ArrayList<WordRain> currentThreads = new ArrayList<>(); 	// 현재 살아있는 쓰레드 목록

    // 생성자: instance를 생성하지 못하도록 함
    private GameInfo() {
        throw new AssertionError();
    }

    /* -------------- 다음 레벨 이동할 때 초기화하는 method -------------- */
    public static void resetData() {
        life = 3;
        stop = false;
        back = false;

        currentWords.clear();
        currentThreads.clear();
    }

    /* -------------- 초기화 method -------------- */
    public static void resetAll() {
        level = 1;
        score = 0;
        life = 3;

        stop = false;
        back = false;

        speed = 1000;
        interval = 3000;

        currentWords.clear();
        currentThreads.clear();
    }

    /* -------------- 레벨 업 method -------------- */
    public static void levelUp() {
        level++;
        speed -= 85;
        interval -= 250;
    }
}
