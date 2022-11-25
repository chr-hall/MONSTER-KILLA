package org.example;

public class Score {
    int scoreColumn = 1;
    int scoreRow = 1;
    private int score = 0;

    public Score(int score) {
        this.score = score;

    }

    public Score() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increseScore(){
        this.setScore(getScore()+100);
    }

    public String getScoreText(){
        return "Score " + this.getScore();
    }


}
