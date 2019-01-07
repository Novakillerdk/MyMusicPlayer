package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
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
    private Image playPauseImg = new Image(new File(playPath).toURI().toString());

    public void initialize()
    {
        play.setContentDisplay(ContentDisplay.CENTER);
        play.setGraphic(new ImageView(playPauseImg));
    }

    @FXML
    private void handlePlayPause (ActionEvent event)
    {
        if(isPlaying)
        {
            isPlaying = false;
            playPauseImg = new Image(new File(playPath).toURI().toString());
        }
        else if (!isPlaying)
        {
            isPlaying = true;
            playPauseImg = new Image(new File(pausePath).toURI().toString());
        }
        play.setGraphic(new ImageView(playPauseImg));
    }
}
