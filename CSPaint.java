// I worked on the homework assignment alone, using only course materials.
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

/**
 * Represents a Paint application.
 *
 * @author Emily Costello
 * @version 1.0
 */
public class CSPaint extends Application {
    protected Canvas canva = new Canvas(650, 450);
    protected RadioButton drawbut = new RadioButton("Draw");
    protected RadioButton erasebut = new RadioButton("Erase");
    protected RadioButton circlebut = new RadioButton("Circle");
    protected TextField colortxt = new TextField("Type a color");
    protected Button clear = new Button("Clear Canvas");
    protected Button changeBkg = new Button("Change Background Color");
    protected Paint bkgColor = Color.WHITE;
    protected Paint curFill = Color.BLACK;
    protected int shapeCount = 0;
    protected String coord = "(0.0, 0.0)";
    protected String numShapes = "Number of shapes: 0";
    protected Label coordLabel;
    protected Label shapeLabel;

    /**
     * Sets up stage and allows user to draw, erase, clear, change background color,
     * and add circles
     *
     * @param primaryStage the stage that the canvas is on
     */
    public void start(Stage primaryStage) throws Exception {
        BorderPane bpane1 = new BorderPane();
        BorderPane leftPane = new BorderPane();


        GraphicsContext graphics = canva.getGraphicsContext2D();
        graphics.setFill(bkgColor);
        graphics.fillRect(0, 0, 650, 450);

        VBox bottomClear = new VBox(15);
        bottomClear.setAlignment(Pos.CENTER);
        bottomClear.setPadding(new Insets(0, 0, 35, 0));
        bottomClear.getChildren().add(clear);
        bottomClear.getChildren().add(changeBkg);

        leftPane.setTop(getVBox());
        leftPane.setBottom(bottomClear);

        clear.setOnMousePressed(e -> {
                graphics.setFill(bkgColor);
                graphics.fillRect(0, 0, 650, 450);
                shapeCount = 0;
                numShapes = "Number of shapes: " + shapeCount;
                shapeLabel.setText(numShapes);
            });

        changeBkg.setOnMousePressed(e -> {
                bkgColor = curFill;
                graphics.setFill(bkgColor);
                graphics.fillRect(0, 0, 650, 450);
                if (!(bkgColor.equals(Color.WHITE))) {
                    curFill = Color.WHITE;
                } else {
                    curFill = Color.BLACK;
                }
            });

        ToggleGroup group = new ToggleGroup();
        drawbut.setToggleGroup(group);
        erasebut.setToggleGroup(group);
        circlebut.setToggleGroup(group);

        colortxt.setOnAction(e -> {
                String color;
                color = colortxt.getCharacters().toString();
                try {
                    curFill = Paint.valueOf(color);
                    graphics.setFill(curFill);
                } catch (Exception error) {
                    Alert alert = new Alert(AlertType.ERROR, "Invalid Color Entered");
                    alert.show();
                }
            });

        canva.setOnMouseDragged(e -> {
                if (drawbut.isSelected()) {
                    graphics.setStroke(curFill);
                    graphics.setLineWidth(10);
                    graphics.strokeOval(e.getX(), e.getY(), 2, 2);
                } else if (erasebut.isSelected()) {
                    graphics.setStroke(bkgColor);
                    graphics.strokeOval(e.getX(), e.getY(), 10, 10);
                }
            });

        canva.setOnDragDetected(e -> {
                if (drawbut.isSelected()) {
                    shapeCount++;
                    numShapes = "Number of shapes: " + shapeCount;
                    shapeLabel.setText(numShapes);
                }
            });

        canva.setOnMousePressed(e -> {
                if (circlebut.isSelected()) {
                    graphics.setFill(curFill);
                    graphics.fillOval(e.getX(), e.getY(), 15, 15);
                    shapeCount++;
                    numShapes = "Number of shapes: " + shapeCount;
                    shapeLabel.setText(numShapes);
                }
            });

        canva.setOnMouseMoved(e -> {
                coord = "(" + e.getX() + ", " + e.getY() + ")";
                coordLabel.setText(coord);
            });

        bpane1.setCenter(canva);
        bpane1.setLeft(leftPane);
        bpane1.setBottom(getHBox());
        Scene scene = new Scene(bpane1);
        primaryStage.setTitle("CSPaint");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private VBox getVBox() {
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(15, 15, 15, 15));
        vBox.getChildren().addAll(drawbut, erasebut, circlebut, colortxt);
        return vBox;
    }

    private HBox getHBox() {
        HBox hBox = new HBox(15);
        coordLabel = new Label(coord);
        shapeLabel = new Label(numShapes);
        hBox.getChildren().addAll(coordLabel, shapeLabel);
        return hBox;
    }

    private void draw() {

    }

    /**
     * Lauches application
     *
     * @param args user given arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}