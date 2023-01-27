package DigiPet.GUI;

import Events.Signal;
import Events.SignalHandle;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PetPanel
{
    private JPanel root;
    private JLabel petImageLabel;
    private JProgressBar happinessValueProgressBar;
    private JLabel happinessTitleLabel;
    private JLabel happinessValueLabel;
    private JLabel healthTitleLabel;
    private JProgressBar healthValueProgressBar;
    private JLabel healthValueLabel;
    private JLabel cleannessTitleLabel;
    private JProgressBar cleannessValueProgressBar;
    private JLabel cleannessValueLabel;
    private JLabel petNameLabel;
    private final Signal petClickedSignal = new Signal();

    public PetPanel()
    {
        petImageLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                petClickedSignal.invoke();
            }
        });
    }
    
    public SignalHandle onPetClicked()
    {
        return petClickedSignal;
    }
    
    public void reset()
    {
        petImageLabel.setIcon(null);
        setHealth(0);
        setHappiness(0);
        setCleanness(0);
    }
    
    public void setIcon(Icon icon)
    {
        petImageLabel.setIcon(icon);
    }
    
    public void setName(String name)
    {
        petNameLabel.setText(name);
    }
    
    public void setHealth(int value)
    {
        
        healthValueLabel.setText(String.valueOf(value));
        healthValueProgressBar.setValue(clamp(0, value, 100));
    }
    
    public void setHappiness(int value)
    {
        happinessValueLabel.setText(String.valueOf(value));
        happinessValueProgressBar.setValue(clamp(0, value, 100));
    }
    
    public void setCleanness(int value)
    {
        cleannessValueLabel.setText(String.valueOf(value));
        cleannessValueProgressBar.setValue(clamp(0, value, 100));
    }
    
    private int clamp(int min, int val, int max)
    {
        return Math.max(Math.min(val, max), min);
    }
}
