package projekt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import projekt.Kniha.Dostupnost;
import projekt.Roman.Zanr;
import projekt.Ucebnice.Rocnik;

public class App {
	
	public static int Cisla(Scanner sc) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala vyjimka typu " + e.toString());
			System.out.println("Zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = Cisla(sc);
		}
		return cislo;
	}

	public static int CislaDo2(Scanner sc) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala vyjimka typu " + e.toString());
			System.out.println("Zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = CislaDo2(sc);
		}
		if (cislo != 1 && cislo != 2) {
			System.out.println("Zadejte prosim cele cislo v rozmezí 1 až 2");
			sc.nextLine();
			cislo = CislaDo2(sc);
		}
		return cislo;
	}

	public static int CislaDo5(Scanner sc) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala vyjimka typu " + e.toString());
			System.out.println("Zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = CislaDo5(sc);
		}
		if (cislo < 1 || cislo > 5) {
			System.out.println("Zadejte prosim cele cislo v rozmezí 1 až 5");
			sc.nextLine();
			cislo = CislaDo5(sc);
		}
		return cislo;
	}

	public static int CislaDo4(Scanner sc) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala vyjimka typu " + e.toString());
			System.out.println("Zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = CislaDo4(sc);
		}
		if (cislo < 1 || cislo > 5) {
			System.out.println("Zadejte prosim cele cislo v rozmezí 1 až 4");
			sc.nextLine();
			cislo = CislaDo4(sc);
		}
		return cislo;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String autors[] = { "Pepa", "David" };

		Dostupnost dostupnost;
		dostupnost = Dostupnost.DOSTUPNÁ;

		Zanr zanr;
		zanr = Zanr.FANTASY;

		Rocnik rocnik;
		rocnik = Rocnik.STUPEŇ2;

		Kniha[] mojeKniha = new Kniha[50];
		mojeKniha[0] = new Roman("Rohlik", autors, 2003, dostupnost, zanr);
		mojeKniha[1] = new Ucebnice("Kleste", autors, 2023, dostupnost, rocnik);
		mojeKniha[2] = new Roman("Chleba", autors, 2008, dostupnost, zanr);
		mojeKniha[3] = new Roman("Jablko", autors, 2019, dostupnost, zanr);

		for (int i = 0; i < 4; i++) {
			System.out.print("Název: " + mojeKniha[i].getJmeno() + ", autor: "
					+ Arrays.toString(mojeKniha[i].getAutor()) + ", dostupnost: " + mojeKniha[i].getDostupnost());
			if (mojeKniha[i] instanceof Roman)
				System.out.println(", Žánr: " + ((Roman) mojeKniha[i]).getTyp_zanru());
			else if (mojeKniha[i] instanceof Ucebnice)
				System.out.println(", Žánr: " + ((Ucebnice) mojeKniha[i]).getTyp_rocniku());
			else
				System.out.println();
		}

		Scanner sc = new Scanner(System.in);

		boolean run = true;
		int typ;
		int rok;
		String nazev;
		int vyber_dostupnost;
		int vyber_zanr;
		int pocet_autoru;
		int pocet_knih = 4;
		while (run) {
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. Vložení nové knihy");
			System.out.println("2 .. Výpis databáze");
			int volba = CislaDo2(sc);
			switch (volba) {
			case 1:
				System.out.println("Vyberte typ knihy:\nZadejte '1' pro Román\nZadejte '2' pro Učebnici");
				typ = CislaDo2(sc);
				System.out.println("Zadejte název knihy: ");
				nazev = sc.next();
				nazev += sc.nextLine();
				System.out.println("Zadejte počet autorů: ");
				pocet_autoru = CislaDo5(sc);
				String[] autor = new String[pocet_autoru];
				for (int i = 0; i < pocet_autoru; i++) {
					System.out.println("Zadejte jméno autora: ");
					autor[i] = sc.next();
					autor[i] += sc.nextLine();
				}
				System.out.println("Zadejte rok vydání knihy: ");
				rok = Cisla(sc);
				System.out.println("Zadejte dostupnost knihy:\nZadejte '1' pro Dostupná\nZadejte '2' pro Nedostupná");
				vyber_dostupnost = CislaDo2(sc);
				if (vyber_dostupnost == 1)
					dostupnost = Dostupnost.DOSTUPNÁ;
				else
					dostupnost = Dostupnost.NEDOSTUPNÁ;

				if (typ == 1) {
					System.out.println(
							"Zadejte žánr románu:\nZadejte '1' pro Fantasy\nZadejte '2' pro Detektivní\nZadejte '3' pro Dobrodružný\nZadejte '4' pro Psychologický\nZadejte '5' pro Horor");
					vyber_dostupnost = CislaDo5(sc);
					if (vyber_dostupnost == 1)
						zanr = Zanr.FANTASY;
					if (vyber_dostupnost == 2)
						zanr = Zanr.DETEKTIVNÍ;
					if (vyber_dostupnost == 3)
						zanr = Zanr.DOBRODRUŽNÝ;
					if (vyber_dostupnost == 4)
						zanr = Zanr.PSYCHOLOGICKÝ;
					if (vyber_dostupnost == 5)
						zanr = Zanr.HOROR;

					mojeKniha[pocet_knih] = new Roman(nazev, autor, rok, dostupnost, zanr);
					pocet_knih++;
				} else {
					System.out.println(
							"Zadejte určený ročník učebnice:\nZadejte '1' pro 1. stupeň ZŠ\nZadejte '2' pro 2. stupeň ZŠ\nZadejte '3' pro Střední školy\nZadejte '4' pro Vysoké školy");
					vyber_dostupnost = CislaDo4(sc);
					if (vyber_dostupnost == 1)
						rocnik = Rocnik.STUPEŇ1;
					if (vyber_dostupnost == 2)
						rocnik = Rocnik.STUPEŇ2;
					if (vyber_dostupnost == 3)
						rocnik = Rocnik.SŠ;
					if (vyber_dostupnost == 4)
						rocnik = Rocnik.VŠ;
					mojeKniha[pocet_knih] = new Roman(nazev, autor, rok, dostupnost, zanr);
					pocet_knih++;
				}

				break;
			case 2:
				for (int i = 0; i < pocet_knih; i++) {
					System.out.print("Název: " + mojeKniha[i].getJmeno() + ", autor: "
							+ Arrays.toString(mojeKniha[i].getAutor()) + ", dostupnost: " + mojeKniha[i].getDostupnost());
					if (mojeKniha[i] instanceof Roman)
						System.out.println(", Žánr: " + ((Roman) mojeKniha[i]).getTyp_zanru());
					else if (mojeKniha[i] instanceof Ucebnice)
						System.out.println(", Žánr: " + ((Ucebnice) mojeKniha[i]).getTyp_rocniku());
					else
						System.out.println();
				}

			}
		}

	}
}
