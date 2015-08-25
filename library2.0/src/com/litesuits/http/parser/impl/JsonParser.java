package com.litesuits.http.parser.impl;

import com.litesuits.http.data.Json;
import com.litesuits.http.parser.MemeoryDataParser;
import com.litesuits.http.request.AbstractRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * parse inputstream to java model.
 *
 * @author MaTianyu
 *         2014-4-19
 */
public class JsonParser<T> extends MemeoryDataParser<T> {
    protected Type claxx;
    protected String json;

    public JsonParser(AbstractRequest<T> request, Type claxx) {
        super(request);
        this.claxx = claxx;
    }

    @Override
    protected T parseNetStream(InputStream stream, long totalLength, String charSet,
                               String cacheDir) throws IOException {
        json = streamToString(stream, totalLength, charSet);
        if (request.isCached()) {
            keepToCache(json, getSpecifyFile(cacheDir));
        }
        return Json.get().toObject(json, claxx);
    }

    @Override
    protected T parseDiskCache(InputStream stream, long length) throws IOException {
        json = streamToString(stream, length, charSet);
        return Json.get().toObject(json, claxx);
    }

    /**
     * get the row string
     */
    @Override
    public String getRawString() {
        return json;
    }

    /**
     * get the json model
     */
    public <C> C getJsonModel(Class<C> claxx) {
        return Json.get().toObject(json, claxx);
    }

    @Override
    public String toString() {
        return "JsonParser{" +
               "claxx=" + claxx +
               "} " + super.toString();
    }
}