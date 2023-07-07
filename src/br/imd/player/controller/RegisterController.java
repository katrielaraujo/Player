package br.imd.player.controller;

import br.imd.player.DAO.MediaManager;
import br.imd.player.model.User;
import br.imd.player.model.UserVip;
import br.imd.player.util.UserType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

public class RegisterController {
    @FXML
    private CheckBox vipButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button registrarButton;

    @FXML
    private void initialize() {
        // Aqui você pode adicionar lógica de inicialização, se necessário
    }

    @FXML
    private void handleRegistrarButton() {

        boolean isVip = vipButton.isSelected();
        String email = emailField.getText();
        String senha = senhaField.getText();



        if (isVip){
            UserVip user = new UserVip();
            user.setEmail(email);
            user.setPassword(senha);
            user.setType(UserType.VIP);



        }


            if (saveUser(email, senha, isVip)) {
            System.out.println("Usuário salvo: " + email);
        } else {
            System.out.println("Usuário já existe: " + email);
        }
    }

    private boolean saveUser(String email, String senha, boolean isVip) {
        String url = "jdbc:sqlite:database.db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE email = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                return false; // Usuário já existe
            } else {
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO usuarios (email, senha, is_vip) VALUES (?, ?, ?)");
                insertStmt.setString(1, email);
                insertStmt.setString(2, senha);
                insertStmt.setBoolean(3, isVip);
                insertStmt.executeUpdate();
                return true; // Usuário salvo com sucesso
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Ocorreu um erro ao salvar o usuário
        }
    }


}
