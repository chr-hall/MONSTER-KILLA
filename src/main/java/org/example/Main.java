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
        char monsterPlayer = '\uDC7D';
        String boom = "Game over";
        Character direction = 'd';
        int count = 0;
        Timer timer = new Timer();
        Score score =new Score();
        Score.createScore(terminal, score);


        ArrayList<Position> walls = new ArrayList<>();

        ArrayList<Position> walls2 = Walls.CreateWalls1(terminal);
        Position monsterPosition = CreateMonster(terminal);

        terminal.setCursorPosition(column, row);
        terminal.putCharacter(player);
        terminal.setCursorVisible(false);
        terminal.flush();

        boolean monster = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    walls2.add(CreateMonster(terminal));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 3000);

        Countdown.printTimeLeft(terminal);
        Countdown.Countdown(terminal, timer, score);

        if (monsterPosition.column == column && monsterPosition.row == row) {
            monster = true;
        }

        if (monster) {
            column = oldPositionC;
            row = oldPositionR;
        } else {
            terminal.setCursorPosition(oldPositionC, oldPositionR);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(column, row);
            terminal.putCharacter(player);
        }

        while (continueReadingInput == true) {

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
                case 'w':
                    row -= 1;
                    direction = 'w';
                    break;
                case 'a':
                    column -= 1;
                    direction = 'a';
                    break;
                case 's':
                    row += 1;
                    direction = 's';
                    break;
                case 'd':
                    column += 1;
                    direction = 'd';
                    break;
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

            if (column == monsterPosition.column && row == monsterPosition.row) {
                for (int i = 0; i < boom.length(); i++) {
                    terminal.setCursorPosition(monsterPosition.column + i, monsterPosition.row);
                    terminal.putCharacter(boom.charAt(i));
                    terminal.flush();

                }
            }


            // Shoot method call
            if (c == Character.valueOf('k')){
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