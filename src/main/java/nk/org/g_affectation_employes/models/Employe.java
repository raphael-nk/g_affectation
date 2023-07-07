package nk.org.g_affectation_employes.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    private String codeemp;
    private String nom;
    private String prenom;
    private String poste;

    public Employe() {
    }

    public Employe(String codeemp, String nom, String prenom, String poste) {
        this.codeemp = codeemp;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
    }

    public String getCodeemp() {
        return codeemp;
    }

    public void setCodeemp(String codeemp) {
        this.codeemp = codeemp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }
}
