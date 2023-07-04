package br.imd.player.DAO;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.imd.player.model.User;

public class UserDao {
	private static final String USER_FILE_PATH = "users.txt";
	private Map<Integer,User> users;
	
	public UserDao() {
		users = loadUsers();
	}
	
	public void addUser(User user) {
		users.put(user.getId(),user);
		saveUsers();
	}
	
	public void removeUser(User user) {
		users.remove(user.getId());
		saveUsers();
	}
	
	public void saveUsers() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = new FileOutputStream(USER_FILE_PATH);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			try {
				if(fos != null)
					fos.close();
				if(oos != null)
					oos.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Map<Integer,User> loadUsers(){
		File file = new File(USER_FILE_PATH);
		if(file.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try{
				fis = new FileInputStream(USER_FILE_PATH);
				ois = new ObjectInputStream(fis);
				return (Map<Integer,User>) ois.readObject();
			}catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}finally {
				try {
					if(fis != null)
						fis.close();
					if(ois != null)
						ois.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new HashMap<>();
	}
}
