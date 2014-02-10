package swp.bibjsf.presentation;

import java.util.Date;

import swp.bibcommon.News;

public abstract class NewsForm extends BusinessObjectForm<News>{

	private static final long serialVersionUID = 8403380750745615341L;
	
    public String getNews() {  
        return element.getNews();  
    }  
  
    public void setNews(String news) {  
        element.setNews(news);  
    }
    
//    public String getDateTime(){
//    	return date;
//    }
//    
//    public void setDateTime(){
//    	this.date = new SimpleDateFormat("dd/MM/yyyy H:mm:ss").format(new Date());
//    }
    
    public Date getDateOfAddition() {
        return element.getDateOfAddition();
    }

    public void setDateOfAddition(Date dateOfAddition) {
        element.setDateOfAddition(dateOfAddition);
    }
}
