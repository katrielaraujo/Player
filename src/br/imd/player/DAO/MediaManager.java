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
	private Connection connection;
	
	public MediaManager(String dbFileName) {
		try {
			String url = "jdbc:sqlite:"+dbFileName;
			
			connection = DriverManager.getConnection(url);
		}catch(SQLException e) {
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
	public void insertUsuario(User user) {
		String sql = "INSERT INTO User(Email, Password, Directory, Type) VALUES(?,?,?,?)";
		
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3,user.getDirectory());
			pstmt.setInt(4,user.getType().ordinal());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void updateUsuario(User user) {
		String sql = "UPDATE User SET Email = ?, Password = ?, Directory = ?, Type = ? WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getDirectory());
            pstmt.setInt(4, user.getType().ordinal());
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void deleteUsuario(Integer userId) {
		String sql = "DELETE FROM User WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public Map<Integer,User> getAllUsuarios() {
        Map<Integer,User> users = new HashMap<>();
        String sql = "SELECT * FROM User";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user;
                if (rs.getInt("Type") == UserType.VIP.ordinal()) {
                    user = new UserVip();
                } else {
                    user = new UserRegular();
                }
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setDirectory(rs.getString("Directory"));
                users.put(user.getId(),user);
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
	
	public Map<Integer,Song> getMusicasByPlaylist(int playlistId) {
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
	
	public void insertMusica(Song song) {
		String sql = "INSERT INTO Song(title, FilePath) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getFilePath());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void updateMusica(Song song) {
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
	
	public void deleteMusica(Integer songId) {
		String sql = "DELETE FROM Song WHERE ID = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, songId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
}
