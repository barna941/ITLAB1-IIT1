package jpa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Program {

	private EntityManagerFactory factory;
	private EntityManager em;

	public void initDB() {
		factory = Persistence.createEntityManagerFactory(CommonData.getUnit());
		em = factory.createEntityManager();
	}

	void closeDB() {

		em.close();
	}

	public Program(EntityManager em) {
	        this.em = em;
	}	

	public Program() {
}	
	
	public static void main(String[] args) {
		Program app = new Program();
		app.initDB();
		app.startControl();
		app.closeDB();
	
    }


    public void startControl() {
//	    InputStream input = System.in;
        BufferedReader instream = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print(">");
                String inputLine = instream.readLine();
                StringTokenizer tokenizer = new StringTokenizer(inputLine, "   ");
                String command = tokenizer.nextToken();

                if ("t".startsWith(command)) {
                    ujTipus(readString(tokenizer), readString(tokenizer));
                } else if ("m".startsWith(command)) {
                    ujMozdony(readString(tokenizer), readString(tokenizer), readString(tokenizer));
                } else if ("s".startsWith(command)) {
                    ujVonatszam(readString(tokenizer), readString(tokenizer));
                } else if ("v".startsWith(command)) {
                    ujVonat(readString(tokenizer), readString(tokenizer),  readString(tokenizer), readString(tokenizer));
                } else if ("l".startsWith(command)) {
                    String targy = readString(tokenizer);
                    if ("t".startsWith(targy)) {
                        listazTipus();
                    } else if ("m".startsWith(targy)) {
                        listazMozdony();
                    } else if ("s".startsWith(targy)) {
                        listazVonatszam();
                    } else if ("v".startsWith(targy)) {
                        listazVonat();
                    }
                } else if ("x".startsWith(command)) {
                    lekerdezes(readString(tokenizer));
                } else if ("e".startsWith(command)) {
                    break;
                } else {
                    throw new Exception("Hibas parancs! (" + inputLine + ")");
                }
            } catch (Exception e) {
                System.out.println("? " + e.toString()); 
            }
        }

    }

    static String readString(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        } else {
            throw new Exception("Keves parameter!");
        }
    }

    //Uj entitások felvetelehez kapcsolodo szolgaltat�sok
    protected void ujEntity(Object o) throws Exception {
        em.getTransaction().begin();
        try {
            em.persist(o);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    // Uj tipus felvetele
    public void ujTipus(String azonosito, String fajta) throws Exception {
    	Query q1 = em.createQuery("SELECT t from Tipus t where t.azonosito=:azonosito");
    	q1.setParameter("azonosito", azonosito);
    	
    	try {
    		Object o = q1.getSingleResult();
    		throw new Exception("Mar letezik tipus ilyen azonositoval!");
    	} catch (NoResultException e) {
    		Tipus ujTipus = new Tipus(azonosito, fajta);
        	this.ujEntity(ujTipus);
    	}
    }

    // Uj mozdony felvetele
    public void ujMozdony(String sorszam, String tipusID, String futottkm) throws Exception {
        
        //Alakítsa át a megfelelő típusokra a kapott String paramétereket.
        //Ellenőrizze a típus létezését
    	//Hozza létre az új "Mozdony" entitást és rögzítse adatbázisban az "ujEntity" metódussal.
    }

    // Uj vonatszam felvetele
    public void ujVonatszam(String sorszam, String uthossz) throws Exception {
        //Alakítsa át a megfelelő típusokra a kapott String paramétereket.
        //Ellenőrizze, hogy van-e már ilyen vonatszám
    	//Hozza létre az új "Vonatszám" entitást és rögzítse adatbázisban az "ujEntity" metódussal.
    	
    	// Paraméterek parseolása
    	Integer id;
    	Long hossz;
    	try {
    		id = Integer.parseInt(sorszam);
    		hossz = Long.parseLong(uthossz);
    	} catch (NumberFormatException e) {
    		throw new Exception("Nem megfelelo szamformatum!");
    	}
    	
    	// Megnézzük, hogy van-e már ilyen sorszámú vonatszám
    	Query q1 = em.createQuery("SELECT vsz FROM Vonatszam vsz WHERE vsz.szam=:sorszam");
    	q1.setParameter("sorszam", id);
    	
    	try {
    		q1.getSingleResult();
    		throw new Exception("Már létezik ilyen sorszamu vonatszam!");
    	} catch (NoResultException e) {
    		// Ha nincs ilyen sorszámú vonatszám, hozzáadjuk.
    		Vonatszam ujVonatszam = new Vonatszam(id, hossz);
    		ujEntity(ujVonatszam);
    	}
    }

    // Uj vonat felvetele
    public void ujVonat(String vonatszamAzonosito, String datum, String mozdonySorszam, String keses) throws Exception {
        //Alakítsa át a megfelelő típusokra a kapott String paramétereket. Tipp: használja a SimpleDateFormat-ot
    	//Formátum: "yyyy.MM.dd"
    	Integer vonatszamAzonositoInteger;
    	Date datumDate;
    	Integer mozdonySorszamInteger;
    	Integer kesesInteger;
    	
    	try {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    		datumDate = dateFormat.parse(datum);
    	} catch (ParseException e) {
    		throw new Exception("Nem megfelelo datumformatum");
    	}
    	
    	try {
    		vonatszamAzonositoInteger = Integer.parseInt(vonatszamAzonosito);
    		mozdonySorszamInteger = Integer.parseInt(mozdonySorszam);
    		kesesInteger = Integer.parseInt(keses);
    	} catch (NumberFormatException e) {
    		throw new Exception("Nem megfelelo szamformatum!");
    	}
    	
        //Ellenőrizze, hogy érvényes-e a vonatszám, és létezik-e a mozdony.
    	Vonatszam vonatszam;
    	Query vonatSzamQuery = em.createQuery("SELECT vsz FROM Vonatszam vsz WHERE vsz.szam=:vsz");
    	vonatSzamQuery.setParameter("vsz", vonatszamAzonositoInteger);
    	
    	try {
    		vonatszam = (Vonatszam)vonatSzamQuery.getSingleResult();
    	} catch (NoResultException e) {
    		throw new Exception("Nincs ilyen vonatszam!");
    	}
    	
    	Mozdony mozdony;
    	Query mozdonyQuery = em.createQuery("SELECT m FROM Mozdony m WHERE m.id=:msz");
    	mozdonyQuery.setParameter("msz", mozdonySorszamInteger);
    	
    	try {
    		mozdony = (Mozdony)mozdonyQuery.getSingleResult();
    	} catch (NoResultException e) {
    		throw new Exception("Nincs ilyen mozdony!");
    	}
    	
        //Ellenőrizze, hogy az adott napon nincs másik vonat ugyanezzel a vonatszámmal.	
    	Query vonatUniqueQuery = em.createQuery("SELECT v FROM Vonat v WHERE v.vonatszam=:vonatszam AND datum=:datum");
    	vonatUniqueQuery.setParameter("vonatszam", vonatszam);
    	vonatUniqueQuery.setParameter("datum", datumDate);
    	boolean vonatUnique = false;
    	
    	try {
    		vonatUniqueQuery.getSingleResult();
    	} catch (NoResultException e) {
    		vonatUnique = true;
    	}
    	if (!vonatUnique)
    		throw new Exception("Van mar az adott napon vonat ezzel a vonatszammal!");
    	
    	//Hozza létre az új "Vonat" entitást és rögzítse adatbázisban az "ujEntity" metódussal.
    	
    	Vonat vonat = new Vonat(datumDate, mozdony, vonatszam);
    	ujEntity(vonat);
    	
        //Növelje a mozdony futottkm-ét a vonatszám szerinti úthosszal.
    	int futottkm = mozdony.getFutottkm();
    	futottkm += vonatszam.getUthossz();
    	mozdony.setFutottkm(futottkm);
    	
    	em.persist(mozdony);
    }

    //Listazasi szolgaltatasok
    public void listazEntity(List list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    //Tipusok listazasa
    public void listazTipus() throws Exception {
        listazEntity(em.createQuery("SELECT t FROM Tipus t").getResultList());
    }

    //Mozdonyok listazasa
    public void listazMozdony() throws Exception {
    	//K�sz�tsen lek�rdez�st, amely visszaadja az �sszes mozdonyt, majd
        //irassa ki a listazEntity met�dussal az eredm�nyt.
    	listazEntity(em.createQuery("SELECT m FROM Mozdony m").getResultList());
    }

    //Vonatszamok listazasa
    public void listazVonatszam() throws Exception {
    	//Keszitsen lekerdezest, amely visszaadja az osszes vonatszamot, majd
        //irassa ki a listazEntity metodussal az eredmenyt.
    	Query q1 = em.createQuery("SELECT vsz FROM Vonatszam vsz");
    	listazEntity(q1.getResultList());
    }

    //Vonatok listazasa
    public void listazVonat() throws Exception {
    	//K�sz�tsen lek�rdez�st, amely visszaadja az �sszes vonatot, majd
        //irassa ki a listazEntity met�dussal az eredm�nyt.
    	listazEntity(em.createQuery("SELECT v FROM Vonat v").getResultList());
    }

    //Egyedi lekerdezes
    public void lekerdezes(String datum) throws Exception {
    	//TODO    	
        //�rja ki a param�terk�nt kapott napra (INPUTNAP) vonatkoz�an, hogy az
        //egyes mozdony-fajt�k az adott napon �sszesen h�ny kilom�tert futottak.    	
        //Alak�tsa �t a megfelel� t�pusokra a kapott String param�tereket. Tipp: haszn�lja a SimpleDateFormat-ot
        //Tipp: N�zzen ut�na a "t�bbsz�r�s SELECT" kezel�s�nek
    }
}
