package view;

import boardifier.model.GameStageModel;
import boardifier.view.*;
import model.HoleStageModel;

public class HoleStageView extends GameStageView {
    public HoleStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        HoleStageModel model = (HoleStageModel)gameStageModel;

        addLook(new HoleBoardLook(300, model.getBoard()));
        addLook(new BlackPawnPotLook(model.getBlackPot()));
        addLook(new RedPawnPotLook(320, 80,model.getRedPot()));

        for(int i=0;i<4;i++) {
            addLook(new PawnLook(25,model.getBlackPawns()[i]));
            addLook(new PawnLook(25, model.getRedPawns()[i]));
        }

        addLook(new TextLook(24, "0x000000", model.getPlayerName()));

        /* Example to show how to set a global container to layout all looks in the root pane
           Must also uncomment lines in HoleStageFactory and HoleStageModel
        ContainerLook mainLook = new ContainerLook(model.getRootContainer(), -1);
        mainLook.setPadding(10);
        mainLook.setVerticalAlignment(ContainerLook.ALIGN_MIDDLE);
        mainLook.setHorizontalAlignment(ContainerLook.ALIGN_CENTER);
        addLook(mainLook);

         */
    }
}
