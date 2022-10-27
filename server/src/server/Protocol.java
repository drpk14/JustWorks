package server;


    
import dao.BusinessmanDao;
import dao.UserDao; 
import dao.WorkerDao;
import entities.Businessman;
import entities.User;
import entities.Worker;
import server.Server.ServerThread;  
import static server.States.*;
 

enum States
{
    LOGIN , SINGUP
}

public class Protocol {
  
    private States state = LOGIN;
     
    
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
                    output+="C";
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
        } 
        
        return output;
    }
}
