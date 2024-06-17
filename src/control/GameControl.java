
package control;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import model.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameControl {

    private final GameModel gameBoard;
    private final boolean useStrongAI;
    private final Random random = new Random();

    public GameControl(GameModel gameBoard, boolean useStrongAI) {
        this.gameBoard = gameBoard;
        this.useStrongAI = useStrongAI;
    }

    // Récupère les mouvements valides pour l'IA
    public List<Point2D> getValidMoves(Point2D lastMove) {
        List<Point2D> validMoves = new ArrayList<>();
        for (int i = Math.max(0, (int) lastMove.getY() - 1); i <= Math.min(gameBoard.getSize() - 1, (int) lastMove.getY() + 1); i++) {
            for (int j = Math.max(0, (int) lastMove.getX() - 1); j <= Math.min(gameBoard.getSize() - 1, (int) lastMove.getX() + 1); j++) {
                if (gameBoard.isValidMove(j, i, lastMove)) {
                    validMoves.add(new Point2D(j, i));
                }
            }
        }
        return validMoves;
    }

    // Sélectionne le meilleur mouvement pour l'IA forte
    public Point2D getBestMove(Point2D lastMove) {
        List<Point2D> validMoves = getValidMoves(lastMove);
        if (useStrongAI) {
            // Vérifie si l'IA peut gagner
            for (Point2D move : validMoves) {
                int x = (int) move.getX();
                int y = (int) move.getY();
                if (gameBoard.canWin(x, y, Color.GREEN)) {
                    return move; // Priorise le mouvement gagnant
                }
            }

            // Vérifie si l'IA doit bloquer un mouvement gagnant du joueur
            for (Point2D move : validMoves) {
                int x = (int) move.getX();
                int y = (int) move.getY();
                if (gameBoard.canWin(x, y, Color.PURPLE)) {
                    return move; // Bloque le mouvement gagnant du joueur
                }
            }
        }
        // Choisit un mouvement aléatoire parmi les mouvements valides
        return validMoves.isEmpty() ? null : validMoves.get(random.nextInt(validMoves.size()));
    }
}

