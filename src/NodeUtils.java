import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NodeUtils {

    public static Background getBackground(String path) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(path), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
                new BackgroundSize(0,0, false, false,
                        false, true));

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

        node.setBackground(getBackground(file.getName()));
    }
}
