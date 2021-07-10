package fr.lernejo.navy_battle.game;

public class Cell {
    public final Integer[][] cells = new Integer[10][10];

    public Cell() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (i <= 4 && j == 0) { this.cells[i][j] = 1; }
                else if (i >= 6 && j == 3) { this.cells[i][j] = 2; }
                else if (i <= 2 && j == 6) { this.cells[i][j] = 3; }
                else if (i == 5 && (j >= 5 && j <= 7)) { this.cells[i][j] = 4; }
                else if (i == 2 && (j == 8 || j == 9)) { this.cells[i][j] = 5; }
                else { this.cells[i][j] = 0; }
            }
        }
    }
}
