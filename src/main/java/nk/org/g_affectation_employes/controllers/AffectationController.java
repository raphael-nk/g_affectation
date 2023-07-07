package nk.org.g_affectation_employes.controllers;

import javafx.beans.property.SimpleStringProperty;
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
import nk.org.g_affectation_employes.models.Affecter;
import nk.org.g_affectation_employes.models.Employe;
import nk.org.g_affectation_employes.models.Lieu;
import nk.org.g_affectation_employes.services.AffectationService;
import nk.org.g_affectation_employes.services.EmployeService;
import nk.org.g_affectation_employes.services.LieuService;
import nk.org.g_affectation_employes.utils.Helper;
import nk.org.g_affectation_employes.utils.Notification;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AffectationController implements Initializable {

    @FXML
    private ImageView close_accueil_win;

    @FXML
    private Label txt_new_pointage;

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
    private ComboBox<String> combo_code_emp;

    @FXML
    private ComboBox<String> combo_code_lieu;

    @FXML
    private DatePicker date;

    @FXML
    private TableView<Affecter> table_affectation;

    @FXML
    private TableColumn<Affecter, Integer> col_id;

    @FXML
    private TableColumn<Affecter, String> col_code_emp;

    @FXML
    private TableColumn<Affecter, String> col_code_lieu;

    @FXML
    private TableColumn<Affecter, String> col_date;

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
    public ObservableList<Affecter> data = FXCollections.observableArrayList();
    private Notification notification;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.displayPointageBy();
        this.toggleBtnAndText("NOUVEAU AFFECTATION", true, false);
        this.resetField();
        this.list();
    }

    private void list() {
        this.setCode();
        data.clear();
        List<Affecter> pointages = AffectationService.getAll();
        data.addAll(pointages);

        col_id.setCellValueFactory(new PropertyValueFactory<Affecter, Integer>("id"));
        col_code_emp.setCellValueFactory(new PropertyValueFactory<Affecter, String>("codeemp"));
        col_code_lieu.setCellValueFactory(new PropertyValueFactory<Affecter, String>("codelieu"));
        col_date.setCellValueFactory(new  PropertyValueFactory<Affecter, String>("date"));

        table_affectation.setItems(data);
    }
    public void displayPointageBy(){
        this.display_by.getItems().addAll(this.displayBy);
        this.display_by.setValue(25);
    }
    public void setCode(){
        // set code prof
        List<Employe> employeList = EmployeService.getAll();
        combo_code_emp.getItems().clear();
        for (Employe employe: employeList){
            combo_code_emp.getItems().add(employe.getCodeemp());
        }

        // set code salle
        List<Lieu> lieuList = LieuService.getAll();
        combo_code_lieu.getItems().clear();
        for(Lieu lieu: lieuList){
            combo_code_lieu.getItems().add(lieu.getCodelieu());
        }
    }
    public void resetField(){
        LocalDateTime currDate = LocalDateTime.now();
        date.setValue(LocalDate.from(currDate));
        this.setCode();

        combo_code_emp.setPromptText("Code Employé");
        combo_code_emp.setDisable(false);
        combo_code_lieu.setPromptText("Code Lieu");
        combo_code_lieu.setDisable(false);
    }
    public void toggleBtnAndText(String title, Boolean show, Boolean hide){
        txt_annuler.setVisible(hide);
        txt_new_pointage.setText(title);
        btn_ajouter.setVisible(show);
        btn_annuler.setVisible(show);
        btn_modifier.setVisible(hide);
        btn_supprimer.setVisible(hide);
    }

    public Date toDateFormat(Date date1){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = simpleDateFormat.format(date1);
        return Date.valueOf(formatedDate);
    }

    @FXML
    void TxtAnnulerClicked(MouseEvent event) {
        this.resetField();
        this.toggleBtnAndText("NOUVEAU AFFECTATION", true, false);
    }

    @FXML
    void add(ActionEvent event) {
        String code_empValue = combo_code_emp.getValue();
        String code_lieuValue = combo_code_lieu.getValue();
        Date datePointage = Date.valueOf(date.getValue());
        Integer nextId = AffectationService.total() + 1;
        notification = new Notification("Nouveau Affectation");

        if(code_empValue == null || code_lieuValue == null){
            notification.showAlert("Veuillez remplir les champs obligatoires!", Alert.AlertType.WARNING);
        } else {
            Affecter pointage = new Affecter(nextId, code_empValue, code_lieuValue,this.toDateFormat(datePointage));

            if(AffectationService.store(pointage)){
                notification.showAlert("L'ajout d'un nouveau affecation a été effectué avec succès", Alert.AlertType.INFORMATION);
                this.list();
                this.resetField();
            } else {
                notification.showAlert("Cet employé a été déjà affecté à un endroit sur le même date!", Alert.AlertType.INFORMATION);
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
        Affecter pointage = table_affectation.getSelectionModel().getSelectedItem();
        notification = new Notification("Suppression Affectation");

        if(pointage == null){
            notification.showAlert("Veuillez selectionner une ligne s'il vous plaît!", Alert.AlertType.WARNING);
        }
        else if(
                notification.showConfirmation("Voulez-vous supprimer cette ligne?",
                        Alert.AlertType.CONFIRMATION).showAndWait().get() == ButtonType.OK
                        && AffectationService.delete(pointage)
        ){
            notification.showAlert("La suppression a été effectué avec succès", Alert.AlertType.INFORMATION);
            this.list();
            this.resetField();
            this.toggleBtnAndText("NOUVEAU AFFECTATION", true, false);
        }
    }

    @FXML
    void exportToExcelClicked(MouseEvent event) {
        Helper.exportToExcel(table_affectation, Helper.getFilePath("EXCEL Files", "*.xlsx"));
    }

    @FXML
    void exportToPdfClicked(MouseEvent event) {
        Helper.exportToPDF(table_affectation, Helper.getFilePath("PDF Files", "*.pdf"));
    }

    @FXML
    void getSelectedRowFromPointageTable(MouseEvent event) {

        Affecter pointage = table_affectation.getSelectionModel().getSelectedItem();
        System.out.println(pointage.getCodeemp());

        if(pointage != null){
            this.toggleBtnAndText("MODIFICATION", false, true);
            this.setCode();

            combo_code_emp.setValue(pointage.getCodeemp());
            combo_code_emp.setDisable(true);

            combo_code_lieu.setValue(pointage.getCodelieu());
            combo_code_lieu.setDisable(true);

            date.setValue(pointage.getDate().toLocalDate());
        }
    }

    @FXML
    void handleDisplayBy(ContextMenuEvent event) {

    }

    @FXML
    void txtSearchPointage(KeyEvent event) {
        String recherche = txt_search.getText();
        if(recherche.isEmpty()) this.list();
        else{
            data.clear();
            List<Affecter> pointages = AffectationService.getAll();

            for(Affecter pointage: pointages){
                if(
                        pointage.getCodeemp().toLowerCase().contains(recherche.toLowerCase())
                                || pointage.getCodelieu().toLowerCase().contains(recherche.toLowerCase())
                                || pointage.getDate().toString().toLowerCase().contains(recherche.toLowerCase())
                ){
                    data.add(pointage);
                }
            }

            table_affectation.setItems(data);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Affecter selectedItem = table_affectation.getSelectionModel().getSelectedItem();
        notification = new Notification("Modification Affectation");
        Date datePointage = Date.valueOf(date.getValue());

        if(selectedItem == null){
            notification.showAlert("Veuillez selectionner une ligne s'il vous plaît!", Alert.AlertType.WARNING);
        } else {
            selectedItem.setDate(datePointage);

            if(
                    notification.showConfirmation("Voulez-vous modifier cette ligne?",
                            Alert.AlertType.CONFIRMATION).showAndWait().get() == ButtonType.OK
                            && AffectationService.update(selectedItem)
            ){
                notification.showAlert("La modification a été effectué avec succès", Alert.AlertType.INFORMATION);
                this.list();
                this.resetField();
                this.toggleBtnAndText("NOUVEAU AFFECTATION", true, false);
            }
        }
    }


}