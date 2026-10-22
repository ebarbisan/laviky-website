package br.com.laviky.validation;

import org.springframework.stereotype.Component;

@Component
public class CnpjValidator {

    public boolean isValid(String cnpj) {
        if (cnpj == null) return false;

        // Remove non-numeric characters
        String cleanCnpj = cnpj.replaceAll("\\D", "");

        if (cleanCnpj.length() != 14) return false;

        // Check if all digits are the same
        if (cleanCnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] digits = new int[14];
            for (int i = 0; i < 14; i++) {
                digits[i] = Character.getNumericValue(cleanCnpj.charAt(i));
            }

            // Validate first digit
            int sum1 = 0;
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            for (int i = 0; i < 12; i++) {
                sum1 += digits[i] * weights1[i];
            }
            int remainder1 = sum1 % 11;
            int digit1 = (remainder1 < 2) ? 0 : 11 - remainder1;

            if (digit1 != digits[12]) return false;

            // Validate second digit
            int sum2 = 0;
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            for (int i = 0; i < 13; i++) {
                sum2 += digits[i] * weights2[i];
            }
            int remainder2 = sum2 % 11;
            int digit2 = (remainder2 < 2) ? 0 : 11 - remainder2;

            return digit2 == digits[13];
        } catch (Exception e) {
            return false;
        }
    }
}
