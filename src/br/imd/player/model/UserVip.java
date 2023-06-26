package br.imd.player.model;

import java.util.ArrayList;
import java.util.List;

import br.imd.player.util.PlaylistNotFoundException;

public class UserVip extends User{
	private List<Playlist> playlists;

    public UserVip() {
        super();
        playlists = new ArrayList<>();
    }

    @Override
    public void createPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName);
        playlists.add(newPlaylist);
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

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    private Playlist findPlaylist(String playlistName) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(playlistName)) {
                return playlist;
            }
        }
        return null;
    }
}
