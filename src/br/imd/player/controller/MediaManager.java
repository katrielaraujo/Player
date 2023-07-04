package br.imd.player.controller;

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
			String url = "jdbc:sqlite"+dbFileName;
			
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
		String sql = "INSERT INTO Usuario(Email,Senha,Diretorio,Tipo) VALUES(?,?,?,?)";
		
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
        String sql = "UPDATE Usuario SET Email = ?, Senha = ?, Diretorio = ?, Tipo = ? WHERE ID = ?";

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
        String sql = "DELETE FROM Usuario WHERE ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public Map<Integer,User> getAllUsuarios() {
        Map<Integer,User> users = new HashMap<>();
        String sql = "SELECT * FROM Usuario";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user;
                if (rs.getInt("Tipo") == UserType.VIP.ordinal()) {
                    user = new UserVip();
                } else {
                    user = new UserRegular();
                }
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Senha"));
                user.setDirectory(rs.getString("Diretorio"));
                users.put(user.getId(),user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
	
	//Playlist metodos
	public void insertPlaylist(Playlist playlist) {
        String sql = "INSERT INTO Playlist(Nome, UsuarioID) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playlist.getName());
            pstmt.setInt(2, playlist.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void updatePlaylist(Playlist playlist) {
        String sql = "UPDATE Playlist SET Nome = ?, UsuarioID = ? WHERE ID = ?";

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
        Map<Integer,Song> musicas = new HashMap<>();
        String sql = "SELECT m.* FROM Musica m "
                   + "JOIN Playlist_Musica pm ON m.ID = pm.MusicaID "
                   + "WHERE pm.PlaylistID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, playlistId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Song musica = new Song();
                musica.setId(rs.getInt("ID"));
                musica.setTitle(rs.getString("Nome"));
                musica.setFilePath(rs.getString("Diretorio"));
                musicas.put(musica.getId(),musica);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (SongNotFoundException e) {
			e.printStackTrace();
		}
        return musicas;
    }
	
	public void insertMusica(Song musica) {
        String sql = "INSERT INTO Musica(Nome, Diretorio) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, musica.getTitle());
            pstmt.setString(2, musica.getFilePath());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void updateMusica(Song musica) {
	    String sql = "UPDATE Musica SET Nome = ?, Diretorio = ? WHERE ID = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, musica.getTitle());
	        pstmt.setString(2, musica.getFilePath());
	        pstmt.setInt(3, musica.getId());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void deleteMusica(Integer musicaId) {
	    String sql = "DELETE FROM Musica WHERE ID = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, musicaId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
}
