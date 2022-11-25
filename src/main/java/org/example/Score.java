package org.example;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

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

    public void increseScore() {
        this.setScore(getScore() + 100);
    }

    public String getScoreText() {
        return "Score " + this.getScore();
    }

    static public void createScore(Terminal terminal, Score score) throws IOException {
        String text = score.getScoreText();
        for (var i = 0; i < text.length(); i++) {
            terminal.setCursorPosition(score.scoreColumn + i, score.scoreRow);
            terminal.putCharacter(text.charAt(i));
        }
        terminal.flush();
    }

    static public void addScore(Terminal terminal, Score score) throws IOException {
        score.increseScore();

        //addScore(terminal, score);
        String text = score.getScoreText();
        for (var i = 0; i < text.length(); i++) {
            terminal.setCursorPosition(score.scoreColumn + i, score.scoreRow);
            terminal.putCharacter(text.charAt(i));
        }
        terminal.flush();
    }


}
