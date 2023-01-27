package DigiPet;

public class PrototypeBusinessRules implements InteractionHandler
{
    private final DigiPetModel model;
    
    public PrototypeBusinessRules(DigiPetModel model)
    {
        this.model = model;
    }
    
    @Override
    public void handleInteraction(Interaction interaction)
    {
        switch (interaction.type)
        {
            case Play:
                model.updateIfPresent(
                        interaction.petId,
                        pet -> new Pet(
                                pet.id,
                                pet.icon,
                                pet.name,
                                pet.happiness + 3,
                                pet.health - 1,
                                pet.cleanness - 1
                        ));
                break;
            case Feed:
                model.updateIfPresent(
                        interaction.petId,
                        pet -> new Pet(
                                pet.id,
                                pet.icon,
                                pet.name,
                                pet.happiness - 1,
                                pet.health + 3,
                                pet.cleanness - 1
                        )
                );
                break;
            case Bathe:
                model.updateIfPresent(
                        interaction.petId,
                        pet -> new Pet(
                                pet.id,
                                pet.icon,
                                pet.name,
                                pet.happiness - 1,
                                pet.health - 1,
                                pet.cleanness + 3
                        )
                );
                break;
        }
    }
}
