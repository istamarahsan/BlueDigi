package DigiPet;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;

public class ResourceLoader
{
    public static Optional<ImageIcon> loadImageResource(String fileName)
    {
        URL pathTry = ResourceLoader.class.getResource(fileName);
        return pathTry == null 
                ? Optional.empty() 
                : Optional.of(pathTry)
                .map(ImageIcon::new);
    }
}
