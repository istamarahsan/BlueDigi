package Events;

public interface EventHandler<T>
{
    void handleEvent(T message);
}
