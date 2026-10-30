
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// класс для работы с произвольными полиномами
public class Polynomial {
    // коэффициенты от младшей степени к старшей
    private List<Double> coefficients;

    // конструктор по умолчанию создает нулевой полином
    public Polynomial() {
        coefficients = new ArrayList<>();
        coefficients.add(0.0);
    }

    // конструктор со списком коэффициентов
    public Polynomial(List<Double> coefficients) {
        this.coefficients = new ArrayList<>(coefficients);
    }

    // конструктор с переменным количеством вещественных параметров
    public Polynomial(double... coefficients) {
        this.coefficients = new ArrayList<>();
        for (double c : coefficients) {
            this.coefficients.add(c);
        }
        trim();
    }

    // конструктор с массивом коэффициентов
    public Polynomial(double[] coefficients) {
        this.coefficients = new ArrayList<>();
        for (double c : coefficients) {
            this.coefficients.add(c);
        }
        trim();
    }

    // возвращает копию списка коэффициентов
    public List<Double> getCoefficients() {
        return new ArrayList<>(coefficients);
    }

    // устанавливает коэффициенты, используется наследником
    protected void setCoefficients(List<Double> coefficients) {
        this.coefficients = new ArrayList<>(coefficients);
        trim();
    }

    // удаляет ненужные старшие нулевые коэффициенты
    private void trim() {
        while (coefficients.size() > 1 && coefficients.get(coefficients.size() - 1) == 0.0) {
            coefficients.remove(coefficients.size() - 1);
        }
        if (coefficients.isEmpty()) {
            coefficients.add(0.0);
        }
    }

    // возвращает степень полинома
    public int getDegree() {
        return coefficients.size() - 1;
    }

    // вычисляет значение полинома в точке x по схеме горнера
    public double calc(double x) {
        double result = 0.0;
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            result = result * x + coefficients.get(i);
        }
        return result;
    }

    // синоним метода calc
    public double invoke(double x) {
        return calc(x);
    }

    // сложение полиномов
    public Polynomial plus(Polynomial other) {
        int maxSize = Math.max(this.coefficients.size(), other.coefficients.size());
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            double a = i < this.coefficients.size() ? this.coefficients.get(i) : 0.0;
            double b = i < other.coefficients.size() ? other.coefficients.get(i) : 0.0;
            result.add(a + b);
        }
        return new Polynomial(result);
    }

    // вычитание полиномов
    public Polynomial minus(Polynomial other) {
        int maxSize = Math.max(this.coefficients.size(), other.coefficients.size());
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            double a = i < this.coefficients.size() ? this.coefficients.get(i) : 0.0;
            double b = i < other.coefficients.size() ? other.coefficients.get(i) : 0.0;
            result.add(a - b);
        }
        return new Polynomial(result);
    }

    // умножение полиномов
    public Polynomial times(Polynomial other) {
        int newSize = this.coefficients.size() + other.coefficients.size() - 1;
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < newSize; i++) {
            result.add(0.0);
        }
        for (int i = 0; i < this.coefficients.size(); i++) {
            for (int j = 0; j < other.coefficients.size(); j++) {
                double value = result.get(i + j) + this.coefficients.get(i) * other.coefficients.get(j);
                result.set(i + j, value);
            }
        }
        return new Polynomial(result);
    }

    // умножение полинома на число
    public Polynomial times(double number) {
        List<Double> result = new ArrayList<>();
        for (double c : coefficients) {
            result.add(c * number);
        }
        return new Polynomial(result);
    }

    // деление полинома на число
    public Polynomial div(double number) {
        if (number == 0.0) {
            throw new ArithmeticException("деление на ноль");
        }
        List<Double> result = new ArrayList<>();
        for (double c : coefficients) {
            result.add(c / number);
        }
        return new Polynomial(result);
    }

    // строковое представление полинома
    @Override
    public String toString() {
        if (getDegree() == 0 && coefficients.get(0) == 0.0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int degree = getDegree();
        for (int i = degree; i >= 0; i--) {
            double c = coefficients.get(i);
            if (c == 0.0) {
                continue;
            }
            if (sb.length() > 0) {
                if (c > 0) {
                    sb.append(" + ");
                } else {
                    sb.append(" - ");
                }
                c = Math.abs(c);
            } else {
                if (c < 0) {
                    sb.append("-");
                    c = -c;
                }
            }
            if (i == 0) {
                sb.append(formatNumber(c));
            } else if (i == 1) {
                if (c != 1.0) {
                    sb.append(formatNumber(c));
                }
                sb.append("x");
            } else {
                if (c != 1.0) {
                    sb.append(formatNumber(c));
                }
                sb.append("x^").append(i);
            }
        }
        return sb.toString();
    }

    private String formatNumber(double d) {
        if (d == Math.floor(d) && !Double.isInfinite(d)) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Polynomial that = (Polynomial) o;
        return Objects.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficients);
    }
}