import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * this class is intented to reperesnt each shape drawn
 * in the doily . List of this class will be associated with
 * doilyPanel shapes what will be shown
 */
public class PathShape implements Cloneable {
    GeneralPath path=new GeneralPath();
    Color pathColor;
    private boolean reflected;
    boolean erase;
    private int strokeSize;


    public PathShape(){

    }

    /**
     * this constructor will initiate the main properties
     * @param path  instantiate the path for the instance
     * @param pathColor  set the color for the path
     * @param reflected set whether this shape will be reflected or not
     * @param erase whether is's erase shape or not
     * @param strokeSize  the size of the shape
     */
    public PathShape(GeneralPath path, Color pathColor,boolean reflected,boolean erase,int strokeSize) {
        this.path = path;
        this.pathColor = pathColor;
        this.reflected=reflected;
        this.erase=erase;
        this.strokeSize=strokeSize;
    }

    /**
     * change the shape type from erase to drawing or vise versa
     * @param erase idicate where the instace have to be erase shape or no
     */
    public void setErase(boolean erase) {
        this.erase = erase;
    }

    /**
     * @return the stroke width of shape
     */
    public int getStrokeSize() {
        return strokeSize;
    }

    /**
     * @return is it refelected or not
     */
    public boolean isReflected() {
        return reflected;
    }

    /**
     * @returnr the instance of path
     */
    public GeneralPath getPath() {
        return path;
    }

    /**
     * return the colour of the path
     * @return
     */
    public Color getPathColor() {
        return pathColor;
    }

    /**
     * @return true if it's erase path ,otherwise it's true
     */
    public boolean isErase() {
        return erase;
    }

    /**
     * perfom deep clone on the current object
     * @return the cloneable instance
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        Color C=this.getPathColor();
        PathShape shape=new PathShape();
        shape.path=new GeneralPath(this.getPath());
        shape.pathColor=new Color(C.getRed(),C.getGreen(),C.getBlue());
        shape.erase=this.isErase();
        shape.reflected=this.reflected;
        return shape;
    }
}
