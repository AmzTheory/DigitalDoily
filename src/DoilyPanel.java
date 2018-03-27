import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * DoilyPanel class is intented to be the panel that
 * the doily will be paint int. This class object of config
 * class that would store all of the setting for
 * @see
 */
public class DoilyPanel extends JPanel implements Cloneable {


    private Point Center;
    private ArrayList<PathShape> shapes;
    private Config doilyConfig;
    public Point currentPoint;
    public boolean Entered;
    public DoilyPanel(){

    }

    /**
     * constructor fpr DoilyPanel cbject  where listener and
     * properties are initalized
     * @param W width of the panel that holds the doily
     * @param H height of the panel that holds the doily
     */
    public DoilyPanel(int W, int H){
        this.setBackground(Color.BLACK);
        this.setSize(W,H);
        this.setCenter(new Point(W/2,H/2));
        shapes=new ArrayList<>();
        PanelListener listener=new PanelListener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        doilyConfig=new Config();
        Entered=false;

    }

    /**
     * I overide the paintComponent method so I can
     * draw the sector line and the drawing sketched by
     * the user a
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D G=(Graphics2D)g;


        Shape line;
        double count=0;
        double degrees=360.0/(getNumberOfSectors());

        AffineTransform orginalTransform=G.getTransform();

        //region <drawLines>
        for(int i=0;i<shapes.size();i++){
            count=0.0;
            PathShape current=shapes.get(i);

            for(;(count)<=360.0;count+=degrees){
                if(!current.isErase())
                    G.setColor(current.getPathColor());
                else
                    G.setColor(getDoilyConfig().getBackground().getColor());

                G.setStroke(new BasicStroke(current.getStrokeSize()));
                G.draw(current.getPath());

                if(current.isReflected()) {
                    AffineTransform transformFirst=AffineTransform.getTranslateInstance(-450,-450);
                    AffineTransform transformSec=AffineTransform.getScaleInstance(-1,1);
                    AffineTransform transformThird=AffineTransform.getTranslateInstance(450,450);
                    Shape reflected=transformFirst.createTransformedShape(current.getPath());
                    reflected=transformSec.createTransformedShape(reflected);
                    reflected=transformThird.createTransformedShape(reflected);
                    G.draw(reflected);

                }
                G.rotate(Math.toRadians(degrees),getCenter().getX(), getCenter().getY());

            }
        }
        //endregion
        //region <drawTheTool>
        G.setTransform(orginalTransform);
        if(Entered) {
            Image image = getDoilyConfig().getCurrentTool().getToolImage();
            image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            G.drawImage(image, (int) currentPoint.getX(), (int) currentPoint.getY(), null);
        }
        //endregion
        //region <drawSectorLines>
        count=0.0;
        line = new Line2D.Double(450, 450, 0 , 0);
        if(getDoilyConfig().isShowLines()) {
            for (; (count) <= 360.0; count += degrees) {
                G.setColor(Color.WHITE);
                G.setStroke(new BasicStroke(5));
                G.draw(line);
                G.rotate(Math.toRadians(degrees), getCenter().getX(), getCenter().getY());
            }
        }
        //endregion
    }
    //region <changeConfiguration

    /**
     * will set the passed instance configuration
     * to the current instance
     * @param c  the new instance of configuration
     */
    public void setDoilyConfig(Config c){
        doilyConfig=c;
    }

    /**
     * @return get the current instance of configuration for this current doily
     */
    public Config getDoilyConfig() {
        return doilyConfig;
    }
    /**
     * will get the instance of the configuration and
     * update the line stroke
     * @param value the new value for line stroke
     */
    public void changeStokeSie(int value){
        getDoilyConfig().setStrokeSize(value);
    }

    /**
     *
     * @return will get the current set number of sectors
     */
    private int getNumberOfSectors() {
        return getDoilyConfig().getNumberOfSectors();
    }

    /**
     * will update the value number of sectors
     * @param numberOfSectors the new value of number of sectors
     */
    public void setNumberOfSectors(int numberOfSectors) {
        getDoilyConfig().setNumberOfSectors(numberOfSectors);
        repaint();
    }

