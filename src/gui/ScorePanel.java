package gui;

import Service.GameManager;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class ScorePanel extends JPanel {

    private GameManager gm;
    private Font font;
    JLabel scoreText;
    JLabel score;
    JLabel authorText;
    JLabel authorNumText;
    JLabel authorNameText;

    public ScorePanel() {
        gm = GameManager.getGm();

        scoreText = new JLabel();
        score = new JLabel();
        authorText = new JLabel();
        authorNumText = new JLabel();
        authorNameText = new JLabel();

        font = new Font("Galmuri7 Regular", Font.PLAIN, 20);
        setLayout(null);    // 레이아웃 설정

        init();

        Thread t = new Thread(new ScoreThread());
        t.start();
    }

    private void init() {   // 기본 세팅 메서드
        initScoreLabel();   // 점수 레이블 배치
    }

    private void initScoreLabel() { // 점수 레이블 설정
        scoreText.setText("점수");    // 점수 텍스트
        score.setText(String.valueOf(gm.getUser().getScore())); //점수
        authorText.setText("제작자");
        authorNumText.setText("2171110");
        authorNameText.setText("나은정");

        scoreText.setFont(font);   // 폰트, 크기 세팅
        score.setFont(font);
        authorText.setFont(font);
        authorNumText.setFont(font);
        authorNameText.setFont(font);

        scoreText.setForeground(new Color(255, 255, 255)); // 텍스트 컬러 지정
        score.setForeground(new Color(255, 255, 255));
        authorText.setForeground(new Color(255, 255, 255));
        authorNumText.setForeground(new Color(255, 255, 255));
        authorNameText.setForeground(new Color(255, 255, 255));

        setBackground(new Color(66, 66, 66));   // 배경 색 지정

        scoreText.setBounds(0, 10, 90, 50);    // 배치
        scoreText.setHorizontalAlignment(JLabel.CENTER);
        score.setBounds(0, 70, 90, 20);
        score.setHorizontalAlignment(JLabel.CENTER);
        authorText.setBounds(0, 290, 90, 20);
        authorText.setHorizontalAlignment(JLabel.CENTER);
        authorNumText.setBounds(0, 320, 90, 50);
        authorNumText.setHorizontalAlignment(JLabel.CENTER);
        authorNameText.setBounds(0, 350, 90, 50);
        authorNameText.setHorizontalAlignment(JLabel.CENTER);

        add(scoreText); // 레이블 추가
        add(score);
        add(authorText);
        add(authorNumText);
        add(authorNameText);
    }

    class ScoreThread implements Runnable {

        @Override
        public void run() {
            while (!gm.isGameEnd()) {
                score.setText(String.valueOf(gm.getUser().getScore()));
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
