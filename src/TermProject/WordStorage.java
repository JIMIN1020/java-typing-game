package TermProject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

/*
    < ------- 게임에 사용되는 단어, 퀴즈를 파일로 읽어 저장하는 class ------- >
    - 인스턴스를 단 하나만 가지는 싱글톤 패턴(Singleton Pattern)으로 구현
        -> 메모리에 한번 할당하고 모든 파일에서 공유하여 사용하기 위함

    - week1~week10까지 총 10개의 주차별 ArrayList.
    - 퀴즈와 답을 저장할 LinkedHashMap -> 순서를 보장하기 위해 HashMap 대신 사용.
    - 단어에 붙는 랜덤 이미지 경로를 저장해놓은 Images 배열.
*/
public class WordStorage
{
    /* -------------- 유일한 인스턴스 -------------- */
    private static final WordStorage storage = new WordStorage();


    /* -------------- 주차별 단어 리스트 -------------- */
    private ArrayList<String> week1 = new ArrayList<>();
    private ArrayList<String> week2 = new ArrayList<>();
    private ArrayList<String> week3 = new ArrayList<>();
    private ArrayList<String> week4 = new ArrayList<>();
    private ArrayList<String> week5 = new ArrayList<>();
    private ArrayList<String> week6 = new ArrayList<>();
    private ArrayList<String> week7 = new ArrayList<>();
    private ArrayList<String> week8 = new ArrayList<>();
    private ArrayList<String> week9 = new ArrayList<>();
    private ArrayList<String> week10 = new ArrayList<>();
    public ArrayList<ArrayList> weeks = new ArrayList<>();   // 모든 주차 리스트를 저장할 리스트


    /* -------------- 퀴즈 & 답 리스트 -------------- */
    public LinkedHashMap<String, String> quiz = new LinkedHashMap<>();


    /* -------------- 랜덤 이미지 경로 배열 -------------- */
    public String[] Images = {"./Images/apple.png","./Images/bread.png",  //단어와 떨어질 이미지들을 배열로 저장
            "./Images/fishbread.png","./Images/iceCream.png","./Images/snack.png"};


    /* -------------- 생성자 -> private으로 제한 -------------- */
    private WordStorage()
    {
        weeks.add(week1);
        weeks.add(week2);
        weeks.add(week3);
        weeks.add(week4);
        weeks.add(week5);
        weeks.add(week6);
        weeks.add(week7);
        weeks.add(week8);
        weeks.add(week9);
        weeks.add(week10);

        try {
            readFile();
        } catch (IOException e) {
            System.out.println("[ERROR] IO Exception");
        }

    }

    /* -------------- 파일 읽어오는 method -------------- */
    private void readFile() throws IOException
    {
        /* -------------- 1~10주차 단어 읽어오기 -------------- */
//        BufferedReader br = new BufferedReader(new FileReader("./Text/Test.txt"));
        BufferedReader br = new BufferedReader(new FileReader("./Text/JavaWords.txt"));

        for (ArrayList<String> week: weeks) {
            String words = br.readLine(); // 해당 주차 단어 전체 읽어오기

            // words가 null이면 파일의 끝에 도달했음을 의미하므로 루프를 종료.
            if (words == null) {
                break;
            }

            // 구분자로 끊어 token 구하기
            StringTokenizer token = new StringTokenizer(words, ", ");

            // 해당 주차 리스트에 추가
            while (token.hasMoreTokens()) {
                week.add(token.nextToken());
            }
        }

        /* -------------- 교수님 퀴즈 읽어오기 -------------- */
        br = new BufferedReader(new FileReader("./Text/quiz.txt"));
        String line = br.readLine();

        while (line != null) {
            StringTokenizer token = new StringTokenizer(line, ",");   // 구분자로 끊기

            // LinkedHashMap에 추가
            quiz.put("<html><body style='text-align:center;'>"
                    + token.nextToken() + "<br />"
                    + token.nextToken() + "</body></html>", token.nextToken());

            line = br.readLine();
        }

        br.close(); // BufferedReader 닫기
    }

    /* -------------- instance를 반환해주는 method -------------- */
    public static WordStorage getStorage() {
        return storage;
    }
}
