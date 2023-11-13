package legacy.request;

import java.io.Serializable;

public record Request(RequestType type, Payload... laod) implements Serializable
{
}