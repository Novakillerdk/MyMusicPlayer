package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.File;
import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    private Button playPause;

    @FXML
    private Button stopButton;

    @FXML
    private MediaView mediaPlayer;

    private MediaPlayer mp;
    private Media me;

    @FXML
    private Button track1;
    @FXML
    private Button track2;
    @FXML
    private Slider proBar;
    @FXML
    private Label songName;
    @FXML
    private Label currentTime;
    @FXML
    private Label TTime;
    @FXML
    private FileChooser fileSelection;

    @FXML
    private ListView listView;

    public ArrayList<String> trackList;

    private boolean isPlaying = false;
    private String playPath = new File("src/sample/media/Play.png").getAbsolutePath();
    private String pausePath = new File("src/sample/media/Pause.png").getAbsolutePath();
    private String stopPath = new File("src/sample/media/Stop.png").getAbsolutePath();
    private Image playImg = new Image(new File(playPath).toURI().toString());
    private Image pauseImg = new Image(new File(pausePath).toURI().toString());
    private Image stopImg = new Image(new File(stopPath).toURI().toString());

    private String path = new File(Songs.pizzaTime.getLoc()).getAbsolutePath();

    private Timer updateTimer = new Timer();

    public void initialize() {
        playPause.setContentDisplay(ContentDisplay.CENTER);
        playPause.setGraphic(new ImageView(playImg));

        stopButton.setContentDisplay(ContentDisplay.CENTER);
        stopButton.setGraphic(new ImageView(stopImg));

        path = new File(Songs.pizzaTime.getLoc()).getAbsolutePath();
        setSong();

        mp.setAutoPlay(false);

        addSongs();
        setListView();
    }

    @FXML
    private void handlePlayListChoose(MouseEvent arg0) {
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent click) {

                if (click.getButton() == MouseButton.PRIMARY && click.getClickCount() == 2) {

                    mp.stop();
                    isPlaying = false;
                    playPause.setGraphic(new ImageView(playImg));
                    if (listView.getSelectionModel().getSelectedItem().equals("Pizza")) {
                        path = new File(Songs.pizzaTime.getLoc()).getAbsolutePath();
                        songName.setText(Songs.pizzaTime.getSong());
                    }
                    if (listView.getSelectionModel().getSelectedItem().equals("Bongo")) {
                        path = new File(Songs.testTrack.getLoc()).getAbsolutePath();
                        songName.setText(Songs.testTrack.getSong());
                    }
                    setSong();
                }
            }
        });
    }

    @FXML
    private void handleSongs(ActionEvent event) {
        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();
        mp.stop();
        isPlaying = false;
        playPause.setGraphic(new ImageView(playImg));
        if (buttonPressed.equals("Play track 1")) {
            path = new File(Songs.pizzaTime.getLoc()).getAbsolutePath();
            songName.setText(Songs.pizzaTime.getSong());
        }
        if (buttonPressed.equals("play track 2")) {
            path = new File(Songs.testTrack.getLoc()).getAbsolutePath();
            songName.setText(Songs.testTrack.getSong());
        }
        setSong();
    }

    private void setSong() {
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mediaPlayer.setMediaPlayer(mp);

        updateTimer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                updatesValues();
            }

        };
        updateTimer.schedule(tt, 40);
    }

    public void addSongs() {
        trackList = new ArrayList<>();
        trackList.add(Songs.pizzaTime.getSong());
        trackList.add(Songs.testTrack.getSong());
    }

    @FXML
    private void handlePlayPause(ActionEvent event) {
        if (isPlaying) {
            isPlaying = false;
            playPause.setGraphic(new ImageView(playImg));
            mp.pause();
        } else if (!isPlaying) {
            isPlaying = true;
            playPause.setGraphic(new ImageView(pauseImg));
            mp.play();

            mp.currentTimeProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    updatesValues();
                }
            });
        }
    }

    @FXML
    private void handleStopMedia(ActionEvent event) {
        if (isPlaying) {
            isPlaying = false;
            playPause.setGraphic(new ImageView(playImg));
        }
        mp.stop();
    }

    public void setListView() {
        ObservableList list = FXCollections.observableArrayList(trackList);
        listView.setItems(list);
    }

    private String formatTimer(long formatTime) {
        return String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(formatTime),
                TimeUnit.MILLISECONDS.toSeconds(formatTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(formatTime)));
    }

    private void updatesValues() {
        Platform.runLater(new Runnable() {
            public void run() {
                long secFormat = (long) mp.getCurrentTime().toMillis();
                currentTime.setText(formatTimer(secFormat));

                long endFormat = (long) mp.getStopTime().toMillis();
                TTime.setText(formatTimer(endFormat));

                updateTimer.cancel();
            }
        });
    }

    public void handleNewPlaylist(ActionEvent event) throws Exception {
        try {
            Stage newPlaylist = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("createPlaylist.fxml"));
            newPlaylist.setTitle("New playlist");
            newPlaylist.setScene(new Scene(root1));
            newPlaylist.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handlePlaylist(ActionEvent event) throws Exception {
        try {
            Stage viewPlaylists = new Stage();
            Parent root2 = FXMLLoader.load(getClass().getResource("viewPlaylist.fxml"));
            viewPlaylists.setTitle("Playlists");
            viewPlaylists.setScene(new Scene(root2));
            viewPlaylists.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAllSongs(ActionEvent actionEvent) {
        setListView();
    }

    public void handleSearch(ActionEvent event) throws Exception {
        try {
            Stage searchSongs = new Stage();
            Parent root3 = FXMLLoader.load(getClass().getResource("searchSongs.fxml"));
            searchSongs.setTitle("Search for songs and artists");
            searchSongs.setScene(new Scene(root3));
            searchSongs.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAddSong(ActionEvent event) throws Exception {

        try{
            Stage addsongsTo = new Stage();
            Parent root4 =  FXMLLoader.load(getClass().getResource("tracklist.fxml"));
            addsongsTo.setTitle("Add songs to tracklist");
            addsongsTo.setScene(new Scene(root4));
            addsongsTo.show();

            //TODO
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Add song to tracklist");
            chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("MP3 files", "*.mp3"),
            new FileChooser.ExtensionFilter("WAV files", "*.wav"));
            File defaultDirectory = new File("src/sample/media");
            chooser.setInitialDirectory(defaultDirectory);
            File selectedFile = chooser.showOpenDialog(addsongsTo);
            //String filePath = new File("src/sample/media").getAbsolutePath();
            addsongsTo.close();
        }catch (Exception e){
            e.printStackTrace();
        }





    }
}
