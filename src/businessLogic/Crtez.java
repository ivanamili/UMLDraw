package businessLogic;

import enumerations.DiagramTypeEnum;
import java.util.ArrayList;
import org.hibernate.SessionFactory;

public class Crtez implements IDatabaseStore {

	private int ID;
	private String naslov;
	private DiagramTypeEnum tip;
	private String imeAutora;
	private int elemCounter;
	private int konekcijaCounter;
	private ArrayList<Element> elementi;
	private ArrayList<Veza> veze;

	public int getID() {
		// TODO - implement Crtez.getID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 */
	public void setID(int ID) {
		// TODO - implement Crtez.setID
		throw new UnsupportedOperationException();
	}

	public String getNaslov() {
		return this.naslov;
	}

	/**
	 * 
	 * @param naslov
	 */
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public DiagramTypeEnum getTip() {
		// TODO - implement Crtez.getTip
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tip
	 */
	public void setTip(DiagramTypeEnum tip) {
		// TODO - implement Crtez.setTip
		throw new UnsupportedOperationException();
	}

	public String getImeAutora() {
		return this.imeAutora;
	}

	/**
	 * 
	 * @param imeAutora
	 */
	public void setImeAutora(String imeAutora) {
		this.imeAutora = imeAutora;
	}

	public int getElemCounter() {
		return this.elemCounter;
	}

	/**
	 * 
	 * @param elemCounter
	 */
	public void setElemCounter(int elemCounter) {
		this.elemCounter = elemCounter;
	}

	public int getKonekcijaCounter() {
		return this.konekcijaCounter;
	}

	/**
	 * 
	 * @param konekcijaCounter
	 */
	public void setKonekcijaCounter(int konekcijaCounter) {
		this.konekcijaCounter = konekcijaCounter;
	}

	public Element[] getElementi() {
		// TODO - implement Crtez.getElementi
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param elementi
	 */
	public void setElementi(Element[] elementi) {
		// TODO - implement Crtez.setElementi
		throw new UnsupportedOperationException();
	}

	public Veza[] getVeze() {
		// TODO - implement Crtez.getVeze
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param veze
	 */
	public void setVeze(Veza[] veze) {
		// TODO - implement Crtez.setVeze
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param elem
	 */
	public void dodajElement(Element elem) {
		// TODO - implement Crtez.dodajElement
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiElement(int id) {
		// TODO - implement Crtez.obrisiElement
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param veza
	 */
	public void dodajVezu(Veza veza) {
		// TODO - implement Crtez.dodajVezu
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public void obrisiVezu(Veza id) {
		// TODO - implement Crtez.obrisiVezu
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