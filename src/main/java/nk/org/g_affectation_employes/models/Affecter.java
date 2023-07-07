package nk.org.g_affectation_employes.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "affecter")
public class Affecter {
    @Id
    private int id;
    private String codeemp;
    private String codelieu;
    private Date date;

    public Affecter() {
    }

    public Affecter(String codeemp, String codelieu, Date date) {
        this.codeemp = codeemp;
        this.codelieu = codelieu;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeemp() {
        return codeemp;
    }

    public void setCodeemp(String codeemp) {
        this.codeemp = codeemp;
    }

    public String getCodelieu() {
        return codelieu;
    }

    public void setCodelieu(String codelieu) {
        this.codelieu = codelieu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
