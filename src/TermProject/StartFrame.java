package TermProject;

import javax.swing.*;
import java.awt.event.*;

/*     < ------- 게임의 시작화면을 나타내주는 JFrame ------- >
    - 게임시작 버튼 및 게임설명 버튼을 가지고 있고 각 버튼에는 마우스 이벤트 리스너가 붙어있다.
    - 게임시작 버튼 -> JavaGame으로 전환.
    - 게임설명 버튼 ->게임 설명을 보여주는 Dialog 팝업.
 */
public class StartFrame extends JFrame {
	private ImageIcon bgImage = new ImageIcon("./Images/startbg.png");
	private ImageIcon arrow = new ImageIcon("./Images/arrow.png");
	private ImageIcon arrow_gif = new ImageIcon("./Images/arrow.gif");
	private ImageIcon start = new ImageIcon("./Images/start.png");
	private ImageIcon introduction = new ImageIcon("./Images/introduction.png");

	private JLabel lb_arrow1 = new JLabel(arrow);
	private JLabel lb_arrow2 = new JLabel(arrow);
	private JLabel bgLabel;
	private JButton btn_start = new JButton(start);
	private JButton btn_intro = new JButton(introduction);
	public JavaGame gamePanel;
	public AudioPlayer bgost;

	public StartFrame() {
		setTitle("Java를 잡아먹자!");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(bgImage.getIconWidth(), bgImage.getIconHeight());
		setResizable(false);
		setLocationRelativeTo(null);

		/* -------------- Button 생성 & 위치 설정 -------------- */
		btn_start.setBounds(295, 290, start.getIconWidth(), start.getIconHeight());
		btn_start.setBorderPainted(false);
		btn_start.setContentAreaFilled(false);
		btn_start.setFocusPainted(false);

		btn_intro.setBounds(295, 350, introduction.getIconWidth(), introduction.getIconHeight());
		btn_intro.setBorderPainted(false);
		btn_intro.setContentAreaFilled(false);
		btn_intro.setFocusPainted(false);

		/* -------------- Label 생성 & 위치 설정 -------------- */
		lb_arrow1.setBounds(225, 280, arrow.getIconWidth(), arrow.getIconHeight());
		lb_arrow2.setBounds(225, 340, arrow.getIconWidth(), arrow.getIconHeight());

		/* -------------- Button Event Handler -------------- */
		btn_start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 영역에 들어감
				lb_arrow1.setIcon(arrow_gif);
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 영역에서 벗어남
				lb_arrow1.setIcon(arrow);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				gamePanel = new JavaGame();
				gamePanel.setVisible(true);
				StartFrame.this.dispose(); // Close this current Frame
			}
		});

		btn_intro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lb_arrow2.setIcon(arrow_gif);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lb_arrow2.setIcon(arrow);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// background music추가
				bgost = new AudioPlayer("./Audio/bgost.wav");
				bgost.start();

				// 다이알로그 생성
				JDialog introDialog = new JDialog();
				setDefaultCloseOperation(introDialog.EXIT_ON_CLOSE);
				introDialog.addWindowListener(new WindowAdapter() { // 창이 닫혔을 때 bgost끝나도록 설정
					@Override
					public void windowClosing(WindowEvent e) {
						bgost.interrupt();
					}
				});
				JLabel dialogBG = new JLabel(new ImageIcon("./Images/introgame1.png"));

				// next_btn 추가
				JButton next_btn = new JButton(new ImageIcon("./Images/next.png"));
				next_btn.setBounds(385, 295, next_btn.getIcon().getIconWidth(), next_btn.getIcon().getIconHeight());
				next_btn.setBorderPainted(false); // 테두리 설정 안함
				dialogBG.add(next_btn);

				introDialog.setContentPane(dialogBG);
				introDialog.pack();
				introDialog.setVisible(true);

				//next_btn 이벤트 처리: 몇번 클릭하는지에 따라 보이는 것이 달라짐.
				next_btn.addActionListener(new ActionListener() {
					int clickCount = 1;

					@Override
					public void actionPerformed(ActionEvent event) {
						switch (clickCount) {
						case 1:
							dialogBG.setIcon(new ImageIcon("./Images/introgame2.png"));
							next_btn.setBounds(385, 295, next_btn.getIcon().getIconWidth(),
									next_btn.getIcon().getIconHeight());
							break;
						case 2:
							dialogBG.setIcon(new ImageIcon("./Images/introgame3.png"));
							next_btn.setBounds(385, 170, next_btn.getIcon().getIconWidth(),
									next_btn.getIcon().getIconHeight());
							break;
						case 3:
							dialogBG.setIcon(new ImageIcon("./Images/introgame4.png"));
							next_btn.setBounds(385, 170, next_btn.getIcon().getIconWidth(),
									next_btn.getIcon().getIconHeight());
							break;
						case 4:
							dialogBG.setIcon(new ImageIcon("./Images/introgame5.png"));
							JButton play_btn = new JButton(new ImageIcon("./Images/play.png"));
							play_btn.setBounds(385, 295, play_btn.getIcon().getIconWidth(),
									play_btn.getIcon().getIconHeight());
							play_btn.setBorderPainted(false);

							dialogBG.add(play_btn);
							dialogBG.remove(next_btn);

							play_btn.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									gamePanel = new JavaGame();
									gamePanel.setVisible(true);

									bgost.interrupt();

									introDialog.setVisible(false); // Close the dialog
								}
							});
							break;
						default:
							break;
						}
						clickCount++;
					}
				});
				introDialog.setContentPane(dialogBG);
				introDialog.pack();
				introDialog.setResizable(false);
				introDialog.setLocationRelativeTo(null);
				introDialog.setVisible(true);
			}
		});

		/* -------------- Frame에 컴포넌트 추가 -------------- */
		bgLabel = new JLabel(bgImage);
		bgLabel.setBounds(0, 0, bgImage.getIconWidth(), bgImage.getIconHeight());

		add(lb_arrow1);
		add(lb_arrow2);
		add(btn_start);
		add(btn_intro);
		add(bgLabel);

		/* Set Frame to visible */
		setVisible(true);
	}

}
