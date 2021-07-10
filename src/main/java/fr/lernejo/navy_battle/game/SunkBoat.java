package fr.lernejo.navy_battle.game;

import fr.lernejo.navy_battle.handler.JsonFireHandler;

public class SunkBoat {

    public int sunkBoat(Cell map, JsonFireHandler display_Json) {
        if (map.cells[display_Json.row][display_Json.col] == 1 && isCarrierSunk(map) == 0) return 0;
        else if (map.cells[display_Json.row][display_Json.col] == 2 && isBattleshipSunk(map) == 0) return 0;
        else if (map.cells[display_Json.row][display_Json.col] == 3 && isCruiserSunk(map) == 0) return 0;
        else if (map.cells[display_Json.row][display_Json.col] == 4 && isSubmarineSunk(map) == 0) return 0;
        else if (map.cells[display_Json.row][display_Json.col] == 5 && isDestroyerSunk(map) == 0) return 0;

        return 1;
    }

    private int isDestroyerSunk(Cell map) {
        int PartOfBoat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map.cells[i][j] == 4)
                    PartOfBoat += 1;
            }
        }
        if (PartOfBoat == 1)
            return 0;
        return 1;
    }

    private int isSubmarineSunk(Cell map) {
        for (int i = 0; i < 10; i++) { 
            for (int j = 0; j < 10; j++) {
                if (map.cells[i][j] == 3 && (j > 0 && j < 9 && (map.cells[i][j - 1] != 3 || map.cells[i][j + 1] != 3))) {
                    if (i > 0 && i < 9 && (map.cells[i - 1][j] != 3 || map.cells[i + 1][j] != 3)) { return 0; }
                    else if ((i == 0 && map.cells[i + 1][j] != 3) || (i == 9 && map.cells[i - 1][j] != 3)) { return 0; }
                }
            } 
        }
        return 1;
    }
    private int isCruiserSunk(Cell map) {
        for (int i = 0; i < 10; i++) { 
            for (int j = 0; j < 10; j++) {
                if (map.cells[i][j] == 3 && (j == 0 && map.cells[i][j + 1] != 3) || (j == 9 && map.cells[i][j - 1] != 3)) {
                    if (i > 0 && i < 9 && (map.cells[i - 1][j] != 3 || map.cells[i + 1][j] != 3)) { return 0; }
                    else if ((i == 0 && map.cells[i + 1][j] != 3) || (i == 9 && map.cells[i - 1][j] != 3)) { return 0; }
                } 
            } 
        }
        return 1;
    }

    private int isBattleshipSunk(Cell map) {
        int PartOfBoat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map.cells[i][j] == 2)
                    PartOfBoat += 1;
            }
        }
        if (PartOfBoat == 1)
            return 0;
        return 1;
    }

    private int isCarrierSunk(Cell map) {
        int PartOfBoat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map.cells[i][j] == 1)
                    PartOfBoat += 1;
            }
        }
        if (PartOfBoat == 1)
            return 0;
        return 1;
    }
}
