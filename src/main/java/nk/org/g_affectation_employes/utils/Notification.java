package nk.org.g_affectation_employes.utils;

import javafx.scene.control.Alert;

public class Notification {
    private String title;
    private String headerText;
    private Alert.AlertType type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public Alert.AlertType getType() {
        return type;
    }

    public void setType(Alert.AlertType type) {
        this.type = type;
    }

    public Notification(String title) {
        this.title = title;
    }

    public Notification(String title, Alert.AlertType type) {
        this.title = title;
        this.type = type;
    }

    public Notification(String title, String headerText, Alert.AlertType type) {
        this.title = title;
        this.headerText = headerText;
        this.type = type;
    }

    public Alert showConfirmation(){
        Alert alert = new Alert(this.getType());
        alert.setTitle(this.getTitle());
        alert.setContentText(this.getHeaderText());

        return alert;
    }

    public Alert showConfirmation(String headerText, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(this.getTitle());
        alert.setContentText(headerText);

        return alert;
    }

    public void showAlert(String headerText){
        Alert alert = new Alert(this.getType());
        alert.setTitle(this.getTitle());
        alert.setContentText(headerText);
        alert.show();
    }

    public void showAlert(String headerText, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(this.getTitle());
        alert.setContentText(headerText);
        alert.show();
    }
}