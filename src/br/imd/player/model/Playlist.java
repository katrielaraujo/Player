package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa uma playlist.
 */
public class Playlist {
    private Integer id;
    private String name;
    private Map<String, Song> songs;
    private Integer userId;

    /**
     * Construtor da classe Playlist.
     *
     * @param name   o nome da playlist.
     * @param userId o ID do usuário dono da playlist.
     */
    public Playlist(String name, Integer userId) {
        this.name = name;
        this.userId = userId;
        songs = new HashMap<>();
    }

    /**
     * Define o ID da playlist.
     *
     * @param id o ID da playlist.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o ID da playlist.
     *
     * @return o ID da playlist.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retorna o nome da playlist.
     *
     * @return o nome da playlist.
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna um mapa contendo as músicas da playlist.
     * A chave do mapa é o título da música.
     *
     * @return um mapa contendo as músicas da playlist.
     */
    public Map<String, Song> getSongs() {
        return songs;
    }

    /**
     * Define as músicas da playlist.
     *
     * @param songs o mapa de músicas da playlist.
     */
    public void setSongs(Map<String, Song> songs) {
        this.songs = songs;
    }

    /**
     * Adiciona uma música à playlist.
     *
     * @param song a música a ser adicionada.
     */
    public void addSong(Song song) {
        songs.put(song.getTitle(), song);
    }

    /**
     * Remove uma música da playlist.
     *
     * @param song a música a ser removida.
     */
    public void removeSong(Song song) {
        songs.remove(song.getTitle(), song);
    }

    /**
     * Retorna o ID do usuário dono da playlist.
     *
     * @return o ID do usuário dono da playlist.
     */
    public Integer getUserId() {
        return userId;
    }
}
