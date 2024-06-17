package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import model.HoleStageModel;

public class ControllerHole extends Controller {

    public ControllerHole(Model model, View view) {
        super(model, view);
        setControlKey(new ControllerHoleKey(model, view, this));
        setControlMouse(new ControllerHoleMouse(model, view, this));
        setControlAction (new ControllerHoleAction(model, view, this));
    }

    public void endOfTurn() {
        // use the default method to compute next player
        model.setNextPlayer();
        // get the new player
        Player p = model.getCurrentPlayer();
        // change the text of the TextElement
        HoleStageModel stageModel = (HoleStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        if (p.getType() == Player.COMPUTER) {
            Logger.debug("COMPUTER PLAYS");
            HoleDecider decider = new HoleDecider(model,this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        }
        else {
            Logger.debug("PLAYER PLAYS");
        }
    }
}
