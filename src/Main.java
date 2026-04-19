import boundary.GameUI;
import control.GameController;
import control.SetupController;

public class Main {
    public static void main(String[] args){
        GameUI ui = new GameUI();
        
        try {
            int choice = ui.showMainMenu();

            if (choice == GameUI.EXIT){
            ui.showMessage("Exiting the Arena. Goodbye!");
            return;
            }

        SetupController setup = new SetupController(ui);
        GameController game = setup.createGame();
        game.startBattle();

        }

        catch (UnsupportedOperationException e){
            ui.showMessage("Feature not implemented: " + e.getMessage());

        } catch (Exception e) {
            ui.showMessage("An unexpected error occurred.");
            ui.showMessage("Error details: " + e.getMessage());
        }
        finally {
            ui.closeUI();
        }
        
    }
}
