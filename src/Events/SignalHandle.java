package Events;

public interface SignalHandle
{
    void addListener(SignalListener listener);

    void removeListener(SignalListener listener);
}
