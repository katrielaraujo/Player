package br.imd.player.model;

import br.imd.player.util.PlaylistOperationException;

public abstract class User {
	protected String email;
    protected String password;
    protected Integer type;
    protected Integer id;
    
    public Integer getId() {
    	return id;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
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
}
