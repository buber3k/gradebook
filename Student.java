import java.util.Comparator;

public class Student implements Comparable<Student> {
    private String imie;
    private String nazwisko;
    private StudentCondition stanStudenta;
    private int rokUrodzenia;
    private double iloscPunktow;
    private String numerIndeksu;

    public Student(String imie, String nazwisko, StudentCondition stanStudenta, int rokUrodzenia, double iloscPunktow, String numerIndeksu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.stanStudenta = stanStudenta;
        this.rokUrodzenia = rokUrodzenia;
        this.iloscPunktow = iloscPunktow;
        this.numerIndeksu = numerIndeksu;
    }

    // Getters
    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public StudentCondition getStanStudenta() {
        return stanStudenta;
    }

    public int getRokUrodzenia() {
        return rokUrodzenia;
    }

    public double getIloscPunktow() {
        return iloscPunktow;
    }

    public String getNumerIndeksu() {
        return numerIndeksu;
    }

    // Setters
    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setStanStudenta(StudentCondition stanStudenta) {
        this.stanStudenta = stanStudenta;
    }

    public void setRokUrodzenia(int rokUrodzenia) {
        this.rokUrodzenia = rokUrodzenia;
    }

    public void setIloscPunktow(double iloscPunktow) {
        this.iloscPunktow = iloscPunktow;
    }

    public void setNumerIndeksu(String numerIndeksu) {
        this.numerIndeksu = numerIndeksu;
    }

    public void print() {
        System.out.println("Imię: " + imie);
        System.out.println("Nazwisko: " + nazwisko);
        System.out.println("Stan studenta: " + stanStudenta);
        System.out.println("Rok urodzenia: " + rokUrodzenia);
        System.out.println("Ilość punktów: " + iloscPunktow);
        System.out.println("Numer indeksu: " + numerIndeksu);
    }

    @Override
    public int compareTo(Student other) {
        return this.nazwisko.compareTo(other.nazwisko);
    }
}