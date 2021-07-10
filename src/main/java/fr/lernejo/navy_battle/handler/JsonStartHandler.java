package fr.lernejo.navy_battle.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonStartHandler {
    public final String id;
    public final String url;
    public final String message;

    public JsonStartHandler(@JsonProperty("id") JsonNode id,
                            @JsonProperty("url") JsonNode url,
                            @JsonProperty("message") JsonNode message) {
        this.id = id.toString();
        this.url = url.toString();
        this.message = message.toString();
    }

}
