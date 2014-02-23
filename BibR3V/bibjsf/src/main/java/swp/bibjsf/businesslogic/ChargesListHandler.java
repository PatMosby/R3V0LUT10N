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
import swp.bibcommon.Charges;
import swp.bibcommon.Times;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import swp.bibjsf.exception.DataSourceException;

@ManagedBean(name="charge")
@SessionScoped
public class ChargesListHandler {
 Data data;
 final Logger logger = Logger.getLogger(ChargesListHandler.class);
 
 public ChargesListHandler()throws DataSourceException, NamingException{
  data = new Data();
   final Logger logger = Logger.getLogger(ChargesListHandler.class);
 }
 
 

 public List<Charges> getChargeList(){
  logger.debug("REACHED---GET_Charge");
  
  return data.getChargeList();
 }
 
 
 public void onCellEdit(CellEditEvent event){
     logger.debug("REACHED-----ON_CELL_EDIT!!!!");
     logger.debug(event.getColumn().getHeaderText());
     
     Object newValue = event.getNewValue();
    
     String newData = newValue.toString();
     newData = newData.substring(0, newData.length()-1);
     logger.debug("OUT-PUT:::: "+ newData);
     
     
     
      if (event.getColumn().getHeaderText().equals("Mahngebühren")) {
       
       logger.debug("REACHED-------Mahngebühren");
     
     try{
              data.insertCharges(str_charge.trim(), event.getRowIndex());
              logger.debug("REACHED-------Mahngebühren------TRYYYYYY");
    }catch(Exception e){
     logger.debug("REACHED-------Mahngebühren---------CATCH");
    }
      }
      
    if (event.getColumn().getHeaderText().equals("Ausleihspanne")) {
        
      try{
       data.insertExpireDate(str_expireDate.trim(),event.getRowIndex());
      }catch(Exception e){}
    }
        
    if (event.getColumn().getHeaderText().equals("Fristtoleranz")) {
              
              try{
               data.insertTolerant(str_tolerant.trim(),event.getRowIndex());
              }catch(Exception e){}
          }
       }
  
  private String str_typ="";
  private String str_charge="";
  private String str_expireDate="";
  private String str_tolerant="";

  public String getTyp(){
   logger.debug("REACHED-----GET_Typ!!!!");
   return str_typ;
  }
  
  public void setTyp(String str){
     str_typ = str;
  }
  
  public String getCharge(){
   logger.debug("REACHED-----GET_Charge!!!!");
   return str_charge;
   
  }
  
  public void setCharge(String charge){
   
   str_charge = charge;
  }
  
  public String getExpireDate(){
   logger.debug("REACHED-----GET_expireDate!!!!");
   return str_expireDate;
   
  }
  
  public void setExpireDate(String expireDate){
   
   str_expireDate = expireDate;
  }
  
  public String getTolerant(){
	   logger.debug("REACHED-----GET_tolerant!!!!");
	   return str_tolerant;
	   
	  }
	  
	  public void setTolerant(String tolerant){
	   
	   str_tolerant = tolerant;
	  }
  
 
 
 
}