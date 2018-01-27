import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ButtonUtils {

    public static Button createButton(String text, Stage primaryStage) {
        Button button;
        button = new Button(text);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);        

        setUpButtonActions(button, primaryStage);

        BorderStroke borderStroke = new BorderStroke(Color.valueOf("white"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(0.3));
        button.setBorder(new Border(borderStroke));
        button.setBackground(Background.EMPTY);
        return button;
    }

    private static void setUpButtonActions(Button button, Stage primaryStage) {
        button.setOnContextMenuRequested(event -> {
            MenuItem changeBackground = new MenuItem("Change background");
            changeBackground.setOnAction(event1 -> NodeUtils.changeNodeBackground(primaryStage, button));

            MenuItem removeBackground = new MenuItem("Remove background");
            removeBackground.setOnAction(event1 -> button.setBackground(Background.EMPTY));

            ContextMenu contextMenu = new ContextMenu(changeBackground, removeBackground);
            contextMenu.show(primaryStage, event.getScreenX(), event.getScreenY());
        });
        
        button.setOnDragDetected(event -> {
            Dragboard db = button.startDragAndDrop(TransferMode.ANY);

            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(button.getAccessibleText());
            db.setContent(clipboardContent);

            event.consume();
        });

        button.setOnDragOver(event -> {
            if(event.getGestureSource() != button && event.getDragboard().hasString()){
                event.acceptTransferModes(TransferMode.ANY);
            }

            event.consume();
        });

        button.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasString()) {

                button.setBackground(NodeUtils.getBackground(db.getString(), button));
                button.setAccessibleText(db.getString());

                ((Button) event.getGestureSource()).setBackground(Background.EMPTY);

                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        button.setOnDragEntered(event -> {
            if (event.getGestureSource() != button && event.getDragboard().hasString()) {
                BorderStroke borderStroke = new BorderStroke(Color.valueOf("yellow"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        BorderWidths.DEFAULT);
                button.setBorder(new Border(borderStroke));
            }

            event.consume();
        });

        button.setOnDragExited(event -> {
            BorderStroke borderStroke = new BorderStroke(Color.valueOf("white"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                    new BorderWidths(0.3));

            button.setBorder(new Border(borderStroke));

            event.consume();
        });
    }
}
