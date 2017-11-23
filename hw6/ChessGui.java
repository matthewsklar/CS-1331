import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 */
public class ChessGui extends Application {
    private ChessDb chessDb;
    private TableView<ChessGame> gameView;
    private ObservableList<ChessGame> gameData;

    /**
     *
     */
    @Override
    public void start(Stage stage) {
        chessDb = new ChessDb();
        gameView = new TableView<>();
        gameData = FXCollections.observableArrayList();

        gameView.setEditable(true);

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
            gameData.add(new ChessGame(game.getEvent(), game.getSite(),
                                       game.getDate(), game.getWhite(),
                                       game.getBlack(), game.getResult()));
        }

        gameView.setItems(gameData);
        gameView.getColumns().addAll(eventColumn, siteColumn, dateColumn,
                                     whiteColumn, blackColumn, resultColumn);

        Button viewButton = new Button();
        viewButton.setText("View Game");
        viewButton.setDisable(true);

        Button dismissButton = new Button();
        dismissButton.setText("Dismiss");

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
