package businessLogic.CommonClasses;

import businessLogic.AbstractClassHierarchy.AbstractDiagramElement;
import businessLogic.AbstractClassHierarchy.Veza;
import businessLogic.AbstractClassHierarchy.Element;
import businessLogic.ClassDiagrams.Metod;
import enumerations.DiagramTypeEnum;
import java.io.Serializable;
import java.util.ArrayList;


public class Crtez implements Serializable {

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
        
        public void dodaj(AbstractDiagramElement elem)
        {
            if(elem instanceof Element)
                this.dodajElement((Element)elem);
            else if (elem instanceof Veza)
                this.dodajVezu((Veza)elem);
        }
        
        public void obrisi(AbstractDiagramElement elem)
        {
            int idToRemove=elem.getElemId();
            if(elem instanceof Element)
                this.obrisiElement(idToRemove);
            else if (elem instanceof Veza)
                this.obrisiVezu(idToRemove);
        }
	
	public void dodajElement(Element elem) {
                elem.setCrtezIdentifikator(ID);
                elem.setElemId(elemCounter);
		this.elementi.add(elem);
                this.elemCounter++;
	}

	
	public void obrisiElement(int id) {
           this.elementi.removeIf((Element obj) -> obj.getElemId() == id);
	}

	
	public void dodajVezu(Veza veza) {
                veza.setCrtezIdentifikator(ID);
                veza.setElemId(konekcijaCounter);
		this.veze.add(veza);
                this.konekcijaCounter++;
	}

	
	public void obrisiVezu(int id) {
		this.veze.removeIf((Veza obj) -> obj.getElemId() == id);
	}

    

}