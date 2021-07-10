package fr.lernejo.navy_battle.handler;

public class JsonFireHandler {
    public final int col;
    public final int row;
    public final String cell;

    public JsonFireHandler(String cell) {
        this.cell = cell;
        this.col = cell.charAt(0) - 'A';
        if (cell.length() == 2)
            this.row = cell.charAt(1) - '0' - 1;
        else
            this.row = 9;

    }
}
