import game.Player;
import game.Position;
import game.State;
import game.Type;
import logic.Move;
import logic.Play;


public class Main {
    public static void main(String[] args) {

        Play play = new Play(new State());
        play.play();
        //System.out.println(new State());
    }
}