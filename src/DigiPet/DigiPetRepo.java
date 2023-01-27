package DigiPet;

import Events.Event;
import Events.EventHandle;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DigiPetRepo implements DigiPetModel
{
    private final Event<Pet> petUpdatedEvent = new Event<>();
    private final Event<Pet> petDeletedEvent = new Event<>();
    private final Map<Integer, Pet> pets = new HashMap<>();
    
    @Override
    public EventHandle<Pet> onPetUpdated()
    {
        return petUpdatedEvent;
    }
    
    @Override
    public EventHandle<Pet> onPetDeleted()
    {
        return petDeletedEvent;
    }

    @Override
    public Collection<Pet> all()
    {
        return pets.values();
    }

    @Override
    public void createIfAbsent(Pet pet)
    {
        if (pets.containsKey(pet.id)) return;
        pets.put(pet.id, pet);
        petUpdatedEvent.invoke(pet);
    }
    
    @Override
    public void updateIfPresent(int id, Function<Pet, Pet> updater)
    {
        if (!pets.containsKey(id)) return;
        Pet pet = updater.apply(pets.get(id));
        pets.put(id, pet);
        petUpdatedEvent.invoke(pet);
    }
    
    @Override
    public void delete(int id)
    {
        if (pets.containsKey(id))
        {
            Pet pet = pets.get(id);
            pets.remove(id);
            petDeletedEvent.invoke(pet);
        }
        
    }
    
    @Override
    public Optional<Pet> get(int id)
    {
        return pets.containsKey(id) ? Optional.of(pets.get(id)) : Optional.empty();
    }
    
    
}
