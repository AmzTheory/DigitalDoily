import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * this class is intended to design how will every item
 * in the gallery Jlist Item will look like
 */
public class GalleryItemCell extends JPanel implements ListCellRenderer<DoilyPanel> {
    Image scaledInstance=null;
    public GalleryItemCell(){
        setPreferredSize(new Dimension(300,300));
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends DoilyPanel> list, DoilyPanel value, int index, boolean isSelected, boolean cellHasFocus) {

        // here the doily panel called getImage method to convert the doilypanel to image
        DoilyPanel instance=value;
        scaledInstance=instance.getImage();
        repaint();
        //here I rescale the instance of the image so it can fit he cell
        scaledInstance=scaledInstance.getScaledInstance(280,280,BufferedImage.SCALE_DEFAULT);
        Border border=BorderFactory.createLineBorder(Color.RED,10,true);
        this.setBorder(border);
        return this;
    }

    /**
     * doily will be painted in the cell
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.removeAll();
        Graphics2D G=(Graphics2D)g;
        if(scaledInstance!=null)
            G.drawImage(scaledInstance,10,10,null);
    }

}
