package businessLogic;

import org.hibernate.SessionFactory;

public class ClassDiagramVeza extends Veza {

	private int crtezID;
	private int ID;
	private ClassDiagramElement odKoga;
	private ClassDiagramElement doKoga;

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

	public void getAttribute() {
		// TODO - implement ClassDiagramVeza.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement ClassDiagramVeza.setAttribute
		throw new UnsupportedOperationException();
	}

	public UseCase getOdKoga() {
		// TODO - implement ClassDiagramVeza.getOdKoga
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param odKoga
	 */
	public void setOdKoga(UseCase odKoga) {
		// TODO - implement ClassDiagramVeza.setOdKoga
		throw new UnsupportedOperationException();
	}

	public ClassDiagramElement getDoKoga() {
		return this.doKoga;
	}

	/**
	 * 
	 * @param doKoga
	 */
	public void setDoKoga(ClassDiagramElement doKoga) {
		this.doKoga = doKoga;
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