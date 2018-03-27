import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this class is intended to handle Jlist events the
 * doily can be shown
 */
public class ListItemListener extends MouseAdapter {
    private JList<DoilyPanel> list;
    private DigitalDoleyFrame frame;
    private DefaultListModel<DoilyPanel> model;
    public ListItemListener(DigitalDoleyFrame frame,DefaultListModel<DoilyPanel> model,JList<DoilyPanel> list){
        this.frame=frame;
        this.model=model;
        this.list=list;

    }
    /**
     * so if the Jlist is pressed it would delete or show
     * according to the current mode
     */
    public void mousePressed(MouseEvent e){

        if(frame.isShowMode())
            frame.showDoily(model.getElementAt(getIndex(e.getY())));
        else
            model.removeElementAt(getIndex(e.getY()));

    }

    /**
     * this method is just tackle the problem of
     * how can I find the index of element that I want
     * apply the functinally on ,so by dividing the y coordinate of
     * the mouse event by 300 I can get the index
     * @param y y axis coordinates (mouse Event y)
     * @return
     */
    public static int getIndex(int y){
        int index=y/300;
        return index;
    }
}