import java.lang.Math;


public class Main {
    public static void main(String[] args) {
        // (b - a) / 2 = how many odd/even numbers are in [a, b],
        //    where a and b have different parity
        int wSize = (17 - 3) / 2 + 1;
        int[] w = new int[wSize];
        
        int v = 17;
        for (int i = wSize - 1; i >= 0; i--) {
            w[i] = v;
            v -= 2;
        }

        int xSize = 20;
        double []x = new double[20];
        double xMin = -7.0;
        double xMax = 2.0;
        for (int i = 0; i < xSize; i++) {
            x[i] = xMin + Math.random() * (xMax - xMin);
        }

        double[][] s = new double[8][20];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                fill(s, w, x, i, j);
            }
        }

        display(s);
    }

    private static void fill(double[][] s, int[] w, double[] x, int i, int j) {
        switch (w[i]) {
            case 11:
                s[i][j] = Math.log(
                    Math.pow(
                        2 * Math.pow(Math.tan(x[j]), 2),
                        Math.asin((x[j] - 2.5) / 9)
                    )
                );
                break;
            case 3:
            case 7:
            case 9:
            case 15:
                s[i][j] = Math.atan(
                    1/(Math.pow(
                        Math.E,
                        2 * (Math.PI / Math.pow(Math.tan(x[j]), 2))
                    ))
                );
                break;
            default:
                s[i][j] = Math.pow(
                    Math.E,
                    Math.pow(
                        1/3 / (Math.pow(Math.E, Math.cos(x[j])) + 1),
                        Math.tan(Math.tan(x[j]))
                    )
                );
                break;
        }
    }

    private static void display(double[][] s) {
        int colSize = 11;

        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                if (s[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.printf("Inf    ", s[i][j]);
                }
                else if (s[i][j] == Double.NEGATIVE_INFINITY) {
                    System.out.printf("-Inf   ", s[i][j]);
                }
                else if (Double.isNaN(s[i][j])) {
                    System.out.printf("NaN    ", s[i][j]);
                }
                else {
                    System.out.printf("%.3f", s[i][j]);
                    if (s[i][j] < 0) {
                        System.out.printf(" ");
                    } else {
                        System.out.printf("  ");
                    }
                }
            }
            System.out.printf("\n");
        }
    }
}
