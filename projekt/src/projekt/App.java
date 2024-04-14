package projekt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

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

	public static int CislaDoN(Scanner sc, int n) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala vyjimka typu " + e.toString());
			System.out.println("Zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = CislaDoN(sc, n);
		}
		if (cislo < 1 || cislo > n) {
			System.out.println("Zadejte prosim cele cislo v rozmezí 1 až " + n);
			sc.nextLine();
			cislo = CislaDoN(sc, n);
		}
		return cislo;

	}

	public static void KnihaPrint(Kniha mojeKniha) {
		System.out.print("Název: " + mojeKniha.getJmeno() + ", Autor: " + Arrays.toString(mojeKniha.getAutor())
				+ ", Rok vydání: " + mojeKniha.getRok_vydani() + ", Dostupnost: " + mojeKniha.getDostupnost());
		if (mojeKniha instanceof Roman)
			System.out.println(", Žánr: " + ((Roman) mojeKniha).getTyp_zanru());
		else if (mojeKniha instanceof Ucebnice)
			System.out.println(", Ročník: " + ((Ucebnice) mojeKniha).getTyp_rocniku());
		else
			System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String autors[] = { "Pepa Dvořák", "David Nováček" };

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
			KnihaPrint(mojeKniha[i]);
		}

		Scanner sc = new Scanner(System.in);

		boolean run = true;
		int typ;
		int rok;
		String nazev;
		String hledany_nazev;
		int vyber_dostupnost;
		int vyber_zanr;
		int vyber_rocnik;
		int pocet_autoru;
		int volba_edit;
		int vyber_uprava;
		String find_author;
		Boolean kniha_found = false;
		int pocet_knih = 4;
		while (run) {
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. Vložení nové knihy");
			System.out.println("2 .. Úprava informací o knize");
			System.out.println("3 .. Smazání knihy z databáze");
			System.out.println("5 .. Výpis databáze");
			System.out.println("10 .. Ukončení aplikace");
			int volba = CislaDoN(sc, 10);
			switch (volba) {
			case 1:
				System.out.println("Vyberte typ knihy:\nZadejte '1' pro Román\nZadejte '2' pro Učebnici");
				typ = CislaDoN(sc, 2);
				System.out.println("Zadejte název knihy: ");
				nazev = sc.next();
				nazev += sc.nextLine();
				System.out.println("Zadejte počet autorů: ");
				pocet_autoru = Cisla(sc);
				String[] autor = new String[pocet_autoru];
				for (int i = 0; i < pocet_autoru; i++) {
					System.out.println("Zadejte jméno autora: ");
					autor[i] = sc.next();
					autor[i] += sc.nextLine();
				}
				System.out.println("Zadejte rok vydání knihy: ");
				rok = Cisla(sc);
				System.out.println("Zadejte dostupnost knihy:\nZadejte '1' pro Dostupná\nZadejte '2' pro Nedostupná");
				vyber_dostupnost = CislaDoN(sc, 2);
				if (vyber_dostupnost == 1)
					dostupnost = Dostupnost.DOSTUPNÁ;
				else
					dostupnost = Dostupnost.NEDOSTUPNÁ;

				if (typ == 1) {
					System.out.println(
							"Zadejte žánr románu:\nZadejte '1' pro Fantasy\nZadejte '2' pro Detektivní\nZadejte '3' pro Dobrodružný\nZadejte '4' pro Psychologický\nZadejte '5' pro Horor");
					vyber_zanr = CislaDoN(sc, 5);
					if (vyber_zanr == 1)
						zanr = Zanr.FANTASY;
					if (vyber_zanr == 2)
						zanr = Zanr.DETEKTIVNÍ;
					if (vyber_zanr == 3)
						zanr = Zanr.DOBRODRUŽNÝ;
					if (vyber_zanr == 4)
						zanr = Zanr.PSYCHOLOGICKÝ;
					if (vyber_zanr == 5)
						zanr = Zanr.HOROR;

					mojeKniha[pocet_knih] = new Roman(nazev, autor, rok, dostupnost, zanr);
					pocet_knih++;
				} else {
					System.out.println(
							"Zadejte určený ročník učebnice:\nZadejte '1' pro 1. stupeň ZŠ\nZadejte '2' pro 2. stupeň ZŠ\nZadejte '3' pro Střední školy\nZadejte '4' pro Vysoké školy");
					vyber_rocnik = CislaDoN(sc, 4);
					if (vyber_rocnik == 1)
						rocnik = Rocnik.STUPEŇ1;
					if (vyber_rocnik == 2)
						rocnik = Rocnik.STUPEŇ2;
					if (vyber_rocnik == 3)
						rocnik = Rocnik.SŠ;
					if (vyber_rocnik == 4)
						rocnik = Rocnik.VŠ;
					mojeKniha[pocet_knih] = new Roman(nazev, autor, rok, dostupnost, zanr);
					pocet_knih++;
				}

				break;

			case 2:
				System.out.println("Zadejte název knihy, kterou chcete upravit: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0; i < pocet_knih; i++) {
					nazev = mojeKniha[i].getJmeno();
					if (nazev.equals(hledany_nazev)) {
						System.out.println("Kniha nalezena:");
						kniha_found = true;
						KnihaPrint(mojeKniha[i]);
						while (true) {
							System.out.println(
									"Jakou informaci chcete změnit?\nZadejte '1' pro autora\nZadejte '2' pro rok vydání\nZadejte '3' pro stav dostupnosti\nZadejte '4' pro ukončení úprav");
							volba_edit = CislaDoN(sc, 4);
							if (volba_edit == 1) {
								if (mojeKniha[i].getAutor().length > 1) {
									System.out.println(
											"Zadejte '1' pro změnu jména autora\nZadejte '2' přepsání všech autorů");
									vyber_uprava = CislaDoN(sc, 2);
									if (vyber_uprava == 1) {
										System.out.println("Zadejte jméno autora které chcete změnit");
										find_author = sc.next();
										find_author += sc.nextLine();
										String[] autori = mojeKniha[i].getAutor();
										Boolean found = false;
										for (int j = 0; j < autori.length; j++) {
											if (autori[j].equals(find_author)) {
												System.out.println("Autor nalezen");
												System.out.println("Zadejte nové jméno autora");
												autori[j] = sc.next();
												autori[j] += sc.nextLine();
												mojeKniha[i].setAutor(autori);
												System.out.println("Autor změněn");
												found = true;
											}
										}
										if (!found)
											System.out.println("Autor NENALEZEN!");
									} else {
										System.out.println("Zadejte počet autorů: ");
										pocet_autoru = Cisla(sc);
										autor = new String[pocet_autoru];
										for (int j = 0; j < pocet_autoru; j++) {
											System.out.println("Zadejte jméno autora: ");
											autor[j] = sc.next();
											autor[j] += sc.nextLine();
										}
										mojeKniha[i].setAutor(autor);
										System.out.println("Autoři změněni");
									}
								} else {
									System.out.println("Zadejte nové jméno autora: ");
									String[] new_autor = new String[1];
									new_autor[0] = sc.next();
									new_autor[0] += sc.nextLine();
									mojeKniha[i].setAutor(new_autor);
									System.out.println("Autor změněn");
								}

							} else if (volba_edit == 2) {
								System.out.println("Zadejte rok nový vydání knihy: ");
								rok = Cisla(sc);
								mojeKniha[i].setRok_vydani(rok);
								System.out.println("Rok vydání změněn");
							} else if (volba_edit == 3) {
								System.out.println(
										"Zadejte novou dostupnost knihy:\nZadejte '1' pro Dostupná\nZadejte '2' pro Nedostupná");
								vyber_dostupnost = CislaDoN(sc, 2);
								if (vyber_dostupnost == 1)
									dostupnost = Dostupnost.DOSTUPNÁ;
								else
									dostupnost = Dostupnost.NEDOSTUPNÁ;
								mojeKniha[i].setDostupnost(dostupnost);
								System.out.println("Dostupnost změněna");
							} else
								break;
							KnihaPrint(mojeKniha[i]);
						}
					}

				}
				if (!kniha_found)
					System.out.println("Kniha NENALEZENA");
				break;
			case 3:
				System.out.println("Zadejte název knihy, kterou chcete smazat: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0; i < pocet_knih; i++) {
					nazev = mojeKniha[i].getJmeno();
					if (nazev.equals(hledany_nazev)) {
						System.out.println("Kniha nalezena:");
						kniha_found = true;
						KnihaPrint(mojeKniha[i]);
						System.out.println(
								"Jste si jistí že chcete knihu smazat?\nZadejte '1' pro smazání\nZadejte '2' pro zrušení akce");
						volba_edit = CislaDoN(sc, 2);
						if (volba_edit == 1) {
							for (int j = 0; j < pocet_knih; j++) {
								if(j>i) {
									mojeKniha[j-1] = mojeKniha[j];
								}
								else if(j==i)
									continue;
							}
							pocet_knih--;
							System.out.println("Kniha smazána:");
						}
					} 
				}
				if(!kniha_found)
					System.out.println("Kniha NENALEZENA:");
				break;
			case 5:
				for (int i = 0; i < pocet_knih; i++) {
					KnihaPrint(mojeKniha[i]);
				}
				break;
			case 10:
				run = false;
				System.out.println("Exiting...");
				break;

			}

		}

	}
}
