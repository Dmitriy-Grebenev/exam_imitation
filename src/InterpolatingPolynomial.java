
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// интерполяционный полином ньютона
public class InterpolatingPolynomial extends Polynomial {
    private List<Point> points;

    public InterpolatingPolynomial() {
        super();
        points = new ArrayList<>();
    }

    public InterpolatingPolynomial(Collection<Point> points) {
        super();
        this.points = new ArrayList<>(points);
        rebuild();
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }

    public void addPoint(Point point) {
        points.add(point);
        rebuild();
    }

    public boolean removePoint(Point point) {
        boolean removed = points.remove(point);
        if (removed) {
            rebuild();
        }
        return removed;
    }

    private void rebuild() {
        if (points.isEmpty()) {
            setCoefficients(new ArrayList<>());
            return;
        }
        int n = points.size();
        double[] xs = new double[n];
        double[] coef = new double[n];
        for (int i = 0; i < n; i++) {
            xs[i] = points.get(i).getX();
            coef[i] = points.get(i).getY();
        }
        // разделенные разности
        for (int j = 1; j < n; j++) {
            for (int i = n - 1; i >= j; i--) {
                double denominator = xs[i] - xs[i - j];
                if (denominator == 0.0) {
                    throw new IllegalArgumentException("точки имеют одинаковые x");
                }
                coef[i] = (coef[i] - coef[i - 1]) / denominator;
            }
        }
        // строим полином
        Polynomial result = new Polynomial(0.0);
        Polynomial term = new Polynomial(1.0);
        for (int i = 0; i < n; i++) {
            result = result.plus(term.times(coef[i]));
            if (i < n - 1) {
                term = term.times(new Polynomial(-xs[i], 1.0));
            }
        }
        setCoefficients(result.getCoefficients());
    }
}