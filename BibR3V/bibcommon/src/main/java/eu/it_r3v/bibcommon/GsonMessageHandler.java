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

package eu.it_r3v.bibcommon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A Jersey-Provider to serialize arbitrary data into the JSON-format and back.
 *
 * Every time an object of a Jersey web-service has to be serialized,
 * a new instance of this class is used.
 *
 * URL:
 * http://eclipsesource.com/blogs/2012/11/02/integrating-gson-into-a-jax-rs-
 * based-application
 *
 * @author Moritz Post
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonMessageHandler implements MessageBodyWriter<Object>,
        MessageBodyReader<Object> {

    /**
     *Reference of the GSON-object that executes the serialization.
     */
    private Gson gson;

    /**
     * Returns an instance of the GSON-object (follows the singleton pattern).
     *
     * @return das GSON-Objekt
     */
    private Gson getGson() {
        if (gson == null) {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.create();
        }
        return gson;
    }

    /**
     * Ascertain if the MessageBodyReader can produce an instance of a
     * particular type. The type parameter gives the class of the object that
     * should be produced, the genericType parameter gives the
     * java.lang.reflect.Type of the object that should be produced. E.g. if the
     * object to be produced is List, the type parameter will be java.util.List
     * and the genericType parameter will be
     * java.lang.reflect.ParameterizedType.
     *
     * @param type
     *            the class of object to be produced.
     * @param genericType
     *            the type of object to be produced. E.g. if the message body is
     *            to be converted into a method parameter, this will be the
     *            formal type of the method parameter as returned by
     *            Method.getGenericParameterTypes.
     * @param annotations
     *            an array of the annotations on the declaration of the artifact
     *            that will be initialized with the produced instance. E.g. if
     *            the message body is to be converted into a method parameter,
     *            this will be the annotations on that parameter returned by
     *            Method.getParameterAnnotations.
     * @param mediaType
     *            the media type of the HTTP entity, if one is not specified in
     *            the request then application/octet-stream is used.
     * @return {@code true} since this class assumes that it can produce an
     *         instance of every type
     */
    @Override
    public final boolean isReadable(final Class<?> type,
            final Type genericType, final Annotation[] annotations,
            final MediaType mediaType) {
        return true;
    }

    /**
     * Read a type from the InputStream.
     *
     * @param type
     *            the type that is to be read from the entity stream.
     * @param genericType
     *            the type of object to be produced. E.g. if the message body is
     *            to be converted into a method parameter, this will be the
     *            formal type of the method parameter as returned by
     *            Method.getGenericParameterTypes.
     * @param annotations
     *            an array of the annotations on the declaration of the artifact
     *            that will be initialized with the produced instance. E.g. if
     *            the message body is to be converted into a method parameter,
     *            this will be the annotations on that parameter returned by
     *            Method.getParameterAnnotations.
     * @param mediaType
     *            the media type of the HTTP entity.
     * @param httpHeaders
     *            the read-only HTTP headers associated with HTTP entity.
     * @param entityStream
     *            the InputStream of the HTTP entity. The caller is responsible
     *            for ensuring that the input stream ends when the entity has
     *            been consumed. The implementation should not close the input
     *            stream.
     * @return the type that was read from the stream.
     * @throws java.io.IOException
     *             if an IO error arises
     */
    @Override
    public final Object readFrom(final Class<Object> type,
            final Type genericType, final Annotation[] annotations,
            final MediaType mediaType,
            final MultivaluedMap<String, String> httpHeaders,
            final InputStream entityStream) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(entityStream,
                "UTF-8");
        try {
            Type jsonType;
            if (type.equals(genericType)) {
                jsonType = type;
            } else {
                jsonType = genericType;
            }
            return getGson().fromJson(streamReader, jsonType);
        } finally {
            try {
                if (streamReader != null) {
                    streamReader.close();
                }
            } catch (IOException e) {
                // ignore, nothing to be done here anyway
            }
        }
    }

    /**
     * Called before writeTo to ascertain the length in bytes of the serialized
     * form of object. A non-negative return value is used in a HTTP
     * Content-Length header.
     *
     * @param object
     *            the instance to write
     * @param type
     *            the class of object that is to be written.
     * @param genericType
     *            the type of object to be written, obtained either by
     *            reflection of a resource method return type or by inspection
     *            of the returned instance. GenericEntity provides a way to
     *            specify this information at runtime.
     * @param annotations
     *            an array of the annotations on the resource method that
     *            returns the object.
     * @param mediaType
     *            the media type of the HTTP entity.
     * @return -1, since the length cannot be determined in advance
     */
    @Override
    public final long getSize(final Object object, final Class<?> type,
            final Type genericType, final Annotation[] annotations,
            final MediaType mediaType) {
        return -1;
    }

    /**
     * Write a type to an HTTP response. The response header map is mutable but
     * any changes must be made before writing to the output stream since the
     * headers will be flushed prior to writing the response body.
     *
     *
     @param object
     *            the instance to write.
     * @param type
     *            the class of object that is to be written.
     * @param genericType
     *            the type of object to be written, obtained either by
     *            reflection of a resource method return type or by inspection
     *            of the returned instance. GenericEntity provides a way to
     *            specify this information at runtime.
     * @param annotations
     *            an array of the annotations on the resource method that
     *            returns the object.
     * @param mediaType
     *            the media type of the HTTP entity.
     * @param httpHeaders
     *            a mutable map of the HTTP response headers.
     * @param entityStream
     *            the OutputStream for the HTTP entity. The implementation
     *            should not close the output stream.
     * @throws java.io.IOException
     *             if an IO error arises
     * @throws WebApplicationException
     *             if a specific HTTP error response needs to be produced. Only
     *             effective if thrown prior to the response being committed.
     */
    @Override
    public final void writeTo(final Object object, final Class<?> type,
            final Type genericType, final Annotation[] annotations,
            final MediaType mediaType,
            final MultivaluedMap<String, Object> httpHeaders,
            final OutputStream entityStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(entityStream,
                "UTF-8");
        try {
            Type jsonType;
            if (type.equals(genericType)) {
                jsonType = type;
            } else {
                jsonType = genericType;
            }
            getGson().toJson(object, jsonType, writer);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                // ignore, nothing to be done here anyway
            }
        }
    }

    /**
     * Ascertain if the MessageBodyWriter supports a particular type.
     *
     * @param type
     *            the class of object that is to be written.
     * @param genericType
     *            the type of object to be written, obtained either by
     *            reflection of a resource method return type or via inspection
     *            of the returned instance. GenericEntity provides a way to
     *            specify this information at runtime.
     * @param annotations
     *            an array of the annotations on the resource method that
     *            returns the object.
     * @param mediaType
     *            the media type of the HTTP entity.
     * @return {@code true}, since this class assumes that every type is
     *         supported
     */
    @Override
    public final boolean isWriteable(final Class<?> type,
            final Type genericType, final Annotation[] annotations,
            final MediaType mediaType) {
        return true;
    }

}
