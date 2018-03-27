
import java.awt.*;
import java.awt.geom.GeneralPath;
/**
 * this class is intended for the Peb tool that
 * can be applied into the doily , Pen class extends
 * Tool an Abstract class called , so in this class the implementation
 * of the abstract methods is done
 */
public class Pen extends Tool {

    public Pen(String name, Image image){
        super(name,image);
    }

    /**
     * will add drawing path and set the starting point and added the path
     * to into the passed doilypanel instance and then inovke the repaint methods
     * for the doilyPanel and it will set store the PathShape in current property
     * @param x x coordinates
     * @param y  y coordinate
     * @param panel  instance of the doily where the drawing path will be sketched
     */
    @Override
    public void addShape(int x, int y, DoilyPanel panel) {
        setCurrent(new PathShape(new GeneralPath(),panel.getDoilyConfig().getChosenColour().getColor(),panel.getDoilyConfig().isReflected(),false,panel.getDoilyConfig().getStrokeSize()));
        getCurrent().getPath().moveTo(x,y);
        panel.currentPoint=new Point(x,y);
        panel.addToShapes(getCurrent());
        panel.repaint();
    }
    /**
     * this method will add point to the PathShape which store
     * in the Current property
     * @param x x coordinates
     * @param y y coordinates
     * @param panel
     */
    @Override
    public void moveShape(int x, int y, DoilyPanel panel){
        getCurrent().getPath().lineTo(x,y);
        panel.currentPoint=new Point(x,y);
        panel.repaint();
    }


}
