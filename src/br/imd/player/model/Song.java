package br.imd.player.model;

import br.imd.player.util.SongNotFoundException;

/**
 * Representa uma música.
 */
public class Song {
    private Integer id;
    private String title;
    private String filePath;

    /**
     * Retorna o ID da música.
     *
     * @return o ID da música.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retorna o título da música.
     *
     * @return o título da música.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retorna o caminho do arquivo da música.
     *
     * @return o caminho do arquivo da música.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Define o título da música.
     *
     * @param title o título da música.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Define o caminho do arquivo da música.
     *
     * @param filePath o caminho do arquivo da música.
     * @throws SongNotFoundException se o caminho do arquivo for nulo ou vazio.
     */
    public void setFilePath(String filePath) throws SongNotFoundException {
        if (filePath == null || filePath.isEmpty()) {
            throw new SongNotFoundException("File path cannot be empty");
        }
        this.filePath = filePath;
    }

    /**
     * Retorna uma representação em formato de string da música.
     *
     * @return uma representação em formato de string da música.
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * Define o ID da música.
     *
     * @param id o ID da música.
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
