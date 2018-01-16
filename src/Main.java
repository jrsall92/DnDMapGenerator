import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
    
    private int xTiles, yTiles;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DnD Map Generator");
    
        generatePopup();
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25));
    
        Button button;
        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                button = new Button(i + "," + j);
                button.setOnMouseClicked(event -> System.out.println(((Button) event.getSource()).getText()));
                gridPane.add(button, i, j);
            }
        }
    
        Scene scene = new Scene(gridPane, 100, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
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
            
            if(!x.getText().matches("[+]?\\d+$") || !y.getText().matches("[+]?\\d+$")){
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
