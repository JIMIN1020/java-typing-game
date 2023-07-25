package TermProject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/*
    < ------- 오른쪽 게임 패널 ------- >
    - 하단 InputPanel, 상단 StatusPanel, 중앙 TypingGame을 가지고 있음.
    - 게임 상태에 따른 GameOverPanel, LevelUpPanel, EndingPanel을 가지고 있음.
    - 게임에 필요한 Thread, SoundEffect 객체를 가지고 있음.
    - 게임에 필요한 gameOver(), restart(), reset(), ending() 등 다양한 함수를 가지고 있음.
*/

public class GamePanel extends JPanel {
	private StatusPanel statusPanel;
	private InputPanel inputPanel;
	private TypingGame typingGame;

	public static AudioPlayer gameBGM;

	public GameOverPanel gameOverPanel;
	public LevelUpPanel levelUpPanel;
	public EndingPanel endingPanel;

	public static GameManager gameManager;
	public static WordGenerator wordGenerator;

	public SoundEffect levelUpSound = new SoundEffect();
	public SoundEffect gameOverSe = new SoundEffect();
	public SoundEffect endingSe = new SoundEffect();

	public GamePanel() {
		setLayout(null);

		/* -------------- 상단에 점수, Life가 뜨는 status panel -------------- */
		statusPanel = new StatusPanel();
		statusPanel.setBounds(0, 0, 1000, 60);
		statusPanel.setOpaque(false);

		/* -------------- 하단 입력 창 -------------- */
		inputPanel = new InputPanel();
		inputPanel.setBounds(0, 600, 1000, 100);
		inputPanel.setOpaque(false);

		/* -------------- 게임 리셋 -------------- */
		reset();
		GameInfo.resetAll();

		/* -------------- 중앙 게임 화면 -------------- */
		typingGame = new TypingGame();
		typingGame.setBounds(0, 60, 1000, 540);
		typingGame.setOpaque(false);

		/* -------------- 게임 오버, 레벨업, 엔딩 패널 -------------- */
		gameOverPanel = new GameOverPanel();
		gameOverPanel.setBounds(0, 10, 1000, 540);

		levelUpPanel = new LevelUpPanel();
		levelUpPanel.setBounds(0, 60, 1000, 540);

		endingPanel = new EndingPanel();
		endingPanel.setBounds(0, 10, 1000, 630);

		/* -------------- attach the panels to Frame -------------- */
		add(statusPanel);
		add(typingGame);
		add(inputPanel);

		add(gameOverPanel);
		add(levelUpPanel);
		add(endingPanel);

		setSize(1000, 700);
		setBorder(new LineBorder(Color.BLACK, 1));
		setVisible(true);
	}

	/* -------------- 배경 그리기 -------------- */
	public void paintComponent(Graphics g) {
		ImageIcon bgImg = new ImageIcon("./Images/background.png");
		g.drawImage(bgImg.getImage(), 0, 0, null);
	}

	/* -------------- 게임 다시시작 method -------------- */
	public void restart() {
		typingGame.removeAll();

		// 쓰레드 종료
		stopThreads();

		// 기존 게임 삭제
		remove(typingGame);

		// 리셋
		GameInfo.resetAll();
		reset();

		// 새로운 게임 생성
		typingGame = new TypingGame();
		typingGame.setBounds(0, 60, 1000, 540);
		add(typingGame);
	}

	/* -------------- 게임 오버 method -------------- */
	public void gameOver() {
		gameBGM.interrupt(); 			// 게임오버 시 브금 멈춤
		typingGame.setVisible(false); 	// 기존 게임 숨기기
		gameOverPanel.refresh();		// 점수 업데이트
		gameOverPanel.setVisible(true); // 게임 오버 다이얼로그
		gameOverSe.play("./Audio/gameOverSe.wav", false);
	}

	/* -------------- 게임 정보 리셋 함수 -------------- */
	public void reset() {
		gameManager = new GameManager();
		wordGenerator = new WordGenerator();

		// 레벨, 스코어, 하트 update
		StatusPanel.update();
		UserPanel.update();
		statusPanel.repaintAll(Color.RED); // 하트 리셋
	}

	/* -------------- 쓰레드 종료 함수 -------------- */
	public static void stopThreads() {
		GameInfo.stop = true;	// flag

		// 메인 쓰레드 종료될 때까지 대기
		try {
			gameManager.join();
			wordGenerator.join();
		} catch (InterruptedException e) { }
	}

	/* -------------- 다음 레벨로 넘어가는 함수 -------------- */
	public void nextLevel()
	{
		gameBGM.interrupt(); // 브금 멈춤

		levelUpSound.play("./Audio/harp.wav", false); //레벨업 효과음

		// 기존 게임 삭제
		typingGame.removeAll();
		remove(typingGame);

		// 3초간 Level Up 띄우기
		levelUpPanel.setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) { }
		levelUpPanel.setVisible(false);

		GameInfo.levelUp();	// 레벨업
		GameInfo.resetData();	// 데이터 리셋
		reset();	// 게임 리셋

