package model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class GameModel {

    private final int size;
    private final Color[][] board;

    public GameModel(int size) {
        this.size = size;
        this.board = new Color[size][size];
    }

    // Vérifie si un mouvement est valide
    public boolean isValidMove(int x, int y, Point2D lastMove) {
        if (board[y][x] != null) {
            return false;
        }
        if (lastMove == null) {
            return true;
        }
        for (int i = Math.max(0, (int) lastMove.getY() - 1); i <= Math.min(size - 1, (int) lastMove.getY() + 1); i++) {
            for (int j = Math.max(0, (int) lastMove.getX() - 1); j <= Math.min(size - 1, (int) lastMove.getX() + 1); j++) {
                if (i == y && j == x) {
                    return true;
                }
            }
        }
        return false;
    }

    // Définit la couleur d'une cellule
    public void setCell(int x, int y, Color color) {
        board[y][x] = color;
    }

    // Vérifie si un joueur a gagné
    public boolean checkWin(Color player) {
        // Vérification des lignes
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 3; j++) {
                if (player.equals(board[i][j]) && player.equals(board[i][j + 1]) && player.equals(board[i][j + 2]) && player.equals(board[i][j + 3])) {
                    return true;
                }
            }
        }

        // Vérification des colonnes
        for (int i = 0; i < size - 3; i++) {
            for (int j = 0; j < size; j++) {
                if (player.equals(board[i][j]) && player.equals(board[i + 1][j]) && player.equals(board[i + 2][j]) && player.equals(board[i + 3][j])) {
                    return true;
                }
            }
        }

        // Vérification des diagonales
        for (int i = 0; i < size - 3; i++) {
            for (int j = 0; j < size - 3; j++) {
                if (player.equals(board[i][j]) && player.equals(board[i + 1][j + 1]) && player.equals(board[i + 2][j + 2]) && player.equals(board[i + 3][j + 3])) {
                    return true;
                }
            }
        }

        for (int i = 3; i < size; i++) {
            for (int j = 0; j < size - 3; j++) {
                if (player.equals(board[i][j]) && player.equals(board[i - 1][j + 1]) && player.equals(board[i - 2][j + 2]) && player.equals(board[i - 3][j + 3])) {
                    return true;
                }
            }
        }

        return false;
    }

    // Vérifie si la partie est nulle
    public boolean isDraw() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Vide le tableau
    public void clearBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = null;
            }
        }
    }

    // Renvoie la taille de la grille
    public int getSize() {
        return size;
    }

    // Vérifie si un joueur peut gagner en jouant à une position donnée
    public boolean canWin(int x, int y, Color player) {
        board[y][x] = player;
        boolean win = checkWin(player);
        board[y][x] = null;
        return win;
    }
}

