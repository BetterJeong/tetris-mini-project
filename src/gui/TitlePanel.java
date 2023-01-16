package gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    private JLabel titleLabel;
    private Font font;

    public TitlePanel() {
        titleLabel = new JLabel();
        font = new Font("Galmuri7 Regular", Font.PLAIN, 28);  // 폰트, 크기 지정
        setLayout(null);

        init();
    }

    private void init() {   // 기본 세팅 메서드
        titleLabel.setText("진짜 대박 재밌는 테트리스");   // 텍스트 세팅
        titleLabel.setForeground(new Color(255, 255, 255)); // 텍스트 컬러 지정
        titleLabel.setFont(font); // 폰트, 크기 지정
        setBackground(new Color(66, 66, 66));   // 배경 색 지정

        titleLabel.setBounds(0, 0, 400, 100);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        add(titleLabel);    // 레이블 배치
    }

}
