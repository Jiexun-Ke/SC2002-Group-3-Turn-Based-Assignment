import boundary.GameUI;
import control.GameController;
import control.SetupController;

public class Main {
    public static void main(String[] args){
        GameUI ui = new GameUI();

        int choice = ui.showMainMenu();
        if(choice == 2){
            ui.showMessage("Exiting the Arena. Goodbye!");
            ui.closeUI();
            return;
        }

        SetupController setup = new SetupController(ui);
        
        try {
            GameController game = setup.createGame();
            game.startBattle();
        }
        catch (UnsupportedOperationException e){
            ui.showMessage("Some parts of the game are not fully implemented yet.");
            ui.showMessage("Error details: " + e.getMessage());

        }

        ui.closeUI();
    }
}
