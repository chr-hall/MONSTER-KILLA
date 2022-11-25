package org.example;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

public class Walls {
    final static char block = '\u2588';

    public static ArrayList<Position> CreateWalls1(Terminal terminal) throws IOException {
        ArrayList<Position> walls = new ArrayList<Position>();
        //MITTEN av spelplan col = 40, row = 12
        //Vägg runt plan, Vägg ÖVER spelplan
        for (int i = 0; i < 80; i++) {
            walls.add(new Position(i, 0));
        }
        //Vägg VÄNSTER sida spelplan
        for (int i = 0; i < 24; i++) {
            walls.add(new Position(0, i));
        }
        //Vägg UNDER spelplan
        for (int i = 0; i < 80; i++) {
            walls.add(new Position(i, 23));
        }
        //Vägg HÖGER sida spelplan
        for (int i = 0; i < 24; i++) {
            walls.add(new Position(79, i));
        }
        //TAK på BO
        for (int i = 37; i < 44; i++) {
            walls.add(new Position(i, 11));
        }
        // GOLV på BO
        for (int i = 37; i < 44; i++) {
            walls.add(new Position(i, 13));
        }
        // Kors VÄNSTER sida BO Horisontell
        for (int i = 25; i <= 35; i++) {
            walls.add(new Position(i, 12));
        }
        //Kors VÄNSTER sida bo Vertikal
        for (int i = 9; i < 16; i++) {
            walls.add(new Position(30, i));
        }
        //Kors HÖGER sida bo Horisontell
        for (int i = 45; i <= 55; i++) {
            walls.add(new Position(i, 12));
        }
        //Kors HÖGER sida bo Vertikal
        for (int i = 9; i < 16; i++) {
            walls.add(new Position(50, i));
        }
        //Vägg ÖVERKANT med öppning i mitt
        for (int i = 10; i < 70; i++) {
            if (i > 36 && i < 44){
                continue;
            }
            walls.add(new Position(i, 5));
        }
        //Vägg UNDERKANT med öppning i mitt
        for (int i = 10; i < 70; i++) {
            if (i > 36 && i < 44){
                continue;
            }
            walls.add(new Position(i, 19));
        }
        //Vägg VÄNSTERSIDA med öppning
        for (int i = 5; i < 20; i++) {
            if (i > 10 && i < 14){
                continue;
            }
            walls.add(new Position(9, i));
        }
        //Vägg VÄNSTERSIDA med öppning
        for (int i = 5; i < 20; i++) {
            if (i > 10 && i < 14){
                continue;
            }
            walls.add(new Position(70, i));
        }



        DrawWall(terminal, walls);

        return walls;
    }
    private static void DrawWall(Terminal terminal, ArrayList<Position>  walls) throws IOException {
        for (Position p : walls) {
            terminal.setCursorPosition(p.column, p.row);
            terminal.putCharacter(block);
        }
    }
}
