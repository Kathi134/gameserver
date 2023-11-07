package utils;

import java.net.InetAddress;
import java.util.function.Consumer;

public record ClientAddressInformation(InetAddress address, Consumer<String> callback)
{
	
}