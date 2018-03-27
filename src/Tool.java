import java.awt.*;

public abstract class Tool implements Cloneable  {

    public PathShape getCurrent() {
        return current;
    }

    public void setCurrent(PathShape current) {
        this.current = current;
    }

    private String name;
    private PathShape current;
    private Image toolImage;

    public Tool(String name, Image Image) {
        this.name = name;
        this.toolImage = Image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getToolImage() {
        return toolImage;
    }

    public void setToolImage(Image toolImage) {
        this.toolImage = toolImage;
    }

    public abstract void addShape(int x, int y, DoilyPanel panel);
    public abstract void moveShape(int x, int y, DoilyPanel panel);

}
