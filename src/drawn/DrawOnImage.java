package drawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class DrawOnImage extends JFrame implements ActionListener {
 
    private Draw drawingPanel;
    private JPanel buttonPanel;
    private JButton clearButton,  upSize,  downSize;
 
    public DrawOnImage() {
        super("DrawOnImage");
        drawingPanel = new Draw();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 0, 2, 2));
        addButton(Color.BLACK);
        addButton(Color.BLUE);
        addButton(Color.GREEN);
        upSize = addButton(null);
        upSize.setText("+");
        addButton(Color.RED);
        addButton(Color.ORANGE);
        clearButton = addButton(null);
        clearButton.setText("Clear");
        downSize = addButton(null);
        downSize.setText("-");
        getContentPane().add(new JScrollPane(drawingPanel));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
 
    private JButton addButton(final Color color) {
        JButton button = new JButton();
        button.setBackground(new Color(230, 240, 250));
        button.setBorder(BorderFactory.createEtchedBorder());
        if (color != null) {
            button.setForeground(Color.WHITE);
            button.setBackground(color);
        }
        button.setText("Paint");
        buttonPanel.add(button);
        button.addActionListener(this);
        return (button);
    }
 
    public void actionPerformed(final ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Paint")) {
            JButton button = (JButton) e.getSource();
            drawingPanel.setPaintColor(button.getBackground());
        } else if (s.equals("Clear")) {
            drawingPanel.clearPaint();
        } else if (s.equals("+")) {
            drawingPanel.increaseBrushSize();
        } else {
            drawingPanel.decreaseBrushSize();
        }
    }
 
    public static void main(final String[] args) {
        Runnable gui = new Runnable() {
 
            @Override
            public void run() {
                JFrame frame = new DrawOnImage();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        };
        //GUI must start on EventDispatchThread:
        SwingUtilities.invokeLater(gui);
    }
}