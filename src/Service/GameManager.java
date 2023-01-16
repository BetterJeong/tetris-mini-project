package Service;

import gui.ScorePanel;
import model.Block;
import model.BlockType;
import model.User;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private static final GameManager gm = new GameManager();
    private User user;  // 유저 게임 정보
    private int MAX_X_SIZE = 10;    // 맵 X 크기
    private int MAX_Y_SIZE = 15;    // 맵 Y 크기
    private Block[][] map;  // 전체 맵
    private boolean[][] check;  // 블럭 존재 여부
    private int nowX;   // 현재 생성된 블록 X 좌표
    private int nowY;   // 현재 생성된 블록 Y 좌표
    private ArrayList<int[]> success;  // 없애기에 성공한 블록 좌표 배열
    private boolean[][] visited;    // dfs 에서 방문 처리에 사용

    private GameManager() {
        // 값 초기화
        map = new Block[MAX_X_SIZE][MAX_Y_SIZE];
        check = new boolean[MAX_X_SIZE][MAX_Y_SIZE];
        user = new User();
        nowX = 5;
        nowY = 1;
        success = new ArrayList<>();
        visited = new boolean[MAX_X_SIZE][MAX_Y_SIZE];

        // 맵 초기화
        for (int i = 0; i < MAX_X_SIZE; i++) {
            for (int j = 0; j < MAX_Y_SIZE; j++) {
                map[i][j] = new Block();
                check[i][j] = false;
            }
        }
    }

    public static GameManager getGm() {
        return gm;
    }

    public boolean isGameEnd() {    // 게임이 끝났는지 확인
        boolean b = false;

        // 끝 줄에 블럭이 있으면 true
        for (int i = 0; i < MAX_X_SIZE; i++) {
            if (check[i][0]) {
                b = true;
                System.out.println("게임 종료");
            }
        }

        return b;
    }

    public boolean isDropped() {    // 바닥에 닿았는지 확인
        boolean b = false;

        // 맨 아래칸이거나 아래 칸에 블럭이 있으면 true
        if (nowY == MAX_Y_SIZE-2 || check[nowX][nowY+1]) {
            check[nowX][nowY] = true;
            b = true;
            countScore();
        }

        return b;
    }

    private void fixChecking() {
        for (int i = 0; i < MAX_X_SIZE; i++) {
            for (int j = 0; j < MAX_Y_SIZE; j++) {
                if (map[i][j].getBlockType() == BlockType.NONE && check[i][j]) {
                    check[i][j] = false;
                }
            }
        }
    }

    public void generateBlock() {   // 첫 위치에 랜덤 블럭 타입 지정
        fixChecking();
        nowX = (int) (Math.random() * 10);
        nowY = 0;

        System.out.println("새 블럭 생성");
        map[nowX][nowY].setBlockType(getRandomBlockType());

        // 다른 곳에 블록 출력 방지
        for (int i = 0; i < MAX_X_SIZE; i++) {
            if (i != nowX) {
                map[i][0].setBlockType(BlockType.NONE);
            }
        }
    }

    public boolean isKey() {    // 키 입력을 받아도 되는지 여부
        boolean b = false;

        if (!isDropped() || !isGameEnd()) {
            b = true;
        }

        return b;
    }

    public boolean isDown() {   // 블럭이 아래로 내려가고 있는지 여부
        boolean b = false;

        if (nowY < MAX_Y_SIZE-2) {
            b = true;
        }

        return b;
    }

    public void moveDown() {    // 아래로 한 칸 이동
        BlockType b = map[nowX][nowY].getBlockType();   // 현재 블럭 타입 저장

        map[nowX][nowY].setBlockType(BlockType.NONE);   // 현재 블럭 지우기
        map[nowX][++nowY].setBlockType(b);  // 블럭 이동
        fixChecking();
    }

    public void moveLeft() {    // 왼쪽 한 칸 이동
        if (nowX > 0 && !check[nowX-1][nowY]) {
            BlockType b = map[nowX][nowY].getBlockType();   // 현재 블럭 타입 저장

            map[nowX][nowY].setBlockType(BlockType.NONE);   // 현재 블럭 지우기
            map[--nowX][nowY].setBlockType(b);  // 블럭 이동
        }
        fixChecking();
    }

    public void moveRight() {   // 오른쪽 한 칸 이동
        if (nowX < MAX_X_SIZE-1 && !check[nowX+1][nowY]) {
            BlockType b = map[nowX][nowY].getBlockType();   // 현재 블럭 타입 저장

            map[nowX][nowY].setBlockType(BlockType.NONE);   // 현재 블럭 지우기
            map[++nowX][nowY].setBlockType(b);  // 블럭 이동
        }
        fixChecking();
    }

    public void moveImmediately() { // 바로 땅에 떨어지기
        for (int i = 0; i < MAX_Y_SIZE - 1; i++) {
            if (i == MAX_Y_SIZE - 2 || check[nowX][i+1] == true) {
                BlockType b = map[nowX][nowY].getBlockType();   // 현재 블럭 타입 저장
                map[nowX][nowY].setBlockType(BlockType.NONE);   // 현재 블럭 지우기
                nowY = i;   // y 위치 지정

                check[nowX][nowY] = true;  // 땅에 블록 설치
                map[nowX][nowY].setBlockType(b);  // 블럭 이동

                countScore();
                break;
            }
        }
        fixChecking();
    }

    public BlockType getRandomBlockType() { // 랜덤 블럭 반환
        return BlockType.values()[new Random().nextInt(BlockType.values().length-1)+1];
    }

    private void countScore() { // 점수 계산을 위한 메서드
        int[] point = {nowX, nowY};
        success.clear();    // 점수 배열 초기화
        visited = new boolean[MAX_X_SIZE][MAX_Y_SIZE];  // 방문 배열 초기화
        dfs(point);    // 방금 떨어진 블록 기준으로 dfs

        if (success.size() >= 4 ) {  // 인접 블록이 4개 이상이면
            user.addScore(success.size() * 100);    // 점수 계산
            destroyBlock();
            getOffBlock();
        }
    }

    private void dfs(int[] point) {
        visited[point[0]][point[1]] = true; // 방문 체크
        success.add(point);    // 점수 배열에 좌표 넣기
        BlockType b = map[point[0]][point[1]].getBlockType();   // 같은 타입 블록만 계산

        System.out.println("x: " + point[0] + ", y: "+point[1] +", type: " + b);
        System.out.println(success.size());

        if (point[0] > 0 && !visited[point[0]-1][point[1]] && map[point[0]-1][point[1]].getBlockType() == b) {
            dfs(new int[]{point[0] - 1, point[1]}); // 왼쪽 칸 탐색
            System.out.println(success.size());
        }
        if (point[0] < MAX_X_SIZE - 1 && !visited[point[0]+1][point[1]] && map[point[0]+1][point[1]].getBlockType() == b) {
            dfs(new int[]{point[0] + 1, point[1]}); // 오른쪽 칸 탐색
            System.out.println(success.size());
        }
        if (point[1] > 0 && !visited[point[0]][point[1]-1] && map[point[0]][point[1]-1].getBlockType() == b) {
            dfs(new int[]{point[0], point[1] - 1});   // 위 칸 탐색
            System.out.println(success.size());
        }
        if (point[1] < MAX_Y_SIZE - 1 && !visited[point[0]][point[1]+1] && map[point[0]][point[1]+1].getBlockType() == b) {
            dfs(new int[]{point[0], point[1] + 1});   // 아래 칸 탐색
            System.out.println(success.size());
        }

        System.out.println("탐색 끝");
    }

    private void destroyBlock() {
        System.out.println("블록 지우기");
        for (int[] point : success) {
            map[point[0]][point[1]].setBlockType(BlockType.NONE);   // 블록 없애기
            check[point[0]][point[1]] = false;
        }
    }

    private void getOffBlock() {
        System.out.println("블록 정리");
        for (int i = 0; i < MAX_X_SIZE; i++) {
            ArrayList<BlockType> list = new ArrayList<>();  // 색 블록 순서 저장할 배열

            if (needGetOff(i)) {
                for (int j = MAX_Y_SIZE - 1; j > 0; j--) { // 뒤에서부터 순서대로 블록 종류 저장
                    BlockType b = map[i][j].getBlockType();
                    if (check[i][j]) {
                        list.add(b);
                        System.out.println(b);
                        check[i][j] = false;
                        map[i][j].setBlockType(BlockType.NONE);
                    }
                }
            }

            for (int j = 0; j < list.size(); j++) {
                map[i][MAX_Y_SIZE - 2 - j].setBlockType(list.get(list.size() - 1 - j));
                check[i][MAX_Y_SIZE - 2 - j] = true;
            }
        }
    }

    private boolean needGetOff(int i) {
        boolean b = false;

        for (int[] arr : success) {
            if (arr[0] == i) {
                b = true;
                break;
            }
        }

        return b;
    }


    public int getMAX_X_SIZE() {
        return MAX_X_SIZE;
    }

    public int getMAX_Y_SIZE() {
        return MAX_Y_SIZE;
    }

    public Block[][] getMap() {
        return map;
    }

    public User getUser() {
        return user;
    }

}
