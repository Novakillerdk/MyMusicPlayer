package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;


public class Tracklist {
    private Songs songList = new Songs();

    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private ArrayList<String> trackList1 = new ArrayList<>();
    private ObservableList list= FXCollections.observableArrayList();

    private void addSong()
    {
        songList.addArray();

        trackList1.clear();
        trackList = songList.getTrackList();
        Iterator itr=trackList.iterator();
        while(itr.hasNext()) {
            Songs.songData st = (Songs.songData) itr.next();
            String songN = st.name;
            trackList1.add(songN);
        }
    }
    public void setListView()
    {
        addSong();
        list=FXCollections.observableArrayList(trackList1);
    }

    public ArrayList<Songs.songData> getTrackList() {
        return trackList;
    }

    public ObservableList getList() {
        return list;
    }

}
/*
    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private ArrayList<String> trackList1 = new ArrayList<>();

    public void initialize()
    {
        songList.addArray();
    }
    public void handleRefresh(ActionEvent event)  {

        trackList1.clear();
        trackList = songList.getTrackList();
        Iterator itr=trackList.iterator();
        while(itr.hasNext()) {
            Songs.songData st = (Songs.songData) itr.next();
            String songN = st.name;
            trackList1.add(songN);
        }
            ObservableList list=FXCollections.observableArrayList(trackList1);
            allSongs.setItems(list);

    }
    */