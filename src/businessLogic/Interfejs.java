package businessLogic;

import java.util.ArrayList;
import org.hibernate.SessionFactory;

public class Interfejs extends ClassDiagramElement {

	private int crtezID;
	private int ID;
	private String naziv;
	private int metodCounter;
	private ArrayList<Metod> metode;

	public int getCrtezID() {
		return this.crtezID;
	}

	/**
	 * 
	 * @param crtezID
	 */
	public void setCrtezID(int crtezID) {
		this.crtezID = crtezID;
	}

	public int getID() {
		// TODO - implement Interfejs.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement Interfejs.setID
		throw new UnsupportedOperationException();
	}

	public String getNaziv() {
		return this.naziv;
	}

	/**
	 * 
	 * @param naziv
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getMetodCounter() {
		return this.metodCounter;
	}

	/**
	 * 
	 * @param metodCounter
	 */
	public void setMetodCounter(int metodCounter) {
		this.metodCounter = metodCounter;
	}

	public ArrayList<Metod> getMetode() {
		return this.metode;
	}

	/**
	 * 
	 * @param metode
	 */
	public void setMetode(ArrayList<Metod> metode) {
		this.metode = metode;
	}

	/**
	 * 
	 * @param met
	 */
	public void dodajMetodu(Metod met) {
		// TODO - implement Interfejs.dodajMetodu
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiMetodu(int id) {
		// TODO - implement Interfejs.obrisiMetodu
		throw new UnsupportedOperationException();
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