package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Pawn;

public class PawnLook extends ElementLook {
    private Circle circle;
    private int radius;

    public PawnLook(int radius, GameElement element) {
        super(element);

        this.radius = radius;
        render();
    }

    @Override
    public void onSelectionChange() {
        Pawn pawn = (Pawn)getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(3);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(Color.valueOf("0x333333"));
        }
        else {
            circle.setStrokeWidth(0);
        }
    }

    @Override
    public void onFaceChange() {
    }

    protected void render() {
        Pawn pawn = (Pawn)element;
        circle = new Circle();
        circle.setRadius(radius);
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            circle.setFill(Color.BLACK);
        }
        else {
            circle.setFill(Color.RED);
        }

        addShape(circle);
        // NB: text won't change so no need to put it as an attribute
        Text text = new Text(String.valueOf(pawn.getNumber()));
        text.setFont(new Font(24));
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            text.setFill(Color.valueOf("0xFFFFFF"));
        }
        else {
            text.setFill(Color.valueOf("0x000000"));
        }
        Bounds bt = text.getBoundsInLocal();
        text.setX(-bt.getWidth()/2);
        // since numbers are always above the baseline, relocate just using the part above baseline
        text.setY(text.getBaselineOffset()/2-4);
        addShape(text);
    }
}
