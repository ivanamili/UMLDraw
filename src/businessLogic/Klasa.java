package businessLogic;

import java.util.ArrayList;
import org.hibernate.SessionFactory;

public class Klasa extends ClassDiagramElement {

	private int crtezID;
	private int ID;
	private ArrayList<Atribut> atributi;
	private ArrayList<Metod> metode;
	private boolean isAbstract;
	private boolean isStatic;
	private int atributCounter;
	private int metodCounter;

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
		// TODO - implement Klasa.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement Klasa.setID
		throw new UnsupportedOperationException();
	}

	public ArrayList<Atribut> getAtributi() {
		return this.atributi;
	}

	/**
	 * 
	 * @param atributi
	 */
	public void setAtributi(ArrayList<Atribut> atributi) {
		this.atributi = atributi;
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

	public boolean getIsAbstract() {
		return this.isAbstract;
	}

	/**
	 * 
	 * @param isAbstract
	 */
	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public boolean getIsStatic() {
		return this.isStatic;
	}

	/**
	 * 
	 * @param isStatic
	 */
	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public void getAttribute() {
		// TODO - implement Klasa.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement Klasa.setAttribute
		throw new UnsupportedOperationException();
	}

	public int getAtributCounter() {
		return this.atributCounter;
	}

	/**
	 * 
	 * @param atributCounter
	 */
	public void setAtributCounter(int atributCounter) {
		this.atributCounter = atributCounter;
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

	/**
	 * 
	 * @param attr
	 */
	public void dodajAtribut(Atribut attr) {
		// TODO - implement Klasa.dodajAtribut
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiAtribut(int id) {
		// TODO - implement Klasa.obrisiAtribut
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param metoda
	 */
	public void dodajMetodu(Metod metoda) {
		// TODO - implement Klasa.dodajMetodu
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiMetodu(int id) {
		// TODO - implement Klasa.obrisiMetodu
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