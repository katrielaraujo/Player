package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

import br.imd.player.util.PlaylistNotFoundException;
import br.imd.player.util.UserType;

public class UserVip extends User{
	private Map<Integer,Playlist> playlists;

    public UserVip() {
        super();
        type = UserType.VIP;
        playlists = new HashMap<>();
    }

    @Override
    public void createPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName,this.id);
        playlists.put(newPlaylist.getId(),newPlaylist);
    }

    public void addSongToPlaylist(Integer playlistId, Song song) throws PlaylistNotFoundException {
        Playlist playlist = findPlaylist(playlistId);
        if (playlist != null) {
            playlist.addSong(song);
        } else {
            throw new PlaylistNotFoundException("Playlist not found.");
        }
    }

    public void removeSongFromPlaylist(Integer playlistId, Song song) throws PlaylistNotFoundException {
        Playlist playlist = findPlaylist(playlistId);
        if (playlist != null) {
            playlist.removeSong(song);
        } else {
            throw new PlaylistNotFoundException("Playlist not found.");
        }
    }
    
    public void removePlaylist(Integer playlistId) {
    	playlists.remove(playlistId);
    }

    public Map<Integer,Playlist> getPlaylists() {
        return playlists;
    }

    private Playlist findPlaylist(Integer playlistId) {
    	return playlists.get(playlistId);
    }
}
