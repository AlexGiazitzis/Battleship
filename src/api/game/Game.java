package api.game;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author : AlexGiazitzis
 * @project : Battleship
 * @created : 02-Feb-21 at 2:51 PM
 **/

public class Game {

    Battleship[] player1Ships;
    Battleship[] player2Ships;
    Gamefield game;
    final Scanner scanner = new Scanner(System.in);
    boolean playerTurn = true; // true = player 1's turn

    public Game() {

        this.player1Ships = initBattleshipList();
        this.player2Ships = initBattleshipList();
        this.game = new Gamefield();
    }

    public void start() {

        playerFieldsSetup();
        changePlayer();
        play();

    }

    private void setPlayerShips(Battleship[] ships) {

        if (playerTurn) {
            player2Ships = ships;
        } else {
            player1Ships = ships;
        }

    }

    private Battleship[] initBattleshipList() {
        Battleship[] temp = new Battleship[Ship.maxShips];
        int counter = 0;
        for (Ship ship : Ship.values()) {
            temp[counter] = new Battleship(ship);
            ++counter;
        }

        return temp;
    }

    private void playerFieldsSetup() {

        System.out.println("Player 1, place your ships on the game field\n");
        for (Battleship ship : player1Ships) {

            game.printField(game.getPlayerField(playerTurn));
            placeBattleship(ship);

        }

        game.printField(game.getPlayerField(playerTurn));

        changePlayer();

        System.out.println("Player 2, place your ships on the game field\n");
        for (Battleship ship : player2Ships) {

            game.printField(game.getPlayerField(playerTurn));
            placeBattleship(ship);

        }

        game.printField(game.getPlayerField(playerTurn));


    }

    private void placeBattleship(Battleship bShip) {

        System.out.printf("\nEnter the coordinates of the %s (%d Cells):\n\n", bShip.getShip().getName(), bShip.getShip().getSize());

        int row1;
        int row2;
        int column1;
        int column2;

        do {

            String[] fieldCoords = scanner.nextLine().split("\\s");
            row1 = fieldCoords[0].charAt(0) - 'A';
            row2 = fieldCoords[1].charAt(0) - 'A';
            column1 = Integer.parseInt(fieldCoords[0].substring(1)) - 1;
            column2 = Integer.parseInt(fieldCoords[1].substring(1)) - 1;

        } while (!isPlacementInputCorrect(row1, row2, column1, column2, bShip.getShip(), game.getPlayerField(playerTurn)));

        if (column1 < column2 || row1 < row2) {

            bShip.setxStart(row1);
            bShip.setxFinish(row2);
            bShip.setyStart(column1);
            bShip.setyFinish(column2);

        } else {

            bShip.setxStart(row2);
            bShip.setxFinish(row1);
            bShip.setyStart(column2);
            bShip.setyFinish(column1);

        }

        if (column1 == column2) {

            for (int size = Math.min(row1, row2); size <= Math.max(row1, row2); size++) {

                game.setFieldPointValue(size, column1, 'O', playerTurn);

            }

        } else {

            for (int size = Math.min(column1, column2); size <= Math.max(column1, column2); size++) {

                game.setFieldPointValue(row1, size, 'O', playerTurn);

            }

        }

    }

    private boolean isPlacementInputCorrect(int row1, int row2, int column1, int column2, Ship ship, final char[][] field) {

        //check coords if out of bounds
        if (row1 < 0 || row1 > game.FIELD_LENGTH - 1 || row2 < 0 || row2 > game.FIELD_LENGTH - 1
                || column1 < 0 || column1 > game.FIELD_LENGTH - 1 || column2 < 0 ||
                column2 > game.FIELD_LENGTH - 1) {

            System.out.println("\nError! Row or Column out of bounds of the field. Try again:\n\n");
            return false;

        }

        //check proper orientation
        if (row1 != row2 && column1 != column2) {

            System.out.println("\nError! Wrong ship location! Try again:\n");
            return false;

        }

        //check proper length in regards to ship size
        if (Math.abs(row1 - row2) + 1 != ship.getSize() && column1 == column2 ||
                Math.abs(column1 - column2) + 1 != ship.getSize() && row1 == row2) {

            System.out.printf("\nError! Wrong length of the %s! Try again:\n\n", ship.getName());
            return false;

        }

        //check proximity to another ship
        if (isAnotherNearby(row1, row2, column1, column2, field)) {

            System.out.println("\nError! You placed it too close to another one. Try again:\n");
            return false;

        }

        return true;

    }

