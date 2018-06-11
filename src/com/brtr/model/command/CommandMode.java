package com.brtr.model.command;


public enum CommandMode
{
        FILE("F", "File input"),
        LINE("L", "Type a command"),
        VIEW("V", "View all accounts"),
        EXIT("exit", "Exit the system"),
        ;

        private final String command;

        private final String display;


        CommandMode(String command, String display)
        {
                this.command = command;
                this.display = display;
        }


        public String getCommand()
        {
                return command;
        }


        public String getDisplay()
        {
                return display;
        }


        public static String getDisplayOptions(){
                String displayHint = new String();
                for(CommandMode cm : CommandMode.values()){
                        displayHint = displayHint.concat(cm.getCommand() + "(" + cm.getDisplay() + ")/");
                }
                if(displayHint.length() != 0){
                        displayHint = displayHint.substring(0, displayHint.length()-1);
                }

                return displayHint;
        }


        public static CommandMode readInput(String input)
            throws Exception
        {
                if (input == null || input == "")
                {
                        throw new Exception("No input");
                }
                for (CommandMode cm : CommandMode.values())
                {
                        if (cm.getCommand().equalsIgnoreCase(input))
                                return cm;
                }

                throw new Exception("Not found.");
        }
}
