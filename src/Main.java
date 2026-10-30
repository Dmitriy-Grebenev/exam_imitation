import java.util.Arrays;

// демонстрация работы с полиномами
public class Main {
    public static void main(String[] args) {
        System.out.println("=== базовые операции ===");
        Polynomial p1 = new Polynomial(1, 2, 3);
        Polynomial p2 = new Polynomial(4, 5);
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p1 + p2 = " + p1.plus(p2));
        System.out.println("p1 - p2 = " + p1.minus(p2));
        System.out.println("p1 * p2 = " + p1.times(p2));
        System.out.println("p1(2) = " + p1.calc(2));

        System.out.println();
        System.out.println("=== полином из списка ===");
        Polynomial fromList = new Polynomial(Arrays.asList(1.0, 2.0, 0.0, 0.0));
        System.out.println("fromList = " + fromList);
        System.out.println("степень = " + fromList.getDegree());
        System.out.println("коэффициенты = " + fromList.getCoefficients());

        System.out.println();
        System.out.println("=== взаимное уничтожение ===");
        Polynomial a = new Polynomial(1, 2, 3);
        Polynomial b = new Polynomial(-1, -2, -3);
        Polynomial sum = a.plus(b);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + sum);
        System.out.println("степень = " + sum.getDegree());
        System.out.println("коэффициенты = " + sum.getCoefficients());
    }
}