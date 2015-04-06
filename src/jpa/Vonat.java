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

	@Id
	@Temporal(TemporalType.DATE)
    private  Date datum;
    private int keses;
    @ManyToOne
    private Mozdony mozdony;
    @Id
    @ManyToOne
    private Vonatszam vonatszam;
 
 
	
    public Vonat() {
    }
    
	public Vonat(Date datum, int keses, Mozdony mozdony, Vonatszam vonatszam) {
		super();
		this.datum = datum;
		this.keses = keses;
		this.mozdony = mozdony;
		this.vonatszam = vonatszam;
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

	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		String datumString = dateFormat.format(datum);
		return vonatszam.getSzam() + " " + datumString + " " + mozdony.getId() + " " + mozdony.getFutottkm() + " " + keses;
	}
}
