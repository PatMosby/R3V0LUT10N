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

package it_r3v.bibclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import swp.bibcommon.Book;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * Die Network Klasse sorgt für die Kommunikation mit dem zugehörigen
 * REST-Service.
 *
 * @author dierk
 *
 */
public class Network {

    /**
     * Milisekunden bis zum Timeout einer HTTP-Connection.
     */
    private final int timeout = 2000;

    /**
     * Url des Server.
     */
    private final String server = "http://10.0.2.2:8080";

    /**
     * Pfad zum Buchservices.
     */
    private final String bookpath = "/bibjsf/rest/books";

    /**
     * Fragt bei einem Server nach den Büchern an.
     *
     * @return Die Liste der Bücher.
     * @throws IOException
     *             Kann geworden werden bei IO-Problemen.
     */
    public final List<Book> getBooks() throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        // Setzen eines ConnectionTimeout von 2 Sekunden:
        HttpParams params = httpClient.getParams();
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
        httpClient.setParams(params);

        // Der BibServer sollte lokal laufen, daher zugriff auf Localhost
        // 10.0.2.2 für den AndroidEmulator.
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(server + bookpath);
        String text = null;

        Log.i(Network.class.getName(), "Try to get a http connection...");
        HttpResponse response = httpClient.execute(httpGet, localContext);
        HttpEntity entity = response.getEntity();
        text = getASCIIContentFromEntity(entity);
        Log.i(Network.class.getName(), "Create new Gson object");
        Gson gson = new Gson();
        Log.i(Network.class.getName(), "Create listOfTestObject");
        // Wir arbeiten mit einem TypeToken um für eine generische Liste wieder
        // Objekte zu erhalten.
        Type listOfTestObject = new TypeToken<List<Book>>() { }.getType();

        Log.i(Network.class.getName(), "Convert to a list.");
        /*
         * Hier befindet sich ein unsicherer Cast, da wir nicht wirklich wissen,
         * ob der String, den wir von der Website bekommen, sich wirklich in
         * eine Liste von Büchern umwandeln lässt
         */
        try {
            @SuppressWarnings("unchecked")
            List<Book> list
               = Collections.synchronizedList((List<Book>) gson.fromJson(text, listOfTestObject));
            return list;
        } catch (ClassCastException e) {
            throw new ClientProtocolException("Returned type is not a list of book objects");
        }
    }

    /**
     * Diese Methode liest den Text von der Webschnittstelle.
     *
     * @param entity
     *            Von wo gelesen werden soll.
     * @return Der gelesene Text wird als String zurückgegeben.
     * @throws IOException
     *             Eine mögliche IOExption kann geworfen werden.
     */
    private String getASCIIContentFromEntity(final HttpEntity entity) throws IOException {
        InputStream in = entity.getContent();
        // Ein BufferedReader eignet sich zum einlesen des Textes auf dem
        // Stream.
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            return result;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.i(this.getClass().getName(),
                        "IO-Exception while closing the buffered reader in getASCIIContentFromEntity");
                // ignore, we cannot do anything here anyway
            }
        }
    }

    /**
     * Fügt ein Buch hinzu.
     * 
     * @param book
     *            Das Buch, das hinzugefügt werden soll.
     * @return Der Http Response.
     * @throws IOException
     *             Kann eine IOException werfen.
     */
    public final String addBook(final Book book) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        // Setzen eines ConnectionTimeout von 2 Sekunden:
        HttpParams httpparams = httpClient.getParams();
        httpparams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
        httpClient.setParams(httpparams);

        // Der BibServer sollte lokal laufen, daher zugriff auf Localhost
        // 10.0.2.2 für den AndroidEmulator.
        HttpContext localContext = new BasicHttpContext();
        HttpPut httpPut = new HttpPut(server + bookpath);
        Gson gson = new Gson();
        String json = gson.toJson(book);

        httpPut.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        httpPut.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        HttpResponse response = null;

        httpPut.setEntity(new StringEntity(json));
        response = httpClient.execute(httpPut, localContext);
        return response.toString();
    }

}
