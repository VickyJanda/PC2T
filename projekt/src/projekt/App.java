package projekt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import projekt.Kniha.Dostupnost;
import projekt.Roman.Zanr;
import projekt.Ucebnice.Rocnik;
import projekt.Connect;

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

	public static Kniha[] increase_size(Kniha[] original) {
		Kniha[] new_arr = new Kniha[original.length + 1];
		System.arraycopy(original, 0, new_arr, 0, original.length);
		return new_arr;
	}

	public static Kniha[] decrease_size(Kniha[] original, int i) {
		Kniha[] new_arr = new Kniha[original.length - 1];
		for (int j = 0, k = 0; j < original.length; j++) {
			if (j != i) {
				new_arr[k] = original[j];
				k++;
			}
		}
		return new_arr;
	}

	public static void main(String[] args) throws IOException {
		
		Dostupnost dostupnost;
		dostupnost = Dostupnost.DOSTUPNÁ;

		Zanr zanr;
		zanr = Zanr.FANTASY;

		Rocnik rocnik;
		rocnik = Rocnik.STUPEŇ2;
		Kniha[] pujcene_knihy = new Kniha[0];
		Kniha[] mojeKniha = new Kniha[0];
		
		
		
		Connect con=new Connect();
		con.connect();
		con.createTable();
		
		ResultSet rs  = con.selectAllresult();
		 try {
			while (rs.next()) {
				if(rs.getString("typ").equalsIgnoreCase("FANTASY") || rs.getString("typ").equalsIgnoreCase("DETEKTIVNÍ")|| rs.getString("typ").equalsIgnoreCase("DOBRODRUŽNÝ") ||rs.getString("typ").equalsIgnoreCase("PSYCHOLOGICKÝ") ||rs.getString("typ").equalsIgnoreCase("HOROR"))
				{
					mojeKniha = increase_size(mojeKniha);
					String [] autors = rs.getString("autor").split(",");
					mojeKniha[mojeKniha.length - 1] = new Roman(rs.getString("nazev"),autors, rs.getInt("rok"), Dostupnost.valueOf(rs.getString("dostupnost")), Zanr.valueOf(rs.getString("typ")));
					
				}
				else if(rs.getString("typ").equalsIgnoreCase("STUPEŇ1") ||rs.getString("typ").equalsIgnoreCase("STUPEŇ2") || rs.getString("typ").equalsIgnoreCase("SŠ") || rs.getString("typ").equalsIgnoreCase("VŠ"))
				{
					mojeKniha = increase_size(mojeKniha);
					String [] autors = rs.getString("autor").split(",");
					mojeKniha[mojeKniha.length - 1] = new Ucebnice(rs.getString("nazev"),autors, rs.getInt("rok"), Dostupnost.valueOf(rs.getString("dostupnost")), Rocnik.valueOf(rs.getString("typ")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		while (run) {
			System.out.println("Vyberte požadovanou činnost:");
			System.out.println("1 .. Vložení nové knihy");
			System.out.println("2 .. Úprava informací o knize");
			System.out.println("3 .. Smazání knihy z databáze");
			System.out.println("4 .. Označení knihy za vypůjčenou/vrácenou");
			System.out.println("5 .. Výpis databáze");
			System.out.println("6 .. Vyhledání knihy");
			System.out.println("7 .. Výpis knih od hledaného autora");
			System.out.println("8 .. Výpis knih z hledaného žánru");
			System.out.println("9 .. Výpis všech vypůjčených knih");
			System.out.println("10 .. Výpis informací o knize do souboru");
			System.out.println("11 .. Načtení informací o knize ze souboru");
			System.out.println("12 .. Ukončení aplikace a ulozeni do databaze");
			int volba = CislaDoN(sc, 15);
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
					mojeKniha = increase_size(mojeKniha);
					mojeKniha[mojeKniha.length - 1] = new Roman(nazev, autor, rok, dostupnost, zanr);
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

					mojeKniha = increase_size(mojeKniha);
					mojeKniha[mojeKniha.length - 1] = new Ucebnice(nazev, autor, rok, dostupnost, rocnik);
				}

				break;

			case 2:
				System.out.println("Zadejte název knihy, kterou chcete upravit: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0; i < mojeKniha.length; i++) {
					nazev = mojeKniha[i].getJmeno();
					if (nazev.equalsIgnoreCase(hledany_nazev)) {
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
											if (autori[j].equalsIgnoreCase(find_author)) {
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
					System.out.println("Kniha NENALEZENA.");
				break;
			case 3:
				System.out.println("Zadejte název knihy, kterou chcete smazat: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0; i < mojeKniha.length - 1; i++) {
					nazev = mojeKniha[i].getJmeno();
					if (nazev.equalsIgnoreCase(hledany_nazev)) {
						System.out.println("Kniha nalezena:");
						kniha_found = true;
						KnihaPrint(mojeKniha[i]);
						System.out.println(
								"Jste si jistí že chcete knihu smazat?\nZadejte '1' pro smazání\nZadejte '2' pro zrušení akce");
						volba_edit = CislaDoN(sc, 2);
						if (volba_edit == 1) {
							mojeKniha = decrease_size(mojeKniha, i);
							System.out.println("Kniha smazána:");
						}
					}
				}
				if (!kniha_found)
					System.out.println("Kniha NENALEZENA.");
				kniha_found = false;
				break;
			case 4:
				System.out.println("Zadejte název knihy, kterou chcete vypůjčit/vrátit: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0; i < mojeKniha.length; i++) {
					nazev = mojeKniha[i].getJmeno();
					if (nazev.equalsIgnoreCase(hledany_nazev)) {
						System.out.println("Kniha nalezena:");
						kniha_found = true;
						KnihaPrint(mojeKniha[i]);
						if (mojeKniha[i].typ_dostupnosti == Dostupnost.DOSTUPNÁ) {
							System.out.println(
									"Kniha je dostupná k zapůjčení, přejete si knihu vypůjčit?\nZadejte '1' pro označení knihy za vypůjčenou\nZadejte '2' pro zrušení akce");
							volba_edit = CislaDoN(sc, 2);
							if (volba_edit == 1) {
								mojeKniha[i].setDostupnost(Dostupnost.NEDOSTUPNÁ);
								pujcene_knihy = increase_size(pujcene_knihy);
								pujcene_knihy[pujcene_knihy.length - 1] = mojeKniha[i];
								System.out.println("Kniha byla označena za vypůjčenou");
							}

						} else {
							if (pujcene_knihy.length == 0)
								System.out.println("Kniha je nedostupná k půjčení nebo vrácení");
							for (int j = 0; j < pujcene_knihy.length; j++) {
								if (pujcene_knihy[j] == mojeKniha[i]) {
									System.out.println(
											"Kniha je půjčená, přejete si knihu vrátit?\nZadejte '1' pro označení knihy za vrácenou\nZadejte '2' pro zrušení akce");
									volba_edit = CislaDoN(sc, 2);
									if (volba_edit == 1) {
										mojeKniha[i].setDostupnost(Dostupnost.DOSTUPNÁ);
										pujcene_knihy = decrease_size(pujcene_knihy, j);
										System.out.println("Kniha byla označena za vrácenou");
									}
								} else {
									System.out.println("Kniha je nedostupná k půjčení nebo vrácení");
								}
							}

						}
					}
				}
				if (!kniha_found)
					System.out.println("Kniha NENALEZENA.");
				kniha_found = false;
				break;

			case 5:
				Arrays.sort(mojeKniha, Kniha.razeni_Jmeno());
				for (int i = 0; i < mojeKniha.length; i++) {
					KnihaPrint(mojeKniha[i]);
				}
				break;
			case 6:
				System.out.println("Zadejte název knihy, kterou chcete najít: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0; i < mojeKniha.length; i++) {
					nazev = mojeKniha[i].getJmeno();
					if (nazev.equalsIgnoreCase(hledany_nazev)) {
						System.out.println("Kniha nalezena:");
						kniha_found = true;
						KnihaPrint(mojeKniha[i]);
					}
				}
				if (!kniha_found)
					System.out.println("Kniha NENALEZENA.");
				kniha_found = false;
				break;
			case 7:

				System.out.println("Zadejte jméno autora, kterého chcete najít: ");
				Kniha[] pom_kniha = new Kniha[0];
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0, k = 0; i < mojeKniha.length; i++) {
					autor = mojeKniha[i].getAutor();
					for (String a : autor) {

						if (a.equalsIgnoreCase(hledany_nazev)) {
							kniha_found = true;
							pom_kniha = increase_size(pom_kniha);
							pom_kniha[k] = mojeKniha[i];
							k++;
						}
					}
				}
				if (!kniha_found)
					System.out.println("Kniha NENALEZENA.");
				kniha_found = false;

				Arrays.sort(pom_kniha, Kniha.razeni_Rok());
				for (int i = 0; i < pom_kniha.length; i++) {
					KnihaPrint(pom_kniha[i]);
				}

				break;

			case 8:

				System.out.println(
						"Vyberte žánr, který chcete najít:\nZadejte '1' pro Fantasy\nZadejte '2' pro Detektivní\nZadejte '3' pro Dobrodružný\nZadejte '4' pro Psychologický\nZadejte '5' pro Horor");
				pom_kniha = new Kniha[0];
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

				for (int i = 0, k = 0; i < mojeKniha.length; i++) {
					if (mojeKniha[i] instanceof Roman) {
						if (zanr == ((Roman) mojeKniha[i]).getTyp_zanru()) {
							kniha_found = true;
							pom_kniha = increase_size(pom_kniha);
							pom_kniha[k] = mojeKniha[i];
							k++;
						}
					}
				}
				if (!kniha_found)
					System.out.println("Kniha odpovídající žánru NENALEZENA:");
				kniha_found = false;

				Arrays.sort(pom_kniha, Kniha.razeni_Jmeno());
				for (int i = 0; i < pom_kniha.length; i++) {
					KnihaPrint(pom_kniha[i]);
				}

				break;
			case 9:
				Kniha[] romany = new Kniha[0];
				Kniha[] ucebnice = new Kniha[0];
				for (int i = 0, r = 0, u = 0; i < pujcene_knihy.length; i++) {
					if (pujcene_knihy[i] instanceof Roman) {
						romany = increase_size(romany);
						romany[r] = pujcene_knihy[i];
						r++;
					} else {
						ucebnice = increase_size(ucebnice);
						ucebnice[u] = pujcene_knihy[i];
						u++;
					}
				}
				Arrays.sort(romany, Kniha.razeni_Jmeno());
				Arrays.sort(ucebnice, Kniha.razeni_Jmeno());

				System.out.println("Vypůjčené romány:");
				for (int i = 0; i < romany.length; i++) {
					KnihaPrint(romany[i]);
				}
				System.out.println();
				System.out.println("Vypůjčené učebnice:");
				for (int i = 0; i < ucebnice.length; i++) {
					KnihaPrint(ucebnice[i]);
				}
				break;
			case 10:
				FileWriter fw;
				String filename;
				System.out.println("Zadejte název knihy, kterou chcete najít: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				for (int i = 0; i < mojeKniha.length; i++) {
					nazev = mojeKniha[i].getJmeno();
					if (nazev.equalsIgnoreCase(hledany_nazev)) {
						KnihaPrint(mojeKniha[i]);
						System.out.println(
								"Kniha nalezena, přejete si knihu uložit do souboru?\nZadejte '1' pro uložení knihy\nZadejte '2' pro zrušení akce");
						volba_edit = CislaDoN(sc, 2);
						if (volba_edit == 1) {
							System.out.println("Zadejte název souboru: ");
							filename = sc.next();
							fw = new FileWriter(filename);
							try {
								fw.write("Název:" + mojeKniha[i].getJmeno());
								fw.write("; Autor:");
								for(int j=0;j<mojeKniha[i].autor.length;j++)
								{
									fw.write(mojeKniha[i].autor[j]);
									if(j!=mojeKniha[i].autor.length-1)
									{
										fw.write(",");
									}
								}
								fw.write( "; Rok vydání:"
										+ mojeKniha[i].getRok_vydani() + "; Dostupnost:"
										+ mojeKniha[i].getDostupnost());

								if (mojeKniha[i] instanceof Roman)
									fw.write("; Žánr:" + ((Roman) mojeKniha[i]).getTyp_zanru());
								else if (mojeKniha[i] instanceof Ucebnice)
									fw.write("; Ročník:" + ((Ucebnice) mojeKniha[i]).getTyp_rocniku());

							} catch (IOException e) {

							}
							fw.close();
						}
						kniha_found = true;
					}
				}
				if (!kniha_found)
					System.out.println("Kniha NENALEZENA:");
				kniha_found = false;

				break;
			case 11:
				FileReader fr;
				BufferedReader br;
				
				System.out.println("Zadejte jméno souboru: ");
				hledany_nazev = sc.next();
				hledany_nazev += sc.nextLine();
				
				 try {
				fr = new FileReader(hledany_nazev);
				br = new BufferedReader(fr);
				 } catch (FileNotFoundException ex) {
					 System.out.println( ex);
					 break;
				    }
				String line = br.readLine();
				String[] words = line.split(";");
				String[] fInput = new String[5];
				String[] Authors= new String[5];
				
				for(int i=0;i<words.length;i++)
				{
					String[] input = words[i].split(":");
					if(i==1)
					{
						String[] inputs = input[i].split(",");
						Authors = new String[inputs.length];
						for(int j=0;j<inputs.length;j++)
						{
							Authors[j]=inputs[j];
						}
					}
					else
					{
						
						fInput[i]= input[1];
					}
				
					
					
				}
				if(fInput[4].equalsIgnoreCase("FANTASY") || fInput[4].equalsIgnoreCase("DETEKTIVNÍ")|| fInput[4].equalsIgnoreCase("DOBRODRUŽNÝ") || fInput[4].equalsIgnoreCase("PSYCHOLOGICKÝ") || fInput[4].equalsIgnoreCase("HOROR"))
				{
					mojeKniha = increase_size(mojeKniha);
					mojeKniha[mojeKniha.length - 1] = new Roman(fInput[0],Authors, Integer.valueOf(fInput[2]), Dostupnost.valueOf(fInput[3]), Zanr.valueOf(fInput[4]));
					
				}
				else if(fInput[4].equalsIgnoreCase("STUPEŇ1") || fInput[4].equalsIgnoreCase("STUPEŇ2") || fInput[4].equalsIgnoreCase("SŠ") || fInput[4].equalsIgnoreCase("VŠ"))
				{
					mojeKniha = increase_size(mojeKniha);
					mojeKniha[mojeKniha.length - 1] = new Ucebnice(fInput[0],Authors, Integer.valueOf(fInput[2]), Dostupnost.valueOf(fInput[3]), Rocnik.valueOf(fInput[4]));
				}
				else
				{
					System.out.println("Neplatný soubor."+fInput[4]+'.');
				}
			
				br.close();
				fr.close();
				break;
			case 12:
				run = false;
				String typKnihy=null;
				
				System.out.println("Ukončuji aplikaci a zapisuji do databaze...");
				con.deleteTable();
				con.createTable();
				for (int i = 0; i < mojeKniha.length; i++) {
					String Author=mojeKniha[i].autor[0];
					if (mojeKniha[i] instanceof Roman)
						typKnihy = String.valueOf(((Roman) mojeKniha[i]).getTyp_zanru());
					else if (mojeKniha[i] instanceof Ucebnice)
						typKnihy = String.valueOf(((Ucebnice) mojeKniha[i]).getTyp_rocniku());
					for (int j = 1; j < mojeKniha[i].autor.length; j++) {
						Author=Author +","+ mojeKniha[i].autor[j];
					}
					con.insertRecord(mojeKniha[i].getJmeno(),Author, mojeKniha[i].getRok_vydani(), String.valueOf( mojeKniha[i].getDostupnost()), typKnihy);
				}
				
				
				con.disconnect();
				break;

			}

		}

	}
}
