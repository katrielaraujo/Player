package br.imd.player.model;

import br.imd.player.util.SongNotFoundException;

public class Song {
	private Integer id;
    private String title;
    private String filePath;
    
    public Integer getId() {
    	return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFilePath(String filePath) throws SongNotFoundException {
        if (filePath == null || filePath.isEmpty()) {
            throw new SongNotFoundException("File path cannot be empty");
        }
        this.filePath = filePath;
    }

	public void setId(Integer id) {
		this.id = id;
	}
}
