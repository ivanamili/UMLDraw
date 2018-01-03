package businessLogic;

import enumerations.VisibilityTypeEnum;
import java.util.ArrayList;
import org.hibernate.SessionFactory;

public class Metod implements IDatabaseStore {

	private int crtezID;
	private int klasaIliInterjfejsID;
	private int ID;
	private String naziv;
	private VisibilityTypeEnum vidljivost;
	private boolean isStatic;
	private boolean isAbstract;
	private String povratnaVrednost;
	private ArrayList<Argument> argumenti;
	private int argumentiCounter;

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

	public int getKlasaID() {
		// TODO - implement Metod.getKlasaID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param klasaID
	 */
	public void setKlasaID(int klasaID) {
		// TODO - implement Metod.setKlasaID
		throw new UnsupportedOperationException();
	}

	public int getID() {
		// TODO - implement Metod.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement Metod.setID
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

	public VisibilityTypeEnum getVidljivost() {
		return this.vidljivost;
	}

	/**
	 * 
	 * @param vidljivost
	 */
	public void setVidljivost(VisibilityTypeEnum vidljivost) {
		this.vidljivost = vidljivost;
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

	public String getPovratnaVrednost() {
		return this.povratnaVrednost;
	}

	/**
	 * 
	 * @param povratnaVrednost
	 */
	public void setPovratnaVrednost(String povratnaVrednost) {
		this.povratnaVrednost = povratnaVrednost;
	}

	public ArrayList<Argument> getArgumenti() {
		return this.argumenti;
	}

	/**
	 * 
	 * @param argumenti
	 */
	public void setArgumenti(ArrayList<Argument> argumenti) {
		this.argumenti = argumenti;
	}

	public int getArgumentiCounter() {
		return this.argumentiCounter;
	}

	/**
	 * 
	 * @param argumentiCounter
	 */
	public void setArgumentiCounter(int argumentiCounter) {
		this.argumentiCounter = argumentiCounter;
	}

	public void getAttribute() {
		// TODO - implement Metod.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement Metod.setAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param arg
	 */
	public void dodajArgument(Argument arg) {
		// TODO - implement Metod.dodajArgument
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiArgument(int id) {
		// TODO - implement Metod.obrisiArgument
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