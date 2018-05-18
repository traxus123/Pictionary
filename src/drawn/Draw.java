package drawn;

import java.awt.*;
import javax.swing.*;

class Draw extends JComponent {
 
    private Image image;
    private Graphics2D g2d;
    private int brushSize = 5;
 
    public Draw() {
        super();
        setPreferredSize(new Dimension(300, 300));
        MouseHandler mh = new MouseHandler(this);
        addMouseListener(mh);
        addMouseMotionListener(mh);
    }
 
    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        if (image != null) {
            size.width = image.getWidth(this);
            size.height = image.getHeight(this);
        }
        return size;
    }
 
    public void setPaintColor(final Color color) {
        g2d.setColor(color);
    }
 
    public void clearPaint() {
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        repaint();
        g2d.setColor(Color.black);
    }
 
    public void increaseBrushSize() {
        brushSize += 2;
    }
 
    public void decreaseBrushSize() {
        brushSize -= 2;
        if (brushSize <= 0) {
            brushSize = 1;
        }
    }
 
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // initialises the image with the first paint
        // or checks the image size with the current panelsize
        if (image == null || image.getWidth(this) < getSize().width || image.getHeight(this) < getSize().height) {
            resetImage();
        }
        Rectangle r = g.getClipBounds();
        g.drawImage(image, r.x, r.y, r.width + r.x, r.height + r.y, r.x, r.y, r.width + r.x, r.height + r.y, null);
    }
 
    private void resetImage() {
        Image saveImage = image;
        Graphics2D saveG2d = g2d;
        image = createImage(getWidth(), getHeight());
        g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.black);
        if (saveG2d != null) {
            g2d.setColor(saveG2d.getColor());
            g2d.drawImage(saveImage, 0, 0, this);
        }
    }
 
    public Graphics2D getG2d() {
        return g2d;
    }
 
    public int getBrushSize() {
        return brushSize;
    }
}
