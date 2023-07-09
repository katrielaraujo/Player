package br.imd.player.model;

import br.imd.player.util.PlaylistOperationException;

import java.util.List;

import br.imd.player.util.FileLister;

import java.io.File;
import java.util.ArrayList;
import br.imd.player.util.UserType;

public abstract class User implements FileLister{
	protected Integer id;
	protected String email;
    protected String password;
    protected UserType type;
    protected String directory;
    
    @Override
	public List<File> listFilesInDirectory() {
		List<File> fileList = new ArrayList<>();
		
		File directoryFile = new File(directory);
		File[] files = directoryFile.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                    fileList.add(file);
                }
            }
        }
		return fileList;
	}
    
    
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
