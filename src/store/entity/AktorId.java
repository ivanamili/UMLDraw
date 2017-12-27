package store.entity;
// Generated Dec 27, 2017 1:33:44 AM by Hibernate Tools 4.3.1



/**
 * AktorId generated by hbm2java
 */
public class AktorId  implements java.io.Serializable {


     private int crtezId;
     private int id;

    public AktorId() {
    }

    public AktorId(int crtezId, int id) {
       this.crtezId = crtezId;
       this.id = id;
    }
   
    public int getCrtezId() {
        return this.crtezId;
    }
    
    public void setCrtezId(int crtezId) {
        this.crtezId = crtezId;
    }
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AktorId) ) return false;
		 AktorId castOther = ( AktorId ) other; 
         
		 return (this.getCrtezId()==castOther.getCrtezId())
 && (this.getId()==castOther.getId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getCrtezId();
         result = 37 * result + this.getId();
         return result;
   }   


}


