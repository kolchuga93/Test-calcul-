import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите арифметическое выражение: ");
        String input = scanner.nextLine();

        try {
            String result = Calculator.calc(input);
            System.out.println("результат: " + result);
        } catch (Exception e) {
            System.out.println("ошибка: " + e.getMessage());
        }
    }
}

class Calculator {
    private static final Map<Character, Integer> ROMAN_VALUES = new HashMap<>();
    static {
        ROMAN_VALUES.put('I', 1);
        ROMAN_VALUES.put('V', 5);
        ROMAN_VALUES.put('X', 10);
        ROMAN_VALUES.put('L', 50);
        ROMAN_VALUES.put('C', 100);
        ROMAN_VALUES.put('D', 500);
        ROMAN_VALUES.put('M', 1000);
    }

    public static String calc(String input) {
        String[] tokens = input.trim().split("\\s+");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Недопустимый формат ввода");
        }

        int a, b;
        boolean isRoman = false;

        try {
            a = Integer.parseInt(tokens[0]);
            b = Integer.parseInt(tokens[2]);
        } catch (NumberFormatException e) {
            a = romanToArabic(tokens[0]);
            b = romanToArabic(tokens[2]);
            isRoman = true;
        }

        char op = tokens[1].charAt(0);
        int result;

        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Недопустимый оператор: " + op);
        }

        if (isRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Римские цифры не могут представлять нулевые или отрицательные числа");
            }
            return arabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static int romanToArabic(String s) {
        int result = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            int value = ROMAN_VALUES.get(c);

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }

            prevValue = value;
        }

        return result;
    }

    private static String arabicToRoman(int n) {
        if (n < 1 || n > 3999) {
            throw new IllegalArgumentException("Римские цифры могут обозначать только числа от 1 до 3999");
        }

        StringBuilder sb = new StringBuilder();

        while (n >= 1000) {
            sb.append("M");
            n -= 1000;
        }

        if (n >= 900) {
            sb.append("CM");
            n -= 900;
        }

        while (n >= 500) {
            sb.append("D");
            n -= 500;
        }

        if (n >= 400) {
            sb.append("CD");
            n -= 400;
        }


        while (n >= 100) {
            sb.append("C");
            n -= 100;
        }

        if (n >= 90) {
            sb.append("XC");
            n -= 90;
        }

        while (n >= 50) {
            sb.append("L");
            n -= 50;
        }

        if (n >= 40) {
            sb.append("XL");
            n -= 40;
        }

        while (n >= 10) {
            sb.append("X");
            n -= 10;
        }

        if (n >= 9) {
            sb.append("IX");
            n -= 9;
        }

        while (n >= 5) {
            sb.append("V");
            n -= 5;
        }

        if (n >= 4) {
            sb.append("IV");
            n -= 4;
        }

        while (n >= 1) {
            sb.append("I");
            n -= 1;
        }

        return sb.toString();
    }
}