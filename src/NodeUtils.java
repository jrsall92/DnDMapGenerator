import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NodeUtils {

    public static Background getBackground(String path, Region node) {
        ImageView image = new ImageView(path);
        image.setPreserveRatio(true);
        
        image.fitHeightProperty().bind(node.heightProperty());
        image.fitWidthProperty().bind(node.widthProperty());
        
        BackgroundImage backgroundImage = new BackgroundImage(image.getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
                new BackgroundSize(0,0, false, false,
                        true, false));

        return new Background(backgroundImage);
    }

    public static void changeNodeBackground(Stage primaryStage, Region node) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(primaryStage);

        node.setAccessibleText(file.getName());

        if(file.getPath().equals("/resources")){
            System.out.println("hi");
        }

        node.setBackground(getBackground(file.getName(), node));
    }
}
