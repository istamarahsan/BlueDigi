package DigiPet.GUI;

import DigiPet.DigiPetView;
import DigiPet.Interaction;
import DigiPet.InteractionType;
import DigiPet.Pet;
import Events.Event;
import Events.EventHandle;
import Events.Signal;
import Events.SignalHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class DigiPetGUI implements DigiPetView
{
    private static final Cursor SELECTION_CURSOR = new Cursor(Cursor.CROSSHAIR_CURSOR);
    public final Runnable onCreateGUI = this::onCreateGUI;
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JPanel petsPanel;
    private JPanel actionsPanel;
    private JButton playButton;
    private JButton eatButton;
    private JButton bathButton;
    private JButton exit;
    private PetPanel petPanel1;
    private PetPanel petPanel2;
    private JPanel root;
    private JLabel commentLabel;
    private final Dimension size;
    private final Map<Integer, PetPanel> petPanels = new HashMap<>(2);
    private final Event<Interaction> interactionEvent = new Event<>();
    private final Signal exitSignal = new Signal();
    private GUISelectionState selectionState;
    private GUIState guiState;

    public DigiPetGUI(Dimension size)
    {
        this.size = size;
        this.selectionState = GUISelectionState.None;
        this.guiState = GUIState.Open;
        playButton.addActionListener(this::onPlayPressed);
        eatButton.addActionListener(this::onFeedPressed);
        bathButton.addActionListener(this::onBathePressed);
        exit.addActionListener(this::onExitPressed);
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
    public void close()
    {
        if (guiState == GUIState.Closed) return;
        mainFrame.dispose();
        guiState = GUIState.Closed;
    }
    
    @Override
    public void updatePet(Pet pet)
    {
        if (guiState == GUIState.Closed) return;
        int id = pet.id;
        if (petPanels.size() >= 2 && !petPanels.containsKey(id)) return;

        switch (petPanels.size())
        {
            case 0:
                petPanels.put(id, petPanel1);
                petPanel1.onPetClicked().addListener(() -> onPetClicked(id));
                break;
            case 1:
                petPanels.put(id, petPanel2);
                petPanel2.onPetClicked().addListener(() -> onPetClicked(id));
                break;
        }

        PetPanel panelToUpdate = petPanels.get(id);
        updatePetPanel(panelToUpdate, pet);
    }

    @Override
    public void clearPet(int id)
    {
        if (guiState == GUIState.Closed) return;
        if (!petPanels.containsKey(id)) return;

        petPanels.get(id)
                .reset();
        petPanels.remove(id);
    }

    private void onCreateGUI()
    {
        mainFrame = new JFrame("DigiPet");
        mainFrame.setMinimumSize(size);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane()
                .add(root);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void onPlayPressed(ActionEvent e)
    {
        if (selectionState != GUISelectionState.None) return;

        root.setCursor(SELECTION_CURSOR);
        commentLabel.setText("Click on a pet to play with.");

        selectionState = GUISelectionState.SelectingPlay;
    }

    private void onFeedPressed(ActionEvent e)
    {
        if (selectionState != GUISelectionState.None) return;

        root.setCursor(SELECTION_CURSOR);
        commentLabel.setText("Click on a pet to feed.");

        selectionState = GUISelectionState.SelectingFeed;
    }

    private void onBathePressed(ActionEvent e)
    {
        if (selectionState != GUISelectionState.None) return;

        root.setCursor(SELECTION_CURSOR);
        commentLabel.setText("Click on a pet to bathe.");

        selectionState = GUISelectionState.SelectingBathe;
    }

    private void onExitPressed(ActionEvent e)
    {
        exitSignal.invoke();
    }

    private void onPetClicked(int id)
    {
        if (selectionState == GUISelectionState.None) return;

        switch (selectionState)
        {
            case SelectingFeed:
                interactionEvent.invoke(
                        new Interaction(
                                InteractionType.Feed,
                                id));
                break;
            case SelectingPlay:
                interactionEvent.invoke(
                        new Interaction(
                                InteractionType.Play,
                                id));
                break;
            case SelectingBathe:
                interactionEvent.invoke(
                        new Interaction(
                                InteractionType.Bathe,
                                id));
                break;
        }

        root.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        commentLabel.setText("");
        selectionState = GUISelectionState.None;
    }
    
    private void updatePetPanel(PetPanel panelToUpdate, Pet pet)
    {
        panelToUpdate.setIcon(pet.icon);
        panelToUpdate.setName(pet.name);
        panelToUpdate.setHealth(pet.health);
        panelToUpdate.setHappiness(pet.happiness);
        panelToUpdate.setCleanness(pet.cleanness);
    }
}
