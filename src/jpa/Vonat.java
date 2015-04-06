package jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Vonat {

	@Temporal(TemporalType.DATE)
    private  Date datum;
    private int keses = 0;
    @ManyToOne
    private Mozdony mozdony;
    @ManyToOne
    private Vonatszam vonatszam;



	@Id
	private int id;    
 
	
    public Vonat() {
    }
    
    

	public Vonat(Date datum, Mozdony mozdony, Vonatszam vonatszam) {
		super();
		this.datum = datum;
		this.mozdony = mozdony;
		this.vonatszam = vonatszam;
	}



	public int getId() {
    	return id;
	}

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getKeses() {
        return keses;
    }

    public void setKeses(int keses) {
        this.keses = keses;
    }
    
	public Mozdony getMozdony() {
		return mozdony;
	}

	public void setMozdony(Mozdony mozdony) {
		this.mozdony = mozdony;
	}

	public Vonatszam getVonatszam() {
		return vonatszam;
	}

	public void setVonatszam(Vonatszam vonatszam) {
		this.vonatszam = vonatszam;
	}

}
