package com.brtr.model.command;


import com.brtr.model.SystemErrorCode;
import com.brtr.model.SystemException;
import com.brtr.util.StringUtils;
import com.brtr.util.Validator;

import java.math.BigDecimal;


public class Command
{
        private CommandOperation operation;

        private String accountName;

        private String cardNumber;

        private BigDecimal amount;

        private BigDecimal accountLimit;

        private String currencySymbol;

        private String originalRequest;


        public CommandOperation getOperation()
        {
                return operation;
        }


        public void setOperation(CommandOperation operation)
        {
                this.operation = operation;
        }


        public String getAccountName()
        {
                return accountName;
        }


        public void setAccountName(String accountName)
        {
                this.accountName = accountName;
        }


        public String getCardNumber()
        {
                return cardNumber;
        }


        public void setCardNumber(String cardNumber)
        {
                this.cardNumber = cardNumber;
        }


        public BigDecimal getAmount()
        {
                return amount;
        }


        public void setAmount(BigDecimal amount)
        {
                this.amount = amount;
        }


        public BigDecimal getAccountLimit()
        {
                return accountLimit;
        }


        public void setAccountLimit(BigDecimal accountLimit)
        {
                this.accountLimit = accountLimit;
        }


        public String getCurrencySymbol()
        {
                return currencySymbol;
        }


        public void setCurrencySymbol(String currencySymbol)
        {
                this.currencySymbol = currencySymbol;
        }


        public String getOriginalRequest()
        {
                return originalRequest;
        }


        public void setOriginalRequest(String originalRequest)
        {
                this.originalRequest = originalRequest;
        }


        //Utils below


        /**
         * Parse a command string into an command object
         *
         * @param clstr command input
         * @return new Command object input parsed.
         * @throws SystemException recording system exceptions.
         */
        public static Command parse(String clstr)
            throws SystemException
        {
                //1 Validation
                if (StringUtils.isBlank(clstr))
                {
                        throw new SystemException(
                            SystemErrorCode.INPUT_NOT_PRESENT,
                            "The command input is empty");
                }

                //1.1 Process initial input
                clstr = clstr.trim();
                String[] strs = clstr.split(" ");
                if (strs == null || !(strs.length == 3 || strs.length == 4))
                {
                        throw new SystemException(
                            SystemErrorCode.INPUT_NOT_VALID,
                            "The command input is invalid");
                }
                //1.2 get operation of the command
                CommandOperation cmdo = null;
                try
                {
                        cmdo =
                            CommandOperation.getOperation(strs[0]);
                }
                catch (Exception e)
                {
                        throw new SystemException(
                            SystemErrorCode.INPUT_NOT_VALID,
                            "The command operation is invalid.");
                }

                if (cmdo == null)
                {
                        throw new SystemException(
                            SystemErrorCode.INPUT_NOT_VALID,
                            "The command operation is not recognized.");
                }

                Command ret = new Command();
                ret.setOperation(cmdo);
                String amount = null;
                String limit = null;
                //1.3 Process account name
                if (StringUtils.isBlank(strs[1]))
                {
                        throw new SystemException(
                            SystemErrorCode.INPUT_NOT_VALID,
                            "Account name is not provided.");
                }
                ret.setAccountName(strs[1]);

                //1.4 process diversed fields
                switch (cmdo)
                {
                case ADD_ACCOUNT:
                {
                        //validation
                        if (strs.length != 4
                            || StringUtils.isBlank(strs[2])
                            || StringUtils.isBlank(strs[3]))
                        {
                                throw new SystemException(
                                    SystemErrorCode.INPUT_NOT_VALID,
                                    "Command is not complete or not valid");
                        }

                        //set value
                        ret.setCardNumber(strs[2]);
                        ret.setLimitAndCurrency(strs[3]);
                }
                break;
                case CHARGE:
                case CREDIT:
                {
                        if (strs.length != 3
                            || StringUtils.isBlank(strs[2]))
                        {
                                throw new SystemException(
                                    SystemErrorCode.INPUT_NOT_VALID,
                                    "Operation is not complete or not valid");
                        }
                        ret.setAmountAndCurrency(strs[2]);
                }
                break;
                default:
                        throw new SystemException(
                            SystemErrorCode.INPUT_NOT_VALID,
                            "Operation <" + cmdo + "> is not supported.");
                }

                ret.setOriginalRequest(clstr);
                return ret;
        }


        public void setLimitAndCurrency(String str)
        {
                String currencySymbol = String.valueOf(str.charAt(0));

                BigDecimal amount =
                    new BigDecimal(str.substring(1, str.length()));
                setCurrencySymbol(currencySymbol);
                setAccountLimit(amount);
        }


        public void setAmountAndCurrency(String str)
        {
                String currencySymbol = String.valueOf(str.charAt(0));

                BigDecimal amount =
                    new BigDecimal(str.substring(1, str.length()));
                setCurrencySymbol(currencySymbol);
                setAmount(amount);
        }
}
