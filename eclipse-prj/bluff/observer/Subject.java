package observer;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Subject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private transient ArrayList<Observer> observers; 
	
	protected Subject()
	{
		observers = new ArrayList<>();
	}

    public void subscribe(Observer observer)
    {
    	if(observers == null) 
    	{
    		observers = new ArrayList<>();
    	}
        observers.add(observer);
    }

    public void unsubscribe(Observer observer)
    {
        observers.remove(observer);
    }

    protected void notifyObservers()
    {
    	for(Observer o: observers)
    	{
    		o.update(this.getClass());
    	}
    }
}
