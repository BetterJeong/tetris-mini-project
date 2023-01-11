package gui;

import Service.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TetrisFrame extends JFrame {

    GameManager gm;

    public TetrisFrame() throws HeadlessException {
        gm = GameManager.getGm();
        setSize(400,700); // 창 사이즈 지정
        setTitle("진짜 대박 재밌는 테트리스"); // 창 이름 지정
        setLocationRelativeTo(null); // 창을 가운데로 띄움
        setDefaultCloseOperation(EXIT_ON_CLOSE); // X 버튼으로 창을 닫으면 프로그램 종료
        setLayout(null);    // 레이아웃 설정

        setVisible(true); // 창을 화면에 보이게 설정
        setResizable(false);    // 창 크기 변경 불가능

        addKeyListener(new KeyListener());    // 키 리스너 추가

        init(); // 초기 화면 설정
    }

    private void init() {   // 기본 세팅 메서드
        add(getTitlePanel());   // 타이틀 패널 배치
        add(getScorePanel());    // 점수 패널 배치
        add(getGamePanel());    // 게임 패널 배치
    }

    private JPanel getTitlePanel() {    // 타이틀 패널 세팅 메서드
        TitlePanel panel = new TitlePanel();
        panel.setBounds(0, 0, 400, 100);
        return panel;
    }

    private JPanel getScorePanel() {    // 점수 패널 세팅 메서드
        ScorePanel panel = new ScorePanel(gm.getUser());
        panel.setBounds(300, 101, 100, 40);
        return panel;
    }

    private JPanel getGamePanel() { // 게임 패널 세팅 메서드
        GamePanel panel = new GamePanel();
        panel.setBounds(0, 100, 300, 600);
        return panel;
    }

    public class KeyListener extends KeyAdapter {
        // 키를 누르면 좌, 우 이동, 아래 방향키 누르면 빠르게 떨어지기, 스페이스바 누르면 바로 낙하
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int keyCode = e.getKeyCode();

            if (gm.isKey()) {   // 블럭이 땅에 있지 않은 경우만 이동 가능
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:  // 왼쪽 1칸 이동
                        gm.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT: // 오른쪽 1칸 이동
                        gm.moveRight();
                        break;
                    case KeyEvent.VK_DOWN:  // 아래로 1칸 이동
                        if (gm.isDown()) {
                            gm.moveDown();
                        }
                        break;
                    case KeyEvent.VK_SPACE: // 바로 땅에 떨어지기
                        gm.moveImmediately();
                        gm.generateBlock();
                        break;
                }
                repaint();
            }
        }
    }
}