import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {

    private JFrame frame;
    private JButton button;

    private int size;
    private JImageDisplay display;
    private FractalGenerator generator;
    private Rectangle2D.Double panel;

    public FractalExplorer(int size) {
        this.size = size;
        this.panel = new Rectangle2D.Double();
        this.generator = new Mandelbrot();

        this.generator.getInitialRange(this.panel);
    }

    public void createAndShowGUI() {
        DisplayMouseHandler mouseHandler = new DisplayMouseHandler();

        this.display = new JImageDisplay(this.size, this.size);

        this.display.addMouseListener(mouseHandler);

        this.display.clearImage();
        this.display.repaint();

        this.button = new JButton("Reset display");

        ResetHandler resetHandler = new ResetHandler();
        this.button.addActionListener(resetHandler);

        this.frame = new JFrame("Fractal explorer");

        this.frame.add(this.display, BorderLayout.CENTER);
        this.frame.add(this.button, BorderLayout.SOUTH);

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setResizable(false);
    }

    private void drawFractal() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                double xCoord = this.generator.getCoord(this.panel.x, this.panel.x + this.panel.width, this.size, i);
                double yCoord = this.generator.getCoord(this.panel.y, this.panel.y + this.panel.height, this.size, j);

                int iterCount = this.generator.numIterations(xCoord, yCoord);

                if (iterCount == -1) {
                    this.display.drawPixel(i, j, Color.BLACK.getRGB());
                } else {
                    float hue = 0.7f + (float) iterCount / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    System.out.println(rgbColor);
                    this.display.drawPixel(i, j, rgbColor);
                }
            }
        }

        this.display.repaint();
    }

    private class ResetHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            generator.getInitialRange(panel);
            drawFractal();
        }
    }

    private class DisplayMouseHandler extends MouseAdapter implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int x = e.getX();
            int y = e.getY();

            double xCoord = generator.getCoord(panel.x, panel.x + panel.width, size, x);
            double yCoord = generator.getCoord(panel.y, panel.y + panel.height, size, y);

            generator.recenterAndZoomRange(panel, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
}