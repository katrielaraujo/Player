package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

import br.imd.player.util.PlaylistNotFoundException;
import br.imd.player.util.UserType;

public class UserVip extends User{
	private Map<String,Playlist> playlists;

    public UserVip() {
        super();
        type = UserType.VIP;
        playlists = new HashMap<>();
    }

    @Override
    public void createPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName,this.id);
        playlists.put(playlistName,newPlaylist);
    }

    public void addSongToPlaylist(String playlistName, Song song) throws PlaylistNotFoundException {
        Playlist playlist = findPlaylist(playlistName);
        if (playlist != null) {
            playlist.addSong(song);
        } else {
            throw new PlaylistNotFoundException("Playlist not found.");
        }
    }

    public void removeSongFromPlaylist(String playlistName, Song song) throws PlaylistNotFoundException {
        Playlist playlist = findPlaylist(playlistName);
        if (playlist != null) {
            playlist.removeSong(song);
        } else {
            throw new PlaylistNotFoundException("Playlist not found.");
        }
    }
    
    public void removePlaylist(String playlistName) {
    	playlists.remove(playlistName);
    }

    public Map<String,Playlist> getPlaylists() {
        return playlists;
    }
    
    public void setPlaylists(Map<String,Playlist> list) {
        this.playlists = list;
    }

    private Playlist findPlaylist(String playlistName) {
    	return playlists.get(playlistName);
    }
}
