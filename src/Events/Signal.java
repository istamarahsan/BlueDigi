package Events;

import java.util.ArrayList;
import java.util.List;

public class Signal implements SignalHandle
{
    private final List<SignalListener> listeners = new ArrayList<>();
    
    public void invoke()
    {
        listeners.forEach(SignalListener::listen);
    }
    
    @Override
    public void addListener(SignalListener listener)
    {
        listeners.add(listener);
    }
    
    @Override
    public void removeListener(SignalListener listener)
    {
        listeners.remove(listener);
    }
}
