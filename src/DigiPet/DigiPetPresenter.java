package DigiPet;

import java.util.ArrayList;
import java.util.List;

public class DigiPetPresenter
{
    private final DigiPetView view;
    private final DigiPetModel model;
    private final List<InteractionHandler> interactionHandlers = new ArrayList<>();

    public DigiPetPresenter(DigiPetView view, DigiPetModel model)
    {
        this.view = view;
        this.model = model;
        this.view.onInteraction()
                .addListener(this::onInteraction);
        this.view.onExit()
                .addListener(this::onExitRequested);
        this.model.onPetUpdated()
                .addListener(this::onPetUpdated);
        this.model.onPetDeleted()
                .addListener(this::onPetDeleted);
        this.model.all().forEach(view::updatePet);
    }
    
    public void addInteractionHandler(InteractionHandler newHandler)
    {
        interactionHandlers.add(newHandler);
    }

    public void removeInteractionHandler(InteractionHandler handlerToRemove)
    {
        interactionHandlers.remove(handlerToRemove);
    }

    private void onInteraction(Interaction interaction)
    {
        if (!model.get(interaction.petId)
                .isPresent())
        {
            return;
        }
        
        interactionHandlers.forEach(handler -> handler.handleInteraction(interaction));
    }

    private void onPetUpdated(Pet updatedPet)
    {
        view.updatePet(updatedPet);
    }

    private void onPetDeleted(Pet deletedPet)
    {
        view.clearPet(deletedPet.id);
    }
    
    private void onExitRequested()
    {
        view.close();
    }
}