		// 새로운 게임 생성
		typingGame = new TypingGame();
		typingGame.setBounds(0, 60, 1000, 540);
		add(typingGame);

		revalidate();
		repaint();
	}

	/* -------------- 엔딩 쓰레드 -------------- */
	public void ending() {
		gameBGM.interrupt(); // 브금 멈춤

		// 기존 게임 삭제
		typingGame.removeAll();
		remove(typingGame);

		// 엔딩 패널
		endingPanel.setVisible(true);
		endingSe.play("./Audio/endingSe.wav", false);
	}

	/* -------------- 교수님 퀴즈 내기 -------------- */
	public boolean quiz() {

		Word quiz = new Word(false);

		// 게임 패널에 추가
		typingGame.add(quiz);

		int currentScore = GameInfo.score;

		// 교수님 쓰레드 시작 -> 일반 단어들보다 세로가 길어서 따로 데드라인 줌 (y: 410)
		WordRain wordRain = new WordRain(quiz, 410);
		wordRain.start();

		try {
			wordRain.join();
		} catch (InterruptedException e) { }

		// 문제를 맞췄다면? (= 스코어가 올라갔다면)
		return GameInfo.score != currentScore;
	}

	/* -------------- 타자 게임 패널 -------------- */
	class TypingGame extends JPanel {
		private JLabel snowCb; // 눈송빌런 이미지

		public TypingGame() {
			setLayout(null);
			setOpaque(false);

			/* -------------- 눈송빌런 이미지 -------------- */
			snowCb = new JLabel(new ImageIcon("./Images/snowCb.gif"));
			snowCb.setBounds(850, 410, snowCb.getIcon().getIconWidth(), snowCb.getIcon().getIconHeight());
			add(snowCb);

			/* -------------- 게임 bgm -------------- */
			gameBGM = new AudioPlayer("./Audio/gamebgm.wav");
			gameBGM.start();

			/* -------------- 게임 시작 -------------- */
			gameManager.start();

			setSize(1000, 500);
			setVisible(true);
		}
	}

	/* -------------- 게임 관리 쓰레드 -------------- */
	class GameManager extends Thread {
		public void run() {

			// 단어 생성 쓰레드 실행
			wordGenerator.start();

			// 5초 뒤부터 확인 시작
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				return;
			}

			// stop 사인이 올때까지 sleep
			while (!GameInfo.stop) {
				try {
					// 단어 생성이 끝났고 쓰레드가 비어있으면 탈출 -> 다음 레벨!
					if (!wordGenerator.isAlive() && GameInfo.currentThreads.size() == 0)
					{
						// 퀴즈 method 호출 후 결과값으로 level up or game over 결정
						if (quiz()) {
							break;
						}
						else {
							gameOver();
							return;
						}
					}
					Thread.sleep(500);
				} catch (InterruptedException e) {
					return;
				}
			}

			// while loop 탈출 후 각 조건에 맞게 처리
			if (GameInfo.life == 0) {
				gameOver();
			}
			// back 버튼 눌렸을 경우 종료
			else if (GameInfo.back) {
				return;
			}
			else if (!wordGenerator.isAlive() && GameInfo.currentThreads.size() == 0) {
				if (GameInfo.level == 10) {
					ending();
					return;
				}
				nextLevel();
			}
		}
	}

	/* -------------- 단어 생성 쓰레드 -------------- */
	// 단어 하나씩 n초 간격으로 프레임에 붙이기
	class WordGenerator extends Thread
	{
		public ArrayList<String> words = new ArrayList<>();	// 현재 주차 단어들

		public void run() {
			words.clear();
			words.addAll(WordStorage.getStorage().weeks.get(GameInfo.level-1)); // 해당 주차 단어 저장 (level-1이 인덱스임)
			Collections.shuffle(words); // 단어 섞기

			for (int i = 0; i < words.size(); i++) {
				try {
					// stop 사인 왔을 때 break
					if (!GameInfo.stop)
					{
						// 단어 생성
						Word word = new Word(words.get(i));

						// 패널에 붙이기
						typingGame.add(word);

						// 산성비 쓰레드 시작
						WordRain wordRain = new WordRain(word);
						wordRain.start();

						Thread.sleep(GameInfo.interval); // 레벨 별 단어 붙이는 interval
					} else {
						break;
					}
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}

	/* -------------- 게임 오버 패널 -------------- */
	class GameOverPanel extends JPanel {
		JLabel lb_title, tr_title, lb_score, lb_level;
		JLabel btn_restart, btn_exit;

		public GameOverPanel() {
			setLayout(null);

			/* -------------- GameOver Title -------------- */
			lb_title = new JLabel(new ImageIcon("./Images/gameover.png"));
			int gap = 5;
			int x = (1000 - lb_title.getIcon().getIconWidth()) / 2;
			int y = gap;
			lb_title.setBounds(x-10, y+60, lb_title.getIcon().getIconWidth(), lb_title.getIcon().getIconHeight());

			/* -------------- Score -------------- */
			lb_score = new JLabel("최종 스코어: " + GameInfo.score + "점");
			lb_score.setFont(new Font(null, Font.BOLD, 20));
			y += lb_title.getIcon().getIconHeight() +70;
			lb_score.setBounds((1000 - lb_score.getPreferredSize().width) / 2 - 35, y, 300, lb_score.getPreferredSize().height);

			/* -------------- Level -------------- */
			lb_level = new JLabel(" 최종 레벨: " + GameInfo.level + "주차");
			lb_level.setFont(new Font(null, Font.BOLD, 20));
			y += lb_score.getHeight() + 10;
			lb_level.setBounds((1000 - lb_level.getPreferredSize().width) / 2 - 37, y, 300, lb_level.getPreferredSize().height);


			/* -------------- tryagain -------------- */
			tr_title = new JLabel(new ImageIcon("./Images/tryagain.png"));
			y += lb_level.getHeight() + 20;
			tr_title.setBounds((1000 - tr_title.getIcon().getIconWidth()) / 2 - 20, y, tr_title.getIcon().getIconWidth(), tr_title.getIcon().getIconHeight());


			/* -------------- Button(Yes/No)-------------- */
			btn_restart = new JLabel(new ImageIcon("./Images/yes.png"));
			y += tr_title.getHeight() + gap;
			btn_restart.setBounds((1000 - btn_restart.getIcon().getIconWidth()) / 2- 75, y, btn_restart.getIcon().getIconWidth(), btn_restart.getIcon().getIconHeight());

			btn_restart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setVisible(false);
					restart(); // 재시작
				}
			});

			btn_exit = new JLabel(new ImageIcon("./Images/no.png"));
			int x1 = btn_restart.getX() + btn_restart.getWidth() + gap;
			btn_exit.setBounds(x1, y+3, btn_exit.getIcon().getIconWidth(), btn_exit.getIcon().getIconHeight());

			btn_exit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.exit(0); // 프로그램 종료
				}
			});

			/* -------------- 폰트 적용 -------------- */
			try {
				Font font = Font.createFont(Font.TRUETYPE_FONT, new File("./Text/font.ttf"));

				Font sizedFont = font.deriveFont(20f);
				lb_score.setFont(sizedFont);
				lb_level.setFont(sizedFont);
			} catch (IOException | FontFormatException e) { }

			add(lb_title);
			add(lb_score);
			add(lb_level);
			add(tr_title);
			add(btn_restart);
			add(btn_exit);

			setSize(1000,540);
			setOpaque(false);
			setVisible(false); //게임 종료되었을 때만 게임오버 패널이 뜨게 하기 위함.
		}

		public void refresh() {
			lb_score.setText("최종 스코어: " + GameInfo.score + "점");
			lb_level.setText(" 최종 레벨: " + GameInfo.level + "주차");
		}
	}

	/* -------------- Ending Panel -------------- */
	class EndingPanel extends JPanel {
		JLabel lb_title;
		JLabel btn_restart, btn_exit, everyone;

		public EndingPanel() {
			setLayout(null);

			/* -------------- Ending Title -------------- */
			lb_title = new JLabel(new ImageIcon("./Images/gamecomplete.png"));
			int gap = 5;
			int x = (1000 - lb_title.getIcon().getIconWidth()) / 2;
			int y = gap;
			lb_title.setBounds(x-10, y+30, lb_title.getIcon().getIconWidth(), lb_title.getIcon().getIconHeight());


			/* -------------- Restart Button-------------- */
			btn_restart = new JLabel(new ImageIcon("./Images/yes.png"));
			int btn_restart_X = (1000 - btn_restart.getIcon().getIconWidth()) / 2-80;
			int btn_restart_Y = lb_title.getY() + lb_title.getHeight() + 10;
			btn_restart.setBounds(btn_restart_X, btn_restart_Y, btn_restart.getIcon().getIconWidth(), btn_restart.getIcon().getIconHeight());

			btn_restart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setVisible(false);
					restart(); // 재시작
				}
			});

			/* -------------- Exit Button-------------- */
			btn_exit = new JLabel(new ImageIcon("./Images/no.png"));
			int btn_exit_X = (1000 - btn_exit.getIcon().getIconWidth()) / 2+10;
			int btn_exit_Y = btn_restart_Y;
			btn_exit.setBounds(btn_exit_X, btn_exit_Y, btn_exit.getIcon().getIconWidth(), btn_exit.getIcon().getIconHeight());

			btn_exit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.exit(0); // 프로그램 종료
				}
			});

			/* -------------- Exit Button-------------- */
			everyone = new JLabel(new ImageIcon("./Images/everyone.png"));
			int everyone_X = (1000 - everyone.getIcon().getIconWidth()) / 2-50;
			int everyone_Y = btn_exit.getY() + btn_exit.getHeight() + 7;
			everyone.setBounds(everyone_X, everyone_Y, everyone.getIcon().getIconWidth(), everyone.getIcon().getIconHeight());


			add(lb_title);
			add(btn_restart);
			add(btn_exit);
			add(everyone);

			setSize(1000,640);
			setOpaque(false);
			setVisible(false);
		}
	}
}
