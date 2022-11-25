package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.googlecode.lanterna.Symbols.*;
import static org.example.Shoot.shoot;

public class Main {
    final static char block = '\u2588';

    public static void main(String[] args) throws Exception {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();

        boolean continueReadingInput = true;

        int column = 10;
        int row = 10;
        int oldPositionC = column;
        int oldPositionR = row;
        final char player = FACE_WHITE;
        char direction = 'd';
        Timer timer = new Timer();
        Score score = new Score();
        Score.createScore(terminal, score);

        ArrayList<Position> walls2 = Walls.CreateWalls1(terminal);

        terminal.setCursorPosition(column, row);
        terminal.putCharacter(player);
        terminal.setCursorVisible(false);
        terminal.flush();

        timer.schedule(new TimerTask() {        // Add a new monster every 3 sec
            @Override
            public void run() {
                try {
                    walls2.add(CreateMonster(terminal));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 3000);

        Countdown.Countdown(terminal, timer, score);    // Start countdown


        while (continueReadingInput) {      // Game loop

            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();

            if (c == Character.valueOf('q')) {
                continueReadingInput = false;
                System.out.println("quit");
                terminal.close();
            }

            if (Countdown.timesUp) {
                continueReadingInput = false;
            }

            terminal.setCursorPosition(column, row);
            terminal.putCharacter(' ');
            oldPositionR = row;
            oldPositionC = column;

            switch (c) {
                case 'w' -> {
                    row -= 1;
                    direction = 'w';
                }
                case 'a' -> {
                    column -= 1;
                    direction = 'a';
                }
                case 's' -> {
                    row += 1;
                    direction = 's';
                }
                case 'd' -> {
                    column += 1;
                    direction = 'd';
                }
            }

            for (Position p : walls2) {
                if (column == p.column && row == p.row) {
                    column = oldPositionC;
                    row = oldPositionR;
                    break;
                }
            }

            terminal.setCursorPosition(column, row);
            terminal.putCharacter(player);
            terminal.flush();

            if (c == Character.valueOf('k')) {   // Call shoot method when k is pressed
                shoot(terminal, column, row, direction, walls2, score);
            }

        }
    }

    public static Position CreateMonster(Terminal terminal) throws IOException {
        Random addMonster = new Random();
        Position monsterPosition = new Position(addMonster.nextInt(80), addMonster.nextInt(24));
        terminal.setCursorPosition(monsterPosition.column, monsterPosition.row);
        terminal.putCharacter(DOUBLE_LINE_CROSS);
        terminal.flush();

        return monsterPosition;
    }


}