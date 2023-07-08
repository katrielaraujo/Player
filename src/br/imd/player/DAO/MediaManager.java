package br.imd.player.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import br.imd.player.model.Playlist;
import br.imd.player.model.Song;
import br.imd.player.model.User;
import br.imd.player.model.UserRegular;
import br.imd.player.model.UserVip;
import br.imd.player.util.SongNotFoundException;
import br.imd.player.util.UserType;

public class MediaManager {
	private static final String dbFileName = "./src/resources/database.db";
	private Connection connection;
	
	public MediaManager(){
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:"+dbFileName;
			connection = DriverManager.getConnection(url);
			System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void close() {
		try {
			if(connection != null) {
				connection.close();
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//User metodos
	public void insertUser(User user) {
		String sql = "INSERT INTO User(Email, Password, Directory, Type) VALUES(?,?,?,?)";
		
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3,user.getDirectory());
			pstmt.setInt(4,user.getType().getValue());
			pstmt.executeUpdate();
			
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
		        if (rs.next()) {
		            int generatedId = rs.getInt(1);
		            user.setId(generatedId);
		        }
		    }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void updateUser(User user) {
		String sql = "UPDATE User SET Email = ?, Password = ?, Directory = ?, Type = ? WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getDirectory());
            pstmt.setInt(4, user.getType().getValue());
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void deleteUser(Integer userId) {
		String sql = "DELETE FROM User WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public Map<String,User> getAllUsers() {
        Map<String,User> users = new HashMap<>();
        String sql = "SELECT * FROM User";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user;
                if (rs.getInt("Type") == UserType.VIP.getValue()) {
                    user = new UserVip();
                } else {
                    user = new UserRegular();
                }
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setDirectory(rs.getString("Directory"));
                users.put(user.getEmail(),user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
	
	//Playlist metodos
	public void insertPlaylist(Playlist playlist) {
		String sql = "INSERT INTO Playlist(Name, UserID) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playlist.getName());
            pstmt.setInt(2, playlist.getUserId());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
		        if (rs.next()) {
		            int generatedId = rs.getInt(1);
		            playlist.setId(generatedId);
		        }
		    }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void updatePlaylist(Playlist playlist) {
		String sql = "UPDATE Playlist SET Name = ?, UserID = ? WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playlist.getName());
            pstmt.setInt(2, playlist.getUserId());
            pstmt.setInt(3, playlist.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void deletePlaylist(Integer playlistId) {
        String sql = "DELETE FROM Playlist WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, playlistId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	// Método para associar uma música a uma playlist específica
	public void associateSongToPlaylist(int songId, int playlistId) {
	    String sql = "INSERT INTO Playlist_Song (PlaylistID, SongID) VALUES (?, ?)";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, playlistId);
	        pstmt.setInt(2, songId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public Map<Integer,Song> getSongsByPlaylist(int playlistId) {
        Map<Integer,Song> songs = new HashMap<>();
        String sql = "SELECT s.* FROM Song s "
                + "JOIN Playlist_Song ps ON s.ID = ps.SongID "
                + "WHERE ps.PlaylistID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, playlistId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Song musica = new Song();
                musica.setId(rs.getInt("ID"));
                musica.setTitle(rs.getString("title"));
                musica.setFilePath(rs.getString("FilePath"));
                songs.put(musica.getId(),musica);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (SongNotFoundException e) {
			e.printStackTrace();
		}
        return songs;
    }
	
	public void insertSong(Song song) {
		String sql = "INSERT INTO Song(title, FilePath) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getFilePath());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
		        if (rs.next()) {
		            int generatedId = rs.getInt(1);
		            song.setId(generatedId);
		        }
		    }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void updateSong(Song song) {
		String sql = "UPDATE Song SET title = ?, FilePath = ? WHERE ID = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, song.getTitle());
	        pstmt.setString(2, song.getFilePath());
	        pstmt.setInt(3, song.getId());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void deleteSong(Integer songId) {
		String sql = "DELETE FROM Song WHERE ID = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, songId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
}
