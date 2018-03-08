package businessLogic.CommonClasses;

import businessLogic.AbstractClassHierarchy.Veza;
import businessLogic.AbstractClassHierarchy.Element;
import enumerations.DiagramTypeEnum;
import java.util.ArrayList;


public class Crtez {

	private int ID;
	private String naslov;
	private DiagramTypeEnum tip;
	private String imeAutora;
	private int elemCounter;
	private int konekcijaCounter;
	private ArrayList<Element> elementi;
	private ArrayList<Veza> veze;
        
        public Crtez()
        {
            this.elementi=new ArrayList<Element>();
            this.veze= new ArrayList<Veza>();
            this.elemCounter=0;
            this.konekcijaCounter=0;
            this.ID=0;
        }
        

	public int getID() {
		return this.ID;
	}
	public void setID(int ID) {
		this.ID=ID;
	}

	public String getNaslov() {
		return this.naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public DiagramTypeEnum getTip() {
		return this.tip;
	}
	public void setTip(DiagramTypeEnum tip) {
		this.tip=tip;
	}

	public String getImeAutora() {
		return this.imeAutora;
	}
	public void setImeAutora(String imeAutora) {
		this.imeAutora = imeAutora;
	}

	public int getElemCounter() {
		return this.elemCounter;
	}
	public void setElemCounter(int elemCounter) {
		this.elemCounter = elemCounter;
	}

	public int getKonekcijaCounter() {
		return this.konekcijaCounter;
	}
	public void setKonekcijaCounter(int konekcijaCounter) {
		this.konekcijaCounter = konekcijaCounter;
	}

	public ArrayList<Element> getElementi() {
		return this.elementi;
	}
	public void setElementi(ArrayList<Element> elementi) {
		this.elementi=elementi;
	}

	public ArrayList<Veza> getVeze() {
		return this.veze;
	}
	public void setVeze(ArrayList<Veza> veze) {
		this.veze=veze;
	}

	
	public void dodajElement(Element elem) {
		this.elementi.add(elem);
                this.elemCounter++;
	}

	
	public void obrisiElement(int id) {
		// TODO - implement Crtez.obrisiElement
		throw new UnsupportedOperationException();
	}

	
	public void dodajVezu(Veza veza) {
		this.veze.add(veza);
                this.konekcijaCounter++;
	}

	
	public void obrisiVezu(Veza id) {
		// TODO - implement Crtez.obrisiVezu
		throw new UnsupportedOperationException();
	}

    

}