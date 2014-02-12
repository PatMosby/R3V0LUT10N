package swp.bibjsf.presentation;

import java.util.Date;

import swp.bibcommon.News;

public abstract class NewsForm extends BusinessObjectForm<News>{

	private static final long serialVersionUID = 8403380750745615341L;
	
    public String getNews() {  
        return element.getNews();  
    }  
  
    public void setNews(String news) {  
    	logger.debug("Treffer, bro!");
        element.setNews(news);  
    }
    
    private boolean dateOfAdditionSelected                = false;
    
    public boolean isDateOfAdditionSelected() {
		return dateOfAdditionSelected;
	}

	public void setDateOfAdditionSelected(boolean dateOfAdditionSelected) {
		this.dateOfAdditionSelected = dateOfAdditionSelected;
	}
    
    protected void mergeSelectedAttributes(News toNews, News fromNews) {
		if (isDateOfAdditionSelected()) {
            toNews.setDateOfAddition(fromNews.getDateOfAddition());
        }
    }
    
    public Date getDateOfAddition() {
        return element.getDateOfAddition();
    }

    public void setDateOfAddition(Date dateOfAddition) {
        element.setDateOfAddition(dateOfAddition);
    }
}
