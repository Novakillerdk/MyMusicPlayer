package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.Observable;

public class viewPlaylist {


    @FXML ListView viewPlaylist;
    @FXML Button removePlaylist;
    @FXML Button selectPlaylist;


    public void handleDB(ActionEvent event) {

        Button b = (Button) event.getSource();
        String buttonpresed = b.getText();
        //String playlistName = viewPlaylist.
        if (buttonpresed.equals("Select playlist")){

        }
        if(buttonpresed.equals("Remove playlist")){

            //TODO
          //  DB.deleteSQL("Delete from tblSongs where fldPlaylistName like '"+playlistName+"';");

        }

    }

    public void handleSendToMain(ActionEvent event) {
    }
}
