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
import java.util.Map;

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
    	User user;
    	
        boolean isVip = vipButton.isSelected();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (isVip){
            user = new UserVip();
            user.setEmail(email);
            user.setPassword(senha);
            user.setType(UserType.VIP);
        }else {
        	user = new UserVip();
            user.setEmail(email);
            user.setPassword(senha);
            user.setType(UserType.REGULAR);
        }


        if (saveUser(user)) {
            System.out.println("Usuário salvo: " + email);
        } else {
            System.out.println("Usuário já existe: " + email);
        }
    }

    private boolean saveUser(User user) {
        MediaManager media = new MediaManager();
        Map<String,User> users = media.getAllUsers();
        if(users.containsKey(user.getEmail())) {
        	return false;
        }
        
        media.insertUser(user);
        return true;
    }


}
