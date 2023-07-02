package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

import br.imd.player.util.IdGenerator;

public class Playlist {
    private String name;
    private Map<Integer,Song> songs;
    private Integer id;
    
    public Integer getId() {
    	return id;
    }

    public Playlist(String name) {
        this.name = name;
        songs = new HashMap<>();
        this.id = IdGenerator.getNextId();
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
}