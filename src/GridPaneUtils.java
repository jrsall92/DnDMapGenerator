import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GridPaneUtils {
    
    private int width, height, xTiles, yTiles;
    private Stage primaryStage;

    GridPaneUtils(int width, int height, int xTiles, int yTiles, Stage primaryStage) {
        this.width = width;
        this.height = height;
        this.xTiles = xTiles;
        this.yTiles = yTiles;
        this.primaryStage = primaryStage;
    }

    public GridPane generateRootPane(GridPane gridPane) {
        GridPane rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPrefSize(width, height);
        rootPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        rootPane.getColumnConstraints().add(getColumnConstraints(10));
        rootPane.getColumnConstraints().add(getColumnConstraints(80));
        rootPane.getColumnConstraints().add(getColumnConstraints(10));


        rootPane.getRowConstraints().add(getRowConstraints(10));
        rootPane.getRowConstraints().add(getRowConstraints(80));
        rootPane.getRowConstraints().add(getRowConstraints(10));

        rootPane.add(gridPane, 1, 1, 1, 1);
        return rootPane;
    }

    private ColumnConstraints getColumnConstraints(Number percentage) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        columnConstraints.setPercentWidth(percentage.doubleValue());
        return columnConstraints;
    }

    private RowConstraints getRowConstraints(Number percentage) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);
        rowConstraints.setPercentHeight(percentage.doubleValue());
        return rowConstraints;
    }

    public GridPane generateGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25));
        gridPane.setPrefSize(width, height);
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gridPane.setBackground(NodeUtils.getBackground("little_tavern__1st_floor__by_daceyrose_rpg-d67pt64.jpg"));

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);

        GridPane tile;
        for (int i = 0; i < yTiles; i++) {
            for (int j = 0; j < xTiles; j++) {
                tile = generateTile();
                gridPane.add(tile, j, i, 1, 1);
                if(i==0) {
                    gridPane.getColumnConstraints().add(columnConstraints);
                }
            }
            gridPane.getRowConstraints().add(rowConstraints);
        }
        return gridPane;
    }
    
    private GridPane generateTile(){
        GridPane tile;
        tile = new GridPane();
        tile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);

        Button button;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                button = ButtonUtils.createButton(null, primaryStage);
                tile.add(button, j, i, 1, 1);
                if(i==0) {
                    tile.getColumnConstraints().add(columnConstraints);
                }
            }
            tile.getRowConstraints().add(rowConstraints);
        }
        
        BorderStroke borderStroke = new BorderStroke(Color.valueOf("black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT);
        tile.setBorder(new Border(borderStroke));
        tile.setBackground(Background.EMPTY);
        return tile;
    }
}