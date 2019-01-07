package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Controller {

    @FXML
    private Button play;

    private boolean isPlaying = false;
    private String playPath = new File("src/sample/media/Play.png").getAbsolutePath();
    private String pausePath = new File("src/sample/media/Pause.png").getAbsolutePath();
    private String stopPath = new File("src/sample/media/Stop.png").getAbsolutePath();

    public void initialize()
    {
        Image playImg = new Image(new File(playPath).toURI().toString());
        play.setGraphic(new ImageView(playImg));
    }

    @FXML
    private void handlePlayPause (ActionEvent event)
    {
        Image playImg = new Image(new File(playPath).toURI().toString());
        if(isPlaying)
        {
            isPlaying = false;
            playImg = new Image(new File(playPath).toURI().toString());
        }
        else if (!isPlaying)
        {
            isPlaying = true;
            playImg = new Image(new File(pausePath).toURI().toString());
        }
        play.setGraphic(new ImageView(playImg));
        System.out.println(isPlaying);
    }
}
