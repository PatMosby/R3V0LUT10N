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
import swp.bibcommon.Times;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import swp.bibjsf.exception.DataSourceException;


/**
 * 
 * @author Ellhoff
 *
 */
@ManagedBean(name="zeit")
@SessionScoped
public class TimesListHandler {
 Data data;
 final Logger logger = Logger.getLogger(TimesListHandler.class);
 
 public TimesListHandler()throws DataSourceException, NamingException{
  data = new Data();
   final Logger logger = Logger.getLogger(TimesListHandler.class);
 }
 
 

 public List<Times> getTimeList(){
  logger.debug("REACHED---GET_Times");
  
  return data.getTimeList();
 }
 
 
 public void onCellEdit(CellEditEvent event){
     logger.debug("REACHED-----ON_CELL_EDIT!!!!");
     logger.debug(event.getColumn().getHeaderText());
     
     Object newValue = event.getNewValue();
    
     String newData = newValue.toString();
     newData = newData.substring(0, newData.length()-1);
     logger.debug("OUT-PUT:::: "+ newData);
     
     
     
      if (event.getColumn().getHeaderText().equals("Öffnung")) {
       
       logger.debug("REACHED-------ÖFFNUNNGGGGGGGG");
     
     try{
              data.insertOpenTime(str_open.trim(), event.getRowIndex());
              logger.debug("REACHED-------ÖFFNUNNGGGGGGGG------TRYYYYYY");
    }catch(Exception e){
     logger.debug("REACHED-------ÖFFNUNNGGGGGGGG---------CATCH");
    }
      
      }else{
       if (event.getColumn().getHeaderText().equals("Schließung")) {
        
      try{
       data.insertCloseTime(str_close.trim(),event.getRowIndex());
      }catch(Exception e){}
        
       }
       
       
      }
      
     
   
  }
  
  private String str_day="";
  private String str_open="";
  private String str_close="";

  public String getDay(){
   logger.debug("REACHED-----GET_DAY!!!!");
   return str_day;
  }
  
  public void setDay(String str){
     str_day = str;
  }
  
  public String getOpen(){
   logger.debug("REACHED-----GET_OPEN!!!!");
   return str_open;
   
  }
  
  public void setOpen(String fines){
   
   str_open = fines;
  }
  
  public String getClose(){
   logger.debug("REACHED-----GET_CLOSE!!!!");
   return str_close;
   
  }
  
  public void setClose(String fines){
   
   str_close = fines;
  }
  
 
 
 
}