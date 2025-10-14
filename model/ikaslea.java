package model;

public class ikaslea {
    private int nan;
    private String izena;
    private String abizena;
    private int adina;
    private String helbidea;

    public ikaslea() {
        nan = 0;
        izena = "";
        abizena = "";
        adina = 0;
        helbidea = "";
    }

    public ikaslea(int nan, String izena, String abizena, int adina, String email, String helbidea) {
        this.nan = nan;
        this.izena = izena;
        this.abizena = abizena;
        this.adina = adina;
        this.helbidea = helbidea;
    }

    public int getNan() {
        return nan;
    }

    public String getIzena() {
        return izena;
    }

    public String getAbizena() {
        return abizena;
    }

    public int getAdina() {
        return adina;
    }

    public String getHelbidea() {
        return helbidea;
    }

    public void setNan(int nan) {
        this.nan = nan;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public void setAbizena(String abizena) {
        this.abizena = abizena;
    }

    public void setAdina(int adina) {
        this.adina = adina;
    }

    public void setHelbidea(String helbidea) {
        this.helbidea = helbidea;
    }
}
