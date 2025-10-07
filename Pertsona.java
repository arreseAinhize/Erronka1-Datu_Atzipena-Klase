public class Pertsona {
    private String nan;
    private String izena;
    private String abizena;
    private int adina;
    private String helbidea;

    public Pertsona() {
    }

    public Pertsona(String nan, String izena, String abizena) {
        this.nan = nan;
        this.izena = izena;
        this.abizena = abizena;
    }

    public Pertsona(String nan, int adina) {
        this.nan = nan;
        this.adina = adina;
    }

    public Pertsona(String nan, String helbidea) {
        this.nan = nan;
        this.helbidea = helbidea;
    }

    public Pertsona(String nan, String izena, String abizena, int adina, String helbidea) {
        this.nan = nan;
        this.izena = izena;
        this.abizena = abizena;
        this.adina = adina;
        this.helbidea = helbidea;
    }

    public String getNan() {return nan;}
    public String getIzena() {return izena;}
    public String getAbizena() {return abizena;}
    public int getAdina() {return adina;}
    public String getHelbidea() {return helbidea;}

    public void setNan(String nan) {this.nan = nan;}
    public void setIzena(String izena) {this.izena = izena;} 
    public void setAbizena(String abizena) {this.abizena = abizena;}
    public void setAdina(int adina) {this.adina = adina;}
    public void setHelbidea(String helbidea) {this.helbidea = helbidea;}
    
    @Override
    public String toString() {
        return "Pertsona{" +
                "nan='" + nan + '\'' +
                ", izena='" + izena + '\'' +
                ", abizena='" + abizena + '\'' +
                ", adina=" + adina +
                ", helbidea='" + helbidea + '\'' +
                '}';
    }

    public String toCSV() {
        return nan + "," + izena + "," + abizena + "," + adina + "," + helbidea;
    }
}
    
