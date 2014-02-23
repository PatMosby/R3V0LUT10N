







package swp.bibjsf.presentation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import swp.bibcommon.Borrower;
import swp.bibcommon.History;
//import swp.bibcommon.History;
import swp.bibcommon.Reader;
import swp.bibjsf.businesslogic.ReaderHandler;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.persistence.Data;

import org.apache.log4j.Logger;


@ManagedBean
@SessionScoped
public class ShowUserProfil {
 
 /**
  * Unige ID for serialization.
  */
 private static final long serialVersionUID = -494454216495818388L;
 private Reader reader;
 final Logger logger = Logger.getLogger(ShowUserProfil.class);
 private Data data = new Data();
 private List<Borrower> borrowerList = new ArrayList<>();
 
 public ShowUserProfil() throws DataSourceException, NamingException{
  super();
  data = new Data();
 }
 
 public String getUser() {
  FacesContext context = FacesContext.getCurrentInstance();
  HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
  return request.getRemoteUser();
 }
 
 
 public Reader getReaderByUsername(){  
  try {
   ReaderHandler rh = ReaderHandler.getInstance();
   String name = getUser();
   reader = rh.getReaderByUsername(name);
   
  } catch (DataSourceException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return reader;
  
 }
 
 
 public List<Borrower> getBorrowerForUser(){
  Reader r_1= getReaderByUsername();
  logger.debug("REACHED---GET_Borrower");
  int z= r_1.getId();
  
  logger.debug("bORROWERforuser--REACHED!!!!!!" + z);
  String readerID = "3";
  readerID = String.valueOf(z);
  logger.debug("bORROWERforuser--REACHED!!!!!!" + readerID);
  return data.getBorrowerForUser(readerID);
  //return data.getBorrower();
 }
 
 
 public List<History> getHistoryList(){
  Reader r_1= getReaderByUsername();
  logger.debug("REACHED---GET_Borrower");
  int z= r_1.getId();
  
  logger.debug("bORROWERforuser--REACHED!!!!!!" + z);
  String readerID = "3";
  readerID = String.valueOf(z);
  logger.debug("bORROWERforuser--REACHED!!!!!!" + readerID);
  return data.getHistoryForUser(readerID);
  //return data.getBorrower();
 }
 
     
     
    public void send(Borrower borrower)throws DataSourceException,
     SQLException{
    	
    	

     
     data.getBorrowList(borrower);
    }
    
    public void sending(int i){
    	
    	logger.debug("SAAAAAAAVVVVVVVVINNNNNNNNNNN---------"+ i);
    	
    	switch(i){
    	
    	case 1: data.notSaveHistory();
    		break;
    	case 2:
    		break;
    	case 3:
    		break;
    	case 4:
    		break;
        default :
    	}
    }
    
      public void notSaving(){
    	
    	
      }

     public void showing(){
	
	
      }

      public void notShowing(){
	
	
      }



    
    
    
//    public void sendList(){
//     logger.debug("SEND----LIST---REACHED!!!!!");
//     System.out.println("SEND----LIST---REACHED!!!!!");
//     
//     if(borrowerList!= null)
//     data.getBorrowList();
//     
//     
//    }

}