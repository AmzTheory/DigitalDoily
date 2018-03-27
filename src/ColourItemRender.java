import javax.swing.*;
import java.awt.*;

/**
 * This class is intended to construct
 * how the Colour Combo Box Item will look like
 */
public class ColourItemRender implements ListCellRenderer<Colouring> {

    public ColourItemRender(){

    }

    /**
     *This function will return the compenet that will be displayed
     * for every item in the comobox
     * @return
     */
    @Override
    public Component getListCellRendererComponent(JList list, Colouring value, int index, boolean isSelected, boolean cellHasFocus) {
        Colouring C=value;

        //I create the panel that contain label with colour name and small panel with background of the colour
        JPanel panel=new JPanel(new FlowLayout());
        JLabel lbColorName=new JLabel(C.getName());
        JPanel colourPanel=new JPanel();

        colourPanel.setPreferredSize(new Dimension(10,10));
        colourPanel.setBackground(C.getColor());

        panel.add(lbColorName);
        panel.add(colourPanel);

        return panel;
    }
}
