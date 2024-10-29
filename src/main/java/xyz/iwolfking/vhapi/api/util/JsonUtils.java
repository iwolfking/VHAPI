package xyz.iwolfking.vhapi.api.util;

import com.google.gson.*;
import org.apache.commons.lang3.CharSet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonUtils {
    public static JsonElement parseJsonContentFromStream(InputStream stream) {
        Charset inputCharSet = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(stream, inputCharSet);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject;

        try {
            jsonObject = (JsonObject) parser.parse(isr);
        } catch (JsonIOException | JsonSyntaxException e) {
            throw new RuntimeException(e);
        }


        return jsonObject;
    }
}
