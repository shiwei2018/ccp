package com.brtr.model;


import com.brtr.util.StringUtils;
import com.brtr.util.Validator;

import java.math.BigDecimal;
import java.util.*;


public class AccountKeeper
{
        private Map<String,Account> accounts;

        public AccountKeeper()
        {
                this.accounts = new HashMap<String,Account>();
        }


        public Map<String,Account> getAccounts()
        {
                return accounts;
        }


        //Utils below

        /**
         * Get account from the keeper with specific account name
         *
         * @param name account name
         * @return Account object with this account name, if no account is found
         * with this name, return null;
         */
        public Account getAccout(String name)
        {
                if (StringUtils.isBlank(name))
                {
                        System.out.print(
                            "!error: account name is not "
                                + "provided for account search operation.\n");
                        return null;
                }
                if (accounts.containsKey(name))
                {
                        return accounts.get(name);
                }
                else
                {
                        return null;
                }
        }


        public void init()
        {
                accounts = new HashMap<String,Account>();
        }


        /**
         * Input new account into the keeper
         *
         * @param accountName  name of the account
         * @param cardNumber   card number
         * @param currency     currency symbol of the account
         * @param accountLimit limit of the account
         */
        public boolean addAccount(
            String accountName,
            String cardNumber,
            String currency,
            BigDecimal accountLimit)
        {
                //validation
                if (StringUtils.isBlank(accountName)
                    || StringUtils.isBlank(cardNumber)
                    || accountLimit == null
                    || accountLimit.compareTo(BigDecimal.ZERO) < 0)
                {
                        System.out.print(
                            "!error: necessary information is not fully "
                                + "provided or invalid for add account operation.\n");
                        return false;
                }

                Account acc = getAccout(accountName);
                if (acc != null && acc.isCardValid() == true)
                {
                        System.out.print(
                            "!error: this account already exists.\n");
                        return false;
                }

                Account nacc = new Account();
                nacc.setAccountName(accountName);
                nacc.setCardValid(Validator.validateCC(cardNumber));
                nacc.setBalance(nacc.isCardValid() ? BigDecimal.ZERO : null);
                nacc.setCurrencySymbol(currency);
                nacc.setLimit(accountLimit);

                accounts.put(accountName, nacc);

                if (!nacc.isCardValid())
                {
                        System.out.print("!warning: this account <" + accountName
                            + "> has an invalid card.\n");
                }

                return true;
        }


        /**
         * Credit amount for an account with specific account name
         * @param accountName name of the account
         * @param amount amount to be deducted.
         * @return boolean value indicating whether operation has been
         * successfully executed.
         */
        public boolean deductAmount(String accountName, BigDecimal amount)
        {
                if (StringUtils.isBlank(accountName)
                    || amount.compareTo(BigDecimal.ZERO) < 0)
                {
                        System.out.print(
                            "!error: necessary information is not fully provided"
                                + " or invalid for <"
                                + accountName
                                + "> credit operation. \n");
                        return false;
                }

                Account acc = getAccout(accountName);

                if (acc == null)
                {
                        System.out.print(
                            "!error: account does not exist of credit operation. \n");
                        return false;
                }

                acc.deductBalance(amount);

                return true;
        }

        /**
         * Charge amount for an account with specific account name
         * @param accountName name of the account
         * @param amount amount to be charged.
         * @return boolean value indicating whether operation has been
         * successfully executed.
         */
        public boolean addAmount(String accountName, BigDecimal amount)
        {
                if (StringUtils.isBlank(accountName)
                    || amount.compareTo(BigDecimal.ZERO) < 0)
                {
                        System.out.print(
                            "!error: necessary information is not fully provided"
                                + " or invalid for <"
                                + accountName
                                + "> charge operation. \n");
                        return false;
                }

                Account acc = getAccout(accountName);

                if (acc == null)
                {
                        System.out.print(
                            "!error: account does not exist of charge operation. \n");
                        return false;
                }

                acc.addBalance(amount);

                return true;
        }

        /**
         * List all accounts in String format.
         * @return a list of String describing the current account status
         */
        public List<String> getAccountList()
        {
                List<String> ret = new ArrayList<>();
                if (accounts == null || accounts.isEmpty())
                {
                        return ret;
                }
                else
                {
                        Iterator<String> names = accounts.keySet().iterator();
                        List<String> sortedNames = new ArrayList<>();
                        while (names.hasNext())
                        {
                                sortedNames.add(names.next());
                        }

                        Collections.sort(sortedNames);

                        for(String name : sortedNames)
                        {
                                Account acc = accounts.get(name);
                                ret.add(acc.toString());
                        }
                }

                return ret;
        }

}
