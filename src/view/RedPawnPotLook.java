package view;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class RedPawnPotLook extends GridLook {

    // the array of rectangle composing the grid
    private Rectangle[] cells;

    public RedPawnPotLook(int height, int width,  ContainerElement element) {
        super(height/4, width, element, -1, 1, Color.BLACK);

    }

    protected void render() {
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_CENTER);
        cells = new Rectangle[4];
        // create the rectangles.
        for(int i=0;i<4;i++) {
            cells[i] = new Rectangle(colWidth, rowHeight, Color.WHITE);
            cells[i].setStrokeWidth(3);
            cells[i].setStrokeMiterLimit(10);
            cells[i].setStrokeType(StrokeType.CENTERED);
            cells[i].setStroke(Color.valueOf("0x333333"));
            cells[i].setX(borderWidth);
            cells[i].setY(i*rowHeight+borderWidth);
            addShape(cells[i]);
        }
    }

}
