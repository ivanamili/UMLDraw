package businessLogic;
import org.hibernate.SessionFactory;
import org.jhotdraw.draw.*;

public class Aktor extends Element {

	private int crtezID;
	private int ID;
	private RectangleFigure okvir;

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
		// TODO - implement Aktor.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement Aktor.setID
		throw new UnsupportedOperationException();
	}

	public RectangleFigure getOkvir() {
		return this.okvir;
	}

	/**
	 * 
	 * @param okvir
	 */
	public void setOkvir(RectangleFigure okvir) {
		this.okvir = okvir;
	}

	public void getAttribute() {
		// TODO - implement Aktor.getAttribute
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void setAttribute(int attribute) {
		// TODO - implement Aktor.setAttribute
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