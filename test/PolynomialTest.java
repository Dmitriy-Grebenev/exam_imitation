import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PolynomialTest {

    @Test
    public void testDefaultConstructor() {
        Polynomial p = new Polynomial();
        assertEquals(0, p.getDegree());
        assertEquals(0.0, p.calc(5.0), 1e-9);
        assertEquals("0", p.toString());
    }

    @Test
    public void testConstructorWithList() {
        Polynomial p = new Polynomial(Arrays.asList(1.0, 2.0, 3.0));
        assertEquals(2, p.getDegree());
        assertEquals(6.0, p.calc(1.0), 1e-9);
    }

    @Test
    public void testConstructorWithListAndTrailingZeros() {
        Polynomial p = new Polynomial(Arrays.asList(1.0, 2.0, 0.0, 0.0));
        assertEquals(1, p.getDegree());
        assertEquals(Arrays.asList(1.0, 2.0), p.getCoefficients());
    }

    @Test
    public void testConstructorWithVarargs() {
        Polynomial p = new Polynomial(1.0, 0.0, 3.0);
        assertEquals(2, p.getDegree());
        assertEquals(4.0, p.calc(1.0), 1e-9);
    }

    @Test
    public void testConstructorWithArray() {
        double[] arr = {2.0, -1.0, 1.0};
        Polynomial p = new Polynomial(arr);
        assertEquals(2, p.getDegree());
        assertEquals(2.0, p.calc(0.0), 1e-9);
    }

    @Test
    public void testTrim() {
        Polynomial p = new Polynomial(1.0, 2.0, 0.0, 0.0);
        assertEquals(1, p.getDegree());
    }

    @Test
    public void testGetCoefficientsCopy() {
        Polynomial p = new Polynomial(1.0, 2.0);
        List<Double> c = p.getCoefficients();
        c.add(3.0);
        assertEquals(2, p.getCoefficients().size());
    }

    @Test
    public void testToString() {
        assertEquals("0", new Polynomial().toString());
        assertEquals("x", new Polynomial(0.0, 1.0).toString());
        assertEquals("x^2 - 2x + 1", new Polynomial(1.0, -2.0, 1.0).toString());
        assertEquals("3x^2 - 2x + 1", new Polynomial(1.0, -2.0, 3.0).toString());
        assertEquals("-x + 1", new Polynomial(1.0, -1.0).toString());
        assertEquals("5", new Polynomial(5.0).toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Polynomial p1 = new Polynomial(1.0, 2.0, 3.0);
        Polynomial p2 = new Polynomial(1.0, 2.0, 3.0);
        Polynomial p3 = new Polynomial(1.0, 2.0, 0.0);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
    }

    @Test
    public void testPlus() {
        Polynomial p1 = new Polynomial(1.0, 2.0);
        Polynomial p2 = new Polynomial(3.0, 0.0, 1.0);
        Polynomial sum = p1.plus(p2);
        assertEquals(Arrays.asList(4.0, 2.0, 1.0), sum.getCoefficients());
    }

    @Test
    public void testPlusCancellation() {
        Polynomial a = new Polynomial(1.0, 2.0, 3.0);
        Polynomial b = new Polynomial(-1.0, -2.0, -3.0);
        Polynomial sum = a.plus(b);
        assertEquals(0, sum.getDegree());
        assertEquals(Arrays.asList(0.0), sum.getCoefficients());
    }

    @Test
    public void testMinus() {
        Polynomial p1 = new Polynomial(5.0, 2.0);
        Polynomial p2 = new Polynomial(3.0, 1.0);
        Polynomial diff = p1.minus(p2);
        assertEquals(Arrays.asList(2.0, 1.0), diff.getCoefficients());
    }

    @Test
    public void testTimesPolynomial() {
        Polynomial p1 = new Polynomial(1.0, 1.0);
        Polynomial p2 = new Polynomial(-1.0, 1.0);
        Polynomial product = p1.times(p2);
        assertEquals(Arrays.asList(-1.0, 0.0, 1.0), product.getCoefficients());
    }

    @Test
    public void testTimesNumber() {
        Polynomial p = new Polynomial(1.0, -2.0);
        Polynomial result = p.times(3.0);
        assertEquals(Arrays.asList(3.0, -6.0), result.getCoefficients());
    }

    @Test
    public void testDiv() {
        Polynomial p = new Polynomial(2.0, -4.0);
        Polynomial result = p.div(2.0);
        assertEquals(Arrays.asList(1.0, -2.0), result.getCoefficients());
    }

    @Test
    public void testDivByZero() {
        Polynomial p = new Polynomial(1.0, 2.0);
        assertThrows(ArithmeticException.class, () -> p.div(0.0));
    }

    @Test
    public void testCalc() {
        Polynomial p = new Polynomial(1.0, 2.0, 3.0);
        assertEquals(1.0, p.calc(0.0), 1e-9);
        assertEquals(6.0, p.calc(1.0), 1e-9);
        assertEquals(17.0, p.calc(2.0), 1e-9);
    }
}