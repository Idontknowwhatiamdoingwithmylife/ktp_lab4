import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {

    private BufferedImage img;

    public  JImageDisplay(int width, int height) {
        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension preferedSize = new Dimension(width, height);
        this.setPreferredSize(preferedSize);
    }

    public void clearImage() {
        for (int x = 0; x < this.img.getWidth(); x++) {
            for (int y = 0; y < this.img.getHeight(); y++) {
                this.img.setRGB(x, y, Color.BLACK.getRGB());
            }
        }

    }

    public void drawPixel(int x, int y, int rgbColor) {
        if (x < this.img.getWidth() && y < this.img.getHeight()) {
            this.img.setRGB(x, y, rgbColor);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.img, 0, 0, this.img.getWidth(), this.img.getHeight(), null);
    }
}