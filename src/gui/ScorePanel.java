package gui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private User user;

    public ScorePanel(User user) {
        this.user = user;
        setLayout(new BorderLayout());

        init();
    }

    private void init() {   // 기본 세팅 메서드
        initScoreLabel();   // 점수 레이블 배치
    }

    private void initScoreLabel() { // 점수 레이블 설정
        JLabel scoreText = new JLabel("점수");  // 점수 텍스트
        JLabel score = new JLabel(String.valueOf(user.getScore())); // 점수
        scoreText.setFont(new Font("맑은 고딕", Font.PLAIN, 13));   // 폰트, 크기 세팅
        score.setFont(new Font("맑은 고딕", Font.PLAIN, 13));   // 폰트, 크기 세팅
        add(scoreText, BorderLayout.NORTH); // 레이블 배치
        add(score, BorderLayout.SOUTH);
    }
}
