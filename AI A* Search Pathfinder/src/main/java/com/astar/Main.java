package com.astar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;

//By: Brandon Beckwith

/*
    Java GUI code was created using Java Swing Documentation:
    https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html

    The A Star algorithm was created using the textbook and lecture slides
 */
public class Main {
    private static final int SIZE = 15;
    private static final JButton RESET_B = new JButton();
    private static final JSlider RAND_SLIDER = new JSlider();
    private static final Board BOARD = new Board(SIZE);
    private static final BoardPoints POINTS = new BoardPoints();
    private static SpaceButton[][] buttons;
    private static final JFrame FRAME = new JFrame("A*");

    /**
     * Main class used to run the program
     * @param args
     */
    public static void main (String[] args){
        Main.mainPanel();
    }

    private static void mainPanel(){

        //Initialize the buttons and generate a board
        buttons = new SpaceButton[SIZE][SIZE];
        BOARD.generateBoard(0.1f);

        //Setup the main frame
        FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Initialize the controls and their visuals
        initControls();

        //Initialize the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Colors.Space);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Setup the controls
        JPanel controls = new JPanel();
        controls.setBackground(Colors.Space);
        controls.setForeground(Colors.SpaceBorder);
        controls.add(RESET_B);
        controls.add(RAND_SLIDER);
        controls.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        //Setup the button's grid
        JPanel field = new JPanel();
        GridLayout layout = new GridLayout(SIZE, SIZE);
        layout.setHgap(0);
        layout.setVgap(0);
        field.setLayout(layout);
        field.setBackground(Color.DARK_GRAY);
        RESET_B.setBorder(BorderFactory.createLineBorder(Colors.SpaceBorder, 5, true));

        //Generate the field of buttons
        for (int i=0; i < SIZE; i ++){
            for (int j=0; j < SIZE; j ++) {
                buttons[i][j] = new SpaceButton(BOARD.getSpace(i,j));
                buttons[i][j].update();
                buttons[i][j].addActionListener(buttons[i][j]);
                field.add(buttons[i][j]);
            }
        }

        //Add the controls and field panel to the main panel
        mainPanel.add(controls);
        mainPanel.add(field);

        //Set, pack and show the main frame
        FRAME.setContentPane(mainPanel);
        FRAME.pack();
        FRAME.setSize(600,700);
        FRAME.setVisible(true);
    }

    /**
     * Initialize the controls
     */
    private static void initControls(){

        /* Button Section */

        RESET_B.setText("Reset");
        RESET_B.setBackground(Colors.SpaceBorder);
        RESET_B.setForeground(Colors.Text);

        //Listen for a button press, then reset everything
        RESET_B.addActionListener(e -> {
            BOARD.resetState();
            POINTS.reset();

            updateButtons();
        });


        /* Slider section */

        //Set up the slider's visuals and labels
        RAND_SLIDER.setMajorTickSpacing(10);
        RAND_SLIDER.setValue(10);
        RAND_SLIDER.setPaintLabels(true);
        RAND_SLIDER.setPreferredSize(new Dimension(350, 75));
        RAND_SLIDER.setBackground(Colors.Space);
        RAND_SLIDER.setForeground(Colors.SpaceBorder);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i=0; i <= 10; i++){
            int val = i*10;
            JLabel tLabel = new JLabel(Integer.toString(val) + "%");
            tLabel.setForeground(Colors.Text);
            labels.put(val, tLabel);
        }

        RAND_SLIDER.setLabelTable(labels);
        RAND_SLIDER.setPaintTicks(true);

        //Detect when the slider changes
        RAND_SLIDER.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();

            //Reset and regenerate
            BOARD.resetState();
            BOARD.generateBoard(((float) slider.getValue()) / 100f);
            POINTS.reset();
            updateButtons();
        });
    }

    /**
     * Updates all the buttons
     */
    private static void updateButtons(){
        for (int i=0; i < SIZE; i ++){
            for (int j=0; j < SIZE; j ++) {
                buttons[i][j].update();
            }
        }
        //Redraw the frame to refresh everything
        FRAME.repaint();
    }

    /**
     * Keeps track of starting and ending points
     */
    private static class BoardPoints {
        Point start, end;

        /**
         * Resets start and end to null
         */
        void reset() {
            this.start = null;
            this.end = null;
        }
    }


    private static class SpaceButton extends JButton implements ActionListener{

        final Space space;

        /**
         * Standard initializer
         * @param space The space the button represents
         */
        private SpaceButton(Space space){
            this.space = space;
            this.setPreferredSize(new Dimension(50, 50));
            this.setOpaque(true);
            this.setForeground(Colors.Text);
            this.setBorder(BorderFactory.createLineBorder(Colors.SpaceBorder, 1, false));

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    SpaceButton thisSpace = (SpaceButton) e.getComponent();
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        //Don't do anything when the user clicks a blocked area
                        thisSpace.space.swapType();
                    }
                    else if (e.getButton() == MouseEvent.BUTTON1) {

                        //If the path has already be set, reset it
                        if (POINTS.end != null && POINTS.start != null) {

                            //Reset everything
                            POINTS.reset();
                            BOARD.resetState();

                        } else if (POINTS.start == null) {

                            //Add the starting position
                            if (thisSpace.space.getType() == SpaceType.EMPTY) {
                                POINTS.start = thisSpace.space.getPoint();
                                thisSpace.space.setType(SpaceType.START);
                            }

                        } else {

                            if (thisSpace.space.getType() == SpaceType.EMPTY) {
                                //Add the ending position
                                POINTS.end = thisSpace.space.getPoint();

                                //Run A Star and get the path
                                ArrayList<Space> path = AStar.findPath(BOARD, POINTS.start, POINTS.end);

                                //If there isn't a path, fail and show the user a dialog box
                                if (path.isEmpty()) {

                                    JOptionPane.showMessageDialog(FRAME, "Path not found!");
                                    System.out.println("Failed to find path");

                                } else {

                                    //If there is a path, print it to the console
                                    StringBuilder out = new StringBuilder();

                                    for (Space space : path) {
                                        // Update the spaces to reflect the path
                                        if (space.getType() == SpaceType.EMPTY)
                                            space.setType(SpaceType.PATH);

                                        out.append(space.getPointString()).append(" ");
                                    }

                                    System.out.println("Path is: " + out);
                                    System.out.println(BOARD);
                                }
                                thisSpace.space.setType(SpaceType.END);
                            }
                        }
                    }

                    //Update the buttons to show any changes
                    updateButtons();
                }
            });
        }

        /**
         * An action listener that sets the
         * start and end points of the path
         * @param e Action Event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Needs to be here because it has to be implemented :/
        }

        /**
         * Updates the space to display the field.SpaceType
         */
        private void update(){
            this.setBackground(this.space.getType().toColor());
            this.setText(this.space.getType().toString());
        }
    }
}


