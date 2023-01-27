package DigiPet;

import javax.swing.*;

public class Pet
{
    public final int id;
    public final Icon icon;
    public final String name;
    public final int happiness;
    public final int health;
    public final int cleanness;

    public Pet(int id, Icon icon, String name, int happiness, int health, int cleanness)
    {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.happiness = happiness;
        this.health = health;
        this.cleanness = cleanness;
    }
}
