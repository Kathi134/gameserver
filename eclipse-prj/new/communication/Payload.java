package communication;

import java.io.Serializable;

public record Payload(Object payload, String targetClazz) implements Serializable 
{}
