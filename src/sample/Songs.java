package sample;

import java.util.ArrayList;

public class Songs {

    public ArrayList<songData> trackList = new ArrayList<>();
    public ArrayList<String> playListSong = new ArrayList<>();
    private songData songD = new songData("","","",0);
    private songData playList = new songData("","","",0);

    /**
     * This will look up all the songs in the database and add them all to an array for later use,
     * when we want to setup a table view of the songs
     * It is also able to search by artist or song if the user inputs that
     * @param isSearch This is the input if the user clicks to search by artist or song
     * @param isArtist This is the input if the user clicks to search by artist specifically
     * @param searchInput The text the user has written in the search box, used for finding songs
     */
    public void addArray(boolean isSearch,boolean isArtist,String searchInput)
    {
        int songCounter = 0;
        trackList.clear();
        if(isSearch)
        {
            if (isArtist)
            {
                DB.selectSQL("Select fldPath,fldSong,fldArtist from tblSongs WHERE fldArtist like '%"+ searchInput +"%' ");
            }
            else
            {
                DB.selectSQL("Select fldPath,fldSong,fldArtist from tblSongs WHERE fldSong like '%"+ searchInput +"%' ");
            }
        }
        else {
            DB.selectSQL("Select fldPath,fldSong,fldArtist from tblSongs");
        }
        do {
            songD = new songData("","","",0);
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            }
            else
            {
                songD.location = data;
                data = DB.getData();
                if (data.equals(DB.NOMOREDATA)) {
                    break;
                }
                else
                {
                    songD.name = data;
                    data = DB.getData();
                    if (data.equals(DB.NOMOREDATA)) {
                        break;
                    }
                    else
                    {
                        songD.artist = data;
                        songD.songOrder = songCounter;
                        songCounter++;
                    }
                    trackList.add(songD);
                }

            }
        } while (true);

    }

    /**
     * This will make an array of songs that is only in a specific playlist
     * @param playListName The name of the playlist to search for in the database
     */
    public void addArrayPlayList(String playListName)
    {
        int songCounter = 0;
        trackList.clear();
        ArrayList<String> songsInPlaylist = new ArrayList<>();

        DB.selectSQL("Select fldSong from tblPlaylistSong where fldPlaylistName = '"+playListName+"'");
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            }
            else
            {
                songsInPlaylist.add(data);
            }
        } while (true);

        for (String songName : songsInPlaylist
             ) {
            DB.selectSQL("Select fldPath,fldArtist from tblSongs where fldSong = '"+songName+"'");
            do {
                String data = DB.getData();
                playList = new songData("","","",0);
                if (data.equals(DB.NOMOREDATA)) {
                    break;
                }
                else
                {
                    playList.location = data;
                    data = DB.getData();
                    if (data.equals(DB.NOMOREDATA)) {
                        break;
                    }
                    else
                    {

                        playList.songOrder = songCounter;
                        songCounter++;
                        playList.name = songName;
                        playList.artist = data;
                        trackList.add(playList);
                    }
                }
            } while (true);
        }
    }

    /**
     * This will setup an array to output current songs in an existing playlist to edit the playlist
     * @param playList The current chosen playlist in the choice box in "Create / edit a playlist"
     */
    public void setPlayListSongs(String playList)
    {
        playListSong.clear();
        DB.selectSQL("Select fldSong from tblPlaylistSong where fldPlaylistName = '"+playList+"'");
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {
                playListSong.add(data);
            }
        }while(true);
    }

    /**
     * Gets the playListSong array
     * @return returns the array
     */
    public ArrayList<String> getPlayListSongs()
    {
        return playListSong;
    }

    /**
     * Gets the trackList array
     * @return returns the array
     */
    public ArrayList<songData> getTrackList() {
        return trackList;
    }

    /**
     * This class is setup to have specific data in an array, so the data from 1 song can be grouped into 1 index
     * Location is the path of the song
     * Name is the name of the song
     * Artist is the name of the artist
     */
    public static class songData
    {
        String location, name, artist;
        int songOrder;
        songData(String loc,String name,String artist,int songOrder)
        {
            this.location = loc;
            this.name = name;
            this.artist = artist;
            this.songOrder = songOrder;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getArtist() {
            return artist;
        }

        public int getSongOrder() {
            return songOrder;
        }
    }
}
