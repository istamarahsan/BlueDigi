package DigiPet;

import DigiPet.Console.DigiPetConsole;

import javax.swing.*;

public class ConsoleApp
{
    public static void main(String[] args)
    {
        Pet cat = new Pet(
                0,
                new ImageIcon(),
                "Cat",
                10,
                10,
                10
        );

        Pet dog = new Pet(
                1,
                new ImageIcon(),
                "Dog",
                10,
                10,
                10
        );

        DigiPetModel model = new DigiPetRepo();
        model.createIfAbsent(cat);
        model.createIfAbsent(dog);
        DigiPetConsole console = new DigiPetConsole();
        DigiPetPresenter presenter = new DigiPetPresenter(console, model);
        presenter.addInteractionHandler(new PrototypeBusinessRules(model));
        console.start();
    }
}
