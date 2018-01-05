package store.entity;
// Generated Jan 5, 2018 2:02:22 AM by Hibernate Tools 4.3.1



/**
 * UseCaseKonekcijaDb generated by hbm2java
 */
public class UseCaseKonekcijaDb  implements java.io.Serializable {


     private UseCaseKonekcijaDbId id;
     private CrtezDb crtezDb;
     private UseCaseDb useCaseDbByUseCaseConnOdKoga;
     private UseCaseDb useCaseDbByUseCaseConnDoKoga;
     private String tipVeze;

    public UseCaseKonekcijaDb() {
    }

	
    public UseCaseKonekcijaDb(UseCaseKonekcijaDbId id, CrtezDb crtezDb, UseCaseDb useCaseDbByUseCaseConnOdKoga, UseCaseDb useCaseDbByUseCaseConnDoKoga) {
        this.id = id;
        this.crtezDb = crtezDb;
        this.useCaseDbByUseCaseConnOdKoga = useCaseDbByUseCaseConnOdKoga;
        this.useCaseDbByUseCaseConnDoKoga = useCaseDbByUseCaseConnDoKoga;
    }
    public UseCaseKonekcijaDb(UseCaseKonekcijaDbId id, CrtezDb crtezDb, UseCaseDb useCaseDbByUseCaseConnOdKoga, UseCaseDb useCaseDbByUseCaseConnDoKoga, String tipVeze) {
       this.id = id;
       this.crtezDb = crtezDb;
       this.useCaseDbByUseCaseConnOdKoga = useCaseDbByUseCaseConnOdKoga;
       this.useCaseDbByUseCaseConnDoKoga = useCaseDbByUseCaseConnDoKoga;
       this.tipVeze = tipVeze;
    }
   
    public UseCaseKonekcijaDbId getId() {
        return this.id;
    }
    
    public void setId(UseCaseKonekcijaDbId id) {
        this.id = id;
    }
    public CrtezDb getCrtezDb() {
        return this.crtezDb;
    }
    
    public void setCrtezDb(CrtezDb crtezDb) {
        this.crtezDb = crtezDb;
    }
    public UseCaseDb getUseCaseDbByUseCaseConnOdKoga() {
        return this.useCaseDbByUseCaseConnOdKoga;
    }
    
    public void setUseCaseDbByUseCaseConnOdKoga(UseCaseDb useCaseDbByUseCaseConnOdKoga) {
        this.useCaseDbByUseCaseConnOdKoga = useCaseDbByUseCaseConnOdKoga;
    }
    public UseCaseDb getUseCaseDbByUseCaseConnDoKoga() {
        return this.useCaseDbByUseCaseConnDoKoga;
    }
    
    public void setUseCaseDbByUseCaseConnDoKoga(UseCaseDb useCaseDbByUseCaseConnDoKoga) {
        this.useCaseDbByUseCaseConnDoKoga = useCaseDbByUseCaseConnDoKoga;
    }
    public String getTipVeze() {
        return this.tipVeze;
    }
    
    public void setTipVeze(String tipVeze) {
        this.tipVeze = tipVeze;
    }




}


