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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
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
    private TextField getContent;
    @FXML
    private Slider timeSlider;
    @FXML
    private Label songName;
    @FXML
    private Label currentTime;
    @FXML
    private Label TTime;

    @FXML
    private TableView trackTable;
    @FXML
    private TableColumn trackTableSong;
    @FXML
    private TableColumn trackTableArtist;

    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private TrackList setTrackList = new TrackList();

    private boolean isPlaying = false;
    private String playPath = new File("src/sample/media/Play.png").getAbsolutePath();
    private String pausePath = new File("src/sample/media/Pause.png").getAbsolutePath();
    private String stopPath = new File("src/sample/media/Stop.png").getAbsolutePath();
    private Image playImg = new Image(new File(playPath).toURI().toString());
    private Image pauseImg = new Image(new File(pausePath).toURI().toString());
    private Image stopImg = new Image(new File(stopPath).toURI().toString());

    private String path;
    private String songN;
    private int currentSongNum = 0;

    private Timer updateTimer = new Timer();

    /**
     * Will initialize as soon as the program is started
     * Sets the graphic for the play and stop button, and sets up the table view
     * Sets up the first song in the song list, including the song label.
     */
    public void initialize() {
        playPause.setContentDisplay(ContentDisplay.CENTER);
        playPause.setGraphic(new ImageView(playImg));

        stopButton.setContentDisplay(ContentDisplay.CENTER);
        stopButton.setGraphic(new ImageView(stopImg));

        setTrackList.setListView(false,false,null);
        setLists(setTrackList.getTrackList());

        trackList = setTrackList.getTrackList();

        currentSongNum = 0;
        path = new File(trackList.get(currentSongNum).location).getAbsolutePath();
        setSong();
        songN = trackList.get(currentSongNum).name;
        songName.setText(songN);

        mp.setAutoPlay(false);

        setTimeSlider();

    }

    /**
     * This add a listener to the slider
     * which follows the the updated duration of the song
     */
    private void setTimeSlider()
    {
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov)
            {
                updatesSlider();
            }
        });
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                updatesValues();
            }
        }; updateTimer.schedule(tt,30);
    }

    /**
     * This method will select a song, and get the information needed
     * to setup the current song to the one the user wants to select.
     * It will set the song label to the selected song, and starts it automaticly
     * @param arg0
     */
    @FXML
    private void handlePlayListChoose(MouseEvent arg0)
    {
        trackTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent click) {

                if (click.getButton() == MouseButton.PRIMARY && click.getClickCount() == 2)
                {
                    mp.stop();
                    isPlaying = true;
                    playPause.setGraphic(new ImageView(pauseImg));

                    for (int i = 0; i < trackList.size(); i++) {
                        if(trackTable.getSelectionModel().getSelectedItem().equals(trackList.get(i)))
                        {
                            path = new File(trackList.get(i).location).getAbsolutePath();
                            currentSongNum = i;
                            songN = trackList.get(i).name;
                            break;
                        }
                    }
                    songName.setText(songN);
                    setSong();

                    mp.play();
                    addTimeListener();
                    setTimeSlider();
                }
            }
        });
    }

    /**
     * This method sets a selected song to start playing
     * It will update the total duration of the song the moment it is ready,
     * and add a "setOnEndOfMedia" to be able to go to goNextSong
     */
    private void setSong ()
    {
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mediaPlayer.setMediaPlayer(mp);
        updateTimer = new Timer();
        mp.setOnReady(new Runnable() {
            @Override
            public void run() {
                long endFormat = (long) me.getDuration().toMillis();
                TTime.setText(formatTimer(endFormat));
            }
        });
        mp.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                goNextSong();
            }
        });
        addTimeListener();
        setTimeSlider();
    }

    /**
     * When a song has ended, this method will make sure that
     * the next song on the list will start playing,
     * and if it is the last song, it will go back to the first without playing
     */
    private void goNextSong()
    {
        boolean isLastSong = false;
        if(currentSongNum + 1 == trackList.size())
        {
            currentSongNum = 0;
            isLastSong = true;
        }
        else
        {
            currentSongNum = currentSongNum + 1;
            isLastSong = false;
        }
        trackTable.getSelectionModel().select(currentSongNum);
        path = new File(trackList.get(currentSongNum).location).getAbsolutePath();
        songN = trackList.get(currentSongNum).name;
        songName.setText(songN);
        setSong();
        mp.play();
        if(isLastSong)
        {
            isPlaying = false;
            playPause.setGraphic(new ImageView(playImg));
            mp.pause();
            timeSlider.setValue(0);
        }
    }

    /**
     * When the user clicks on the pause/play icon
     * the current song will be paused or start playing again
     * given it's current state
     * It will also change the icon of the button,
     * between play and pause depending on the state
     * @param event Upon clicking the play / pause button
     */
    @FXML
    private void handlePlayPause (ActionEvent event)
    {
        if(isPlaying)
        {
            isPlaying = false;
            playPause.setGraphic(new ImageView(playImg));
            mp.pause();
        }
        else if (!isPlaying)
        {
            isPlaying = true;
            playPause.setGraphic(new ImageView(pauseImg));
            mp.play();
        }
    }

    /**
     * This method adds a listener to the current song
     * it will keep track of the songs current time.
     * It also adds the function to use the slider
     * to skip back and forth in the song.
     */
    private void addTimeListener() {
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updatesValues();
            }
        });
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isPressed()) {

                    mp.seek(mp.getMedia().getDuration().multiply(timeSlider.getValue() / 100));
                }
            }
        });
    }

    /**
     * When the user pressed the stop icon
     * then this method stops the song
     * and then resets the time
     * @param event Upon clicking the stop button
     */
    @FXML
    private void handleStopMedia (ActionEvent event)
    {
        if(isPlaying)
        {
            isPlaying = false;
            playPause.setGraphic(new ImageView(playImg));
        }
        mp.stop();
    }

    /**
     * This method adds all the songs from the media folder
     * to the table view list
     * @param playList An arrayList for all the songs to put into the table view
     */
    public void setLists(ArrayList<Songs.songData> playList)
    {

        ObservableList<Songs.songData> list = FXCollections.observableArrayList(playList);
        trackTableSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        trackTableArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        trackTable.setItems(list);
    }

    /**
     * This method formats the songs total duration into MM:SS
     * @param formatTime The time to change into the format
     * @return Returns the time in the given format
     */
    private String formatTimer(long formatTime)
    {
        return  String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(formatTime),
                TimeUnit.MILLISECONDS.toSeconds(formatTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(formatTime)));
    }

    /**
     * This method will set a listener
     * to update the current time of the song while it plays
     */
    private void updatesValues()
    {
        Platform.runLater(new Runnable() {
            public void run()
            {
                long secFormat = (long) mp.getCurrentTime().toMillis();
                currentTime.setText(formatTimer(secFormat));
                updateTimer.cancel();
            }
        });
    }

    /**
     * This method will set a listener
     * to update the position of the slider, based on the current time of the song
     */
    protected void updatesSlider()
    {
        Platform.runLater(new Runnable() {
            public void run()
            {
                timeSlider.setValue(mp.getCurrentTime().toMillis() / mp.getTotalDuration().toMillis() * 100);
            }
        });
    }


    /**
     * This method will create a new window
     * where the user can create new playlists and edit their current playlists
     * @param event Upon clicking "Create / edit playlist"
     * @throws Exception
     */
    public void handleNewPlaylist(ActionEvent event) throws Exception{
        try {
            Stage newPlaylist = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("createPlaylist.fxml"));
            newPlaylist.setTitle("Create / edit playlist");
            newPlaylist.setScene(new Scene(root1));
            newPlaylist.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This methods will open up a new window where the user can select or delete a playlist
     * Upon selecting a playlist, it will setup the table view to the list of songs from the selected playlist
     * @param event Upon clicking the "view playlist" button
     * @throws Exception
     */
    public void handlePlaylist(ActionEvent event) throws Exception{
        try {
            Stage viewPlaylists = new Stage();
            Parent root2 = FXMLLoader.load(getClass().getResource("viewPlaylist.fxml"));
            viewPlaylists.setTitle("View all playlists");
            viewPlaylists.setScene(new Scene(root2));
            viewPlaylists.show();
            viewPlaylists.setOnHiding((WindowEvent t)-> {
                if(setTrackList.getClosedWithSelect()) {
                    setTrackList.setPlaylist(setTrackList.getSelectedTracklist());
                    setLists(setTrackList.getTrackList());
                    mp.stop();
                    path = new File(trackList.get(0).location).getAbsolutePath();
                    currentSongNum = 0;
                    songN = trackList.get(currentSongNum).name;
                    songName.setText(songN);
                    setSong();

                    isPlaying = false;
                    playPause.setGraphic(new ImageView(playImg));

                    addTimeListener();
                    setTimeSlider();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This will reset the table view to all songs in the current database
     * @param actionEvent Upon clicking "Show all songs"
     */
    public void handleAllSongs(ActionEvent actionEvent) {
        setTrackList.setListView(false,false,null);
        setLists(setTrackList.getTrackList());
    }

    /**
     * This method will search the database from the users input
     * if it finds the searched content it will set the table view to songs found.
     * @param event Upon clicking "search by artist" or "search by song", while a value has been input into search
     */
    public void handleSearch(ActionEvent event) {

        boolean isArtist = false;

        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();

        if (buttonPressed.equals("Search for song")) {
            isArtist = false;
        }

        if (buttonPressed.equals("Search for artist")){
            isArtist = true;
        }
        String searchInput = getContent.getText();
        setTrackList.setListView(true,isArtist,searchInput);
        setLists(setTrackList.getTrackList());
    }

    /**
     * This method opens up a file browser where the user can select a new song
     * which they wish to add to their media player
     * @param event Upon clicking "Add songs"
     * @throws Exception
     */
    public void handleAddSong(ActionEvent event) throws Exception {

        try {
            Stage addsongsTo = new Stage();
            Parent root4 = FXMLLoader.load(getClass().getResource("tracklist.fxml"));
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
            File openFileBrowser = chooser.showOpenDialog(addsongsTo);
            File mediaDir = new File(System.getProperty("user.home"), "src/sample/mediaDir");
            if (!mediaDir.exists()) {
                mediaDir.mkdir();
            }
            //String filePath = new File("src/sample/media").getAbsolutePath();
            addsongsTo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

