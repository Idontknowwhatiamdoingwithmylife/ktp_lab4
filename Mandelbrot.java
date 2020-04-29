import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {

    public static final int MAX_ITERATION_COUNT = 2000;

    public void getInitialRange(Rectangle2D.Double r) {
        r.x = -2;
        r.y = -1.5;
        r.height = 3;
        r.width = 3;
    }

    public int numIterations(double x, double y) {
        int iteration = 0;
        double zreal = 0;
        double zimaginary = 0;

        while (zreal*zreal + zimaginary*zimaginary <= 4) {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;

            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration++;
            if (iteration > MAX_ITERATION_COUNT) {
                return -1;
            }
        }

        return iteration;
    }
}