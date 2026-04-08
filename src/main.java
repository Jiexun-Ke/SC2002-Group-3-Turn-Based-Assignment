import boundary.GameUI;
import control.SetupController;

public class main {
    public static void main(String[] args) {
        SetupController setCon = new SetupController(new GameUI());
        setCon.createGame();
    }
}