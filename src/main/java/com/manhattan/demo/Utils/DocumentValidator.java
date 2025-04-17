package com.manhattan.demo.Utils;

public final class DocumentValidator {
    public static  boolean isValidDocument(String document){
        if(document.length() == 11) return isValidCPF(document);
        if(document.length() == 14) return isValidCNPJ(document);
        return false;
    }

    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(cpf.charAt(i));
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }

            int digit1 = (sum1 * 10) % 11;
            if (digit1 == 10) digit1 = 0;

            sum2 += digit1 * 2;
            int digit2 = (sum2 * 10) % 11;
            if (digit2 == 10) digit2 = 0;

            return digit1 == Character.getNumericValue(cpf.charAt(9)) && digit2 == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum1 = 0, sum2 = 0;

            for (int i = 0; i < 12; i++) {
                int digit = Character.getNumericValue(cnpj.charAt(i));
                sum1 += digit * weights1[i];
                sum2 += digit * weights2[i];
            }

            int digit1 = (sum1 % 11 < 2) ? 0 : 11 - (sum1 % 11);
            sum2 += digit1 * weights2[12];
            int digit2 = (sum2 % 11 < 2) ? 0 : 11 - (sum2 % 11);

            return digit1 == Character.getNumericValue(cnpj.charAt(12)) && digit2 == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }
}
