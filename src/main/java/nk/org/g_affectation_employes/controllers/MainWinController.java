package nk.org.g_affectation_employes.controllers;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nk.org.g_affectation_employes.GestionAffectationApp;
import nk.org.g_affectation_employes.utils.Notification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWinController implements Initializable {

    @FXML
    private Pane menu_accueil;

    @FXML
    private Pane menu_employe;

    @FXML
    private Pane menu_lieu;

    @FXML
    private Pane menu_affectation;

    @FXML
    private Pane menu_logout;

    @FXML
    private AnchorPane main_content, main_window;

    Notification notification;
    private Parent fxml;
    Stage stage;

    @FXML
    private ImageView btn_close_main_window;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menuAccueilClicked(null);
    }

    public MainWinController() {
    }

    public AnchorPane getMain_window() {
        return main_window;
    }

    public void setMain_window(AnchorPane main_window) {
        this.main_window = main_window;
    }

    @FXML
    void closeMainWindowClicked(MouseEvent event) {
        this.closeMainWindow();
    }

    public void loadFxlView(String resource_name, Pane activePane, Pane[] deactivePane) throws IOException {
        this.fxml = FXMLLoader.load(GestionAffectationApp.class.getResource(resource_name));
        main_content.getChildren().removeAll();
        main_content.getChildren().setAll(this.fxml);

        this.activePane(activePane);
        this.deactivePane(deactivePane);
    }

    void activePane(Pane pane) {
        pane.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), true);
    }

    void deactivePane(Pane[] pane) {
        for (Pane pane2 : pane) {
            pane2.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), false);
        }
    }

    @FXML
    void menuAccueilClicked(MouseEvent event) {
        try {
            Pane[] panes = { menu_employe, menu_lieu, menu_affectation };
            this.loadFxlView("accueil-view.fxml", menu_accueil, panes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void menuLogoutClicked(MouseEvent event) throws IOException {
        notification = new Notification("Déconnexion", "Voulez-vous vous quitter l'application?", Alert.AlertType.CONFIRMATION);
        if(notification.showConfirmation().showAndWait().get() == ButtonType.OK){
            Stage main = new Stage();
            fxml = FXMLLoader.load(GestionAffectationApp.class.getResource("landing-view.fxml"));
            Scene scene = new Scene(fxml);

            main.setScene(scene);
            main.setResizable(false);
            main.initStyle(StageStyle.UNDECORATED);
            main.show();

            // close landing stage
            this.closeMainWindow();
        }
    }

    private void closeMainWindow() {
        stage = (Stage) main_window.getScene().getWindow();
        stage.close();
    }

    @FXML
    void menuAffectationClicked(MouseEvent event) {
        try {
            Pane[] panes = { menu_accueil, menu_employe, menu_lieu };
            this.loadFxlView("affectation-view.fxml", menu_affectation, panes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void menuEmployeClicked(MouseEvent event) {
        try {
            Pane[] panes = { menu_accueil, menu_affectation, menu_lieu };
            this.loadFxlView("employe-view.fxml", menu_employe, panes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void menuLieuClicked(MouseEvent event) {
        try {
            Pane[] panes = { menu_accueil, menu_employe,  menu_affectation };
            this.loadFxlView("lieu-view.fxml", menu_lieu, panes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}