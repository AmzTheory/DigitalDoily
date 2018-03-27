import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this class is intended to handle the mouse motion
 * event that occur for the doilyPanel
 */
public class PanelListener extends MouseAdapter {
    boolean pressed=false;//used to indicate if the path is still being drawn
    DoilyPanel panel; //this instance used to be passed to the tool so it know where to perform the drawing and erasing
    public PanelListener(DoilyPanel panel)
    {
        this.panel=panel;
    }

    /**
     * ask the tool to start Path in the doily and also
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e){
        panel.Entered=true;
        panel.currentPoint=new Point(e.getX(), e.getY());
        panel.repaint();
    }

    /**
     * change the value of Entered and it stop showing the icon of the cursor
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        panel.Entered=false;
        panel.repaint();
    }
    /**
     * ask the tool to start Path in the doily and also  ,and if the tool is not throws
     * exception and alert the user
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pressed=true;
        Tool tool=panel.getDoilyConfig().getCurrentTool();
        try {

            if (tool == null)
                throw new NoToolException();
            else
                panel.getDoilyConfig().getCurrentTool().addShape(e.getX(), e.getY(), panel);
        }catch (NoToolException E){
            JOptionPane.showMessageDialog(panel,E.getMessage());
        }
    }

    /**
     * if it's current state of drawing to would add to the path by asking th tool
     * to do so
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e){
        if(pressed)
            panel.getDoilyConfig().getCurrentTool().moveShape(e.getX(), e.getY(),panel);
    }

    /**
     * if the mouse release to would change pressed to false
     * and the tool would stop drawing until it's panel is pressed
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e){
        pressed=false;
    }

}
