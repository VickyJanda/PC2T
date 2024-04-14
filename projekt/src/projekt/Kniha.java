package projekt;

public class Kniha {
String jmeno;
String autor[];
int rok_vydani;
public enum Dostupnost {
	  DOSTUPNÁ,
	  NEDOSTUPNÁ;

}

public Dostupnost typ_dostupnosti;

public Kniha(String jmeno, String[] autor, int rok_vydani, Dostupnost dostupnost) {
	super();
	this.jmeno = jmeno;
	this.autor = autor;
	this.rok_vydani = rok_vydani;
	typ_dostupnosti = dostupnost;
}
public String getJmeno() {
	return jmeno;
}
public void setJmeno(String jmeno) {
	this.jmeno = jmeno;
}
public String[] getAutor() {
	return autor;
}
public void setAutor(String[] autor) {
	this.autor = autor;
}
public int getRok_vydani() {
	return rok_vydani;
}
public void setRok_vydani(int rok_vydani) {
	this.rok_vydani = rok_vydani;
}
public Dostupnost getDostupnost() {
	return typ_dostupnosti;
}

public void setDostupnost(Dostupnost typ_dostupnosti) {
	this.typ_dostupnosti = typ_dostupnosti;
}

}
