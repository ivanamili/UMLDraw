package businessLogic;

import enumerations.VisibilityTypeEnum;
import org.hibernate.SessionFactory;

public class Atribut implements IDatabaseStore {

	private int crtezID;
	private int klasaID;
	private int ID;
	private String naziv;
	private String tip;
	private VisibilityTypeEnum vidljivost;
	private boolean isStatic;

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
		return this.klasaID;
	}

	/**
	 * 
	 * @param klasaID
	 */
	public void setKlasaID(int klasaID) {
		this.klasaID = klasaID;
	}

	public int getID() {
		// TODO - implement Atribut.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement Atribut.setID
		throw new UnsupportedOperationException();
	}

	public void getAttribute() {
		// TODO - implement Atribut.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement Atribut.setAttribute
		throw new UnsupportedOperationException();
	}

	public String getTip() {
		return this.tip;
	}

	/**
	 * 
	 * @param tip
	 */
	public void setTip(String tip) {
		this.tip = tip;
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