package nk.org.g_affectation_employes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nk.org.g_affectation_employes.models.Lieu;
import nk.org.g_affectation_employes.services.LieuService;
import nk.org.g_affectation_employes.utils.Notification;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LieuController implements Initializable {

    @FXML
    private ImageView close_accueil_win;

    @FXML
    private TextField input_code;

    @FXML
    private TextField designation;

    @FXML
    private Label txt_new_salle;

    @FXML
    private Button btn_supprimer;

    @FXML
    private Button btn_modifier;

    @FXML
    private Button btn_ajouter;

    @FXML
    private Text txt_annuler;

    @FXML
    private Button btn_annuler;

    @FXML
    private ChoiceBox<String> combo_province;

    @FXML
    private TableView<Lieu> table_lieu;

    @FXML
    private TableColumn<Lieu, String> col_code;

    @FXML
    private TableColumn<Lieu, String> col_designation;

    @FXML
    private TableColumn<Lieu, String> col_province;

    @FXML
    private TextField txt_search;

    @FXML
    private Label txt_new_prof1;

    @FXML
    private ImageView export_to_pdf;

    @FXML
    private ImageView export_to_excel;

    @FXML
    private ChoiceBox<Integer> display_by;

    private Integer[] displayBy = {25, 50, 100, 250, 500};
    private String[] province = {"ANTANANARIVO", "FIANARANTSOA", "TOAMASINA", "MAHAJANGA", "TOLIARA", "ANTSIRANANA"};

    public ObservableList<Lieu> data = FXCollections.observableArrayList();
    private Notification notification;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.displayCombo();
        this.toggleBtnAndText("NOUVEAU LIEU", true, false);
        this.list();
    }

    private void list() {
        this.setCode();
        data.clear();
        List<Lieu> lieux = LieuService.getAll();
        data.addAll(lieux);

        col_code.setCellValueFactory(new PropertyValueFactory<Lieu, String>("codelieu"));
        col_designation.setCellValueFactory(new PropertyValueFactory<Lieu, String>("designation"));
        col_province.setCellValueFactory(new PropertyValueFactory<Lieu, String>("province"));

        table_lieu.setItems(data);
    }

    public void displayCombo(){
        this.display_by.getItems().removeAll(this.displayBy);
        this.display_by.getItems().addAll(this.displayBy);
        this.display_by.setValue(25);

        this.combo_province.getItems().removeAll(this.province);
        this.combo_province.getItems().addAll(this.province);
        this.combo_province.setValue("FIANARANTSOA");
    }
    
    @FXML
    void TxtAnnulerClicked(MouseEvent event) {
        this.toggleBtnAndText("NOUVEAU LIEU", true, false);
        this.resetField();
    }

    public void toggleBtnAndText(String title, Boolean show, Boolean hide){
        txt_annuler.setVisible(hide);
        txt_new_salle.setText(title);
        btn_ajouter.setVisible(show);
        btn_annuler.setVisible(show);
        btn_modifier.setVisible(hide);
        btn_supprimer.setVisible(hide);
    }

    private void resetField() {
        this.setCode();
        designation.setText("");
        displayCombo();
    }

    private void setCode() {
        int nextID = LieuService.total() + 1;
        input_code.setText("ADDR-" + nextID);
    }

    @FXML
    void add(ActionEvent event) {
        String code = input_code.getText();
        String design = designation.getText();
        String province = combo_province.getValue();
        notification = new Notification("AJOUT NOUVEAU LIEU");

        if(code.isEmpty() || design.isEmpty()){
            notification.showAlert("Veuillez remplir les champs obligatoires!", Alert.AlertType.WARNING);
        } else {
            Lieu lieu = new Lieu(code, design, province);

            if(LieuService.store(lieu)){
                notification.showAlert("L'ajout d'un nouveau lieu a été effectué avec succès", Alert.AlertType.INFORMATION);
                this.resetField();
                this.list();
            }
        }
    }

    @FXML
    void btnCancelClicked(ActionEvent event) {
        this.TxtAnnulerClicked(null);
    }

    @FXML
    void btnCloseAccueilClicked(MouseEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {
        notification = new Notification("Suppression Lieu");
        Lieu salle = table_lieu.getSelectionModel().getSelectedItem();

        if(salle == null){
            notification.showAlert("Veuillez selectionner une ligne s'il vous plaît!", Alert.AlertType.WARNING);
        } else if(
                notification.showConfirmation("Voulez-vous supprimer cette ligne?",
                        Alert.AlertType.CONFIRMATION).showAndWait().get() == ButtonType.OK
                        && LieuService.delete(salle)
        ) {
            notification.showAlert("La suppression a été effectué avec succès", Alert.AlertType.INFORMATION);
            this.list();
            this.TxtAnnulerClicked(null);
        }
    }

    @FXML
    void exportToExcelClicked(MouseEvent event) {

    }

    @FXML
    void exportToPdfClicked(MouseEvent event) {

    }

    @FXML
    void getSelectedRowFromTable(MouseEvent event) {
        this.toggleBtnAndText("MODIFICATION", false, true);
        Lieu selectedItem = table_lieu.getSelectionModel().getSelectedItem();

        input_code.setText(selectedItem.getCodelieu());
        designation.setText(selectedItem.getDesignation());
        combo_province.setValue(selectedItem.getProvince());
    }

    @FXML
    void handleDisplayBy(ContextMenuEvent event) {

    }

    @FXML
    void txtSearchSalle(KeyEvent event) {
        String recherche = txt_search.getText();

        if(recherche.isEmpty()) this.list();
        else {
            data.clear();
            List<Lieu> lieux = LieuService.getAll();

            for(Lieu lieu: lieux){
                if(
                        lieu.getCodelieu().toLowerCase().contains(recherche.toLowerCase())
                                || lieu.getDesignation().toLowerCase().contains(recherche.toLowerCase())
                                || lieu.getProvince().toLowerCase().contains(recherche.toLowerCase())
                ) {
                    data.add(lieu);
                }
            }

            table_lieu.setItems(data);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Lieu selectedItem = table_lieu.getSelectionModel().getSelectedItem();
        notification = new Notification("Modification Salle");

        String code =  input_code.getText();
        String design = designation.getText();
        String province = combo_province.getValue();

        if(selectedItem == null){
            notification.showAlert("Veuillez selectionner une ligne s'il vous plaît!", Alert.AlertType.WARNING);
        } else {
            selectedItem.setCodelieu(code);
            selectedItem.setDesignation(design);
            selectedItem.setProvince(province);

            if(
                    notification.showConfirmation("Voulez-vous modifier cette ligne?",
                            Alert.AlertType.CONFIRMATION).showAndWait().get() == ButtonType.OK
                            && LieuService.update(selectedItem)
            ){
                notification.showAlert("La modification a été effectué avec succès", Alert.AlertType.INFORMATION);
                this.TxtAnnulerClicked(null);
                this.list();
            }
        }
    }
}
