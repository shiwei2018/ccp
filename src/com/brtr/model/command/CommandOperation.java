package com.brtr.model.command;


public enum CommandOperation
{
        ADD_ACCOUNT("Add"),
        CHARGE("Charge"),
        CREDIT("Credit"),;


        private final String lineStarter;


        public String getLineStarter()
        {
                return lineStarter;
        }


        CommandOperation(String lineStarter)
        {
                this.lineStarter = lineStarter;
        }


        public static CommandOperation getOperation(String input)
            throws Exception
        {
                if (input == null || input == "")
                {
                        throw new Exception("No input");
                }
                for (CommandOperation co : CommandOperation.values())
                {
                        if (co.getLineStarter().equalsIgnoreCase(input))
                                return co;
                }

                throw new Exception("Not found.");
        }
}
