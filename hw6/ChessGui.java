import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TablePosition;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Create the GUI for the program.
 *
 * @author msklar3
 * @version 1.4
 * @since 1.4
 */
public class ChessGui extends Application {
    private ChessDb chessDb;
    private TableView<ChessGame> gameView;
    private ListView<String> moveView;
    private ObservableList<ChessGame> gameData;
    private ObservableList<String> moveData;

    /**
     * Run when the application starts. Creates the GUI.
     */
    @Override
    public void start(Stage stage) {
        chessDb = new ChessDb();
        gameView = new TableView<>();
        moveView = new ListView<>();
        gameData = FXCollections.observableArrayList();
        moveData = FXCollections.observableArrayList();

        TableColumn eventColumn = new TableColumn("Event");
        eventColumn.setMaxWidth(250);
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));

        TableColumn siteColumn = new TableColumn("Site");
        siteColumn.setMaxWidth(150);
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setMaxWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn whiteColumn = new TableColumn("White");
        whiteColumn.setMaxWidth(100);
        whiteColumn.setCellValueFactory(new PropertyValueFactory<>("white"));

        TableColumn blackColumn = new TableColumn("Black");
        blackColumn.setMaxWidth(100);
        blackColumn.setCellValueFactory(new PropertyValueFactory<>("black"));

        TableColumn resultColumn = new TableColumn("Result");
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        for (ChessGame game : chessDb.getGames()) {
            gameData.add(game);
        }

        gameView.setItems(gameData);
        gameView.getColumns().addAll(eventColumn, siteColumn, dateColumn,
                                     whiteColumn, blackColumn, resultColumn);

        Button viewButton = new Button();

        final ObservableList<TablePosition> selectedCells =
            gameView.getSelectionModel().getSelectedCells();

        selectedCells.addListener(new ListChangeListener<TablePosition>() {
                @Override
                public void onChanged(Change change) {
                    moveData.clear();

                    for (TablePosition pos : selectedCells) {
                        int i = 1;
                        boolean done = false;

                        while (!done) {
                            try {
                                moveData.add(gameData.get(pos.getRow())
                                             .getMove(i++));
                            } catch (IndexOutOfBoundsException e) {
                                done = true;
                            }
                        }
                    }

                    viewButton.setDisable(false);
                }
            });

        viewButton.setText("View Game");
        viewButton.setDisable(true);
        viewButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ChessGame game;
                    String labelData = "";

                    for (TablePosition pos : selectedCells) {
                        game = gameData.get(pos.getRow());

                        labelData += game.getEvent() + "\n" + game.getSite()
                            + "\n" + game.getDate() + "\n" + game.getWhite()
                            + "\n" + game.getBlack() + "\n" + game.getResult();
                    }

                    Label metadata = new Label(labelData);

                    moveView.setItems(moveData);

                    HBox hBox = new HBox();
                    hBox.getChildren().addAll(moveView);

                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(metadata, hBox);

                    Stage moveStage = new Stage();
                    Scene scene = new Scene(vBox);
                    moveStage.setScene(scene);
                    moveStage.show();
                }
            });

        Button dismissButton = new Button();
        dismissButton.setText("Dismiss");
        dismissButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });

        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(viewButton, dismissButton);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(gameView, buttonHBox);

        Scene scene = new Scene(vBox, 750, 200);
        stage.setScene(scene);
        stage.setTitle("Chess DB GUI");
        stage.show();
    }
}
