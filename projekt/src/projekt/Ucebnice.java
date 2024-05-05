package projekt;

public class Ucebnice extends Kniha{

	public Ucebnice(String jmeno, String[] autor, int rok_vydani, Dostupnost dostupnost, Rocnik rocnik) {
		super(jmeno, autor, rok_vydani, dostupnost);
		typ_rocniku = rocnik;
		// TODO Auto-generated constructor stub
	}

	enum Rocnik {
		  STUPEŇ1,
		  STUPEŇ2,
		  SŠ,
		  VŠ
		}
	
Rocnik typ_rocniku;

public Rocnik getTyp_rocniku() {
	return typ_rocniku;
}

public void setTyp_rocniku(Rocnik typ_rocniku) {
	this.typ_rocniku = typ_rocniku;
}
	

}
