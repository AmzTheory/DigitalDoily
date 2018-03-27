import javax.swing.*;
import java.awt.*;

/**
 * this class is intended to design how will item
 * in the tool combo box will look like
 */
public class ToolItemRenderer extends JPanel implements ListCellRenderer<Tool> {
    String name;
    Image image;
    @Override
    public Component getListCellRendererComponent(JList<? extends Tool> list, Tool value, int index, boolean isSelected, boolean cellHasFocus) {
        Tool current=value;

        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(65,30));
        image=current.getToolImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        name=current.getName();


        return this;
    }

    /**
     * in the method I will draw the tool image and string of the tool pen
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.removeAll();
        if(image!=null)
            g.drawString(name,5,20);
            g.drawImage(image,48,5,null);
    }
}
