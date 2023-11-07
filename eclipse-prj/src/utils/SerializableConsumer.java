package utils;

import java.io.Serializable;
import java.util.function.Consumer;

public interface SerializableConsumer<T> extends Consumer<T>, Serializable
{
	static final long serialVersionUID = 1L;

	
}