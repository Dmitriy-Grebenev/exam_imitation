import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpolatingPolynomialTest {

    @Test
    public void testDefaultConstructor() {
        InterpolatingPolynomial p = new InterpolatingPolynomial();
        assertEquals(0, p.getDegree());
        assertEquals(0.0, p.calc(10.0), 1e-9);
    }

    @Test
    public void testConstructorWithPoints() {
        List<Point> points = Arrays.asList(
                new Point(0.0, 0.0),
                new Point(1.0, 1.0),
                new Point(2.0, 4.0)
        );
        InterpolatingPolynomial p = new InterpolatingPolynomial(points);
        assertEquals(2, p.getDegree());
        assertEquals(9.0, p.calc(3.0), 1e-9);
    }

    @Test
    public void testAddPoint() {
        InterpolatingPolynomial p = new InterpolatingPolynomial();
        p.addPoint(new Point(0.0, 1.0));
        p.addPoint(new Point(1.0, 3.0));
        assertEquals(1, p.getDegree());
        assertEquals(1.0, p.calc(0.0), 1e-9);
        assertEquals(3.0, p.calc(1.0), 1e-9);
    }

    @Test
    public void testRemovePoint() {
        List<Point> points = Arrays.asList(
                new Point(0.0, 0.0),
                new Point(1.0, 1.0),
                new Point(2.0, 4.0)
        );
        InterpolatingPolynomial p = new InterpolatingPolynomial(points);
        boolean removed = p.removePoint(new Point(2.0, 4.0));
        assertTrue(removed);
        assertEquals(1, p.getDegree());
    }

    @Test
    public void testInterpolationPassesThroughPoints() {
        List<Point> points = Arrays.asList(
                new Point(-1.0, 2.0),
                new Point(0.0, 1.0),
                new Point(1.0, 4.0),
                new Point(2.0, 7.0)
        );
        InterpolatingPolynomial p = new InterpolatingPolynomial(points);
        for (Point pt : points) {
            assertEquals(pt.getY(), p.calc(pt.getX()), 1e-9);
        }
    }

    @Test
    public void testDuplicateXThrows() {
        List<Point> points = Arrays.asList(
                new Point(1.0, 2.0),
                new Point(1.0, 3.0)
        );
        assertThrows(IllegalArgumentException.class, () -> new InterpolatingPolynomial(points));
    }
}