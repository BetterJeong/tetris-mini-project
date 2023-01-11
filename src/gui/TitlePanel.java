package gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    private JLabel titleLabel;

    public TitlePanel() {
        titleLabel = new JLabel();

        init();
    }

    private void init() {   // 기본 세팅 메서드
        initTitleLabel();   // 제목 텍스트 레이블 배치
    }

    private void initTitleLabel() { // 타이틀 레이블 세팅 메서드
        titleLabel.setText("진짜 대박 재밌는 테트리스");   // 텍스트 세팅
        titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15)); // 폰트, 크기 지정
        add(titleLabel);    // 레이블 배치
    }

}
