package Events;

public interface EventHandle<T>
{
    void addListener(EventHandler<T> listener);

    void removeListener(EventHandler<T> listener);
}
