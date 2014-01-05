/*
 * Copyright (c) 2013 AG Softwaretechnik, University of Bremen, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package it_r3v.bibjsf.presentation;

import it_r3v.bibjsf.businesslogic.BusinessObjectHandler;
import it_r3v.bibjsf.exception.DataSourceException;
import it_r3v.bibjsf.renderer.Content;
import it_r3v.bibjsf.renderer.Printer;
import it_r3v.bibjsf.utils.Messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import swp.bibcommon.BusinessObject;

/**
 * A table form for Elements. Supports selection, sorting, filtering,
 * CSV import/export, and lazy loading. Editing is not supported.
 * There will be extra forms for that.
 *
 * Why is editing not supported?
 * Editing turned out to be too difficult to get right. Here is a summary
 * of my attempts. PrimeFaces offers row editors, which can be used to
 * edit rows. They don't allow you to modify expanded rows, however.
 * You could allow modifying expanded rows by conditional rendering
 * input or output text fields. Yet, all these attempts finally failed
 * if a user is allowed to modify the identifying attribute (the ID).
 * That ID is needed to identify the database entry. If it can be changed,
 * we need to have the ID before and after the modification. We could
 * track the old ID by saving it during the OnEditInit event by storing
 * it in a map <object reference -> ID>. The problem is that the row editor seems
 * to create copies of the row element changed. This is another object
 * and the object reference is different from the original object reference
 * stored in the map upon OnEditInit. So we cannot lookup the element in
 * the map anymore. I finally gave up here.
 *
 * @author koschke
 *
 */

public abstract class TableForm<Element extends BusinessObject> implements Serializable {

    private static final long serialVersionUID = -4323158046564482542L;

    protected static final Logger logger = Logger.getLogger(TableForm.class);

    /**
     * The business handler that takes care of the service requests.
     */
    protected final BusinessObjectHandler<Element> handler;

    /**
     * Constructor.
     *
     * @param handler handling the services
     */
    public TableForm(BusinessObjectHandler<Element> handler) {
        this.handler = handler;
    }

    /**************************************************************************
     * Table settings
     *************************************************************************/

    /**
     * True if the table may be modified.
     *
     * @return true if the table may be modified
     */
    public boolean getModifiable() {
        return false;
    }

    /**************************************************************************
     * Filtering
     *************************************************************************/

    /**
     * List of all selected rows.
     */
    protected List<Element> filteredElements;

    /**
     * Returns a list of all selected rows.
     *
     * @return list of all selected rows.
     */
    public List<Element> getFilteredElements() {
        return filteredElements;
    }

    /**
     * Sets the list of selected rows. Called from the Facelet.
     *
     * @param filteredElements the new filtered elements.
     */
    public void setFilteredElements(List<Element> filteredElements) {
        this.filteredElements = filteredElements;
    }

    /**************************************************************************
     * Selection
     *************************************************************************/

    /**
     * All rows currently selected.
     */
    protected List<Element> selectedElements;

    /**
     * Returns all rows currently selected.
     *
     * @return all rows currently selected or null if none are selected
     */
    public List<Element> getSelectedElements() {
    	if (selectedElements == null) {
    		return null;
    	} else {
    	    return new ArrayList<Element>(selectedElements);
    	}
    }

    /**
     * Sets the rows currently selected. Called by the Facelet.
     *
     * @param selectedElements new selected Elements
     */
    public void setSelectedElements(List<Element> selectedElements) {
        this.selectedElements = new ArrayList<Element>(selectedElements);
    }

    /**
     * Deletes all selected elements from the data model.
     */
    public void deleteSelected() {
        if (selectedElements.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, Messages.get("noEntriesSelected"), Messages.get("nothingToDo")));
        } else {
            try {
                handler.delete(selectedElements);
                //populateModel();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Messages.get("success"),
                                selectedElements.size() + " " + Messages.get("elementsDeleted")));
                selectedElements = null;
            } catch (DataSourceException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         Messages.get("noElementDeleted"),
                                         e.getLocalizedMessage()));
            }
        }
    }

    /**************************************************************************
     * Lazy loading data model.
     *************************************************************************/

    /**
     * The lazy loading data model.
     */
    protected LazyDataModel<Element> model;

    /**
     * Returns the data model.
     *
     * @return the data model
     */
    public LazyDataModel<Element> getModel() {
        return model;
    }

    /**
     * Sets the data model. (Is this ever being called?)
     *
     * @param model new data model
     */
    public void setModel(LazyDataModel<Element> model) {
        this.model = model;
    }

    /********************************************************************
     * Upload / Download
     *******************************************************************/

    /**
     * Handles the upload request by the user to upload a CSV file of
     * business objects to be stored in the database.
     *
     * @param event the event that gives us the uploaded file
     */
    public void handleFileUpload(FileUploadEvent event) {
        final UploadedFile uploadedFile = event.getFile();
        String fileName    = uploadedFile.getFileName();
        try {
            final int entries = handler.importCSV(uploadedFile.getInputstream());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    fileName + " " + Messages.get("uploadSuccess"),
                    entries + " " + Messages.get("elements"));
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (DataSourceException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                fileName + " " + Messages.get("uploadFailure"),
                                e.getLocalizedMessage()));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                fileName + " " + Messages.get("uploadFailure"),
                                e.getLocalizedMessage()));
        }
    }

    /**
     * Creates a CSV export file containing all business objects in the table.
     *
     * @return a stream containing the generated CSV
     */
    public StreamedContent getcSV() {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            handler.exportCSV(outStream);
        } catch (DataSourceException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                     Messages.get("csvFailure"),
                                     e.getLocalizedMessage()));
            return null;
        }

        try {
            outStream.close();
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     Messages.get("csvFailure"),
                                     e.getLocalizedMessage()));
        }

        byte[] data = outStream.toByteArray();
        ByteArrayInputStream inStream = new ByteArrayInputStream(data);
        return new DefaultStreamedContent(inStream, "application/csv", "Data.csv");
    }

    /********************************************************************
     * PDF generation
     *******************************************************************/

	/**
	 * Returns a PDF printer.
	 *
	 * @return a PDF printer
	 */
	protected abstract Printer getPrinter();

	/**
	 * Returns the content to be printed as PDF.
	 *
	 * @return content to be printed as PDF.
	 */
	protected abstract List<Content> getContent();

    /**
     * Creates a PDF printout for the ID cards of all selected readers in the table.
     *
     * @return a stream containing the generated PDF
     */
    public StreamedContent getpDF() {

        if (selectedElements.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, Messages.get("noEntriesSelected"), Messages.get("nothingToDo")));
            return null;
        }
        List<Content> idcontent = getContent();
        Printer printer = getPrinter();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        printer.printCards(idcontent, outStream);
        try {
            outStream.close();
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     Messages.get("pdfFailure"),
                                     e.getLocalizedMessage()));
        }

        // Copy outStream data to inStream.
        // This is the easiest approach if your data easily fit into memory.
        // It is not recommended this approach when the data is more than 100Mb
        // (because it's not the only one object on the heap and if
        // there are many simultaneous users of our web application, it can
        // easily eat up all your Java Virtual Machine heap). Yet, we expect
        // the data to be rather limited.
        byte[] data = outStream.toByteArray();
        ByteArrayInputStream inStream = new ByteArrayInputStream(data);

        return new DefaultStreamedContent(inStream, "application/pdf", "Data.pdf");
    }

}
