import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
    
    private static final String numericRegex = "[+]?\\d+$";
    private int xTiles, yTiles;
    private int width = 100;
    private int height = 100;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DnD Map Generator");
    
        generatePopup();
    
        GridPane gridPane = generateGridPane();
        
        Scene scene = new Scene(gridPane, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private GridPane generateGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25));
        gridPane.setPrefSize(width, height);
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        
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
    
    private Button createButton(String text) {
        Button button;
        button = new Button(text);
        button.setOnMouseClicked(event -> System.out.println(text));
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
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
