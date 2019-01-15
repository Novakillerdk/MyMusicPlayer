package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;


public class TrackList {
    private Songs songList = new Songs();

    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private ArrayList<String> trackName = new ArrayList<>();
    private ArrayList<String> trackArtist = new ArrayList<>();
    private ArrayList<String> playLists = new ArrayList<>();
    private ObservableList listNames= FXCollections.observableArrayList();

    private void addSongName()
    {
        songList.addArray();

        trackName.clear();
        trackList = songList.getTrackList();
        Iterator itr=trackList.iterator();
        while(itr.hasNext()) {
            Songs.songData st = (Songs.songData) itr.next();
            String songN = st.getName();
            trackName.add(songN);
        }
    }
    private void addSongArtist()
    {
        songList.addArray();

        trackArtist.clear();
        trackList = songList.getTrackList();
        Iterator itr=trackList.iterator();
        while(itr.hasNext()) {
            Songs.songData st = (Songs.songData) itr.next();
            String songArt = st.getArtist();
            trackArtist.add(songArt);
        }
    }
    public void setPlayLists(boolean withCreateNew)
    {
        playLists.clear();
        if (withCreateNew)
        {
            playLists.add("Create new...");
        }
        DB.selectSQL("Select Trim(fldPlaylist) from tblPlaylist");
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {
                playLists.add(data);
            }
        } while (true);
    }

    public ArrayList<String> getPlayLists() {
        return playLists;
    }

    public void setListView()
    {
        addSongName();
        listNames=FXCollections.observableArrayList(trackName);
    }

    public ArrayList<Songs.songData> getTrackList() {
        return trackList;
    }

    public ObservableList getList() {
        return listNames;
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