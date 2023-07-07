package nk.org.g_affectation_employes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nk.org.g_affectation_employes.GestionAffectationApp;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane landingPane;

    @FXML
    private Button btn_se_connecter;

    @FXML
    private ImageView btn_close;

    @FXML
    void btnSeConnecterOnAction(ActionEvent event) throws IOException {
        // redirect to main window
        Stage main = new Stage();
        Parent fxml = FXMLLoader.load(GestionAffectationApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxml);

        main.setScene(scene);
        main.setResizable(false);
        main.initStyle(StageStyle.UNDECORATED);
        main.show();

        // close landing stage
        this.closeLandingWindow(null);
    }

    @FXML
    void closeLandingWindow(MouseEvent event) {
        Stage stage = (Stage) landingPane.getScene().getWindow();
        stage.close();
    }
}