package projekt;

public class Roman extends Kniha{

	public Roman(String jmeno, String[] autor, int rok_vydani, Dostupnost dostupnost, Zanr zanr) {
		super(jmeno, autor, rok_vydani, dostupnost);
		typ_zanru = zanr;
		// TODO Auto-generated constructor stub
	}
	enum Zanr {
		  FANTASY,
		  DETEKTIVNÍ,
		  DOBRODRUŽNÝ,
		  PSYCHOLOGICKÝ,
		  HOROR
		}
Zanr typ_zanru;
public Zanr getTyp_zanru() {
	return typ_zanru;
}
public void setTyp_zanru(Zanr typ_zanru) {
	this.typ_zanru = typ_zanru;
}

}
