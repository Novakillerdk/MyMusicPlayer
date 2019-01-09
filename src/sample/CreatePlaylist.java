package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreatePlaylist {

    @FXML
    private ListView allSongs;
    @FXML
    private ListView selectedSongs;
    @FXML
    private Button addSongTo;
    @FXML
    private Button removeSong;
    @FXML
    private Button clearPlaylist;
    @FXML
    private Button refreshSongs;
    @FXML
    private Button addPlaylist;
    @FXML
    private TextField playlistName;


    public void handleRefresh(ActionEvent event) {

    }

    public void handleAllSongs(ActionEvent event) {


    }

    public void ClearPlaylist(ActionEvent event) {
    }

    public void sendPlaylist(ActionEvent event) {


    }

}