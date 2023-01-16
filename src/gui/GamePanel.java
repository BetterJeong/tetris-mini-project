package gui;

import Service.GameManager;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GamePanel extends JPanel {

    GameManager gm;

    public GamePanel() {
        gm = GameManager.getGm();

        Thread t = new Thread(new GameThread());    // 게임 진행 쓰레드
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < gm.getMAX_X_SIZE(); i++) {
            for (int j = 0; j < gm.getMAX_Y_SIZE(); j++) {
                // 블럭 종류에 따라 색상 지정
                switch (gm.getMap()[i][j].getBlockType()) {
                    case NONE: g.setColor(new Color(252, 252, 252)); break;
                    case RED: g.setColor(new Color(212, 129, 125)); break;
                    case ORANGE: g.setColor(new Color(208, 162, 113)); break;
                    case YELLOW: g.setColor(new Color(208, 189, 113)); break;
                    case GREEN: g.setColor(new Color(126, 207, 110)); break;
                    case BLUE: g.setColor(new Color(110, 202, 207)); break;
                    case INDIGO: g.setColor(new Color(106, 121, 205)); break;
                    case PURPLE: g.setColor(new Color(200, 106, 205)); break;
                }
                // 블럭 그리기
                g.fillRect(i * 30, j * 30, 30, 30);
            }
        }

    }

    class GameThread implements Runnable {
        @Override
        public void run() {
            synchronized (gm) {
                gm.generateBlock(); // 블럭 생성
                repaint();  // 맵 다시 그리기
                int speed = 0;  // 내려오는 속도

                while (!gm.isGameEnd()) { // 블럭이 땅에 닿으면 종료
                    if (gm.isDropped()) {   // 블럭이 땅에 닿으면
                        System.out.println("호출");
                        gm.generateBlock(); // 새 블럭 생성
                        repaint();  // 맵 다시 그리기
                    }
                    else {  // 블럭 떨어지기
                        gm.moveDown();  // 블럭 한 칸 아래로 이동
                        repaint();  // 맵 다시 그리기
                        try {
                            if (speed < 400) {  // 속도가 점점 빨라짐
                                speed += 1;
                            }
                            sleep(500 - speed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }
}
