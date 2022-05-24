import java.lang.Math;

public class main {
    public static void main(String[] args) {
        System.out.print("Bisection Method: f(x) = 2x^3 - 11.7x^2  + 17.7x - 5\n");
        bisectionA(0, 1);
        bisectionA(1, 2);
        bisectionA(3, 4);
        System.out.print("\nBisection Method: f(x) = x + 10 - x * cosh(50/x)\n");
        bisectionB(120, 130);

        System.out.print("Newton Raphson Method: f(x) = 2x^3 - 11.7x^2  + 17.7x - 5\n");
        newtonA(4);
        newtonA(2);
        newtonA(1);
        System.out.print("\nNewton Raphson Method: f(x) = x + 10 - x * cosh(50/x)\n");
        newtonB(120);

        System.out.print("Secant Method: f(x) = 2x^3 - 11.7x^2  + 17.7x - 5\n");
        secantA(0, 1, 0.0001);
        secantA(1, 2, 0.0001);
        secantA(3, 4, 0.0001);
        System.out.print("Secant Method: f(x) = x + 10 - x * cosh(50/x)\n");
        secantB(120, 130, 0.0001);

        System.out.print("False Position Method: f(x) = 2x^3 - 11.7x^2  + 17.7x - 5\n");
        falsePositionA(0, 1);
        falsePositionA(1, 2);
        falsePositionA(3, 4);
        System.out.print("False Position Method: f(x) = x + 10 - x * cosh(50/x)\n");
        falsePositionB(120, 130);

        System.out.print("Modified Secant Method: f(x) = 2x^3 - 11.7x^2  + 17.7x - 5\n");
        modifiedSecantA(0, 0.00001);
        modifiedSecantA(2, 0.00001);
        modifiedSecantA(4, 0.00001);
        System.out.print("Modified Secant Method: f(x) = x + 10 - x * cosh(50/x)\n");
        modifiedSecantB(130, 0.00001);
    }

    public static float functionA(float x){
        return (float) (2 * Math.pow(x, 3) - 11.7 * Math.pow(x, 2) + 17.7 * x - 5);
    }

    public static float derivFuncA(float x) {
        return (float) (6 * Math.pow(x, 2) - 23.14 * x + 17.7);
    }

    public static float functionB(float x){
        return (float) (x + 10 - x * Math.cosh(50/x));
    }

    public static float derivFuncB(float x){
        return (float) ((50 * Math.sinh(50 / x)/x) - Math.cosh(50 / x) + 1);
    }

