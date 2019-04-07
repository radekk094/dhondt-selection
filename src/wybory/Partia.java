
package wybory;

/**
 *
 * @author Radek
 */
public class Partia {
    
    
    private String partiaNazwa;
    private double partiaSondaz1;
    private double partiaSondaz2;
    private double partiaSondaz3;
    private int liczbaOddanychGlosow;
    private int liczbaOddanychGlosow2;
    private int liczbaMandatow = 0;

    
    public String getPartiaNazwa() {
        return partiaNazwa;
    }

    public void setPartiaNazwa(String partiaNazwa) {
        this.partiaNazwa = partiaNazwa;
    }

    public double getPartiaSondaz1() {
        return partiaSondaz1;
    }

    public void setPartiaSondaz1(double partiaSondaz1) {
        this.partiaSondaz1 = partiaSondaz1;
    }

    public double getPartiaSondaz2() {
        return partiaSondaz2;
    }

    public void setPartiaSondaz2(double partiaSondaz2) {
        this.partiaSondaz2 = partiaSondaz2;
    }

    public double getPartiaSondaz3() {
        return partiaSondaz3;
    }

    public void setPartiaSondaz3(double partiaSondaz3) {
        this.partiaSondaz3 = partiaSondaz3;
    }
    
    public int getLiczbaOddanychGlosow() {
        return liczbaOddanychGlosow;
    }

    public void setLiczbaOddanychGlosow(int liczbaOddanychGlosow) {
        this.liczbaOddanychGlosow = liczbaOddanychGlosow;
    }
    
    public int getLiczbaOddanychGlosow2() {
        return liczbaOddanychGlosow2;
    }

    public void setLiczbaOddanychGlosow2(int liczbaOddanychGlosow2) {
        this.liczbaOddanychGlosow2 = liczbaOddanychGlosow2;
    }
    
    public int getLiczbaMandatow() {
        return liczbaMandatow;
    }

    public void setLiczbaMandatow(int liczbaMandatow) {
        this.liczbaMandatow = liczbaMandatow;
    }

    
    public Partia(String partiaNazwa, double partiaSondaz1, double partiaSondaz2, double partiaSondaz3)
    {
        this.partiaNazwa = partiaNazwa;
        this.partiaSondaz1 = partiaSondaz1;
        this.partiaSondaz2 = partiaSondaz2;
        this.partiaSondaz3 = partiaSondaz3;
    }   
    
    
}
