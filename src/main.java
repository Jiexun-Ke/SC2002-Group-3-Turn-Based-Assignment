import boundary.GameUI;
import control.GameController;
import control.SetupController;

public class main {
    public static void main(String[] args) {
        SetupController setCon = new SetupController(new GameUI());
        GameController gameCon = setCon.createGame();
        gameCon.startBattle();
    }
}