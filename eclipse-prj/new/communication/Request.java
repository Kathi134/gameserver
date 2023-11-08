package communication;

import java.io.Serializable;
import java.util.List;

public record Request(RequestType type, List<Payload> payloads) implements Serializable
{
	public static Request parseString(String str)
	{
		System.out.println(str);
		return new Request(null, null);
	}
}
