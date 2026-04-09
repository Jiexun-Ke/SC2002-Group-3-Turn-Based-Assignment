import boundary.GameUI;
import control.SetupController;
import control.GameController;

public class main {
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

            if (game != null){
                game.startBattle();
            }
        }
        catch (UnsupportedOperationException e){
            ui.showMessage("Finish game controllers before game commence");
            ui.showMessage("Error details: " + e.getMessage());

        }

        ui.closeUI();
    }
}
