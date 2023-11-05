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
import dao.ProfileDao;
import dao.ProfileLabelDao;
import dao.UserDao;  
import dao.WorkerDao;  
import static dao.WorkerDao.checkIfWorker;
import entities.Alert;
import entities.Businessman;
import entities.BusinessmanNotification;
import entities.Candidature;
import entities.Knowledge;
import entities.Label;
import entities.LabelKnowledge;
import entities.LabelOffer;
import entities.Notification;
import entities.Offer;
import entities.Profile;
import entities.ProfileLabel;
import entities.User;
import entities.Worker;
import entities.WorkerNotification;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.Arrays;
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
    public User myUser; 
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
     
    public String processInput(String input) { 
        this.output = "";
        String[] processedInput = input.split(":");  
        
        
        if (state == LOGIN) { 
            if(processedInput[0].equals(CL_LOGIN)){  
                output+=S_LOGIN+":";
                if(UserDao.checkUserPassword(processedInput[1], processedInput[2])){
                    if(!sharedColection.search(processedInput[1])){
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
                        output+= myUser.toString()+":";

                        sharedColection.add(myUser.getUser(), thread); 
                        if(NotificationDao.getMyUnwatchedNotifications(myUser)){
                            output+="True";
                        }else{
                            output+="False";
                        }
                        state = MENU;
                    
                    }else{
                        output+="I:This user is logged in the aplicattion";
                    }
                }else{ 
                    output+="I:User or password incorrect";
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
                boolean follow = true;
                
                User user = new User(processedInput[1],processedInput[2],processedInput[3],processedInput[4],processedInput[5],processedInput[6]);
                 
                if(UserDao.checkIfDniExits(user.getDni())){
                    output+="I:It already exist one user with this dni";
                    follow=false;
                } 

                if(UserDao.checkIfeMailExits(user.getEmail()) && follow){
                    output+="I:It already exist one user with this email";
                    follow=false;
                } 

                if(UserDao.checkIfUsernameExits(user.getUser())&& follow){
                    output+="I:It already exist one user with this username";
                    follow=false;
                } 

                if(follow){
                    
                    UserDao.addUser(user);
                    
                    if(processedInput[7].equals("BusinessMan")){
                        BusinessmanDao.addBusinessman(new Businessman(user));
                    }else if(processedInput[7].equals("Worker")){
                        WorkerDao.addWorker(new Worker(user)); 
                    }
                    
                    output+="C";
                    state = LOGIN;
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
                List<Offer> offers = OfferDao.getAllOffers(myUser);
                List<Offer> offersToDelete = new ArrayList();
                for(Offer offer:offers){
                    Boolean follow = true;
                    Boolean remove = false;
                    if(!processedInput[2].equals("notNameFilter")){
                        if(!offer.getName().toLowerCase().contains(processedInput[2].toLowerCase())){
                            follow = false;
                            remove = true;
                        }
                    }
                    
                    if(Integer.parseInt(processedInput[1]) != 0 && follow){
                        remove = true;
                        Profile profile = ProfileDao.getProfileById(Integer.parseInt(processedInput[1]));
                        List<Label> offerLabels = LabelOfferDao.getLabelsByOffer(offer);
                        for(Label profileLabel:ProfileLabelDao.getLabelsForThisProfile(profile)){
                            if(follow){
                                for(Label offerLabel : offerLabels){
                                    if(follow){
                                        if(profileLabel.getName().equals(offerLabel.getName())){
                                            follow = false;
                                            remove = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    if(remove){
                        offersToDelete.add(offer);
                    }
                }
                
                for(Offer offer:offersToDelete){
                    offers.remove(offer);
                }
                output+=this.processListOfOffers(offers);   
                
            }else if(processedInput[0].equals(CL_MY_OFFERS)){
                if(processedInput.length <= 1){

                    //See all my offers
                    output+=S_MY_OFFERS;
                    output+=this.processListOfOffers(OfferDao.getMyOffers(myUser));
                }
            }else if(processedInput[0].equals(CL_OFFER_DETAILS)){
                //See the details of an offer
                output+=S_OFFER_DETAILS+":"; 
                Offer offer = OfferDao.getOfferDetails(Integer.valueOf(processedInput[1]));
                
                if(BusinessmanDao.checkIfBusinessman(myUser) != null){
                    if(offer.getBusinessman().getUser().toString().equals(myUser.toString())){
                        output+="1";
                    }else{
                        output+="2";
                    }
                }else if(WorkerDao.checkIfWorker(myUser) != null){
                    output+="3";
                }
                
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
                        
                        Offer offerCreated = OfferDao.addOffer(offerToAdd);   
                        
                        for(String labelInfo : labels){
                            String labelName = labelInfo.split("-")[0];
                            String labelObligatority = labelInfo.split("-")[1]; 
                            boolean obligatority;
                            
                            if(labelObligatority.equals("1"))
                                obligatority = true;
                            else
                                obligatority = false;
                            
                            LabelOfferDao.addLabelOffer(new LabelOffer(LabelDao.getLabelByName(labelName),offerToAdd,obligatority));
                            
                            List<Profile> profiles = ProfileLabelDao.getProfileForThisLabel(labelName);
                            for(Profile profile : profiles){
                                Alert alert = AlertDao.getAlertForThisProfile(profile);
                                if(alert != null){
                                       Notification notification = NotificationDao.addNotification(new Notification());
                                       NotificationDao.addNotification(new WorkerNotification(alert,notification,offerCreated)); 
                                }
                            }
                            /*for(Alert alert : AlertDao.getAlertsByLabel(LabelDao.getLabelByName(label))){
                                NotificationDao.addNotification(new Notification(alert,alert.getLabel(),offerToAdd,false));
                                if(!sharedColection.search(alert.getProfile().getWorker().getUser().getUser())){
                                    //thread.sendUDPMessage(1);
                                }
                            }*/
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
                
            }else if(processedInput[0].equals(CL_DELETE_USER)){
                output+=S_DELETE_USER+":";
                sharedColection.remove(myUser.getUser());
                UserDao.deleteUser(myUser);
                thread.setAnotherTime(false);
                output+="C";
            }else if(processedInput[0].equals(CL_MODIFY_USER)){
                output+=S_MODIFY_USER+":";
                User user = new User(processedInput[1],processedInput[2],processedInput[3],processedInput[4],processedInput[5],processedInput[6]);
                if(!myUser.equals(user)){
                    boolean follow = true;
                    if(!user.getDni().equals(myUser.getDni())){
                        if(UserDao.checkIfDniExits(user.getDni())){
                            output+="I:It already exist one user with this dni";
                            follow=false;
                        }
                        
                    } 
                    
                    if(!user.getEmail().equals(myUser.getEmail())){
                        if(UserDao.checkIfeMailExits(user.getEmail())){
                            output+="I:It already exist one user with this email";
                            follow=false;
                        }
                        
                    }  
                    
                    if(!user.getUser().equals(myUser.getUser())){
                        if(UserDao.checkIfUsernameExits(user.getUser())){
                            output+="I:It already exist one user with this username";
                            follow=false;
                        } 
                    }   
                    
                    if(follow){
                        //Delete it just in case the user has change the userName
                        sharedColection.remove(myUser.getUser());
                        
                        
                        myUser.setDni(user.getDni());
                        myUser.setEmail(user.getEmail());
                        myUser.setName(user.getName());
                        myUser.setSurname(user.getSurname());
                        myUser.setUser(user.getUser());
                        myUser.setPassword(user.getPassword());
                        UserDao.updateUser(myUser); 
                        
                        sharedColection.add(myUser.getUser(), thread);
                        output+="C";
                    }
                
                }else{
                    output+="I:You must modify at least one field";
                }
            }else if(processedInput[0].equals(CL_MY_ALERTS)){
                output+=S_MY_ALERTS+":"; 
                 
                for(Alert alert : AlertDao.getMyAlerts(myUser)){
                    output+=alert.getId();
                    output+=":";
                    output+=alert.getProfile().getId(); 
                    output+=":";
                    output+=alert.getProfile().getName();
                    output+=":";
                }
            }else if(processedInput[0].equals(CL_ADD_ALERT)){
                output+=S_ADD_ALERT+":"; 
                
                Profile profile = ProfileDao.getProfileById(Integer.parseInt(processedInput[1]));
                if(AlertDao.getAlertForThisProfile(profile) == null){
                    AlertDao.addAlert(new Alert(profile));
                    output+="C"; 
                }else{
                    output+="I:You already have one alert with this profile"; 
                
                }
            }else if(processedInput[0].equals(CL_DELETE_ALERT)){
                output+=S_DELETE_ALERT+":"; 
                Alert alert = AlertDao.getAlertById(Integer.parseInt(processedInput[1]));
                if(alert != null){
                    AlertDao.deleteAlert(alert);
                    output+="C";  
                }else{
                    output+="I:This alert don't exist";  
                }
            }else if(processedInput[0].equals(CL_MY_NOTIFICATIONS)){
                output+=S_MY_NOTIFICATIONS+":"; 
                if(checkIfBusinessman(myUser) != null){
                    for(BusinessmanNotification bussinesmanNotification : NotificationDao.getMyBusinessmanNotifications(myUser)){
                        Notification notification = bussinesmanNotification.getNotification(); 
                        output+=notification.getId()+":";
                        output+=bussinesmanNotification.getId()+":";
                        output+=bussinesmanNotification.getCandidature().getId()+":";
                        output+=bussinesmanNotification.getCandidature().getOffer().getName()+":"; 
                        bussinesmanNotification.getNotification().setNotified(true);
                        NotificationDao.updateNotification(bussinesmanNotification.getNotification());
                    }
                }else if(checkIfWorker(myUser) != null){
                    for(WorkerNotification workerNotification : NotificationDao.getMyWorkerNotifications(myUser)){
                        Notification notification = workerNotification.getNotification(); 
                        output+=notification.getId()+":";
                        output+=workerNotification.getId()+":";
                        output+=workerNotification.getOffer().getId()+":"; 
                        output+=workerNotification.getAlert().getProfile().getName()+":"; 
                        workerNotification.getNotification().setNotified(true);
                        NotificationDao.updateNotification(workerNotification.getNotification());
                    }
                }
            }else if(processedInput[0].equals(CL_DELETE_NOTIFICATION)){
                output+=S_DELETE_NOTIFICATION+":"; 
                Notification notification = NotificationDao.getNotificationById(Integer.parseInt(processedInput[1]));
                NotificationDao.deleteNotification(notification); 
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
                Offer offer = OfferDao.getOfferDetails(Integer.parseInt(processedInput[1]));
                if(!CandidatureDao.checkIfCandidatureExits(myUser,offer)){
                    Candidature candidature = CandidatureDao.addCandidature(new Candidature(offer,WorkerDao.checkIfWorker(myUser)));
                    Notification notification = NotificationDao.addNotification(new Notification());
                    NotificationDao.addNotification(new BusinessmanNotification(candidature,notification)); 
                    output+="C";
                }else{
                    output+="I";
                }
                  
            }else if(processedInput[0].equals(CL_DELETE_CANDIDATURE)){
                output+=S_DELETE_CANDIDATURE+":";
                CandidatureDao.deleteCandidature(CandidatureDao.getCandidatureDetails(Integer.parseInt(processedInput[1])));
                
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
            }else if(processedInput[0].equals(CL_MY_QUALIFICATION)){ 
                output+=S_MY_QUALIFICATION+":";
                for(Knowledge knowledge  : KnowledgeDao.getMyQualification(myUser)){
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
            }else if(processedInput[0].equals(CL_MY_PROFILES)){
                output+=S_MY_PROFILES+":";
                for(Profile profile : ProfileDao.getMyProfiles(myUser)){ 
                    output+=profile.getId()+":"; 
                    output+=profile.getName()+":"; 
                    output+="Labels,";
                    for(Label label:ProfileLabelDao.getLabelsForThisProfile(profile)){
                        output+=label.getName();
                        output+=","; 
                    }
                    output+=":";  
                }
            }else if(processedInput[0].equals(CL_MY_PROFILES_WITHOUT_ALERTS)){
                output+=S_MY_PROFILES_WITHOUT_ALERTS+":";
                for(Profile profile : ProfileDao.getMyProfiles(myUser)){
                    if(AlertDao.getAlertForThisProfile(profile) ==  null){
                        output+=profile.getId()+":"; 
                        output+=profile.getName()+":"; 
                        output+="Labels,";
                        for(Label label:ProfileLabelDao.getLabelsForThisProfile(profile)){
                            output+=label.getName();
                            output+=","; 
                        }
                        output+=":";
                    } 
                }
            }else if(processedInput[0].equals(CL_ADD_PROFILE)){
                output+=S_ADD_PROFILE+":";
                if(processedInput.length > 1){
                    //Profile profile = new Profile(WorkerDao.checkIfWorker(myUser),processedInput[1]);
                    if(ProfileDao.getMyProfileByName(processedInput[1],myUser) == null){
                        
                       Profile profile = ProfileDao.addProfile(new Profile(WorkerDao.checkIfWorker(myUser),processedInput[1]));
                        String[] labels = processedInput[2].split(",");
                        for(String label:labels){
                            ProfileLabelDao.addProfileLabel(new ProfileLabel(LabelDao.getLabelByName(label),profile));
                        }
                        output+="C";
                    }else{
                        output+="I:You already have a profile with this label";
                    }
                }
            }else if(processedInput[0].equals(CL_MODIFY_PROFILE)){   
                output+=S_MODIFY_PROFILE+":"; 
                if(processedInput.length <= 2){
                    Profile profile = ProfileDao.getProfileById(Integer.parseInt(processedInput[1]));
                    output+=profile.getId()+":";
                    output+=profile.getName()+":";
                    output+="Labels,";
                    for(Label label:ProfileLabelDao.getLabelsForThisProfile(profile)){
                        output+=label.getName();
                        output+=",";
                    }
                }else{
                    Profile profile = ProfileDao.getProfileById(Integer.parseInt(processedInput[1]));  
                    
                    String[] labels = processedInput[3].split(",");  
                    List<String> newLabelsList = new ArrayList(); 
                    for(String label:labels){
                        newLabelsList.add(label.trim()); 
                    }
                    
                    List<String> actualLabelsList = new ArrayList();
                    for(Label label:ProfileLabelDao.getLabelsForThisProfile(profile)){
                        actualLabelsList.add(label.getName().trim()); 
                    }
                    List<String> labelsToAdd = compareStringArrays(newLabelsList,actualLabelsList);  
                    List<String> labelsToDelete = compareStringArrays(actualLabelsList,newLabelsList); 
                    boolean follow = true;    
                    
                    
                    if(!labelsToAdd.isEmpty() || !labelsToDelete.isEmpty() || !profile.getName().equals(processedInput[2])){
                        if(!profile.getName().equals(processedInput[2])){
                            if(ProfileDao.getMyProfileByName(processedInput[2], myUser) == null){
                                profile.setName(processedInput[2]);
                                ProfileDao.updateProfile(profile);   
                            }else{
                                output+="I:You already have one profile with this name";   
                                follow = false;
                            }
                        }
                        
                        if(!labelsToAdd.isEmpty() && follow){
                            for(String label:labelsToAdd){
                                ProfileLabelDao.addProfileLabel(new ProfileLabel(LabelDao.getLabelByName(label),profile));
                            }
                        }  
                        
                        if(!labelsToDelete.isEmpty() && follow){
                            for(String label:labelsToDelete){
                                ProfileLabelDao.deleteProfileLabel(ProfileLabelDao.getProfileLabel(profile.getId(), label));
                            }
                        }
                        
                        if(follow)
                            output+="C";
                    }else{
                        output+="I:You haven't change anything";   
                    }
                }
            }else if(processedInput[0].equals(CL_DELETE_PROFILE)){
                output+=S_DELETE_PROFILE+":";
                ProfileDao.deleteProfile(ProfileDao.getProfileById(Integer.parseInt(processedInput[1])));
                output+="C";
            }else if(processedInput[0].equals(CL_STOP_SERVER)){
                output+=S_STOP_SERVER+":";
                try {
                    ProcessBuilder pb = new ProcessBuilder("C:\\xampp\\xampp_stop.exe");
                    Process process = pb.start();
                    process.waitFor(); 

                    int exitCode = process.exitValue();
                    if (exitCode == 0) {
                        System.out.println("Xampp stopped correctly");
                        output+="C";
                    } else {
                        System.err.println("Error stopping xampp");
                        output+="I";
                    }
                } catch (IOException e) {
                    System.err.println("Error starting xampp: " + e.getMessage());
                    output+="I";
                } catch (InterruptedException e) {
                    System.err.println("Error waiting for xampp to start: " + e.getMessage());
                    output+="I";
                } 
                thread.setAnotherTime(false);
                Server.setFollow(false);
            }else if(processedInput[0].equals(CL_EXIT)){
                output+=S_EXIT+":C";
                sharedColection.remove(myUser.getUser());
                thread.setAnotherTime(false);
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
    
    public static List<String> compareStringArrays(List<String> array1,List<String> array2){
        List<String> differences = new ArrayList(); 
        
        for (String element : array1) { 
            if (!array2.contains(element)) {
                differences.add(element);
            }
        } 
        return differences;
    }
}
