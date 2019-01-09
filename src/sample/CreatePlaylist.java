package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

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

        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();
        if(buttonPressed.equals("Add")){

        }
        if(buttonPressed.equals("Remove")){

        }


    }

    public void ClearPlaylist(ActionEvent event) {
        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();
        if(buttonPressed.equals("Clear playlist")){

        }


    }

    public void sendPlaylist(ActionEvent event) {

        String nameOfPlaylist = playlistName.getText();
        System.out.println(nameOfPlaylist);
        playlistName.clear();

    }

    public void handleMouseAll(MouseEvent mouseEvent) {
        selectedSongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {

                if (click.getButton() == MouseButton.PRIMARY && click.getClickCount() == 2) {


                }

            }
        });
    }
}