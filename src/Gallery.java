import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this class is intend to represent the gallery where
 * the doily will be store and they can shown and deleted
 * from the gallery . doily are shown using the Jlist and
 * using the context menu of every item it can be shown or deleted
 */
public class Gallery extends ScrollPane {
    JList<DoilyPanel> listOfDoily;
    DefaultListModel<DoilyPanel> model;
    DigitalDoleyFrame frame;

    /**
     * this constructor will set the main properties of the gallyer
     * size and how sett rendered item and the model
     * @param frame
     */
    public Gallery(DigitalDoleyFrame frame){
        setPreferredSize(new Dimension(300,900));
        model=new DefaultListModel<>();
        listOfDoily=new JList<>();
        listOfDoily.setModel(model);
        listOfDoily.setCellRenderer(new GalleryItemCell());
        this.add(listOfDoily);
        this.frame=frame;
        listOfDoily.addMouseListener(new ListItemListener(frame,model,listOfDoily));

    }

    /**
     * add instance of the passed doily to the gallery Jlist
     * @param doily
     */
    public void AddTogallery(DoilyPanel doily){
        int size=model.size();
        model.addElement(doily);
    }
}
