package api.game;

import java.util.Arrays;

/**
 * @author : AlexGiazitzis
 * @project : Battleship
 * @created : 02-Feb-21 at 2:52 PM
 **/

class Gamefield {

    final private char[][] player1FoggedField;
    final private char[][] player2FoggedField;
    final private char[][] player1Field;
    final private char[][] player2Field;
    final int FIELD_LENGTH = 10;

    Gamefield() {

        this.player1Field = initField();
        this.player2Field = initField();

        this.player1FoggedField = initField();
        this.player2FoggedField = initField();

    }

    public char[][] getPlayerField(boolean playerTurn) {

        if (playerTurn) {
            return player1Field.clone();
        } else {
            return player2Field.clone();
        }

    }



    private char[][] initField() {

        char[][] field = new char[FIELD_LENGTH][FIELD_LENGTH];
        Arrays.stream(field).forEach(inner -> Arrays.fill(inner, '~'));

        return field.clone();

    }

    public void printPlayerPerspective(boolean playerTurn) {

        if (playerTurn) {

            printField(player2FoggedField);
            System.out.println("---------------------");
            printField(player1Field);

        } else {

            printField(player1FoggedField);
            System.out.println("---------------------");
            printField(player2Field);

        }

    }

    public void printField(char[][] field) {

        System.out.print("  ");

        for (int counter = 0; counter < field[0].length; counter++) {
            System.out.print((counter + 1) + " ");
        }

        System.out.println();

        for (int i = 0; i < field.length; i++) {
            System.out.print("" + (char) ('A' + i) + ' ');
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    void setFieldPointValue(int x, int y, char value, boolean playerTurn) {

        if (playerTurn) {

            player1Field[x][y] = value;

            if (value != 'O') {
                player1FoggedField[x][y] = value;
            }

        } else {

            player2Field[x][y] = value;

            if (value != 'O') {
                player2FoggedField[x][y] = value;
            }

        }

    }

}
