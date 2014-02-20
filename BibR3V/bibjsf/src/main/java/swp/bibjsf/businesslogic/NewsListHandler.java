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
import swp.bibcommon.News;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import swp.bibjsf.exception.DataSourceException;

@ManagedBean(name = "newsHandler")
@SessionScoped
public class NewsListHandler {
	Data data;
	final Logger logger = Logger.getLogger(NewsListHandler.class);
	
	public NewsListHandler()throws DataSourceException, NamingException{
	 data = new Data();
	  final Logger logger = Logger.getLogger(NewsListHandler.class);
	}
	
	

	public List<News> getNewsList(){
		logger.debug("REACHED---GET_News");
		
		return data.getNewsList();
	}
	
	
	
}
