package Events;

import java.util.ArrayList;
import java.util.List;

public class Event<T> implements EventHandle<T>
{
    private final List<EventHandler<T>> listeners = new ArrayList<>();
    
    public void invoke(T message)
    {
        listeners.forEach(l -> l.handleEvent(message));
    }
    
    @Override
    public void addListener(EventHandler<T> listener)
    {
        listeners.add(listener);
    }
    
    @Override
    public void removeListener(EventHandler<T> listener)
    {
        listeners.remove(listener);
    }
}
