package com.brtr.util;


public class Validator
{
        /**
         * Validate credit card.
         * @param cc input String of an credit card.
         * @return boolean value indicating whether a credit card number is valid
         * format-wise.
         */
        public static boolean validateCC(String cc)
        {
                if (StringUtils.isBlank(cc))
                {
                        return false;
                }
                int sum = 0;
                boolean alternate = false;
                for (int i = cc.length() - 1; i >= 0; i--)
                {
                        int n = Integer.parseInt(cc.substring(i, i + 1));
                        if (alternate)
                        {
                                n *= 2;
                                if (n > 9)
                                {
                                        n = (n % 10) + 1;
                                }
                        }
                        sum += n;
                        alternate = !alternate;
                }
                return (sum % 10 == 0);
        }


        ;
}
