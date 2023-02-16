package server;


    
import dao.AlertDao;
import dao.BusinessmanDao; 
import static dao.BusinessmanDao.checkIfBusinessman;
import dao.KnowledgeDao;
import dao.LabelDao;
import dao.LabelOfferDao;
import dao.NotificationDao;
import dao.OfferDao;
import dao.UserDao; 
import dao.WorkerDao;  
import static dao.WorkerDao.checkIfWorker;
import entities.Alert;
import entities.Businessman;
import entities.Label;
import entities.LabelOffer;
import entities.Notification;
import entities.Offer;
import entities.User;
import entities.Worker;
import java.util.ArrayList; 
import java.util.List; 
import server.Server.ServerThread;  
import static server.States.*;
 

enum States
{
    LOGIN , SINGUP , MENU
}

public class Protocol {
  
    private States state = LOGIN;
    public static User myUser; 
    private String output;
    
    private ServerThread hilo; 
 
    public States getState() {
        return state;
    }

    Protocol(ServerThread hilo) {
        this.hilo = hilo; 
    }
    
    /*
    
        vector[]={"mensaje","mensaje"}
     
    
        S1-Login-ok
    
    */
    public String processInput(String input) {
        this.output = "";
        String[] processedInput = input.split(":"); 
        
        
        if (state == LOGIN) {
            if(processedInput[0].equals("L")){ 
                output+="L:";
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
                    state = MENU;
                }else{ 
                    output+="I";
                }
            
            }else if(processedInput[0].equals("R")){  
                state = SINGUP;
                output+="R";
            }  
        
        } else if(state == SINGUP) {
    
            if(processedInput[0].equals("R")){  
                output+="R:";
                
                
                
                 
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
            }else if(processedInput[0].equals("L")){
                output+="L:";
                state = LOGIN;
            } 
        }else if(state == MENU){
            if(processedInput[0].equals("AllO")){ 
                //See all offers
                output+="O";
                output+=this.processListOfOffers(OfferDao.getAllOffers(myUser)); 
                
            }else if(processedInput[0].equals("MyO")){
                if(processedInput.length <= 1){

                    //See all my offers
                    output+="MyO";
                    output+=this.processListOfOffers(OfferDao.getMyOffers(myUser));
                }else{
                    
                }
            }else if(processedInput[0].equals("ODet")){
                //See the details of an offer
                output+="ODet"; 
                Offer offer = OfferDao.getOfferDetails(Integer.valueOf(processedInput[1]));
                List offerArrayList = new ArrayList();
                offerArrayList.add(offer);
                output+=this.processListOfOffers(offerArrayList); 
                
            }else if(processedInput[0].equals("ModO")){
                //Modify an existing offer
                output+="ModO"; 
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
            }else if(processedInput[0].equals("AddO")){
                output+="AddO";
                output+=":"; 
                if(processedInput.length > 1){
                    
                    
                    String[] labels = processedInput[6].split(",");
                    Offer offerToAdd = new Offer(BusinessmanDao.checkIfBusinessman(myUser),processedInput[1],processedInput[2],processedInput[3],Integer.valueOf(processedInput[4]),processedInput[5]); 
                       
                    if(!OfferDao.checkIfExistOffer(offerToAdd,myUser)){    
                        OfferDao.addOffer(offerToAdd);    

                        for(String label : labels){ 
                            LabelOfferDao.addLabelOffer(new LabelOffer(LabelDao.getLabelByName(label),offerToAdd));
                            for(Alert alert : AlertDao.getAlertsByLabel(LabelDao.getLabelByName(label))){
                                NotificationDao.addNotification(new Notification(alert,alert.getLabel(),offerToAdd));
                            }
                        }

                        output+="C";
                    }else{
                        output+="I:You already have one offer with this name";   
                    }
                }
                
            }else if(processedInput[0].equals("DelO")){
                output+="DelO";
                output+=":"; 
                OfferDao.deleteOffer(OfferDao.getOfferDetails(Integer.valueOf(processedInput[1])));
                    
                output+="C";
            }else if(processedInput[0].equals("L")){
                output+="L:"; 
                 
                for(Label label : LabelDao.getAllLabels()){
                    output+=label.getName();
                    output+=":";
                } 
            }else if(processedInput[0].equals("UDet")){
                output+="UDet:";
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
                
            }else if(processedInput[0].equals("ModU")){
                output+="ModU:";
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
            }else if(processedInput[0].equals("MyA")){
                output+="MyA:"; 
                 
                for(Alert alert : AlertDao.getMyAlerts(myUser)){
                    output+=alert.getLabel().getName();
                    output+=":";
                } 
            }else if(processedInput[0].equals("AddA")){
                output+="AddA:"; 
                if(AlertDao.getAlert(myUser, LabelDao.getLabelByName(processedInput[1])) == null){
                    AlertDao.addAlert(new Alert(LabelDao.getLabelByName(processedInput[1]),WorkerDao.checkIfWorker(myUser)));
                    output+="C"; 
                }else{
                    output+="I:You already have one alert with this label:"; 
                
                }
            }else if(processedInput[0].equals("DelA")){
                output+="DelA:"; 
                if(AlertDao.getAlert(myUser, LabelDao.getLabelByName(processedInput[1]))!= null){
                    AlertDao.deleteOffer(AlertDao.getAlert(myUser,LabelDao.getLabelByName(processedInput[1])));
                    output+="C";  
                }else{
                    output+="I:This alert don't exist:"; 
                
                }
            }else if(processedInput[0].equals("MyN")){
                output+="MyN:"; 
                 
                for(Notification notification : NotificationDao.getMyNotifications(myUser)){
                    output+=notification.getId()+":";
                    output+=notification.getOffer().getName()+":";
                    output+=notification.getOffer().getId()+":";
                    output+=notification.getLabel().getName()+":";
                } 
            }else if(processedInput[0].equals("DelN")){
                output+="DelN:"; 
                
                NotificationDao.deleteNotification(NotificationDao.getNotificationById(Integer.valueOf(processedInput[1])));
                output+="C";
            }else if(processedInput[0].equals("DelN")){
                output+="DelN:"; 
                
                NotificationDao.deleteNotification(NotificationDao.getNotificationById(Integer.valueOf(processedInput[1])));
                output+="C";
            }else if(processedInput[0].equals("CheckC")){
                output+="CheckC:"; 
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
