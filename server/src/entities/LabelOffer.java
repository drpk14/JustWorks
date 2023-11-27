package entities;
// Generated 15-nov-2023 18:41:52 by Hibernate Tools 4.3.1



/**
 * LabelOffer generated by hbm2java
 */
public class LabelOffer  implements java.io.Serializable {


     private Integer id;
     private Label label;
     private Offer offer;
     private Boolean obligatory;

    public LabelOffer() {
    }

    public LabelOffer(Label label, Offer offer, Boolean obligatory) {
       this.label = label;
       this.offer = offer;
       this.obligatory = obligatory;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Label getLabel() {
        return this.label;
    }
    
    public void setLabel(Label label) {
        this.label = label;
    }
    public Offer getOffer() {
        return this.offer;
    }
    
    public void setOffer(Offer offer) {
        this.offer = offer;
    }
    public Boolean getObligatory() {
        return this.obligatory;
    }
    
    public void setObligatory(Boolean obligatory) {
        this.obligatory = obligatory;
    }




}


