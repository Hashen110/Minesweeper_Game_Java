package game.minesweeper.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MinesweeperController implements Initializable {
    private Label[][] boxes = new Label[10][10];
    private boolean isGameOver = false;
    @FXML
    private GridPane board;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateMines();
    }

    private void generateMines() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Label mine = new Label("  ");
            mine.setTextFill(Color.RED);
            setLabel(mine, random.nextInt(10), random.nextInt(10));
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (boxes[i][j] == null) setLabel(new Label(" "), i, j);
                int finalI = i;
                int finalJ = j;
                boxes[i][j].setOnMouseClicked(event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    if (!isGameOver) {
                        Label label = (Label) event.getSource();
                        label.setStyle("-fx-background-color: #ccc;");
                        if (label.getText().equalsIgnoreCase("  ")) {
                            alert.setHeaderText("You Lose...!");
                            alert.showAndWait();
                            isGameOver = true;
                            for (Label[] box : boxes) {
                                for (Label box1 : box) {
                                    if (box1.getText().equalsIgnoreCase("  ")) box1.setText("X");
                                }
                            }
                        } else {
                            int mines = 0;
                            for (int k = Math.max(finalI - 1, 0); k <= Math.min(finalI + 1, 9); k++) {
                                for (int l = Math.max(finalJ - 1, 0); l <= Math.min(finalJ + 1, 9); l++) {
                                    if (boxes[k][l].getText().equalsIgnoreCase("  ")) mines++;
                                }
                            }
                            boolean isWin = true;
                            label.setText(String.valueOf(mines));
                            for (Label[] box : boxes) {
                                for (Label box1 : box) {
                                    if (box1.getText().equalsIgnoreCase(" ")) isWin = false;
                                }
                            }
                            if (isWin) {
                                alert.setHeaderText("You Win...!");
                                alert.showAndWait();
                                isGameOver = true;
                            }
                        }
                    }
                });
            }
        }
    }

    private void setLabel(Label label, int row, int col) {
        label.setFont(new Font(40));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        board.add(label, row, col);
        boxes[row][col] = label;
    }

    @FXML
    private void btnNewGameOnAction() {
        isGameOver = false;
        Node node = board.getChildren().get(0);
        board.getChildren().clear();
        board.getChildren().add(node);
        boxes = new Label[10][10];
        generateMines();
    }
}
