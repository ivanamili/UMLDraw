package businessLogic;

import org.hibernate.SessionFactory;

public class Korisnik implements IDatabaseStore {

	private String ime;
	private String sifra;
	private Crtez trenutniCrtez;

	public int getID() {
		// TODO - implement Class.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement Class.setID
		throw new UnsupportedOperationException();
	}

	public String getIme() {
		return this.ime;
	}

	/**
	 * 
	 * @param ime
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getSifra() {
		return this.sifra;
	}

	/**
	 * 
	 * @param sifra
	 */
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public Crtez getTrenutniCrtez() {
		return this.trenutniCrtez;
	}

	/**
	 * 
	 * @param trenutniCrtez
	 */
	public void setTrenutniCrtez(Crtez trenutniCrtez) {
		this.trenutniCrtez = trenutniCrtez;
	}

    @Override
    public void save(SessionFactory sessionFactory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(SessionFactory sessionFactory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SessionFactory sessionFactory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getByID(int[] idComponents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}