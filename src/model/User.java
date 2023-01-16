package model;

public class User {

    int score;

    public User() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
