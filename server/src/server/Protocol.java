package server;


    
import dao.AlertDao;
import dao.BusinessmanDao; 
import static dao.BusinessmanDao.checkIfBusinessman;
import dao.CandidatureDao;
import dao.KnowledgeDao;
import dao.LabelDao;
import dao.LabelKnowledgeDao;
import dao.LabelOfferDao;
import dao.NotificationDao;
import dao.OfferDao;
import dao.UserDao;  
import dao.WorkerDao;  
import static dao.WorkerDao.checkIfWorker;
import entities.Alert;
import entities.Businessman;
import entities.Candidature;
import entities.Knowledge;
import entities.Label;
import entities.LabelKnowledge;
import entities.LabelOffer;
import entities.Notification;
import entities.Offer;
import entities.User;
import entities.Worker;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List; 
import server.Server.ServerThread;  
import static server.States.*;
import static util.Messages.*;
 

enum States
{
    LOGIN , SINGUP , MENU
}

public class Protocol {
  
    private States state = LOGIN;
    public static User myUser; 
    private String output;
    SharedColection sharedColection;
    
    private ServerThread thread; 
 
    public States getState() {
        return state;
    }

    Protocol(ServerThread thread,SharedColection sharedColection) {
        this.thread = thread; 
        this.sharedColection = sharedColection;
    }
    
