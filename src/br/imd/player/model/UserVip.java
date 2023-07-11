package br.imd.player.model;

import java.util.HashMap;
import java.util.Map;

import br.imd.player.util.PlaylistNotFoundException;
import br.imd.player.util.UserType;

/**
 * Representa um usuário VIP do sistema.
 */
public class UserVip extends User {
    private Map<String, Playlist> playlists;

    /**
     * Construtor da classe UserVip.
     */
    public UserVip() {
        super();
        type = UserType.VIP;
        playlists = new HashMap<>();
    }

    /**
     * Cria uma nova playlist para o usuário VIP.
     *
     * @param playlistName o nome da playlist.
     */
    @Override
    public void createPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName, this.id);
        playlists.put(playlistName, newPlaylist);
    }

    /**
     * Adiciona uma música a uma playlist do usuário VIP.
     *
     * @param playlistName o nome da playlist.
     * @param song         a música a ser adicionada.
     * @throws PlaylistNotFoundException se a playlist não for encontrada.
     */
    public void addSongToPlaylist(String playlistName, Song song) throws PlaylistNotFoundException {
        Playlist playlist = findPlaylist(playlistName);
        if (playlist != null) {
            playlist.addSong(song);
        } else {
            throw new PlaylistNotFoundException("Playlist not found.");
        }
    }

    /**
     * Remove uma música de uma playlist do usuário VIP.
     *
     * @param playlistName o nome da playlist.
     * @param song         a música a ser removida.
     * @throws PlaylistNotFoundException se a playlist não for encontrada.
     */
    public void removeSongFromPlaylist(String playlistName, Song song) throws PlaylistNotFoundException {
        Playlist playlist = findPlaylist(playlistName);
        if (playlist != null) {
            playlist.removeSong(song);
        } else {
            throw new PlaylistNotFoundException("Playlist not found.");
        }
    }

    /**
     * Remove uma playlist do usuário VIP.
     *
     * @param playlistName o nome da playlist a ser removida.
     */
    public void removePlaylist(String playlistName) {
        playlists.remove(playlistName);
    }

    /**
     * Retorna um mapa contendo as playlists do usuário VIP.
     * A chave do mapa é o nome da playlist.
     *
     * @return um mapa contendo as playlists do usuário VIP.
     */
    public Map<String, Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Define as playlists do usuário VIP.
     *
     * @param list um mapa contendo as playlists do usuário VIP.
     */
    public void setPlaylists(Map<String, Playlist> list) {
        this.playlists = list;
    }

    private Playlist findPlaylist(String playlistName) {
        return playlists.get(playlistName);
    }
}
