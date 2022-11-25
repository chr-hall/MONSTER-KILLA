package org.example;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

    static int counter = 10;    // Counter seconds
    static int counterMin = 5;  // Counter minute
    static boolean timesUp = false;

    public static void Countdown(Terminal terminal, Timer timer, Score score) {

        timer.schedule(new TimerTask() {        // Timer Task to count down timer
            @Override
            public void run() {
                addCount();
                if (timesUp) {                  // End game when time is up
                    try {
                        gameOver(terminal, score);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    printTimeLeft(terminal);
                    printTime(terminal, counter, counterMin);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 1000);

    }

    public static void printTimeLeft(Terminal terminal) throws IOException {
        String count = "TIME LEFT";
        for (var i = 0; i < count.length(); i++) {
            terminal.setCursorPosition(1 + i, 2);
            terminal.putCharacter(count.charAt(i));
            terminal.flush();
        }
    }

    public static void printTime(Terminal terminal, int counter, int counterMin) throws IOException {
        terminal.setCursorPosition(2, 3);
        terminal.putCharacter((char) (counter + '0'));       // Cast int to char
        terminal.setCursorPosition(1, 3);
        terminal.putCharacter((char) (counterMin + '0'));
        terminal.flush();
    }

    public static int addCount() {
        counter--;
        if (counter == 0) {
            counter = 9;
            counterMin--;
        }
        if (counterMin < 0) { // After 1 minute, time is up
            timesUp = true;
        }
        return counter;
    }

    public static void gameOver(Terminal terminal, Score score) throws IOException, InterruptedException {
        if (Countdown.timesUp) {    // Clear screen, print GAME OVER and display score
            String gameover = "GAME OVER!";
            String sco = "YOUR SCORE WAS: " + score.getScore();
            terminal.clearScreen();
            for (int i = 0; i < gameover.length(); i++) {
                terminal.setCursorPosition(35 + i, 10);
                terminal.putCharacter(gameover.charAt(i));
            }
            for (int i = 0; i < sco.length(); i++) {
                terminal.setCursorPosition(30 + i, 12);
                terminal.putCharacter(sco.charAt(i));
            }
            terminal.flush();
            Thread.sleep(5000);
        }
    }


}