    /*
    
        vector[]={"mensaje","mensaje"}
     
    
        S1-Login-ok
    
    */
    public String processInput(String input) {
        this.output = "";
        String[] processedInput = input.split(":"); 
        
        
        if (state == LOGIN) {
            if(processedInput[0].equals(CL_LOGIN)){ 
                output+=S_LOGIN+":";
                if(UserDao.checkUserPassword(processedInput[1], processedInput[2])){ 
                    myUser = UserDao.getUser(processedInput[1]);
                    output+="C:";
                    if(checkIfBusinessman(myUser) != null){
                        output+="B";
                    }else if(checkIfWorker(myUser) != null){
                        output+="W";
                    }else{
                        output+="A";
                        
                    }
                    output+=":";
                    
                    
                    sharedColection.add(myUser.getUser(), thread);
                    if(NotificationDao.getMyUnwatchedNotifications(myUser)){
                        output+="True";
                    }else{
                        output+="False";
                    }
                    state = MENU;
                }else{ 
                    output+="I";
                }
            
            }else if(processedInput[0].equals(CL_REGISTER)){  
                state = SINGUP;
                output+=S_REGISTER+":";
            }else if(processedInput[0].equals(CL_EXIT)){ 
                output+=S_EXIT+":";
            }  
        
        } else if(state == SINGUP) {
    
            if(processedInput[0].equals(CL_REGISTER)){  
                output+=S_REGISTER+":";
                
                
                
                 
                if(!UserDao.checkIfDniExits(UserDao.getUser(processedInput[1]))){
                    
                    
                    
                    
                    
                    
                    
                    //Comprobar
                    User actualUser = new User(processedInput[1],processedInput[2],processedInput[3],processedInput[4],processedInput[5],processedInput[6]);
                    UserDao.addUser(actualUser);
                    if(processedInput[7].equals("BusinessMan")){
                        BusinessmanDao.addBusinessman(new Businessman(actualUser));
                    }else if(processedInput[7].equals("Worker")){
                        WorkerDao.addWorker(new Worker(actualUser)); 
                    }
                           
                        
                    output+="C"; 
                    state = LOGIN;
                }else{ 
                    output+="I";
                }
            }else if(processedInput[0].equals(CL_LOGIN)){
                output+=S_LOGIN;
                state = LOGIN;
            }else if(processedInput[0].equals(CL_EXIT)){ 
                output+=S_EXIT;
            } 
        }else if(state == MENU){
            if(processedInput[0].equals(CL_ALL_OFFERS)){ 
                //See all offers
                output+=S_ALL_OFFERS;
                output+=this.processListOfOffers(OfferDao.getAllOffers(myUser)); 
                
            }else if(processedInput[0].equals(CL_MY_OFFERS)){
                if(processedInput.length <= 1){

                    //See all my offers
                    output+=S_MY_OFFERS;
                    output+=this.processListOfOffers(OfferDao.getMyOffers(myUser));
                }else{
                    
                }
            }else if(processedInput[0].equals(CL_OFFER_DETAILS)){
                //See the details of an offer
                output+=S_OFFER_DETAILS; 
                Offer offer = OfferDao.getOfferDetails(Integer.valueOf(processedInput[1]));
                List offerArrayList = new ArrayList();
                offerArrayList.add(offer);
                output+=this.processListOfOffers(offerArrayList); 
                
            }else if(processedInput[0].equals(CL_MODIFY_OFFER)){
                //Modify an existing offer
                output+=S_MODIFY_OFFER; 
                if(processedInput.length <= 2){
                    //Get the offer details  
                    Offer offer = OfferDao.getOfferDetails(Integer.valueOf(processedInput[1]));
                    List offerArrayList = new ArrayList();
                    offerArrayList.add(offer);
                    output+=this.processListOfOffers(offerArrayList); 
                    
                    
                }else{
                    //Modify the offer
                    Offer offer = OfferDao.getOfferDetails(Integer.valueOf(processedInput[1]));
                    
                    if(OfferDao.checkIfExistOffer(offer,myUser)){    
                        
                        if(!offer.getName().equals(processedInput[2]))
                            offer.setName(processedInput[2]);

                        if(!offer.getDescription().equals(processedInput[3]))
                            offer.setDescription(processedInput[3]);

                        if(!offer.getUbication().equals(processedInput[4]))
                            offer.setUbication(processedInput[4]);
                        if(!String.valueOf(offer.getSalary()).equals(processedInput[5]))
                            offer.setSalary(Integer.valueOf(processedInput[5]));

                        if(!offer.getContractType().equals(processedInput[6]))
                            offer.setContractType(processedInput[6]);
                        
                        if(!OfferDao.checkIfOfferUpdated(offer,myUser)){
                            OfferDao.updateOffer(offer);
                            output+=":C";
                        }else{
                            output+=":I:You already have one offer with this name";   
                        }
                    }else{
                        output+=":I:This offer doesn't exist";   
                    }
                }
            }else if(processedInput[0].equals(CL_ADD_OFFER)){
                output+=S_ADD_OFFER+":"; 
                if(processedInput.length > 1){
                    
                    
                    String[] labels = processedInput[6].split(",");
                    Offer offerToAdd = new Offer(BusinessmanDao.checkIfBusinessman(myUser),processedInput[1],processedInput[2],processedInput[3],Integer.valueOf(processedInput[4]),processedInput[5]); 
                       
                    if(!OfferDao.checkIfExistOffer(offerToAdd,myUser)){    
                        OfferDao.addOffer(offerToAdd);    

                        for(String label : labels){ 
                            LabelOfferDao.addLabelOffer(new LabelOffer(LabelDao.getLabelByName(label),offerToAdd));
                            for(Alert alert : AlertDao.getAlertsByLabel(LabelDao.getLabelByName(label))){
                                NotificationDao.addNotification(new Notification(alert,alert.getLabel(),offerToAdd,false));
                                if(!sharedColection.search(alert.getWorker().getUser().getUser())){
                                    thread.write("N:");
                                }
                            }
                        }

                        output+="C";
                    }else{
                        output+="I:You already have one offer with this name";   
                    }
                }
                
            }else if(processedInput[0].equals(CL_DELETE_OFFER)){
                output+=S_DELETE_OFFER+":"; 
                OfferDao.deleteOffer(OfferDao.getOfferDetails(Integer.valueOf(processedInput[1])));
                    
                output+="C";
            }else if(processedInput[0].equals(CL_ALL_LABELS)){
                output+=S_ALL_LABELS+":"; 
                 
                for(Label label : LabelDao.getAllLabels()){
                    output+=label.getName();
                    output+=":";
                } 
            }else if(processedInput[0].equals(CL_ADD_LABEL)){
                output+=S_ADD_LABEL+":";
                if(LabelDao.getLabelByName(processedInput[1]) == null){
                    LabelDao.addLabel(new Label(processedInput[1]));
                    output+="C";
                }else{
                    output+="I";
                }
            }else if(processedInput[0].equals(CL_USER_DETAILS)){
                output+=S_USER_DETAILS+":";
                if(processedInput.length <= 1){ 
                    output+=myUser.getDni()+":";
                    output+=myUser.getName()+":";
                    output+=myUser.getSurname()+":";
                    output+=myUser.getEmail()+":";
                    output+=myUser.getUser()+":";
                    output+=myUser.getPassword()+":";
                }else{
                    User user = UserDao.getUser(processedInput[1]); 
                    output+=user.getDni()+":";
                    output+=user.getName()+":";
                    output+=user.getSurname()+":";
                    output+=user.getEmail()+":";
                    output+=user.getUser()+":";
                    output+=user.getPassword()+":";
                
                }
                
            }else if(processedInput[0].equals(CL_MODIFY_USER)){
                output+=S_MODIFY_USER+":";
                User user = new User(processedInput[1],processedInput[2],processedInput[3],processedInput[4],processedInput[5],processedInput[6]);
                if(!myUser.equals(user)){
                    boolean follow = true;
                    if(!user.getDni().equals(myUser.getDni()) && UserDao.checkIfDniExits(user)){
                        output+="I:It already exist one user with this dni";
                        follow=false;
                    } 
                    
                    if(!user.getEmail().equals(myUser.getEmail()) && UserDao.checkIfeMailExits(user) && follow){
                        output+="I:It already exist one user with this email";
                        follow=false;
                    } 
                    
                    if(!user.getUser().equals(myUser.getUser()) && UserDao.checkIfUsernameExits(user)&& follow){
                        output+="I:It already exist one user with this username";
                        follow=false;
                    } 
                    
                    if(follow){
                        myUser.setDni(user.getDni());
                        myUser.setEmail(user.getDni());
                        myUser.setName(user.getName());
                        myUser.setSurname(user.getSurname());
                        myUser.setUser(user.getUser());
                        myUser.setPassword(user.getPassword());
                        UserDao.updateUser(myUser); 
                        output+="C";
                    }
                
                }else{
                    output+="I:You must modify at least one field";
                }
            }else if(processedInput[0].equals(CL_MY_ALERTS)){
                output+=S_MY_ALERTS+":"; 
                 
                for(Alert alert : AlertDao.getMyAlerts(myUser)){
                    output+=alert.getLabel().getName();
                    output+=":";
                } 
            }else if(processedInput[0].equals(CL_ADD_ALERT)){
                output+=S_ADD_ALERT+":"; 
                if(AlertDao.getAlert(myUser, LabelDao.getLabelByName(processedInput[1])) == null){
                    AlertDao.addAlert(new Alert(LabelDao.getLabelByName(processedInput[1]),WorkerDao.checkIfWorker(myUser)));
                    output+="C"; 
                }else{
                    output+="I:You already have one alert with this label"; 
                
                }
            }else if(processedInput[0].equals(CL_DELETE_ALERT)){
                output+=S_DELETE_ALERT+":"; 
                if(AlertDao.getAlert(myUser, LabelDao.getLabelByName(processedInput[1]))!= null){
                    AlertDao.deleteOffer(AlertDao.getAlert(myUser,LabelDao.getLabelByName(processedInput[1])));
                    output+="C";  
                }else{
                    output+="I:This alert don't exist"; 
                
                }
            }else if(processedInput[0].equals(CL_MY_NOTIFICATIONS)){
                output+=S_MY_NOTIFICATIONS+":"; 
                 
                for(Notification notification : NotificationDao.getMyNotifications(myUser)){
                    output+=notification.getId()+":";
                    output+=notification.getOffer().getName()+":";
                    output+=notification.getOffer().getId()+":";
                    output+=notification.getLabel().getName()+":";
                    notification.setNotified(true);
                    NotificationDao.updateNotification(notification);
                } 
            }else if(processedInput[0].equals(CL_DELETE_NOTIFICATION)){
                output+=S_DELETE_NOTIFICATION+":"; 
                
                NotificationDao.deleteNotification(NotificationDao.getNotificationById(Integer.valueOf(processedInput[1])));
                output+="C";
            }else if(processedInput[0].equals(CL_CHECK_IF_CANDIDATURE_IS_ABLE)){
                output+=S_CHECK_IF_CANDIDATURE_IS_ABLE+":"; 
                Offer offer = OfferDao.getOfferDetails(Integer.parseInt(processedInput[1]));
                
                Boolean ninguna = true; 
                ArrayList<Boolean> returnValue = new ArrayList();
                
                for(Label label : LabelOfferDao.getLabelsByOffer(offer)){
                    if(KnowledgeDao.getKnowledgeForThisLabel(label, myUser).size() > 0){
                        ninguna = false; 
                        returnValue.add(true);
                    }else{
                        returnValue.add(false);
                    }
                }
                
                if(areAllTrue(returnValue)){
                    output+="C:";     
                }else if(!ninguna){
                    output+="I:Some";  
                }else if(ninguna){
                    output+="I:Any";  
                }
                
            }else if(processedInput[0].equals(CL_ADD_CANDIDATURE)){
                output+=S_ADD_CANDIDATURE+":";  
                CandidatureDao.addCandidature(new Candidature(OfferDao.getOfferDetails(Integer.parseInt(processedInput[1])),WorkerDao.checkIfWorker(myUser)));
                output+="C";  
            }else if(processedInput[0].equals(CL_MY_CANDIDATURES)){
                output+=S_MY_CANDIDATURES+":";  
                for(Candidature candidature : CandidatureDao.getMyCandidatures(myUser,processedInput[1])){
                    output+=candidature.getId()+":";
                    output+=candidature.getOffer().getId()+":";
                    output+=candidature.getWorker().getUser().getId()+":";
                    output+=candidature.getOffer().getName()+":";
                    output+=candidature.getWorker().getUser().toString()+":";
                    output+=candidature.getState()+":";
                    output+="Labels,";
                    for(Label actualLabel: LabelOfferDao.getLabelsByOffer(candidature.getOffer())){
                        
                        output+=actualLabel.getName();
                        output+=",";
                    }
                    output+=":";  
                }
                  
            }else if(processedInput[0].equals(CL_CANDIDATURE_DETAILS)){
                output+=S_CANDIDATURE_DETAILS+":";     
                Candidature candidature =CandidatureDao.getCandidatureDetails(Integer.parseInt(processedInput[1]));
                output+=candidature.getId()+":";
                output+=candidature.getOffer().getId()+":";
                output+=candidature.getWorker().getUser().getId()+":";
                output+=candidature.getOffer().getName()+":";
                output+=candidature.getWorker().getUser().toString()+":";
                output+=candidature.getState()+":";
                output+="Labels,";
                for(Label actualLabel: LabelOfferDao.getLabelsByOffer(candidature.getOffer())){
                    
                    output+=actualLabel.getName();
                    output+=",";
                }
                output+=":"; 
                
            }else if(processedInput[0].equals(CL_CANDIDATURES_FOR_ONE_OFFER)){
                output+=S_CANDIDATURES_FOR_ONE_OFFER+":";
                for(Candidature candidature : CandidatureDao.getCandidaturesForOneOffer(OfferDao.getOfferDetails(Integer.parseInt(processedInput[1])),processedInput[2])){
                    output+=candidature.getId()+":";
                    output+=candidature.getOffer().getId()+":";
                    output+=candidature.getWorker().getUser().getId()+":";
                    output+=candidature.getOffer().getName()+":";
                    output+=candidature.getWorker().getUser().toString()+":";
                    output+=candidature.getState()+":";
                    output+="Labels,";
                    for(Label actualLabel: LabelOfferDao.getLabelsByOffer(candidature.getOffer())){

                        output+=actualLabel.getName();
                        output+=",";
                    }
                    output+=":";
                }
                
            }else if(processedInput[0].equals(CL_CHANGE_CANDIDATURE_STATE)){
                 
                output+=S_CHANGE_CANDIDATURE_STATE+":";
                Candidature candidature = CandidatureDao.getCandidatureDetails(Integer.parseInt(processedInput[1]));
                candidature.setState(processedInput[2]);
                CandidatureDao.updateCandidature(candidature);
                output+="C";
            }else if(processedInput[0].equals(CL_KNOWLEDGE_BY_LABEL)){
                
                output+=S_KNOWLEDGE_BY_LABEL+":";
                for(Knowledge knowledge  : LabelKnowledgeDao.getKnowledgeByLabel(LabelDao.getLabelByName(processedInput[2]),UserDao.getUserById(Integer.parseInt(processedInput[1])),processedInput[3])){
                    output+=knowledge.getId()+":";
                    output+=knowledge.getWorker().getUser().toString()+":";
                    output+=knowledge.getName()+":";
                    output+=knowledge.getPlace()+":";
                    output+=knowledge.getTitle()+":";
                    output+=knowledge.getType()+":";
                    output+=knowledge.getFechaInicio().toString()+":";
                    output+=knowledge.getFechaFin().toString()+":";
                    output+="Labels,";
                    for(Label actualLabel: LabelKnowledgeDao.getLabelsForThisKnowledge(knowledge)){

                        output+=actualLabel.getName();
                        output+=",";
                    }
                    output+=":";
                }
            }else if(processedInput[0].equals(CL_MY_WORK_EXPERIENCE)){ 
                output+=S_MY_WORK_EXPERIENCE+":";
                for(Knowledge knowledge  : KnowledgeDao.getMyWorkExperience(myUser)){
                    output+=knowledge.getId()+":";
                    output+=knowledge.getWorker().getUser().toString()+":";
                    output+=knowledge.getName()+":";
                    output+=knowledge.getPlace()+":";
                    output+=knowledge.getTitle()+":";
                    output+=knowledge.getType()+":";
                    output+=knowledge.getFechaInicio().toString()+":";
                    output+=knowledge.getFechaFin().toString()+":";
                    output+="Labels,";
                    for(Label actualLabel: LabelKnowledgeDao.getLabelsForThisKnowledge(knowledge)){

                        output+=actualLabel.getName();
                        output+=",";
                    }
                    output+=":";
                }
            }else if(processedInput[0].equals(CL_KNOWLEDGE_DETAILS)){ 
                output+=S_KNOWLEDGE_DETAILS+":";
                Knowledge knowledge = KnowledgeDao.getKnowledgeById(Integer.parseInt(processedInput[1]));
                output+=knowledge.getId()+":";
                output+=knowledge.getWorker().getUser().toString()+":";
                output+=knowledge.getName()+":";
                output+=knowledge.getPlace()+":";
                output+=knowledge.getTitle()+":";
                output+=knowledge.getType()+":";
                output+=knowledge.getFechaInicio().toString()+":";
                output+=knowledge.getFechaFin().toString()+":";
                output+="Labels,";
                for(Label actualLabel: LabelKnowledgeDao.getLabelsForThisKnowledge(knowledge)){

                    output+=actualLabel.getName();
                    output+=",";
                }
                output+=":";
                
            }else if(processedInput[0].equals(CL_ADD_KNOWLEDGE)){ 
                
                output+=S_ADD_KNOWLEDGE+":";
                
                         
                if(processedInput.length > 2){
                    
                    
                    String[] labels = processedInput[7].split(",");
                    Knowledge knowledge = new Knowledge(WorkerDao.checkIfWorker(myUser),processedInput[1],processedInput[2],processedInput[3],processedInput[4],Date.valueOf(processedInput[5]),Date.valueOf(processedInput[6]));
               
                    if(!KnowledgeDao.checkIfKnowledgeExist(knowledge,myUser)){    
                        KnowledgeDao.addKnowledge(knowledge);

                        for(String label : labels){ 
                            LabelKnowledgeDao.addLabelKnowledge(new LabelKnowledge(knowledge,LabelDao.getLabelByName(label))); 
                        }

                        output+="C";
                    }else{
                        output+="I:You already have one offer with this name";   
                    }
                }else{
                    if(processedInput[1].equals("WE"))
                        output+="WE:";
                    else if(processedInput[1].equals("Q"))
                        output+="Q:"; 
                }
                
            }else if(processedInput[0].equals(CL_DELETE_KNOWLEDGE)){ 
                output+=S_DELETE_KNOWLEDGE+":";
                Knowledge knowledge = KnowledgeDao.getKnowledgeById(Integer.parseInt(processedInput[1]));
                
                List<Label> labels = LabelKnowledgeDao.getLabelsForThisKnowledge(knowledge);
                
                for(Label label: labels){
                    if(CandidatureDao.getMyCandidaturesWithLabel(myUser, label)){
                        if(LabelKnowledgeDao.getKnowledgeByLabel(label, myUser).size() > 1){
                            KnowledgeDao.deleteKnowledge(knowledge);
                            output+="C";
                        }else{
                            output+="I";
                        }
                    }else{
                        KnowledgeDao.deleteKnowledge(knowledge);
                        output+="C";
                    }
                }
            }else if(processedInput[0].equals(CL_EXIT)){
                sharedColection.remove(myUser.getUser());
                output+=S_EXIT;
            }
        } 
        
        return output;
    }
    
    private String processListOfOffers(List<Offer> offers){
        String output = "";
        for(Offer actualOffer: offers){
            output+=":";
            output+=actualOffer.getId()+":";
            output+=actualOffer.getBusinessman().getUser().toString()+":"; 
            output+=actualOffer.getName()+":";
            output+=actualOffer.getDescription()+":";
            output+=actualOffer.getUbication()+":";  
            output+=actualOffer.getSalary()+":";
            output+=actualOffer.getContractType()+":";
            output+="Labels,";
            if(LabelOfferDao.getLabelsByOffer(actualOffer).size() > 0){

                for(Label actualLabel: LabelOfferDao.getLabelsByOffer(actualOffer)){
                    Label actualLabelChanged = actualLabel;
                    output+=actualLabelChanged.getName();
                    output+=",";
                }

            }
        }
        return output;
    
    }
 
    public static boolean areAllTrue(ArrayList<Boolean> array){
        
        for(boolean b : array)
            if(!b) 
                return false;
        
        
        return true;
    }
}
