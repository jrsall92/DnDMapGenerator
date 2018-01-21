import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
    
    private static final String numericRegex = "[+]?\\d+$";
    private int xTiles, yTiles;
    private int width = 1000;
    private int height = 500;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DnD Map Generator");
    
        generatePopup();
        
        GridPaneUtils gridPaneUtils = new GridPaneUtils(width, height, xTiles, yTiles, primaryStage);
        
        GridPane gridPane = gridPaneUtils.generateGridPane();
        GridPane rootPane = gridPaneUtils.generateRootPane(gridPane);

        constructMenuBar(primaryStage, gridPane, rootPane);
        
        Scene scene = new Scene(rootPane, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void constructMenuBar(Stage primaryStage, GridPane gridPane, GridPane rootPane) {
        MenuItem changeBackground = new MenuItem("Change map...");

        changeBackground.setOnAction(event -> NodeUtils.changeNodeBackground(primaryStage, gridPane));

        Menu fileMenu = new Menu("File", null, changeBackground);
        MenuBar menuBar = new MenuBar(fileMenu);

        rootPane.add(menuBar, 0, 0, 3, 1);
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
