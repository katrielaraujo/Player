package br.imd.player.model;

import br.imd.player.util.PlaylistOperationException;
import br.imd.player.util.UserType;

public abstract class User {
	protected Integer id;
	protected String email;
    protected String password;
    protected UserType type;
    protected String directory;
    
    
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directoryPath) {
        this.directory = directoryPath;
    }
    
    public Integer getId() {
    	return id;
    }

    public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
    
    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public abstract void createPlaylist(String playlistName) throws PlaylistOperationException;

	public void setId(int id) {
		this.id = id;
	}
}
