package DigiPet;

import Events.EventHandle;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public interface DigiPetModel
{
    EventHandle<Pet> onPetUpdated();

    EventHandle<Pet> onPetDeleted();

    Collection<Pet> all();
    
    void createIfAbsent(Pet pet);

    void updateIfPresent(int id, Function<Pet, Pet> updater);

    void delete(int id);

    Optional<Pet> get(int id);
}
