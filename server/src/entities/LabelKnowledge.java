package entities;
// Generated 15-nov-2023 18:41:52 by Hibernate Tools 4.3.1



/**
 * LabelKnowledge generated by hbm2java
 */
public class LabelKnowledge  implements java.io.Serializable {


     private Integer id;
     private Knowledge knowledge;
     private Label label;

    public LabelKnowledge() {
    }

    public LabelKnowledge(Knowledge knowledge, Label label) {
       this.knowledge = knowledge;
       this.label = label;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Knowledge getKnowledge() {
        return this.knowledge;
    }
    
    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }
    public Label getLabel() {
        return this.label;
    }
    
    public void setLabel(Label label) {
        this.label = label;
    }




}


