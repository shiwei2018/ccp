package com.brtr.model;


import com.brtr.util.StringUtils;

import java.math.BigDecimal;


public class Account
{

        private String accountName;

        private BigDecimal limit;

        private BigDecimal balance;

        private String currencySymbol;

        private boolean cardValid;

        //TODO whether to keep it here or encrypt it, or don't store it at all.
//        private String source;

        //TODO What if there are more than one with this name,
        //what is the unique identifier.
        //        private String accountId;

        public String getAccountName()
        {
                return accountName;
        }


        public void setAccountName(String accountName)
        {
                this.accountName = accountName;
        }


        public BigDecimal getLimit()
        {
                return limit;
        }


        public void setLimit(BigDecimal limit)
        {
                this.limit = limit;
        }


        public BigDecimal getBalance()
        {
                return balance;
        }


        public void setBalance(BigDecimal balance)
        {
                this.balance = balance;
        }


        public boolean isCardValid()
        {
                return cardValid;
        }


        public void setCardValid(boolean cardValid)
        {
                this.cardValid = cardValid;
        }


        public String getCurrencySymbol()
        {
                return currencySymbol;
        }


        public void setCurrencySymbol(String currencySymbol)
        {
                this.currencySymbol = currencySymbol;
        }


        //Utils below

        /**
         * Increase balance of this account.
         * @param amount amount to be added to the balance.
         */
        public void addBalance(BigDecimal amount)
        {
                if (!this.isCardValid() || this.balance == null)
                {
                        //ignore
                        return;
                }

                BigDecimal nb = this.balance.add(amount);

                if (this.limit == null)
                {
                        this.balance = this.balance == null ?
                            amount :
                            this.balance.add(amount);
                }

                if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
                {
                        //ignore
                        return;
                }
                if (nb.compareTo(this.limit) > 0)
                {
                        //ignore
                        return;
                }

                this.balance = nb;
        }


        /**
         * Deduct balance from an account.
         * @param amount amount to be deducted to the balance.
         */
        public void deductBalance(BigDecimal amount)
        {
                if (!this.isCardValid() || this.balance == null)
                {
                        //ignore
                        return;
                }

                BigDecimal nb = this.balance.subtract(amount);

                this.balance = nb;
        }


        @Override
        public String toString()
        {
                String ret = new String();
                //Lisa: $-93
                ret = ret.concat(this.accountName + ": ");

                if (isCardValid())
                {

                        if (balance == null)
                        {
                                System.out.println(
                                    "Card shows valid, but balance does not "
                                        + "exist, please check account <"
                                        + accountName + ">.");
                                ret = ret.concat("error");
                        }
                        else
                        {
                                ret = ret.concat(currencySymbol)
                                    .concat(balance.setScale(0)
                                        .toPlainString());
                        }
                }
                else
                {
                        ret = ret.concat("error");
                }

                return StringUtils.isBlank(ret) ? null : ret;
        }
}
