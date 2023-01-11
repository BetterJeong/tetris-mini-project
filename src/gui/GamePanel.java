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
                    case NONE:
                        g.setColor(Color.WHITE);
                        break;
                    case PINK:
                        g.setColor(Color.PINK);
                        break;
                    case YELLOW:
                        g.setColor(Color.YELLOW);
                        break;
                    case GREEN:
                        g.setColor(Color.GREEN);
                        break;
                    case BLUE:
                        g.setColor(Color.BLUE);
                        break;
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

                while (!gm.isGameEnd()) { // 블럭이 땅에 닿으면 종료
                    if (gm.isDropped()) {   // 블럭이 땅에 닿으면
                        System.out.println("블럭 생성");
                        gm.generateBlock(); // 새 블럭 생성
                        repaint();  // 맵 다시 그리기
                    }
                    else {  // 블럭 떨어지기
                        gm.moveDown();  // 블럭 한 칸 아래로 이동
                        repaint();  // 맵 다시 그리기
                        try {
                            sleep(700); // 0.7초 마다 떨어짐
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }
        }
    }
}
