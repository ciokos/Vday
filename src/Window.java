import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends Application {

    private Stage window;
    private RandomImageGenerator rig;
    private Image image;
    private BufferedImage image_to_save;
    private String image_name;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        rig = new RandomImageGenerator();
        window.setScene(StartScene());
        window.show();
    }

    private Scene StartScene(){
        Button start = new Button("Start");
        Label txt = new Label("dla Gosi");
        txt.setId("text");
        start.setOnAction(e -> {
            window.setScene(TextScene());
        });
        BorderPane root = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.getChildren().addAll(txt);
        root.setCache(true);
        root.setCenter(start);
        root.setBottom(hBox);
        root.getStylesheets().add("styles.css");
        root.setPadding(new Insets(30, 0, 30, 0));
        return new Scene(root, 640, 430);
    }

    private Scene TextScene(){
        Button generate = new Button("Generate Image");
        Label txt = new Label("Give me some text");
        TextField textfield = new TextField();
        BorderPane root = new BorderPane();
        HBox hBox = new HBox();
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.BASELINE_CENTER);
        hBox2.getChildren().addAll(txt);
        txt.setId("text");
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.getChildren().addAll(generate);
        root.setCache(true);
        root.setCenter(textfield);
        root.setTop(hBox2);
        root.setBottom(hBox);
        root.getStylesheets().add("styles.css");
        root.setPadding(new Insets(60, 50, 30, 50));

        generate.setOnAction(e -> {
            if(!textfield.getText().isEmpty()){
                image_name = textfield.getText();
                image = SwingFXUtils.toFXImage(rig.createImage(textfield.getText()), null);
                image_to_save = rig.createImage(textfield.getText());
                window.setScene(DisplayScene());
            }
            else{
                AlertBox.display("Error", "C'mon give me some text to work with");
            }
        });

        return new Scene(root, 640, 430);
    }

    private Scene DisplayScene(){

        Button back = new Button("Return");
        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        back.setOnAction(e -> {
            window.setScene(TextScene());
        });
        Button save = new Button("Save");

        save.setOnAction(e -> {

            try{
                File f = new File(System.getProperty("user.dir") + "/" +image_name + ".png");
                ImageIO.write(image_to_save, "png", f);
            }catch(IOException e1){
                System.out.println("Error: " + e);
            }
            window.setScene(EndScene());

        });
        BorderPane root = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(20);
        hBox.getChildren().addAll(back,save);
        root.setCache(true);
        root.setCenter(imageView);
        root.setBottom(hBox);
        root.getStylesheets().add("styles.css");
        root.setPadding(new Insets(10, 0, 20, 0));
        return new Scene(root, 640, 430);
    }

    private Scene EndScene(){
        Button exit = new Button("Exit");
        exit.setOnAction(e -> window.close());

        Button again = new Button("Make Another");
        again.setOnAction(e -> window.setScene(TextScene()));

        BorderPane root = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.getChildren().addAll(exit);
        root.setCache(true);
        root.setCenter(again);
        root.setBottom(hBox);
        root.getStylesheets().add("styles.css");
        root.setPadding(new Insets(30, 0, 30, 0));
        return new Scene(root, 640, 430);
    }
}