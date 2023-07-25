package TermProject;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/*
    < ------- 왼쪽 사용자 패널 ------- >
    - 사용자 이미지, 현재 주차 JLabel, Back 버튼이 있음
    - 사용자 이미지는 랜덤적으로 생성
    - Back 버튼에는 mouseClicked 이벤트 등록
        -> 모든 쓰레드 종료 후 게임 종료, 시작화면 생성
*/

public class UserPanel extends JPanel
{
    private JLabel userImage;
    public static JLabel lb_level, lb_subject;
    private JButton btn_back;

    /* -------------- 주차별 주제 -------------- */
    public static String[] subjects = {"Java란?","Java 기초","객체와 클래스","예외처리","AWT",  //해당 주차의 주제들을 배열로 저장
            "Swing","Graphic&2D","Event","Thread","Java I/O"};

    public UserPanel()
    {
        setLayout(null);

        /* -------------- user image -------------- */
        int randomIndex = (int)(Math.random()*4)+1; // 1~4 랜덤 정수 생성
        String user_image_path = "./Images/user"+randomIndex+".png";  //프로필 이미지를 랜덤으로 불러옴

        userImage = new JLabel(new ImageIcon(user_image_path));
        userImage.setHorizontalAlignment(SwingConstants.CENTER);
        userImage.setVerticalAlignment(SwingConstants.CENTER);
        userImage.setBounds(10, 20, 180, 250);

        /* -------------- 현재 레벨(=주차)을 나타낼 JLabel -------------- */
        lb_level = new JLabel(GameInfo.level + "주차", JLabel.CENTER);
        lb_level.setOpaque(true);  //라벨의 배경을 투명하게 해줌
        lb_level.setBackground(Color.LIGHT_GRAY);
        lb_level.setBounds(15, 350, 170, 70);

        /* -------------- 현재 레벨 주제를 나타낼 JLabel -------------- */
        lb_subject = new JLabel(subjects[GameInfo.level-1], JLabel.CENTER);
        lb_subject.setOpaque(true);  //라벨 배경을 투명하게 해줌
        lb_subject.setBackground(Color.LIGHT_GRAY);
        lb_subject.setBounds(37, 440, 120, 50);


        /* -------------- 폰트 적용-------------- */
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("./Text/font.ttf"));  //폰트 파일을 불러와 폰트 설정

            Font sizedFont = font.deriveFont(23f);
            lb_level.setFont(sizedFont);
            sizedFont = font.deriveFont(17f);
            lb_subject.setFont(sizedFont);
        } catch (IOException | FontFormatException e) { }


        /* -------------- back 버튼 -------------- */
        btn_back = new JButton(new ImageIcon("./Images/back.png"));
        btn_back.setBounds(45, 610, btn_back.getIcon().getIconWidth(), btn_back.getIcon().getIconHeight());
        btn_back.setBorderPainted(false); //테두리 설정 안함
        btn_back.setContentAreaFilled(false);

        btn_back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                GamePanel.gameBGM.interrupt(); // 브금 멈춤

                GameInfo.back = true;

                //GamePanel에서 실행중인 Thread종료
                GamePanel.stopThreads();

                //JavaGame 프레임 종료(UserPanel이 포함된 패널 종료하게 함)
                SwingUtilities.getWindowAncestor(UserPanel.this).dispose();

                GameInfo.resetAll();    // 리셋

                new StartFrame();

            }
        });

        /* -------------- 배경 투명화하기 -------------- */
        userImage.setOpaque(false);
        lb_level.setOpaque(false);
        lb_subject.setOpaque(false);
        btn_back.setOpaque(false);

        /* -------------- Panel에 붙이기 -------------- */
        add(userImage);
        add(lb_level);
        add(lb_subject);
        add(btn_back);


        setSize(200, 700);
        setBorder(new LineBorder(Color.BLACK, 1));
        setVisible(true);
    }

    /* -------------- JLabel 업데이트 하는 method -------------- */
    public static void update() {
        lb_level.setText(GameInfo.level + "주차");  //레벨 업을 할 시 주차 라벨의 숫자가 1올라감
        lb_subject.setText(subjects[GameInfo.level-1]);  //레벨 업을 할 시 주제 라벨이 바뀜
    }

    /* -------------- set Background -------------- */
    public void paintComponent(Graphics g){
        ImageIcon userbg = new ImageIcon("./Images/userbg.png");  //유저패널에 배경 이미지를 그려넣음
        g.drawImage(userbg.getImage(), 0, 0, null);

        ImageIcon userimg = new ImageIcon("./Images/userimg.png");  //유저패널의 프로필에 하얀 배경 이미지를 그려넣음
        g.drawImage(userimg.getImage(), 10, 25, null);

        ImageIcon userlev = new ImageIcon("./Images/userlevel.png");  //유저패널의 주차 라벨 뒤에 하얀 배경 이미지를 그려넣음
        g.drawImage(userlev.getImage(), 0, 340, null);

        ImageIcon usersub = new ImageIcon("./Images/usersub.png");  //유저 패널의 주차 주제 라벨 뒤에 하얀 배경 이미지를 그려넣음
        g.drawImage(usersub.getImage(), 18, 428, null);

        //사과 이미지
        ImageIcon apple = new ImageIcon(WordStorage.getStorage().Images[0]);  //떨어지는 음식 이미지를 배열에서 가져옴
        Image apple1 = apple.getImage();
        Image apple2 = apple1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);  //이미지의 크기를 조절
        ImageIcon apple3 = new ImageIcon(apple2);
        g.drawImage(apple3.getImage(), 120, 500, null);  //작아진 음식 이미지를 그려 넣음

        //붕어빵 이미지
        ImageIcon fish = new ImageIcon(WordStorage.getStorage().Images[2]);
        Image fish1 = fish.getImage();
        Image fish2 = fish1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon fish3 = new ImageIcon(fish2);
        g.drawImage(fish3.getImage(), 40, 530, null);

        //아이스크림 이미지
        ImageIcon ice = new ImageIcon(WordStorage.getStorage().Images[3]);
        Image ice1 = ice.getImage();
        Image ice2 = ice1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon ice3 = new ImageIcon(ice2);
        g.drawImage(ice3.getImage(), 40, 280, null);

        //과자 이미지
        ImageIcon snack = new ImageIcon(WordStorage.getStorage().Images[4]);
        Image snack1 = snack.getImage();
        Image snack2 = snack1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon snack3 = new ImageIcon(snack2);
        g.drawImage(snack3.getImage(), 110, 300, null);

    }
}