package DigiPet;

import Events.EventHandle;
import Events.SignalHandle;

public interface DigiPetView
{
    void close();
    EventHandle<Interaction> onInteraction();
    SignalHandle onExit();
    void updatePet(Pet pet);
    void clearPet(int id);
}
