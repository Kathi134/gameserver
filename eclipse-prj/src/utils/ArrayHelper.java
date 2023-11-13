package utils;

public class ArrayHelper
{
	public static <T> boolean elementIn(T element, T[] array)
	{
//		return Arrays.stream(array).anyMatch(e -> e.equals(element));
		for(T t: array)
			if (element == t)
				return true;
		return false;
	}

	public static boolean elementIn(char input, char[] options)
	{
		for(char t: options)
			if (input == t)
				return true;
		return false;
	}
}