package org.example;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

    static int counter = 10;
    static int counterMin = 5;

    static boolean timesUp = false;

    public static void Countdown(Terminal terminal, Timer timer) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addCount();
                    try {
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
        terminal.setCursorPosition(3, 3);
        terminal.putCharacter((char)(counter + '0'));
        terminal.setCursorPosition(2, 3);
        terminal.putCharacter((char)(counterMin + '0'));
        terminal.flush();
    }


    public static int addCount() {
        counter--;
        if (counter == 0) {
                counter = 9;
                counterMin--;
        } if (counterMin < 0) {
            timesUp = true;
        }
        return counter;
    }


}
