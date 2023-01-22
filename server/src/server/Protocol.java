package server;


    
import dao.BusinessmanDao; 
import static dao.BusinessmanDao.checkIfBusinessman;
import dao.OfertDao;
import dao.UserDao; 
import dao.WorkerDao; 
import static dao.WorkerDao.checkIfWorker;
import entities.Businessman;
import entities.Ofert;
import entities.User;
import entities.Worker;
import server.Server.ServerThread;  
import static server.States.*;
 

enum States
{
    LOGIN , SINGUP , MENU
}

public class Protocol {
  
    private States state = LOGIN;
    public static User myUser; 
    
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
        
        String output = "";
        String[] processedInput = input.split(":"); 
        
        
        if (state == LOGIN) {
            if(processedInput[0].equals("L")){ 
                output+="L:";
                if(UserDao.checkUserPassword(processedInput[1], processedInput[2])){ 
                    myUser = UserDao.getUser(processedInput[1]);
                    output+="C:";
                    if(checkIfBusinessman(myUser) != null){
                        output+="B";
                    }else if(checkIfWorker(myUser)){
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
                if(!UserDao.checkUserIsIn(processedInput[5])){
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
                //See all oferts
                output+="O";
                for(Ofert actualOfert: OfertDao.getAllOferts(myUser)){
                    output+=":";
                    output+=actualOfert.getId()+":";
                    output+=actualOfert.getBusinessman().getUser().toString()+":"; 
                    output+=actualOfert.getName()+":";
                    output+=actualOfert.getDescription()+":";
                    output+=actualOfert.getUbication()+":";  
                    output+=actualOfert.getSalary()+":";
                    output+=actualOfert.getContractType()+"";
                }
            }else if(processedInput[0].equals("MyO")){
                //See all my oferts
                output+="MyO";
                for(Ofert actualOfert: OfertDao.getMyOferts(myUser)){
                    output+=":";
                    output+=actualOfert.getId()+":";
                    output+=actualOfert.getBusinessman().getUser().toString()+":"; 
                    output+=actualOfert.getName()+":";
                    output+=actualOfert.getDescription()+":";
                    output+=actualOfert.getUbication()+":";  
                    output+=actualOfert.getSalary()+":";
                    output+=actualOfert.getContractType()+"";
                }
            }else if(processedInput[0].equals("ODet")){
                //See the details of an ofert
                output+="ODet";
                output+=":"; 
                Ofert ofert = OfertDao.getOfertDetails(Integer.valueOf(processedInput[1]));
                output+=ofert.getId()+":";
                output+=ofert.getBusinessman().getUser().toString()+":"; 
                output+=ofert.getName()+":";
                output+=ofert.getDescription()+":";
                output+=ofert.getUbication()+":";  
                output+=ofert.getSalary()+":";
                output+=ofert.getContractType()+""; 
            }else if(processedInput[0].equals("ModO")){
                //Modify an existing ofert
                output+="ModO";
                output+=":"; 
                if(processedInput.length <= 2){
                    //Get the oferts details  
                    Ofert ofert = OfertDao.getOfertDetails(Integer.valueOf(processedInput[1]));
                    output+=ofert.getId()+":";
                    output+=ofert.getBusinessman().getUser().toString()+":"; 
                    output+=ofert.getName()+":";
                    output+=ofert.getDescription()+":";
                    output+=ofert.getUbication()+":";  
                    output+=ofert.getSalary()+":";
                    output+=ofert.getContractType()+"";
                }else{
                    //Modify the ofert
                    Ofert ofert = OfertDao.getOfertDetails(Integer.valueOf(processedInput[1]));
                    if(!ofert.getName().equals(processedInput[2]))
                        ofert.setName(processedInput[2]);
                    
                    if(!ofert.getDescription().equals(processedInput[3]))
                        ofert.setDescription(processedInput[3]);
                    
                    if(!ofert.getUbication().equals(processedInput[4]))
                        ofert.setUbication(processedInput[4]);
                    if(!String.valueOf(ofert.getSalary()).equals(processedInput[5]))
                        ofert.setSalary(Integer.valueOf(processedInput[5]));
                    
                    if(!ofert.getContractType().equals(processedInput[6]))
                        ofert.setContractType(processedInput[6]);
                    
                    OfertDao.updateOfert(ofert);
                    output+="C";
                }
            }else if(processedInput[0].equals("AddO")){
                output+="AddO";
                output+=":"; 
                if(processedInput.length > 1){
                        OfertDao.addOfert(new Ofert(BusinessmanDao.checkIfBusinessman(myUser),processedInput[1],processedInput[2],processedInput[3],Integer.valueOf(processedInput[4]),processedInput[5]));
                        output+="C";
                }
            }else if(processedInput[0].equals("DelO")){
                output+="DelO";
                output+=":"; 
                OfertDao.deleteOfert(OfertDao.getOfertDetails(Integer.valueOf(processedInput[1])));
                output+="C";
            }
        } 
        
        return output;
    }
}
