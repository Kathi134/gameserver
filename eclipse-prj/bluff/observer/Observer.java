package observer;

public abstract class Observer
{
	/**
	 * updates the observed state of type subjectType.
	 * @param subjectType
	 */
    public abstract void update(Class<? extends Subject> subjectClass);
}
