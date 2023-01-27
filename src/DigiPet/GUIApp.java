package DigiPet;

import DigiPet.GUI.DigiPetGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUIApp
{
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 300;
    private static final int ICON_SIZE = 80;
    
    public static void main(String[] args)
    {
        Image catImage = getScaledImage(ResourceLoader.loadImageResource("cat.jpg").orElse(new ImageIcon()).getImage(), ICON_SIZE, ICON_SIZE);
        Image dogImage = getScaledImage(ResourceLoader.loadImageResource("dog.jpg").orElse(new ImageIcon()).getImage(), ICON_SIZE, ICON_SIZE);
        
        Pet cat = new Pet(
                0,
                new ImageIcon(catImage),
                "Cat",
                10,
                10,
                10
        );

        Pet dog = new Pet(
                1,
                new ImageIcon(dogImage),
                "Dog",
                10,
                10,
                10
        );
        
        DigiPetModel model = new DigiPetRepo();
        model.createIfAbsent(cat);
        model.createIfAbsent(dog);
        
        DigiPetGUI gui = new DigiPetGUI(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        DigiPetPresenter presenter = new DigiPetPresenter(gui, model);
        presenter.addInteractionHandler(new PrototypeBusinessRules(model));
        javax.swing.SwingUtilities.invokeLater(gui.onCreateGUI);
    }

    private static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
}
