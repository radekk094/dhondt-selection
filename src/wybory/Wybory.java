
package wybory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Radek
 */
public class Wybory {


    public static void main(String[] args) {
        
        
        System.out.println("SYMULAROR OBLICZANIA MIEJSC W SEJMIE, ZGODNIE Z METODĄ D'HONDTA,");
        System.out.println("      NA PODSTAWIE TRZECH, NIEZALEŻNYCH SONDAŻY WYBORCZYCH      ");
        System.out.println("***************************************************************");
        

        // Pomocnicze zmienne typu Scanner i Random oraz deklaracja stałych
        
        Scanner odczyt = new Scanner(System.in);
        Random los = new Random();
        
        final double BLAD_SONDAZU = 0.03; // Średni błąd statystyczny sondaży wyborczych, deklarowany przez sondaż IBRIS w 2016r.
        final int LICZBA_UPRAWNIONYCH = 30732398; // Dane na podstawie raportu z Wyborów 2015
        final double ODSETEK_GLOSOW_WAZNYCH = 0.965; // Średnia wartość na podstawie raportu z Wyborów 2015 i 2011
        final double PROG_WYBORCZY = 0.05; // Aktualny próg wyborczy w Polsce
        final int CALKOWITA_LICZBA_MANDATOW = 460; // Całkowita liczba mandatów do przyznania (liczba miejsc w Sejmie)        
        
        
        // Wprowadzanie z konsoli podstawowych danych, dotyczących wyborów
        
        System.out.println("\nPodaj ilość kadydujących partii: ");
        int iloscPartii = odczyt.nextInt();
        Partia[] partie = new Partia[iloscPartii];
                
        System.out.println("Wprowadź frekwencję wyborów - w procentach (np. 30): ");
        double frekwencja = (odczyt.nextDouble())/100;
        int glosyWazne = (int)(ODSETEK_GLOSOW_WAZNYCH * (frekwencja * LICZBA_UPRAWNIONYCH));
        
        
        // Wprowadzenie z konsoli danych, dotyczących poszczególnych partii + utworzenie partii
        
        System.out.println();
        for(int i=0; i<iloscPartii; i++)
        {
            System.out.println("\nPodaj dane dla partii nr " + (i+1));
            System.out.println("Nazwa partii: ");
            String nazwaPartii = odczyt.next();
            
            double[] sondaze = new double[3];
            
            for (int j=0; j<3; j++)
            {
                System.out.println("Wyniki sondażu nr " + (j+1) + " - w procentach (np. 30): ");
                sondaze[j] = (odczyt.nextDouble())/100;
            }
            
            partie[i] = new Partia(nazwaPartii, sondaze[0], sondaze[1], sondaze[2]);
        }
        
        
        // Wylosowanie wyników wyborów na podstawie wprowadzonych sondaży, z uwzględnieniem średniego błędu sondażu
        // Sprawdzenie, czy spełniony jest warunek progu wyborczego
        
        for(int i=0; i<iloscPartii; i++)
        {
            double sondaz1 = partie[i].getPartiaSondaz1();
            double sondaz2 = partie[i].getPartiaSondaz2();
            double sondaz3 = partie[i].getPartiaSondaz3();
            
            double wynik1 = glosyWazne * (los.nextDouble() * (2 * BLAD_SONDAZU) + (sondaz1 - BLAD_SONDAZU));
            double wynik2 = glosyWazne * (los.nextDouble() * (2 * BLAD_SONDAZU) + (sondaz2 - BLAD_SONDAZU));
            double wynik3 = glosyWazne * (los.nextDouble() * (2 * BLAD_SONDAZU) + (sondaz3 - BLAD_SONDAZU));
            
            double wynik = (wynik1 + wynik2 + wynik3)/3;
            
            partie[i].setLiczbaOddanychGlosow2((int)wynik);
            
            if (wynik >= PROG_WYBORCZY * glosyWazne)
            {
                partie[i].setLiczbaOddanychGlosow((int)wynik);
            }
            else
            {
                partie[i].setLiczbaOddanychGlosow(0);
            }            
        }
        
        
        // Stworzenie pomocniczej listy oraz dodanie do niej wartości poszczególnych ilorazów wyników (zgodnie z metodą D'Hondta)
        
        ArrayList<Integer> listaGlosow = new ArrayList<>();
        
        for(int i=0; i<iloscPartii; i++)
        {
            int biezacaLiczbaGlosow = partie[i].getLiczbaOddanychGlosow();
            
            for (int j=1; j<=CALKOWITA_LICZBA_MANDATOW; j++)
            {
                listaGlosow.add(biezacaLiczbaGlosow/j);
            }
        }
        
        
        // Sortowanie listy i jej odwrócenie (w celu uzyskania sortowania malejącego), ustalenie progu wyborczego
        
        Collections.sort(listaGlosow);
        Collections.reverse(listaGlosow);
        
        int progGlosow = listaGlosow.get(CALKOWITA_LICZBA_MANDATOW);
        
        
        // Obliczenie liczby przyznanych mandatów dla poszczególnych partii (zgodnie z metodą D'Hondta
        
        for(int i=0; i<iloscPartii; i++)
        {
            if(partie[i].getLiczbaOddanychGlosow()==0)
            {
                partie[i].setLiczbaMandatow(0);
            }
            else
            {
                for(int j=1; j<=CALKOWITA_LICZBA_MANDATOW; j++)
                {
                    if((partie[i].getLiczbaOddanychGlosow()/j) > progGlosow)
                    {
                        partie[i].setLiczbaMandatow(partie[i].getLiczbaMandatow()+1);
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
        
        
        // Wyświetlenie wyników działania programu
        
        java.text.DecimalFormat df = new java.text.DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        
        System.out.println("\n***************************************************************");
        System.out.println("\nZałożenia:\n");
        System.out.println("Liczba partii, biorących udział w wyborach: " + iloscPartii);
        System.out.println("Liczba osób, uprawnionych do głosowania: " + LICZBA_UPRAWNIONYCH);
        System.out.println("Frekwencja wyborów: " + df.format((frekwencja*100)) + "%");
        System.out.println("Odsetek głosów nieważnych: " + df.format((1-ODSETEK_GLOSOW_WAZNYCH)*100) + "%");
        System.out.println("Całkowita liczba mandatów do obsadzenia: " + CALKOWITA_LICZBA_MANDATOW);
        System.out.println("Próg wyborczy: " + df.format(PROG_WYBORCZY*100) + "%");
        System.out.println("Średni błąd sondażu: " + df.format(BLAD_SONDAZU*100) + "%");
        
        System.out.println("\n\nWyniki wyborów:");
        
        for(int i=0; i<iloscPartii; i++)
        {
            System.out.println("\nNazwa partii: " + partie[i].getPartiaNazwa());
            System.out.println("Całkowita liczba oddanych głosów: " + partie[i].getLiczbaOddanychGlosow2());
            System.out.println("Odsetek oddanych głosów: " + df.format(100*((double)(partie[i].getLiczbaOddanychGlosow2())/(double)(glosyWazne))) + "%");
            System.out.println("Liczba przyznanych miejsc w Sejmie: " + partie[i].getLiczbaMandatow());
        }
        
        
    }
    
}
