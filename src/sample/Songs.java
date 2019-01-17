package sample;

import java.util.ArrayList;

public class Songs {

    public ArrayList<songData> trackList = new ArrayList<>();
    public ArrayList<String> playListSong = new ArrayList<>();
    private songData songD = new songData("","","",0);
    private songData playList = new songData("","","",0);

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
    public ArrayList<String> getPlayListSongs()
    {
        return playListSong;
    }
    public ArrayList<songData> getTrackList() {
        return trackList;
    }

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
