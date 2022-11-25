package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        ArrayList<Position> walls = CreateWalls(terminal);
        Position monsterPosition = CreateMonster(terminal);

        Score score =new Score();
        createScore(terminal, score);

        terminal.setCursorPosition(column, row);
        terminal.putCharacter(player);
        terminal.flush();

        boolean monster = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    walls.add(CreateMonster(terminal));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 5000);

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

            terminal.setCursorPosition(column, row);
            terminal.putCharacter(' ');

            switch (c) {
                case 'w':
                    oldPositionR = row;
                    row -= 1;
                    direction = 'w';
                    break;
                case 'a':
                    oldPositionC = column;
                    column -= 1;
                    direction = 'a';
                    break;
                case 's':
                    oldPositionR = row;
                    row += 1;
                    direction = 's';
                    break;
                case 'd':
                    oldPositionC = column;
                    column += 1;
                    direction = 'd';
                    break;
            }

            for (Position p : walls) {
                if (column == p.column && row == p.row) {
                    column = oldPositionC;
                    row = oldPositionR;
                    break;
                }

            }

            terminal.setCursorPosition(column, row);
            terminal.putCharacter(player);
            terminal.setCursorVisible(false);
            terminal.flush();


            if (column == monsterPosition.column && row == monsterPosition.row) {



        }
                for (int i = 0; i < boom.length(); i++) {
                    terminal.setCursorPosition(monsterPosition.column + i, monsterPosition.row);
                    terminal.putCharacter(boom.charAt(i));
                    terminal.flush();

                }
            }

            // Shoot method call
            if (c == Character.valueOf('k')){
                shoot(terminal, column, row, direction, walls);

            }


        }

    }

    private static ArrayList<Position> CreateWalls(Terminal terminal) throws IOException {
        ArrayList<Position> walls = new ArrayList<Position>();
        walls.add(new Position(4, 15));
        walls.add(new Position(5, 15));
        walls.add(new Position(6, 15));
        walls.add(new Position(7, 15));
        walls.add(new Position(8, 15));
        walls.add(new Position(9, 15));
        walls.add(new Position(10, 15));
        walls.add(new Position(11, 15));
        walls.add(new Position(12, 15));
        walls.add(new Position(13, 15));
        walls.add(new Position(14, 15));
        walls.add(new Position(15, 15));
        walls.add(new Position(1, 10));
        walls.add(new Position(2, 10));
        walls.add(new Position(3, 10));
        walls.add(new Position(4, 10));
        walls.add(new Position(5, 10));
        walls.add(new Position(6, 10));
        walls.add(new Position(7, 10));

        DrawWall(terminal, walls);

        return walls;
    }

    private static void DrawWall(Terminal terminal, ArrayList<Position> walls) throws IOException {
        for (Position p : walls) {
            terminal.setCursorPosition(p.column, p.row);
            terminal.putCharacter(block);
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

    public static void shoot(Terminal terminal, int x, int y, Character direction) throws IOException, InterruptedException {
        TerminalSize t = terminal.getTerminalSize();

        for (int i = 2; i < t.getColumns(); i++) {
            switch (direction) {
                case 'w' -> {
                    terminal.setCursorPosition(x, y - i);
                    terminal.putCharacter(TRIANGLE_UP_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(x, y - i + 1);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
                case 's' -> {
                    terminal.setCursorPosition(x, y + i);
                    terminal.putCharacter(TRIANGLE_DOWN_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(x, y + i - 1);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
                case 'a' -> {
                    terminal.setCursorPosition(x - i, y);
                    terminal.putCharacter(TRIANGLE_LEFT_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(x - i + 1, y);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
                case 'd' -> {
                    terminal.setCursorPosition(x + i, y);
                    terminal.putCharacter(TRIANGLE_RIGHT_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(x + i - 1, y);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
            }
            Thread.sleep(3);
        }
    }


}

//Position bombPosition = CreateBomb(terminal);
/*// om bombposition är på samma column och row som x, så kommer massa B skrivas ut på skärmen, game over.
                if (column == bombPosition.column && row == bombPosition.row) {
                    TerminalSize t = terminal.getTerminalSize();
                    for (int i = 0; i < t.getColumns(); i++) {
                        for (int j = 0; j < t.getRows(); j++) {
                            terminal.setCursorPosition(i, j);
                            terminal.putCharacter('B');
                            //  String message = "GAME OVER!";
                        }
                    }

                    private static Position CreateBomb(Terminal terminal) throws IOException {
            Random r = new Random();
            Position bombPosition = new Position(r.nextInt(80), r.nextInt(24));
            terminal.setCursorPosition(bombPosition.column, bombPosition.row);
            terminal.putCharacter('B');
            terminal.flush();

            return bombPosition;
        }

                }*/
