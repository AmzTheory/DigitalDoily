import java.util.Stack;

/**
 * this class is intended to set the configuration
 * of every doily .From the number of sectors to the background
 * and tool colour
 */
public class Config implements Cloneable{
    private boolean reflected;  //boolean sate whether draw shape reflected
    private int numberOfSectors; //number of sectors shown in the doily
    private Colouring chosenColour;//current drawing colour
    private Tool currentTool;//instance of the current tool where Pen or eraser
    private boolean showLines;//boolean that states whether show sectore line or not
    private int strokeSize;//stroke size of the tool
    private Colouring background;//current doily background colour
    private Stack<PathShape> recentShapes;//store shape drawn so it can easy to undo Shapes
    private Stack<PathShape> undoShapes;//store the undo shapes so it can be ease to redo shapes

    /**
     * in the constructor instance of configuration will be
     * instantiated with default settings
     */
    public Config(){
        this.reflected=false;
        this.showLines=true;
        this.strokeSize=5;
        this.numberOfSectors=12;
        this.recentShapes=new Stack<>();
        this.undoShapes=new Stack<>();
    }

    /**
     * @return the current stroke size
     */
    public int getStrokeSize() {
        return strokeSize;
    }

    /**
     * will update the value of the store size for the tool
     * @param strokeSize value for the update value
     */
    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    /**
     * @return true , it the shape have to be reflected ,otherwise false
     */
    public boolean isReflected() {
        return reflected;
    }

    /**
     * update the setting for the reflection
     * @param reflected ture , to reflect   ,otherwise don't reflect
     */
    public void setReflected(boolean reflected) {
        this.reflected = reflected;
    }

    /**
     * @return the number of sector lines
     */
    public int getNumberOfSectors() {
        return numberOfSectors;
    }

    /**
     * update the value of number sectors (as slider change value )
     * @param numberOfSectors new value
     */
    public void setNumberOfSectors(int numberOfSectors) {
        this.numberOfSectors = numberOfSectors;
    }

    /**
     * @return current chosen drawing colour
     */
    public Colouring getChosenColour() {
        return chosenColour;
    }

    /**
     * update the drawing colour
     * @param chosenColour instance of the new colour
     */
    public void setChosenColour(Colouring chosenColour) {
        this.chosenColour = chosenColour;
    }

    /**
     * @return the current instance fo tool either Pen or eraser
     */
    public Tool getCurrentTool() {
            return currentTool;
    }

    /**
     * change the tool form pen to Erase and vise verse
     * @param currentTool
     */
    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    /**
     * @return true if the line have to be shown, false sector line have to be hidden
     */
    public boolean isShowLines() {
        return showLines;
    }

    /**
     * @param showLines true  , to show the sectors line  otherwise don't shown them
     */
    public void setShowLines(boolean showLines) {
        this.showLines = showLines;
    }

    /**
     * whenever shape is drawn regardless of any tool used it would
     * add it to the stack of recent shapes so it make really easy
     * to undo shapes
     * * @param shape instance of the recent drawing
     */
    public void addRecentShape(PathShape shape){
        this.recentShapes.push(shape);
    }

    /**
     * this method will be invoked when clearing the doily , to clear both
     * undo and recent stack
     */
    public void deleteRecent(){
        this.recentShapes.clear();
        this.undoShapes.clear();
        this.recentShapes=new Stack<>();
        this.undoShapes=new Stack<>();
    }

    /**
     * @return the instance of current background
     */
    public Colouring getBackground() {
        return background;
    }

    /**
     * update the background of the doily
     * @param background instance of the new background
     */
    public void setBackground(Colouring background) {
        this.background = background;
    }

    /**
     * will return the recent shape by poping the recentstack ,so we can perform
     * undo by returning the instance of the path that have to be deleted form the
     * doily
     * @return instance that have to be delete formt he doily
     */
    public PathShape getRecentShape() {
        if(!this.recentShapes.isEmpty())
            return this.recentShapes.pop();

        return null;
    }

    /**
     * when path is undo from the doily it will be added to this stack , in order to
     * make ir easy for me to redo if the user wanted to do so
     * @param shape
     */
    public void addUndoShapes(PathShape shape){
        this.undoShapes.push(shape);
    }

    /**
     * will return most recent shape the undo functionality was applied on
     * from the stack by the poping element from the stack so it can be drawn
     * again
     * @return instance of the shape ,so we can perform redo
     */
    public PathShape getUndoSshapes(){
        if(!this.undoShapes.isEmpty())
            return this.undoShapes.pop();

        return null;
    }

    /**
     * perform deep cloning in the current instance and return
     * the cloneable object
     * @return cloneable instance
     * @throws CloneNotSupportedException
     */
    public Object clone()throws CloneNotSupportedException{
        super.clone();
        Config cloneConfig=new Config();
        cloneConfig.setChosenColour((Colouring)this.chosenColour.clone());
        cloneConfig.setBackground((Colouring)this.background.clone());
        cloneConfig.numberOfSectors=this.getNumberOfSectors();
        cloneConfig.recentShapes=new Stack<>();
        cloneConfig.recentShapes.addAll(this.recentShapes);
        cloneConfig.undoShapes=new Stack<>();
        cloneConfig.undoShapes.addAll(this.undoShapes);
        cloneConfig.setStrokeSize(this.getStrokeSize());
        cloneConfig.reflected=this.isReflected();
        cloneConfig.showLines=this.isShowLines();
        cloneConfig.setCurrentTool(this.getCurrentTool());
        return cloneConfig;
    }
}
