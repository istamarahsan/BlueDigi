package DigiPet.Console;

import DigiPet.DigiPetView;
import DigiPet.Interaction;
import DigiPet.InteractionType;
import DigiPet.Pet;
import Events.Event;
import Events.EventHandle;
import Events.Signal;
import Events.SignalHandle;

import java.util.*;
import java.util.stream.IntStream;

public class DigiPetConsole implements DigiPetView
{
    private static final String HEADER = "DIGIPET";
    private final Event<Interaction> interactionEvent = new Event<>();
    private final Signal exitSignal = new Signal();
    private boolean isOpen = true;
    private final List<Pet> petsToDisplay = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void start()
    {
        Set<Integer> choiceNumbers = IntStream.range(1, 5)
                .collect(HashSet::new, Set::add, Set::addAll);
        while (isOpen)
        {
            System.out.println(HEADER);
            System.out.println("What would you like to do?");
            System.out.print(
                    "1. Play\n" + "2. Eat\n" + "3. Bath\n" + "4. Exit\n"
            );
            int interactionChoice = -1;

            while (!choiceNumbers.contains(interactionChoice))
            {
                interactionChoice = scanner.nextInt();
            }

            if (interactionChoice == 4)
            {
                exitSignal.invoke();
                continue;
            }

            if (petsToDisplay.size() == 0) 
            {
                System.out.println("You don't have any pets!");
                continue;
            }

            for (int i = 0; i < petsToDisplay.size(); i++)
            {
                System.out.print(i + "\n" + formatPet(petsToDisplay.get(i)));
            }

            int chosenPetIndex;
            do
            {
                chosenPetIndex = scanner.nextInt();
            } while (!(chosenPetIndex >= 0 && chosenPetIndex < petsToDisplay.size()));

            InteractionType interactionType = InteractionType.Play;
            switch (interactionChoice)
            {
                case 2:
                    interactionType = InteractionType.Feed;
                    break;
                case 3:
                    interactionType = InteractionType.Bathe;
                    break;
            }

            interactionEvent.invoke(
                    new Interaction(
                            interactionType,
                            petsToDisplay.get(chosenPetIndex).id)
            );
        }
    }
    
    @Override
    public void close()
    {
        isOpen = false;
    }

    @Override
    public EventHandle<Interaction> onInteraction()
    {
        return interactionEvent;
    }

    @Override
    public SignalHandle onExit()
    {
        return exitSignal;
    }

    @Override
    public void updatePet(Pet petToUpdate)
    {
        clearPet(petToUpdate.id);
        petsToDisplay.add(petToUpdate);
    }

    @Override
    public void clearPet(int id)
    {
        int indexToRemove = -1;
        for (int i = 0; i < petsToDisplay.size(); i++)
        {
            if (petsToDisplay.get(i).id == id) indexToRemove = i;
        }
        if (indexToRemove >= 0) petsToDisplay.remove(indexToRemove);
    }
    
    private String formatPet(Pet pet)
    {
        return String.format(
                "Name: %s\n" + "Health: %d\n" + "Happiness: %d\n" + "Cleanness: %d\n",
                pet.name, pet.health, pet.happiness, pet.cleanness
        );
    }
}
