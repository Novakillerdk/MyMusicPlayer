package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SearchSongs {


    @FXML
    private Button searchForContent;
    @FXML
    private TextField getContent;
    @FXML
    private TextArea listContent;

    public void handleContent(ActionEvent event) {

        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();

        if (buttonPressed.equals("Search for song")) {
            String contentName = getContent.getText();

            DB.selectSQL("Select * from tblSongs WHERE fldSong = '"+ contentName +"' ");

            do{
                String data = DB.getDisplayData();
                if (data.equals(DB.NOMOREDATA)){
                    break;
                }else{
                    System.out.print(data);
                    listContent.setText(data);
                }
            } while(true);

        }
        if (buttonPressed.equals("Search for artist")){
            String contentName = getContent.getText();
            DB.selectSQL("Select * from tblSongs WHERE fldArtist = '"+ contentName +"' ");


            do{
                String data = DB.getDisplayData();
                if (data.equals(DB.NOMOREDATA)){
                    break;
                }else{
                    System.out.print(data);
                    listContent.setText(data);
                }
            } while(true);

        }

    }
}
