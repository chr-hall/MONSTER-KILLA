package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

import static com.googlecode.lanterna.Symbols.*;

public class Shoot {
    public static void shoot(Terminal terminal, int column, int row, Character direction, ArrayList<Position> walls) throws IOException, InterruptedException {

        TerminalSize t = terminal.getTerminalSize();
        int bulletRow = row;
        int bulletCol = column;


        for (int i = 2; i < t.getColumns(); i++) {
            switch (direction) {
                case 'w' -> {
                    terminal.setCursorPosition(column, row - i);
                    bulletRow = row - i;
                    terminal.putCharacter(TRIANGLE_UP_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(column, row - i + 1);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
                case 's' -> {
                    terminal.setCursorPosition(column, row + i);
                    bulletRow = row + i;
                    terminal.putCharacter(TRIANGLE_DOWN_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(column, row + i - 1);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
                case 'a' -> {
                    terminal.setCursorPosition(column - i, row);
                    bulletCol = column - i;
                    terminal.putCharacter(TRIANGLE_LEFT_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(column - i + 1, row);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
                case 'd' -> {
                    terminal.setCursorPosition(column + i, row);
                    bulletCol = column + i;
                    terminal.putCharacter(TRIANGLE_RIGHT_POINTING_BLACK);
                    terminal.flush();
                    terminal.setCursorPosition(column + i - 1, row);
                    terminal.putCharacter(' ');
                    terminal.flush();
                }
            }
            monsterCheck(bulletRow, bulletCol, walls, terminal);
            Thread.sleep(3);
        }


    }

    public static void monsterCheck(int bulletRow, int bulletCol, ArrayList<Position> walls, Terminal terminal) throws IOException, InterruptedException {
        for (Position p : walls) {
            if (bulletCol == p.column && bulletRow == p.row) {
                terminal.setCursorPosition(p.column, p.row);
                terminal.putCharacter('\u2620');
                terminal.flush();
                Thread.sleep(1000);
            }
        }
    }


}
