
import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class DigitalDoleyFrame extends JFrame {

    //region properties
    private DoilyPanel drawing;
    private Gallery gallery;
    private JPanel settingsPanel;
    private JComboBox<Colouring> cbColor;
    private JComboBox<Colouring> cbBackground;
    private JComboBox<Tool> cbTool;
    private JCheckBox chLines;
    private JCheckBox chreflector;
    private JSlider slSectors;
    private JSlider slStroke;
    private boolean showMode;
    //endregion
    public DigitalDoleyFrame(){
        super("Digital Dolies");
    }

    /**
     * this methodd will construct how will the frame look like
     * and controls taht would bad added too and the listeners the
     * control will have ,bascially make this class alive
     * @throws IOException
     */
    public void init() throws IOException {

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1400,900);
        showMode=true;
        JPanel mainPanel=(JPanel)this.getContentPane();
        mainPanel.setLayout(new BorderLayout());
        Image penIcon;
        penIcon = ImageIO.read(getClass().getResource("pencil.png"));
        Image eraserIcon=ImageIO.read(getClass().getResource("Eraser.png"));

        // region Settings Panel
        settingsPanel=new JPanel(new GridBagLayout());
        settingsPanel.setPreferredSize(new Dimension(200,200));
        settingsPanel.setBackground(Color.WHITE);
        JLabel lbTool=new JLabel("Tool");
        JLabel lbSectors=new JLabel("Sectors");
        JLabel lbColor=new JLabel("Colour");
        JLabel lbSectorsValue=new JLabel("12");
        JLabel lbBackground=new JLabel("Background");
        JLabel lbStrokeSize=new JLabel("Stroke size");

        slSectors=new JSlider(JSlider.HORIZONTAL,1,100,12);
        slSectors.setPreferredSize(new Dimension(150,40));
        slStroke=new JSlider(1,15,5);
        slStroke.setBackground(settingsPanel.getBackground());
        slStroke.setPreferredSize(new Dimension(150,40));
        chLines=new JCheckBox("show lines",true);
        chreflector=new JCheckBox("reflector");

        setBackgroundComponent(chLines);
        setBackgroundComponent(chreflector);
        setBackgroundComponent(slSectors);
        setBackgroundComponent(slStroke);

        cbColor=new JComboBox<>();
        cbBackground=new JComboBox<>();
        this.setColuringComboBox(cbColor);
        this.setColuringComboBox(cbBackground);
        cbTool=new JComboBox<>();
        cbTool.setRenderer(new ToolItemRenderer());
        Tool penTool=new Pen("pen",penIcon);
        cbTool.addItem(penTool);
        cbTool.addItem(new Eraser("Eraser",eraserIcon));

        JButton btClear=new JButton("Clear");
        JButton btundo=new JButton("undo");
        JButton btredo=new JButton("redo");
        JButton btSave=new JButton("save");
        JButton btStartNew=new JButton("start New Doily");
        JToggleButton btAboutGallery=new JToggleButton("Show Mode",true);

        btClear.setPreferredSize(new Dimension(120,30));
        btredo.setPreferredSize(new Dimension(120,30));
        btundo.setPreferredSize(new Dimension(120,30));
        btSave.setPreferredSize(new Dimension(120,30));
        btStartNew.setPreferredSize(new Dimension(120,30));
        btAboutGallery.setPreferredSize(new Dimension(120,30));

        settingsPanel.add(lbTool,getConstraint(new Insets(0,0,8,10),0,0,1,1));
        settingsPanel.add(cbTool,getConstraint(new Insets(0,0,8,10),1,0,1,1));
        settingsPanel.add(lbStrokeSize,getConstraint(new Insets(0,0,4,0),0,1,2,1));
        settingsPanel.add(slStroke,getConstraint(new Insets(0,0,8,10),0,2,2,1));
        settingsPanel.add(lbColor,getConstraint(new Insets(0,0,8,10),0,3,1,1));
        settingsPanel.add(cbColor,getConstraint(new Insets(0,0,8,10),1,3,1,1));
        settingsPanel.add(lbBackground,getConstraint(new Insets(0,0,8,10),0,4,1,1));
        settingsPanel.add(cbBackground,getConstraint(new Insets(0,0,8,10),1,4,1,1));
        settingsPanel.add(lbSectors,getConstraint(new Insets(0,0,8,10),0,5,1,1));
        settingsPanel.add(lbSectorsValue,getConstraint(new Insets(0,0,8,10),1,5,1,1));
        settingsPanel.add(slSectors,getConstraint(new Insets(0,0,8,10),0,6,2,1));
        settingsPanel.add(chLines,getConstraint(new Insets(0,0,8,2),0,7,1,1));
        settingsPanel.add(chreflector,getConstraint(new Insets(0,0,8,2),1,7,1,1));
        settingsPanel.add(btClear,getConstraint(new Insets(0,0,10,0),0,8,2,1));
        settingsPanel.add(btundo,getConstraint(new Insets(0,0,10,0),0,9,2,1));
        settingsPanel.add(btredo,getConstraint(new Insets(0,0,10,0),0,10,2,1));
        settingsPanel.add(btSave,getConstraint(new Insets(0,0,10,0),0,11,2,1));
        settingsPanel.add(btStartNew,getConstraint(new Insets(0,0,10,0),0,12,2,1));
        settingsPanel.add(btAboutGallery,getConstraint(new Insets(0,0,10,0),0,13,2,1));
        //endregion

        //region listeners
        slSectors.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value=slSectors.getValue();
                lbSectorsValue.setText(Integer.toString(value));
                drawing.setNumberOfSectors(value);
            }
        });
        slStroke.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                drawing.changeStokeSie(slStroke.getValue());
            }
        });
        chLines.addItemListener(new ItemListener() {
                                    @Override
                                    public void itemStateChanged(ItemEvent e) {
                                        if(e.getStateChange()==ItemEvent.SELECTED)
                                            drawing.getDoilyConfig().setShowLines(true);
                                        else
                                            drawing.getDoilyConfig().setShowLines(false);

                                        repaint();
                                    }
                                });
        chreflector.addItemListener(new ItemListener() {
                                        @Override
                                        public void itemStateChanged(ItemEvent e) {
                                            if (e.getStateChange() == ItemEvent.SELECTED)
                                                drawing.getDoilyConfig().setReflected(true);
                                            else
                                                drawing.getDoilyConfig().setReflected(false);

                                            repaint();
                                        }
                                    });
                btClear.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        drawing.clearShapes();
                    }
                });
        btundo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PathShape recent=drawing.getDoilyConfig().getRecentShape();
                if(recent!=null)
                    drawing.removeShape(recent);
            }
        });
        btredo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PathShape recentUndo=drawing.getDoilyConfig().getUndoSshapes();
                if(recentUndo!=null)
                    drawing.addShape(recentUndo);
            }
        });
        btSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartNewDoily(true,true,true);
            }
        });
        btStartNew.addActionListener(new ActionListener() {
                                         public void actionPerformed(ActionEvent e) {

                                             StartNewDoily(true,false,true);
                                         }
                                     });
        cbColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Colouring chosen = (Colouring) cbColor.getSelectedItem();
                drawing.changeColour(chosen);
                drawing.clashColor();
            }
        });
        cbTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tool chosen=(Tool)cbTool.getSelectedItem();
                drawing.changeTool(chosen);
            }
        });
        cbBackground.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Colouring chosen=(Colouring)cbBackground.getSelectedItem();
                drawing.changeBackground(chosen);
                drawing.clashColor();
            }
        });
        btAboutGallery.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(btAboutGallery.isSelected()){
                    setShowMode(true);
                    btAboutGallery.setText("Show Mode");
                }else{
                    setShowMode(false);
                    btAboutGallery.setText("Delete Mode");
                }

            }
        });
        //endregion

        //region addingPanelsToFrame
        JPanel galleryPanel=new JPanel();
        galleryPanel.setBackground(Color.LIGHT_GRAY);
        galleryPanel.setPreferredSize(new Dimension(200,900));
        gallery=new Gallery(this);
        drawing=new DoilyPanel(900,900);
        mainPanel.add(settingsPanel,BorderLayout.WEST);
        mainPanel.add(gallery,BorderLayout.EAST);
        mainPanel.add(drawing, BorderLayout.CENTER);
        //endregion

        //region set the defualt settings
        cbTool.setSelectedIndex(0);
        cbColor.setSelectedIndex(1);
        cbBackground.setSelectedIndex(0);
        drawing.changeColour((Colouring)cbColor.getSelectedItem());
        drawing.changeTool((Tool)cbTool.getSelectedItem());
        drawing.changeBackground((Colouring)cbBackground.getSelectedItem());
        //endregion
        //show the frame
        this.setVisible(true);
    }

    /**
     *will set the default items for comobo box
     * that are showing colours
     * @param cb reference of the comobox that the items will be added to
     */
    public void setColuringComboBox(JComboBox<Colouring> cb){
        cb.setRenderer(new ColourItemRender());
        cb.setPreferredSize(new Dimension(100,30));
        cb.addItem(new Colouring(Color.BLACK,"Black"));
        cb.addItem(new Colouring(Color.WHITE,"White"));
        cb.addItem(new Colouring(Color.RED,"Red"));
        cb.addItem(new Colouring(Color.ORANGE,"Orange"));
        cb.addItem(new Colouring(Color.BLUE,"Blue"));
        cb.addItem(new Colouring(Color.GREEN,"Green"));
        cb.addItem(new Colouring(Color.PINK,"Pink"));
        cb.addItem(new Colouring(Color.YELLOW,"Yellow"));
        cb.addItem(new Colouring(new Color(165,42,42),"Brown"));
    }

    /**
     * will change the backgroun of component
     * @param C instance of component to change backgroun for
     */
    public void setBackgroundComponent(Component C){
        C.setBackground(settingsPanel.getBackground());
    }

    /**
     * this function will set the constraint for element that would
     * be added to panel with gridbagLayout
     * @param bounds margin
     * @param c    column number
     * @param r    row number
     * @param wSize row Span
     * @param hSize columns span
     * @return  instacne of constraint with specification passed
     */
    public GridBagConstraints getConstraint(Insets bounds,int c,int r,int wSize,int hSize){
        GridBagConstraints cons=new GridBagConstraints();
        cons.insets=bounds;
        cons.gridx=c;
        cons.gridy=r;
        cons.gridheight=hSize;
        cons.gridwidth=wSize;
        cons.fill=GridBagConstraints.VERTICAL;
        return cons;
    }

    /**
     * will start new Panel by showing the adding the
     * element again to the frame. it could be blank doily
     * or one that was store in the gallery
     * @param remove true will indicate that we have to remove all shapes drawn and start blank one
     * @param Save   true will be when the user clicked save so it would save the current doilypanel instance as an image
     * @param startNew true , when we want to start new doily (blank) false, when we want to use
     *                 the current instance of drawing settings
     */
    public void StartNewDoily(boolean remove,boolean Save,boolean startNew){

        if(remove)
            this.remove(drawing);

        if(Save)
            gallery.AddTogallery(drawing);
        if(startNew) {
            drawing = new DoilyPanel(900, 900);
            //in case it's new panel I will set the new doily setting based on the current chosen setting
            drawing.changeColour((Colouring) cbColor.getSelectedItem());
            drawing.changeTool((Tool) cbTool.getSelectedItem());
            drawing.changeBackground((Colouring) cbBackground.getSelectedItem());
            drawing.getDoilyConfig().setNumberOfSectors(slSectors.getValue());
            drawing.getDoilyConfig().setReflected(chreflector.isSelected());
            drawing.getDoilyConfig().setShowLines(chLines.isSelected());
        }else {
            //otherwise if it not new I will set the setting in the settings panel  to what actually the current setting ofr the doily
            cbTool.setSelectedItem(drawing.getDoilyConfig().getCurrentTool());
            cbBackground.setSelectedItem(drawing.getDoilyConfig().getBackground());
            cbColor.setSelectedItem(drawing.getDoilyConfig().getChosenColour().getColor());
            slSectors.setValue(drawing.getDoilyConfig().getNumberOfSectors());
            chreflector.setSelected(drawing.getDoilyConfig().isReflected());
            chLines.setSelected(drawing.getDoilyConfig().isShowLines());
        }

        //remove the old Panel and create new one with new doily in the Center
        JPanel P=new JPanel(new BorderLayout());
        this.getContentPane().removeAll();
        P.add(this.settingsPanel,BorderLayout.WEST);
        P.add(this.drawing,BorderLayout.CENTER);
        P.add(this.gallery,BorderLayout.EAST);

        setContentPane(P);
        setVisible(true);
    }
    public boolean isShowMode(){
        return showMode;
    }
    public void setShowMode(boolean b){
        showMode=b;
    }
    /**
     * will replace the current instance of panel  with
     * one passed to it
     * @param panel reference to the panel to be shown
     */
    public void showDoily(DoilyPanel panel){

        try {
            drawing=new DoilyPanel();
            drawing=(DoilyPanel)panel.clone();
            drawing.repaint();
            StartNewDoily(false,false,false);
        }catch (Exception e){

        }
    }


}

