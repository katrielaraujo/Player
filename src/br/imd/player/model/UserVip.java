package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

import br.imd.player.util.IdGenerator;
import br.imd.player.util.PlaylistNotFoundException;

public class UserVip extends User{
	private Map<Integer,Playlist> playlists;

    public UserVip() {
        super();
        playlists = new HashMap<>();
        this.id = IdGenerator.getNextId();
    }

    @Override
    public void createPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName);
        playlists.put(newPlaylist.getId(),newPlaylist);
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

    public Map<Integer,Playlist> getPlaylists() {
        return playlists;
    }

    private Playlist findPlaylist(String playlistName) {
        for (Playlist playlist : playlists.values()) {
            if (playlist.getName().equals(playlistName)) {
                return playlist;
            }
        }
        return null;
    }
}
