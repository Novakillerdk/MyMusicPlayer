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

    private Songs songList = new Songs();
    private Controller getControllerMethods = new Controller();


    public void handleDB(ActionEvent event) {


        DB.selectSQL("Select * from tblPlaylist ");

        do{
            String data = DB.getDisplayData();
            if (data.equals(DB.NOMOREDATA)){
                break;
            }else{
                System.out.print(data);
                viewPlaylist.getItems().addAll(songList.trackList);

            }
        } while(true);

        Button b = (Button) event.getSource();
        String buttonpresed = b.getText();

        if (buttonpresed.equals("Select playlist")){

        }
        if(buttonpresed.equals("Remove playlist")){

            ObservableList<String> playlistName;
            playlistName = viewPlaylist.getSelectionModel().getSelectedItems();
            DB.deleteSQL("Delete from tblSongs where fldPlaylistName like '"+playlistName+"';");

        }

    }

    public void handleSendToMain(ActionEvent event) {
    }
}
