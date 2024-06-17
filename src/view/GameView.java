package view;

import control.GameControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.GameModel;


public class GameView extends Application {

    private static final int SIZE = 7;
    private static final int CUBE_SIZE = 50;
    private Color currentPlayer = Color.PURPLE;
    private GameModel gameBoard;
    private Scene gameScene;
    private Label turnInfo;
    private boolean isOver = false;
    private Point2D lastMove;
    private GameControl ai;
    private boolean useStrongAI = true;

    @Override
    public void start(Stage primaryStage) {
        Scene startScene = createStartScene(primaryStage);
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private Scene createStartScene(Stage primaryStage) {
        VBox startMenu = new VBox(20);
        startMenu.setAlignment(Pos.CENTER);
        Button strongAIButton = new Button("Jouer contre IA forte");
        Button weakAIButton = new Button("Jouer contre IA faible");
        Button exitButton = new Button("Quitter");
        startMenu.getChildren().addAll(strongAIButton, weakAIButton, exitButton);

        strongAIButton.setOnAction(event -> {
            useStrongAI = true;
            primaryStage.setScene(createGameScene(primaryStage));
        });
        weakAIButton.setOnAction(event -> {
            useStrongAI = false;
            primaryStage.setScene(createGameScene(primaryStage));
        });
        exitButton.setOnAction(event -> System.exit(0));

        return new Scene(startMenu, 800, 640);
    }

    private Scene createGameScene(Stage primaryStage) {
        gameBoard = new GameModel(SIZE);
        ai = new GameControl(gameBoard, useStrongAI);
        GridPane grid = createGrid();
        VBox gameInfo = createGameInfo();
        HBox gameControls = new HBox(10, gameInfo, createRestartButton(grid), createGameExitButton());
        gameControls.setAlignment(Pos.CENTER);

        VBox gameScreen = new VBox(20, gameControls, grid);
        gameScreen.setAlignment(Pos.CENTER);

        gameScene = new Scene(gameScreen, 800, 640);
        return gameScene;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        grid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle borderRectangle = new Rectangle(CUBE_SIZE, CUBE_SIZE);
                borderRectangle.setFill(Color.WHITE);
                borderRectangle.setStroke(Color.BLACK);

                Rectangle fillRectangle = new Rectangle(CUBE_SIZE - 5, CUBE_SIZE - 5);
                fillRectangle.setFill(Color.WHITE);

                final int x = j;
                final int y = i;
                fillRectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleRectangleClick(x, y, fillRectangle));

                StackPane stackPane = new StackPane(borderRectangle, fillRectangle);
                grid.add(stackPane, j, i);
            }
        }

        return grid;
    }

    private VBox createGameInfo() {
        Label player1Info = new Label("Joueur 1: VIOLET");
        player1Info.setTextFill(Color.PURPLE);
        Label player2Info = new Label("IA: VERT");
        player2Info.setTextFill(Color.GREEN);
        turnInfo = new Label("Tour du joueur 1");
        turnInfo.setTextFill(Color.BLACK);

        return new VBox(10, player1Info, player2Info, turnInfo);
    }

    private Button createRestartButton(GridPane grid) {
        Button restartButton = new Button("Recommencer");
        restartButton.setOnAction(event -> restartGame(grid));
        return restartButton;
    }

    private Button createGameExitButton() {
        Button gameExitButton = new Button("Quitter");
        gameExitButton.setOnAction(event -> Platform.exit());
        return gameExitButton;
    }

    private void handleRectangleClick(int x, int y, Rectangle rectangle) {
        if (isOver || currentPlayer == Color.GREEN) {
            return;
        }
        if (gameBoard.isValidMove(x, y, lastMove)) {
            makeMove(x, y, rectangle);
            if (!isOver && !gameBoard.isDraw()) {
                switchPlayer();
                Platform.runLater(this::aiMove);
            }
        }
    }

    private void aiMove() {
        if (isOver || currentPlayer == Color.PURPLE) {
            return;
        }

        Point2D bestMove = ai.getBestMove(lastMove);

        if (bestMove != null) {
            int x = (int) bestMove.getX();
            int y = (int) bestMove.getY();
            StackPane stackPane = (StackPane) ((GridPane) gameScene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(y * SIZE + x);
            Rectangle fillRectangle = (Rectangle) stackPane.getChildren().get(1);
            makeMove(x, y, fillRectangle);
            if (!isOver && !gameBoard.isDraw()) {
                switchPlayer();
            }
        }
    }

    private void makeMove(int x, int y, Rectangle rectangle) {
        rectangle.setFill(currentPlayer);
        gameBoard.setCell(x, y, currentPlayer);
        lastMove = new Point2D(x, y);
        if (gameBoard.checkWin(currentPlayer)) {
            String winnerMessage = (currentPlayer.equals(Color.PURPLE)) ? "Le joueur 1 gagne!" : "L'IA a gagné!";
            turnInfo.setText(winnerMessage);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin du jeu");
            alert.setHeaderText(null);
            alert.setContentText(winnerMessage);
            alert.showAndWait();
            isOver = true;
        } else if (gameBoard.isDraw()) {
            turnInfo.setText("Le jeu est nul!");
            isOver = true;
        } else {
            turnInfo.setText("Tour du joueur " + (currentPlayer.equals(Color.PURPLE) ? "2" : "1"));
        }
    }

    private void restartGame(GridPane grid) {
        gameBoard.clearBoard();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                StackPane stackPane = (StackPane) grid.getChildren().get(i * SIZE + j);
                Rectangle fillRectangle = (Rectangle) stackPane.getChildren().get(1);
                fillRectangle.setFill(Color.WHITE);
            }
        }
        isOver = false;
        currentPlayer = Color.PURPLE;
        turnInfo.setText("Tour du joueur 1");
        lastMove = null; // Réinitialiser le dernier mouvement
        ai = new GameControl(gameBoard, useStrongAI); // Réinitialiser l'IA
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer == Color.PURPLE ? Color.GREEN : Color.PURPLE;
    }
}
