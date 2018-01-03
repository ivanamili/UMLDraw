package businessLogic;

import enumerations.UseCaseConnType;
import org.hibernate.SessionFactory;

public class UseCaseVeza extends Veza {

	private int crtezID;
	private int ID;
	private UseCase odKoga;
	private UseCase doKoga;
	private UseCaseConnType tipVeze;

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
		// TODO - implement UseCaseVeza.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement UseCaseVeza.setID
		throw new UnsupportedOperationException();
	}

	public UseCase getOdKoga() {
		return this.odKoga;
	}

	/**
	 * 
	 * @param odKoga
	 */
	public void setOdKoga(UseCase odKoga) {
		this.odKoga = odKoga;
	}

	public UseCase getDoKoga() {
		return this.doKoga;
	}

	/**
	 * 
	 * @param doKoga
	 */
	public void setDoKoga(UseCase doKoga) {
		this.doKoga = doKoga;
	}

	public UseCaseConnType getTipVeze() {
		return this.tipVeze;
	}

	/**
	 * 
	 * @param tipVeze
	 */
	public void setTipVeze(UseCaseConnType tipVeze) {
		this.tipVeze = tipVeze;
	}

	public void getAttribute() {
		// TODO - implement UseCaseVeza.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement UseCaseVeza.setAttribute
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