package swp.bibjsf.businesslogic;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.DateFormat;

import java.lang.Object;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import swp.bibjsf.persistence.Data;
import swp.bibcommon.Borrower;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import swp.bibjsf.exception.DataSourceException;

/**
 * 
 * @author Dellert
 *
 */

@ManagedBean(name = "handler")
@SessionScoped
public class BorrowListHandler {
	Data data;
	final Logger logger = Logger.getLogger(BorrowListHandler.class);
	private boolean isextend=false;
	
	public BorrowListHandler()throws DataSourceException, NamingException{
	 data = new Data();
	  final Logger logger = Logger.getLogger(BorrowListHandler.class);
	}
	
	

	public List<Borrower> getBorrower(){
		logger.debug("REACHED---GET_Borrower");
		isextend = false;
		return data.getBorrower();
		
	}
	
	public List<Borrower> getBorrowerExtend(){
		logger.debug("REACHED---GET_BorrowerExtend");
		isextend = true;
		return data.getBorrowerExtend();
		
	}
	
	public List<Borrower> getBorrowerFromUser(){
		
		return data.getBorrowerFromUser();
	}
	
	public void onCellEdit(CellEditEvent event){
	   logger.debug("REACHED-----ON_CELL_EDIT!!!!");
	   logger.debug(event.getColumn().getHeaderText());
	   
	   Object newValue = event.getNewValue();
		 
	   String newData = newValue.toString();
	   newData = newData.substring(0, newData.length()-1);
	   logger.debug("OUT-PUT:::: "+ newData);
	   
    if(!isextend){
	   if (event.getColumn().getHeaderText().equals("Rückgabedatum")) {
		   if(testInput(true, str_sy.trim())){
			try{
			 data.insertDate(str_sy.trim(),event.getRowIndex());
			}catch(Exception e){}
		   }
	   }else
	   {

	    if (event.getColumn().getHeaderText().equals("Mahngebühren")) {
		   if(testInput(false, str_fi.trim())){
			 try{
             data.insertFines(str_fi.trim(), event.getRowIndex());
			}catch(Exception e){}
		   }
	    }
	    
	   }
    } 
 else{
    	if (event.getColumn().getHeaderText().equals("Rückgabedatum")) {
 		   if(testInput(true, str_sy.trim())){
 			try{
 			 data.insertDateExtend(str_sy.trim(),event.getRowIndex());
 			}catch(Exception e){}
 		   }
 	   }else
 	   {

 	    if (event.getColumn().getHeaderText().equals("Mahngebühren")) {
 		   if(testInput(false, str_fi.trim())){
 			 try{
              data.insertFinesExtend(str_fi.trim(), event.getRowIndex());
 			}catch(Exception e){}
 		   }
 	    }
 	    
 	   }
    }
	
    
		
	}
	
	private String str_sy="";
	private String str_fi="";

	public String getDate(){
		logger.debug("REACHED-----GET_DATE!!!!");
		return str_sy;
	}
	
	public void setDate(String str){
	   str_sy = str;
	}
	
	public String getFines(){
		logger.debug("REACHED-----GET_FINES!!!!");
		return str_fi;
		
	}
	
	public void setFines(String fines){
		
		str_fi = fines;
	}
	
	
	public boolean testInput(boolean isDate, String input){
		
		logger.debug("REACHED TEST-INPUT::::::");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String todayString = formatter.format(today);
		if(isDate){
			logger.debug("IS-A-DATE");
			
		    try {
		        formatter.parse(input);
		        return isInFuture(input,todayString);
		    } catch (Exception e) {
		        return false;
		    }
		}else{
			logger.debug("IS-NOT-A-DATE");
			
			try {
	            Double.parseDouble(input);
	            return isValidPrice(input);
	        } catch (NumberFormatException e) {
	            return false;
	        }
		}
		
	}
	
	public boolean isValidPrice(String input){
		if(input.indexOf(".")==-1){
			return false;
		}
		if(input.substring(input.indexOf(".")+1).length()>2 || input.substring(input.indexOf(".")+1).length()<2){
			return false;
		}
		return true;
	}
	
	
	public boolean isInFuture(String input, String today){
		logger.debug("REACHED---isInFuture!!!!");
	
			
			  
	   for (int i=0; i<3;i++){
		   logger.debug("I======="+ i);	  
		 switch(i){
				  
		 case 0:      logger.debug(Integer.parseInt(input.substring(6)));
			          if( Integer.parseInt(input.substring(6)) > Integer.parseInt(today.substring(6))){
			          logger.debug("isInFuture----biggerYear");
				     return true;          
				  }else{
					  
					  if( Integer.parseInt(input.substring(6)) < Integer.parseInt(today.substring(6))){
						  logger.debug("isInFuture----smallerYear");
			              return false; 
					  }
				  }
		          break;
		          
		 case 1:    logger.debug(Integer.parseInt(input.substring(3,5)));
			        if( Integer.parseInt(input.substring(3,5)) > Integer.parseInt(today.substring(3,5))){
			          logger.debug("isInFuture----biggerMonth");
		            return true;          
		            }else{
		        	 if( Integer.parseInt(input.substring(3,5)) < Integer.parseInt(today.substring(3,5))){
		        		 logger.debug("isInFuture----smallerMonth"); 
					     return false;          
					  }
		         } 
		         break;
		         
		 case 2:  logger.debug(Integer.parseInt(input.substring(0,2)));
			      if( Integer.parseInt(input.substring(0,2)) > Integer.parseInt(today.substring(0,2)) && Integer.parseInt(input.substring(0,2))<32){
			          logger.debug("isInFuture----biggerDay");
		            return true;          
		          }else{
		        	  if( Integer.parseInt(input.substring(0,2)) > Integer.parseInt(today.substring(0,2))){
		        		  logger.debug("isInFuture----smallerDay");
						     return false;          
						  }
		          }
		 default: logger.debug("SWITCH-----DAFAULT");
				          
	     }
	   }
		   
		
		return false;
	}
	
	
}
