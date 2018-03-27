import java.awt.*;

/**
 * this class is intented to represent colour instance
 * which can be used when painting colour into the doily
 */
public class Colouring implements Cloneable {
    private Color color;
    private String name;

    /**
     * this constructor will set the name
     * and the Colour instance
     * @param C reference of color instance
     * @param name name of the colour
     */
    public Colouring(Color C, String name) {
        this.color = C;
        this.name = name;
    }

    /**
     * @return will return the instance of colour for the object
     */
    public Color getColor() {
        return color;
    }

    /**
     * will return the name of the inovked instance
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *will apply deep cloneing to the current object
     * @return new instance of coluring with same attributes the current
     * instance has
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        Color chosen = new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
        Colouring clone = new Colouring(chosen, this.getName());
        return clone;
    }
}
