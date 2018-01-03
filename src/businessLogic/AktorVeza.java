package businessLogic;

import org.hibernate.SessionFactory;

public class AktorVeza extends Veza {

	private int crtezID;
	private int ID;
	private Aktor aktor;
	private UseCase useCase;

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
		// TODO - implement AktorVeza.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement AktorVeza.setID
		throw new UnsupportedOperationException();
	}

	public Aktor getAktor() {
		return this.aktor;
	}

	/**
	 * 
	 * @param aktor
	 */
	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

	public UseCase getUseCase() {
		return this.useCase;
	}

	/**
	 * 
	 * @param useCase
	 */
	public void setUseCase(UseCase useCase) {
		this.useCase = useCase;
	}

	public void getAttribute() {
		// TODO - implement AktorVeza.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement AktorVeza.setAttribute
		throw new UnsupportedOperationException();
	}

	public void getAttribute2() {
		// TODO - implement AktorVeza.getAttribute2
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute2
	 */
	public void setAttribute2(int attribute2) {
		// TODO - implement AktorVeza.setAttribute2
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