    private boolean isAnotherNearby(int row1, int row2, int column1, int column2, final char[][] field) {

        //make sure it's in limits without the need of lots of `if`s
        int yStart = Math.max(Math.min(column1, column2) - 1, 0);
        int yEnd = Math.min(Math.max(column1, column2) + 1, field[0].length - 1);
        int xStart = Math.max(Math.min(row1, row2) - 1, 0);
        int xEnd = Math.min(Math.max(row1, row2) + 1, field.length - 1);

        for (int x = xStart; x <= xEnd; x++) {

            for (int y = yStart; y <= yEnd; y++) {

                if (field[x][y] == 'O') {

                    return true;

                }

            }

        }

        return false;

    }

    private void changePlayer() {

        System.out.println("\nPress Enter and pass the move to another player");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerTurn = !playerTurn;

    }

    private void play() {

        do {
            game.printPlayerPerspective(playerTurn);

            if (playerTurn) {

                System.out.println("\nPlayer 1, it's your turn:\n");

            } else {

                System.out.println("\nPlayer 2, it's your turn:\n");

            }

            shoot();

            if (player1Ships.length == 0 || player2Ships.length == 0) {
                break;
            }

            changePlayer();

        } while (true);

        System.out.println("You sank the last ship. You won. Congratulations!");

        changePlayer();

    }

    private void shoot() {

        int x;
        int y;

        do {

            String shotCoords = scanner.nextLine();
            x = shotCoords.charAt(0) - 'A';
            y = Integer.parseInt(shotCoords.substring(1)) - 1;

        } while (!isShotInputCorrect(x, y));

        modifyValue(x, y);
    }

    private boolean isShotInputCorrect(int x, int y) {

        if (x < 0 || x >= game.FIELD_LENGTH || y < 0 || y >= game.FIELD_LENGTH) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            return false;
        }
        return true;
    }

    private void modifyValue(int x, int y) {

        char[][] field = game.getPlayerField(!playerTurn);

        if (field[x][y] == 'X') {

            System.out.println("You hit a ship!");
            return;

        }

        if (field[x][y] == 'O') {

            boolean sank = shootProperShip(x, y);
            game.setFieldPointValue(x, y, 'X', !playerTurn);

            if (sank && (!playerTurn ? player1Ships : player2Ships).length > 0) {

                System.out.println("\nYou sank a ship!\n");

            } else if ((!playerTurn ? player1Ships : player2Ships).length > 0) {

                System.out.println("\nYou hit a ship!\n");
            }
        } else {

            game.setFieldPointValue(x, y, 'M', !playerTurn);
            System.out.println("\nYou missed!\n");

        }

    }


    // return true if the shot sank a ship, else returns false
    private boolean shootProperShip(int x, int y) {

        int index = 0;

        Battleship[] ships =  (!playerTurn ? player1Ships : player2Ships);

        for (Battleship ship : ships) {

            if (ship.isAlive() && ship.getxStart() <= x && ship.getxFinish() >= x
                    && ship.getyStart() <= y && ship.getyFinish() >= y) {

                int shipHealth = ship.getHealth();
                ship.setHealth(shipHealth > 0 ? shipHealth - 1 : 0);
                ship.setAlive(ship.getHealth() > 0);

                if (!(ship.isAlive())) {

                    setPlayerShips(removeShip(index, ships));
                    return true;

                }

            }

            index++;

        }

        return false;

    }

    private Battleship[] removeShip(int index, Battleship[] ships) {

        List<Battleship> list = new LinkedList<>(Arrays.asList(ships));
        list.remove(index);

        Battleship[] temp = new Battleship[ships.length - 1];

        for (int counter = 0; counter < temp.length; counter++) {
            temp[counter] = list.get(counter);
        }

        return temp.clone();
    }


}