    /**
     * will update the drawing colour
     * @param chosenColour  pass reference of the color to be chosen
     */
    public void changeColour(Colouring chosenColour){
        getDoilyConfig().setChosenColour(chosenColour);
    }

    /**
     * will change the tool
     * @param tool reference of the new colour chosen
     */
    public void changeTool(Tool tool){
        getDoilyConfig().setCurrentTool(tool);
    }

    /**
     * will change the background of the panel
     * @param C
     */
    public void changeBackground(Colouring C){
        getDoilyConfig().setBackground(C);
        this.setBackground(getDoilyConfig().getBackground().getColor());
    }
    //endregion
    //region ManageDrawing

    /**
     * @return this method will return the arraylist of shape (pathes)
     * drawn in the doily
     */
    public ArrayList<PathShape> getShapes() {
        return shapes;
    }

    /**
     * it would set the arraylist of shapes for the doily  (used when cloning)
     * @param shapes reference of arrayList of shapes
     */
    public void setShapes(ArrayList<PathShape> shapes) {
        this.shapes = shapes;
    }

    /**
     * this method it would instance of shape(Path) to the list of shapes
     * and paint the panel again
     * @param shape
     */
    public void addShape(PathShape shape){
        this.shapes.add(shape);
        addToRecentShapes(shape);
        repaint();
    }

    /**
     * this methods will be called to clear all of
     * the drawing (shapes) in the doily , and paint
     * the doily again
     */
    public void clearShapes(){
        this.shapes.clear();
        this.shapes=new ArrayList<>();
        getDoilyConfig().deleteRecent();
        repaint();
    }

    /**
     *
     * @param shape
     */
    private void addToRecentShapes(PathShape shape){
        this.getDoilyConfig().addRecentShape(shape);
    }

    /**
     * this will be done when undo functionality is requested , it
     * would remove the pass PathShape from ArrayList of shapes
     * and then add it to the undoShape by using instance
     * of the configuration
     * @param shape instance of shape to be deleted
     */
    public void removeShape(PathShape shape){
        getDoilyConfig().addUndoShapes(shape);
        this.shapes.remove(shape);
        repaint();
    }

    /**
     * this method would be called from tool , it would
     * pass instance of path to drawn and then paint again
     * at he end
     * @param path
     */
    public void addToShapes(PathShape path){
        this.shapes.add(path);
        addToRecentShapes(path);
        repaint();
    }
    //endregion

    /**
     * @return it would return image instance of the doily
     * so it can be stores in the gallery
     */
    public Image getImage(){
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = image.createGraphics();
        print( gd );
        return  image;
    }

    /**
     * @return the Center point for the doily
     */
    public Point getCenter() {
        return Center;
    }


    /**
     * set the Center point for the doily
     * @param center new value for the Center
     */
    private void setCenter(Point center) {
        Center = center;
    }

    /**
     * this methods is just to check if the background and drawing colour
     * clash it would inform the user about the clash
     */
    public void clashColor(){
        Config con=getDoilyConfig();
        try {
            if (con.getBackground().getColor() == con.getChosenColour().getColor())
                JOptionPane.showMessageDialog(this, "just note you've set the same colour  for paint and background");
        }catch (Exception E){

        }
    }

    /**
     * this method will return clone copy of the
     * current instacne ,which will help when
     * selecting to show an image from the gallery
     * @return cloned copy of the current instance
     * @throws CloneNotSupportedException
     */
    public Object clone()throws CloneNotSupportedException{
        super.clone();
        ArrayList<PathShape> cloneShapes=new ArrayList<>(this.getShapes());
        DoilyPanel clonedDoily=new DoilyPanel(900,900);
        clonedDoily.setDoilyConfig((Config)getDoilyConfig().clone());
        clonedDoily.changeBackground(this.getDoilyConfig().getBackground());
        clonedDoily.setCenter(new Point(this.getCenter()));
        clonedDoily.setShapes(cloneShapes);
        return clonedDoily;
    }

}
