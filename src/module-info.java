module media {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;

	opens br.imd.player to javafx.fxml;
	opens br.imd.player.controller to javafx.fxml;
	exports br.imd.player;
	opens br.imd.player.DAO to javafx.fxml;
}
