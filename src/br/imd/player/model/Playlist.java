package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

public class Playlist {
	private Integer id;
    private String name;
    private Map<Integer,Song> songs;
    private Integer userId;
    
    public Integer getId() {
    	return id;
    }

    public Playlist(String name,Integer userId) {
        this.name = name;
        this.userId = userId;
        songs = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<Integer,Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.put(song.getId(),song);
    }

    public void removeSong(Song song) {
        songs.remove(song.getId(),song);
    }

	public Integer getUserId() {
		return userId;
	}
}