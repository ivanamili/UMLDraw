package businessLogic.CommonClasses;

public class Korisnik {

	private String ime;
	private String sifra;
	private Crtez trenutniCrtez;
        
        public Korisnik()
        {
            this.trenutniCrtez=null;
        }
        
	public String getIme() {
		return this.ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getSifra() {
		return this.sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public Crtez getTrenutniCrtez() {
		return this.trenutniCrtez;
	}
	public void setTrenutniCrtez(Crtez trenutniCrtez) {
		this.trenutniCrtez = trenutniCrtez;
	}

}