package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

public class Playlist {
	private Integer id;
    private String name;
    private Map<String,Song> songs;
    private Integer userId;
    
    public Integer getId() {
    	return id;
    }

    public Playlist(String name,Integer userId) {
        this.name = name;
        this.userId = userId;
        songs = new HashMap<>();
    }
    
    public void setSongs(Map<String,Song> so) {
    	this.songs = so;
    }

    public String getName() {
        return name;
    }

    public Map<String,Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.put(song.getTitle(),song);
    }

    public void removeSong(Song song) {
        songs.remove(song.getTitle(),song);
    }

	public Integer getUserId() {
		return userId;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}