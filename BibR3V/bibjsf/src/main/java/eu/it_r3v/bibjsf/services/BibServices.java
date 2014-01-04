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

package eu.it_r3v.bibjsf.services;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import eu.it_r3v.Book;
import eu.it_r3v.bibjsf.businesslogic.BookHandler;
import eu.it_r3v.bibjsf.exception.BibJSFException;
import eu.it_r3v.bibjsf.exception.DataSourceException;
import eu.it_r3v.bibjsf.exception.NoSuchBookExistsException;

/**
 * Implements REST services of this web application. The base path of
 * all services offered by this class is the path of the web application.
 * There can be only one instance of this class.
 *
 * @author K. Hölscher
 */
@Path("")
@Singleton
public class BibServices {

    /**
     * Handler executing the requests.
     */
    private final BookHandler bookHandler;

    /**
     * Logger of this class.
     */
    private final Logger logger = Logger.getLogger(BibServices.class);

    /**
     * Creates new instance of this class with defined book handler.
     *
     * @throws Exception in case the book handler cannot be retrieved
     */
    public BibServices() throws Exception {
        logger.debug("Get instance of business handler...");
        bookHandler = BookHandler.getInstance();
    }

    /**
     * Returns a list of all books. This service is accessed at the annotated path
     * via {@code HTTP_GET}.
     *
     * Throws {@code BibJSFException} in case of problems with the data source.
     *
     * TODO: Currently all fields of a book are transferred even those not
     * relevant to a client including those that should actually be hidden from
     * normal readers accessing via a client.
     *
     * @return list of all books in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("books")
    public final List<Book> books() {
        try {
            return bookHandler.getAllBooks();
        } catch (DataSourceException e) {
            logger.error("A problem occured while trying to access the datasource.");
            throw new BibJSFException(
                    "A problem occured while trying to access the datasource.");
        }
    }

    /**
     * Updates the rating of a book. May be accessed at the annotated path via
     * {@code HTTP_POST} anfragt. The ID of the book and its rating must be contained
     * in the {@code Map params} with the keys {@code id} and {@code stars}. The
     * {@code Map params} is passed in JSON format. Upon success, the answer
     * {@code OK} is returned.
     *
     * This method is not idempotent, that is, multiple calls with the same parameter
     * will change the data base.
     *
     * If negative numbers are given as parameters, status reply {@code BAD_REQUEST}
     * will be returned.
     *
     * If there is book with the given ID in the data base, status reply {@code GONE}
     * will be returned.
     *
     * If there is any problem with the data source. an exception {@code BibJSFException}
     * is thrown.
     *
     * @param params ID of the book and its ranking wrapped in a map
     * @return reply to client on the status of this operation (OK, BAD_REQUEST, GONE).
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("books/rate")
    public final Response rateBook(final Map<String, Integer> params) {
        Integer id = params.get("id");
        Integer stars = params.get("stars");
        logger.debug("rate books called for id " + id + " with stars: " + stars);
        if (stars < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Arguments have to be positive numbers!").build();
        }

        try {
            bookHandler.rateBook(id, stars);
        } catch (NoSuchBookExistsException e1) {
            logger.debug("book with id " + id + " does not exist");
            return Response.status(Response.Status.GONE).entity(e1.getMessage())
                    .build();
        } catch (DataSourceException e2) {
            logger.error("A problem occured while trying to access the datasource.");
            throw new BibJSFException(e2.getMessage());
        }
        return Response.status(Response.Status.OK)
                .entity("Book rating has been updated").build();
    }
}
