package com.brtr.ccp;


import com.brtr.model.Account;
import com.brtr.model.AccountKeeper;
import com.brtr.model.SystemException;
import com.brtr.model.command.Command;
import com.brtr.util.IOUtils;
import com.brtr.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class Processor
{

        private AccountKeeper keeper;


        public Processor()
        {
                keeper = new AccountKeeper();
        }


        /**
         * Process single command line.
         *
         * @param cmd input String of a command
         * @return a boolean indicating whether the command is processed.
         */
        public boolean processLine(String cmd)
        {
                boolean prcd = false;
                //validation
                if (StringUtils.isBlank(cmd))
                {
                        String msg = "!error: Command is not entered.\n";
                        System.out.print(msg);
                        return prcd;
                }

                Command cl = null;
                try
                {
                        cl = Command.parse(cmd);
                }
                catch (SystemException e)
                {
                        String msg = "!error: Parsing command line failed("
                            + e.getMessage() + ").\n";
                        System.out.print(msg);
                        return prcd;
                }

                if (cl == null)
                {
                        String msg = "!error: Parsing command line failed.\n";
                        System.out.print(msg);
                        return prcd;
                }
                else
                {
                        ProcessCommandResponse ret = processCommandLine(cl);
                        if (ret.isSuccess())
                                prcd = true;
                }

                return prcd;
        }


        /**
         * Process the input file using the file path provided.
         *
         * @param fp absolute path of the file.
         * @return a boolean indicating whether the file is processed.
         */
        public boolean processFile(String fp)
        {
                boolean processed = false;
                //validation
                if (StringUtils.isBlank(fp))
                {
                        System.out.println(
                            "File path is not provided.\n");
                        return processed;
                }

                //read lines from the input file
                List<String> lines = new ArrayList<>();
                lines = IOUtils.readFile(fp);
                if (lines == null)
                {
                        System.out.println(
                            "Some error happend while reading the file."
                                + " Returning to start.\n");
                        return processed;
                }

                //process each line
                boolean f = false;
                if (!lines.isEmpty())
                {
                        for (String clstr : lines)
                        {
                                Command cl = Command.parse(clstr);
                                ProcessCommandResponse lnProcessed =
                                    processCommandLine(cl);
                                if (!lnProcessed.isSuccess())
                                {
                                        f = true;
                                }
                        }

                        processed = true;
                }
                else
                {
                        System.out.println(
                            "No commands needs to be processed.\n");
                }

                //If there is failure, notify the user.
                if (f)
                {
                        System.out.println(
                            "Some error happend during processing commands.\n");
                }

                //Declare victory
                return processed;
        }


        /**
         * Output all accounts details to console with option to write to file.
         *
         * @param writeToFile boolean flagging whether to write the result to
         *                    standard file.
         * @return return absolute file path of the output file
         */
        public String outputAll(boolean writeToFile)
        {
                List<String> output = keeper.getAccountList();

                System.out.println("\n");
                for (String str : output)
                {
                        System.out.println(str);
                }
                System.out.println("\n");

                if (writeToFile)
                {
                        String pt = IOUtils.outputToFile(output, "allAccounts");
                        if (pt == null)
                        {
                                System.out.println(
                                    "Error happened during output, file is not created.");
                        }

                        return pt;
                }
                else
                {
                        return null;
                }
        }

        /**
         * Output all accounts details to test files with option to write to file.
         *
         */
        public String outputAllTest(String directory)
        {
                List<String> output = keeper.getAccountList();
                String pt = IOUtils.outputToFileInDirectory(output, "allAccounts", directory);
                if (pt == null)
                {
                        System.out.println(
                            "Error happened during output, file is not created.");
                }

                return pt;
        }


        /**
         * Output account details for a specific account
         * For future function extension
         *
         * @param writeToFile
         */
        public void output(String accountName, boolean writeToFile)
        {
                //TODO
        }

        /* ************************************************************ */
        /* ************************************************************ */
        /* private method below */
        /* ************************************************************ */
        /* ************************************************************ */


        /**
         * Process each parsed Command object
         *
         * @param cmd Command object
         * @return ProcessCommandResponse object
         */
        private ProcessCommandResponse processCommandLine(Command cmd)
        {
                //validation
                if (cmd == null)
                {
                        System.out.print("!error: Command is not available.\n");
                        return null;
                }

                ProcessCommandResponse ret =
                    new ProcessCommandResponse(cmd.getOriginalRequest());
                if (StringUtils.isBlank(cmd.getAccountName()))
                {
                        String msg = "!error: Account name is not available.\n";
                        System.out.print(msg);
                        ret.setSuccess(false);
                        ret.setError(msg);

                        return null;
                }
                if (cmd.getOperation() == null)
                {
                        String msg = "!error: Operation is not available.\n";
                        System.out.print(msg);
                        ret.setSuccess(false);
                        ret.setError(msg);

                        return null;
                }

                //Process
                Account acc = keeper.getAccout(cmd.getAccountName());
                boolean succeed = false;
                switch (cmd.getOperation())
                {
                case ADD_ACCOUNT:
                {
                        succeed = keeper.addAccount(
                            cmd.getAccountName(),
                            cmd.getCardNumber(),
                            cmd.getCurrencySymbol(),
                            cmd.getAccountLimit());
                        if (!succeed)
                        {
                                String msg =
                                    "!error: Unable to add new account.\n";
                                System.out.print(msg);
                                ret.setSuccess(false);
                        }
                        else
                        {
                                ret.setSuccess(true);
                        }
                }
                break;
                case CHARGE:
                {
                        if (acc == null)
                        {
                                String msg =
                                    "!error: no account exist for the customer.\n";
                                System.out.print(msg);
                                ret.setSuccess(false);
                        }

                        succeed = keeper.addAmount(
                            acc.getAccountName(),
                            cmd.getAmount());

                        if (!succeed)
                        {
                                String msg =
                                    "!error: Unable to charge this account <"
                                        + acc.getAccountName() + ">\n";
                                System.out.print(msg);
                                ret.setSuccess(false);
                        }
                        else
                        {
                                ret.setSuccess(true);
                        }
                }
                break;
                case CREDIT:
                {

                        if (acc == null)
                        {
                                String msg =
                                    "!error: no account exist for the customer.\n";
                                System.out.print(msg);
                                ret.setSuccess(false);
                        }

                        succeed = keeper.deductAmount(
                            acc.getAccountName(),
                            cmd.getAmount());
                        if (!succeed)
                        {
                                String msg =
                                    "!error: Unable to credit this account <"
                                        + acc.getAccountName() + ">\n";
                                System.out.print(msg);
                                ret.setSuccess(false);
                        }
                        else
                        {
                                ret.setSuccess(true);
                        }
                }
                break;
                default:
                        String msg =
                            "!error: Operation <"
                                + cmd.getOperation() + "> is not supported. \n";
                        System.out.print(msg);
                        ret.setSuccess(false);
                }

                return ret;
        }


        /**
         * Class describing  the command processing result.
         */
        private class ProcessCommandResponse
        {
                String error;

                boolean success;

                String request;


                public ProcessCommandResponse(String request)
                {
                        this.request = request;
                        success = false;
                }


                public String getError()
                {
                        return error;
                }


                public void setError(String error)
                {
                        this.error = error;
                }


                public boolean isSuccess()
                {
                        return success;
                }


                public void setSuccess(boolean success)
                {
                        this.success = success;
                }


                public String getRequest()
                {
                        return request;
                }


                public void setRequest(String request)
                {
                        this.request = request;
                }
        }

}

