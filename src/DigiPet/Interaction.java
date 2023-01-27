package DigiPet;

public class Interaction
{
    public final InteractionType type;
    public final int petId;

    public Interaction(InteractionType type, int petId)
    {
        this.type = type;
        this.petId = petId;
    }
}