    public static void bisectionA(float a, float b){
        if (functionA(a) * functionA(b) >= 0) {
            System.out.print("Assumed A and B incorrectly.");
            return;
        }

        float c = a;
        int n = 0;

        System.out.printf("a = %f, b = %f\n", a, b);
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("n\ta\t\tb\t\tc\t\tf(a)\t\tf(b)\t\tf(c)\t\tError\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        while ((b - a) >= 0.01)
        {
            c = (a + b) / 2;
            if(n == 0)
                System.out.printf("%d\t%.4f\t%.4f\t%.4f\t%.4f\t\t%.4f\t\t%.4f\n", n, a, b, c, functionA(a), functionA(b), functionA(c));
            else {
                float error = (b - a) / 2;
                System.out.printf("%d\t%.4f\t%.4f\t%.4f\t%.4f\t\t%.4f\t\t%.4f\t\t%.4f\n", n, a, b, c, functionA(a), functionA(b), functionA(c), error);
            }

            if (functionA(c) == 0.0) {
                System.out.print("Root has diverged");
                break;
            }
            else if (functionA(c) * functionA(a) < 0)
                b = c;
            else
                a = c;
            n++;
            if (n > 99)
                break;
        }
        System.out.printf("Root is: %.4f\n\n", c);
    }

    public static void bisectionB(float a, float b){
        if (functionB(a) * functionB(b) >= 0) {
            System.out.print("Assumed A and B incorrectly.");
            return;
        }

        float c = a;
        int n = 0;

        System.out.printf("a = %f, b = %f\n", a, b);
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("n\ta\t\t\tb\t\t\tc\t\t\tf(a)\t\tf(b)\t\tf(c)\t\tError\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        while ((b - a) >= 0.01)
        {
            c = (a + b) / 2;
            if(n == 0)
                System.out.printf("%d\t%.4f\t%.4f\t%.4f\t%.4f\t\t%.4f\t\t%.4f\n", n, a, b, c, functionB(a), functionB(b), functionB(c));
            else {
                float error = (b - a) / 2;
                System.out.printf("%d\t%.4f\t%.4f\t%.4f\t%.4f\t\t%.4f\t\t%.4f\t\t%.4f\n", n, a, b, c, functionB(a), functionB(b), functionB(c), error);
            }

            if (functionB(c) == 0.0) {
                System.out.print("Root has diverged");
                break;
            }
            else if (functionB(c) * functionB(a) < 0)
                b = c;
            else
                a = c;
            n++;
            if (n > 99)
                break;
        }
        System.out.printf("Root is: %.4f\n\n", c);
    }

    public static void newtonA(float x){
        float h = functionA(x) / derivFuncA(x);
        int n = 0;

        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("n\tx\t\t\tf(x)\t\tf'(x)\t\tx(n+1)\t\tError\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        while (Math.abs(h) >= 0.01)
        {
            System.out.printf("%d\t%.4f\t\t%.4f\t\t%.4f\t\t", n, x, functionA(x), derivFuncA(x));
            h = functionA(x) / derivFuncA(x);

            // x(i+1) = x(i) - f(x) / f'(x)
            x = x - h;
            System.out.printf("%.4f\t\t%.4f\n", x, Math.abs(h));
            n++;
            if (n > 99)
                break;
        }

        System.out.printf("The root is: %f\n\n", x);
    }

    public static void newtonB(float x){
        float h = functionB(x) / derivFuncB(x);
        int n = 0;

        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("n\tx\t\t\tf(x)\t\tf'(x)\t\tx(n+1)\t\tError\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        while (Math.abs(h) >= 0.01)
        {
            System.out.printf("%d\t%.4f\t%.4f\t\t%.4f\t\t", n, x, functionB(x), derivFuncB(x));
            h = functionB(x) / derivFuncB(x);

            // x(i+1) = x(i) - f(x) / f'(x)
            x = x - h;
            System.out.printf("%.4f\t%.4f\n", x, Math.abs(h));
            n++;
            if (n > 99)
                break;
        }

        System.out.printf("The root is: %f\n\n", x);
    }

    public static void secantA(float x1, float x2, double epsilon) {
        float x3, x0, c, error;
        int n = 0;
        if (functionA(x1) * functionA(x2) < 0) {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.print("n\tx(n-1)\t\tx(n)\t\tx(n+1)\t\tError\n");
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            do {
                x0 = (x1 * functionA(x2) - x2 * functionA(x1)) / (functionA(x2) - functionA(x1));
                System.out.printf("%d\t%.4f\t\t%.4f\t\t", n, x1, x2);
                c = functionA(x1) * functionA(x0);
                x1 = x2;
                x2 = x0;
                n++;
                if (c == 0)
                    break;
                x3 = (x1 * functionA(x2) - x2 * functionA(x1)) / (functionA(x2) - functionA(x1));
                error = Math.abs(x3 - x0);
                System.out.printf("%.4f\t\t%.5f\n", x0, error);
            } while (error >= epsilon && n <= 100);

            System.out.printf("Root is: %.4f\n\n", x0);
        } else
            System.out.printf("Can not find a root in the given interval\n\n");
    }

    public static void secantB(float x1, float x2, double epsilon) {
        float x3, x0, c, error;
        int n = 0;
        if (functionB(x1) * functionB(x2) < 0) {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.print("n\tx(n-1)\t\tx(n)\t\tx(n+1)\t\tError\n");
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            do {
                x0 = (x1 * functionB(x2) - x2 * functionB(x1)) / (functionB(x2) - functionB(x1));
                System.out.printf("%d\t%.4f\t%.4f\t", n, x1, x2);
                c = functionB(x1) * functionB(x0);
                x1 = x2;
                x2 = x0;
                n++;
                if (c == 0)
                    break;
                x3 = (x1 * functionB(x2) - x2 * functionB(x1)) / (functionB(x2) - functionB(x1));
                error = Math.abs(x3 - x0);
                System.out.printf("%.4f\t%.5f\n", x0, error);
            } while (error >= epsilon && n <= 100);

            System.out.printf("Root is: %.4f\n\n", x0);
        } else
            System.out.printf("Can not find a root in the given interval\n\n");
    }

    public static void falsePositionA(float a, float b) {
        if (functionA(a) * functionA(b) >= 0)
        {
            System.out.print("You have assumed wrong a and b\n");
            return;
        }

        float c = a, error, prevA = 0;

        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("n\ta\t\tb\t\tc\t\tf(a)\tf(b)\tf(c)\tError\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        for (int i = 0; i < 100; i++)
        {
            // Find the point that touches x axis
            c = (a * functionA(b) - b * functionA(a)) / (functionA(b) - functionA(a));
            error = Math.abs(a - b);
            prevA = a;
            System.out.printf("%d\t%.4f\t%.4f\t%.4f\t", i, a, b, c);

            if (functionA(c) == 0)
                break;

                // Decide the side to repeat the steps
            else if (functionA(c)*functionA(a) < 0)
                b = c;
            else
                a = c;
            System.out.printf("%.4f\t%.4f\t%.4f\t%.4f\t\n", functionA(a), functionA(b), functionA(c), error);
            if (prevA == a || error < 0.0001)
                break;
        }
        System.out.printf("Root is: %.4f\n\n", c);
    }

    public static void falsePositionB(float a, float b) {
        if (functionB(a) * functionB(b) >= 0)
        {
            System.out.print("You have assumed wrong a and b\n");
            return;
        }

        float c = a, error, prevA = 0;

        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("n\ta\t\t\tb\t\t\tc\t\t\tf(a)\tf(b)\tf(c)\tError\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        for (int i = 0; i < 100; i++)
        {
            // Find the point that touches x axis
            c = (a * functionB(b) - b * functionB(a)) / (functionB(b) - functionB(a));
            error = Math.abs(a - b);
            prevA = a;
            System.out.printf("%d\t%.4f\t%.4f\t%.4f\t", i, a, b, c);

            if (functionB(c) == 0)
                break;

                // Decide the side to repeat the steps
            else if (functionB(c) * functionB(a) < 0)
                b = c;
            else
                a = c;
            System.out.printf("%.4f\t%.4f\t%.4f\t%.4f\t\n", functionB(a), functionB(b), functionB(c), error);
            if (prevA == a & error < 0.0001)
                break;
        }
        System.out.printf("Root is: %.4f\n\n", c);
    }

    public static void modifiedSecantA(float x1, double epsilon) {
        float x3, x0, c, error;
        float x2 = x1 + (float)epsilon;
        int n = 0;

            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.print("n\tx(n-1)\t\tx(n)\t\tx(n+1)\t\tError\n");
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            do {
                x0 = (x1 * functionA(x2) - x2 * functionA(x1)) / (functionA(x2) - functionA(x1));
                System.out.printf("%d\t%.4f\t\t%.4f\t\t", n, x1, x2);
                c = functionA(x1) * functionA(x0);
                x1 = x2;
                x2 = x0;
                n++;
                if (c == 0)
                    break;
                x3 = (x1 * functionA(x2) - x2 * functionA(x1)) / (functionA(x2) - functionA(x1));
                error = Math.abs(x3 - x0);
                System.out.printf("%.4f\t\t%.5f\n", x0, error);
            } while (error >= epsilon && n <= 100);

            System.out.printf("Root is: %.4f\n\n", x0);
    }

    public static void modifiedSecantB(float x1, double epsilon) {
        float x3, x0, c, error;
        float x2 = x1 + (float)epsilon;
        int n = 0;

        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.print("n\tx(n-1)\t\t\tx(n)\t\t\tx(n+1)\t\t\tError\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        do {
            x0 = (x1 * functionB(x2) - x2 * functionB(x1)) / (functionB(x2) - functionB(x1));
            System.out.printf("%d\t%.4f\t\t%.4f\t\t", n, x1, x2);
            c = functionB(x1) * functionB(x0);
            x1 = x2;
            x2 = x0;
            n++;
            if (c == 0)
                break;
            x3 = (x1 * functionB(x2) - x2 * functionB(x1)) / (functionB(x2) - functionB(x1));
            error = Math.abs(x3 - x0);
            System.out.printf("%.4f\t\t%.5f\n", x0, error);
        } while (error >= epsilon && n <= 100);

        System.out.printf("Root is: %.4f\n\n", x0);
    }
}
