import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application{
    
    private static final String numericRegex = "[+]?\\d+$";
    private int xTiles, yTiles;
    private int width = 1000;
    private int height = 500;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DnD Map Generator");
    
        generatePopup();
    
        final GridPane gridPane = generateGridPane();
        GridPane rootPane = generateRootPane(gridPane);
        
        MenuItem changeBackground = new MenuItem("Change map...");
        
        changeBackground.setOnAction(event -> changeNodeBackground(primaryStage, gridPane));

        Menu fileMenu = new Menu("File", null, changeBackground);
        MenuBar menuBar = new MenuBar(fileMenu);
        
        rootPane.add(menuBar, 0, 0, 3, 1);
        
        Scene scene = new Scene(rootPane, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void changeNodeBackground(Stage primaryStage, Region node) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(primaryStage);

        System.out.println(file.getPath());

        if(file.getPath().equals("/resources")){
            System.out.println("hi");
        }

        node.setBackground(getBackground(file.getName()));
    }

    private GridPane generateRootPane(GridPane gridPane) {
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

    private GridPane generateGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25));
        gridPane.setPrefSize(width, height);
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gridPane.setBackground(getBackground("little_tavern__1st_floor__by_daceyrose_rpg-d67pt64.jpg"));
        
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);
        
        Button button;
        for (int i = 0; i < yTiles; i++) {
            for (int j = 0; j < xTiles; j++) {
                button = createButton(i + "," + j);
                gridPane.add(button, j, i, 1, 1);
                if(i==0) {
                    gridPane.getColumnConstraints().add(columnConstraints);
                }
            }
            gridPane.getRowConstraints().add(rowConstraints);
        }
        return gridPane;
    }

    private Background getBackground(String path) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(path), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
                                   new BackgroundSize(0,0, false, false,
                                   false, true));
        
        return new Background(backgroundImage);
    }

    private Button createButton(String text) {
        Button button;
        button = new Button(text);
        button.setOnMouseClicked(event -> System.out.println(text));
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        button.setOnContextMenuRequested(event -> {
            MenuItem changeBackground = new MenuItem("Change background");
            changeBackground.setOnAction(event1 -> changeNodeBackground(primaryStage, button));
            
            MenuItem removeBackground = new MenuItem("Remove background");
            removeBackground.setOnAction(event1 -> button.setBackground(Background.EMPTY));
            
            ContextMenu contextMenu = new ContextMenu(changeBackground, removeBackground);
            contextMenu.show(primaryStage, event.getScreenX(), event.getScreenY());
        });

        BorderStroke borderStroke = new BorderStroke(Color.valueOf("black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                                                     BorderWidths.DEFAULT);        
        button.setBorder(new Border(borderStroke));
        button.setBackground(Background.EMPTY);
        return button;
    }
    
    private void generatePopup(){
        Stage newStage = new Stage();
        TextField x = new TextField("x");
        TextField y = new TextField("y");
        Text multi = new Text("*");
        Label error = new Label("Please make sure you've inserted only positive numbers");
        error.setTextFill(Paint.valueOf("red"));
        error.setVisible(false);
        VBox comp = new VBox(error, new HBox(x, multi, y));
        
        Button ok = new Button("ok");
        ok.setOnMouseClicked(event -> {
            
            if(!x.getText().matches(numericRegex) || !y.getText().matches("[+]?\\d+$")){
                error.setVisible(true);
                return;
            }
            
            xTiles = Integer.parseInt(x.getText());
            yTiles = Integer.parseInt(y.getText());
            newStage.close();
        });
        
        ok.setDefaultButton(true);
        
        comp.getChildren().add(ok);
        
        newStage.setOnHidden(event -> System.out.println(xTiles + ", " + yTiles));
        
        Scene stageScene = new Scene(comp, 100, 100);
        newStage.setScene(stageScene);
        newStage.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
