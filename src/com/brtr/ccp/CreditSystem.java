package com.brtr.ccp;


import com.brtr.model.command.CommandMode;
import com.brtr.util.StringUtils;
import java.util.Scanner;


public class CreditSystem
{

       // public static void main(String[] args)
       // {
       //         System.out.println("[ Welcome to the keeping system ]\n");

       //         Processor processor = new Processor();
       //         Scanner scanner = new Scanner(System.in);

       //         CommandMode mode = CommandMode.FILE;
       //         while (!(mode != null && mode.equals(CommandMode.EXIT)))
       //         {
       //                 System.out.print(
       //                     "What do you want to do? \n"
       //                         + CommandMode.getDisplayOptions() + "\n");

       //                 String modeInput = scanner.nextLine().trim();
       //                 while(StringUtils.isBlank(modeInput)){
       //                         modeInput = scanner.nextLine().trim();
       //                 }

       //                 try
       //                 {
       //                         mode = CommandMode.readInput(modeInput);
       //                 }
       //                 catch (Exception e)
       //                 {
       //                         System.out.print(
       //                             "\n!error: Invalid input, please try again. \n");
       //                         continue;
       //                 }

       //                 switch (mode)
       //                 {
       //                 case FILE:
       //                 {

       //                         System.out.print("\nEnter complete file path or file name under current directory: ");
       //                         String fileName = scanner.next();
       //                         while(StringUtils.isBlank(fileName)){
       //                                 fileName = scanner.nextLine().trim();
       //                         }
       //                         System.out.println("\n#info: input file is: " + fileName);
       //                         if (processor.processFile(fileName))
       //                         {
       //                                 processor.outputAll(true);
       //                                 System.out.print("\n#info: Result has been "
       //                                     + "written to \\output directory.\n");
       //                         }
       //                         mode = null;
       //                 }
       //                 break;
       //                 case LINE:
       //                 {
       //                         System.out.print("\nEnter a new line of command: ");
       //                         String line = scanner.nextLine().trim();
       //                         while (StringUtils.isBlank(line))
       //                         {
       //                                 line = scanner.nextLine().trim();
       //                         }
       //                         if (processor.processLine(line))
       //                         {
       //                                 processor.outputAll(true);
       //                         }
       //                         mode = null;
       //                 }
       //                 break;
       //                 case VIEW:
       //                 {
       //                         processor.outputAll(false);
       //                         mode = null;
       //                 }
       //                 break;
       //                 case EXIT:
       //                 {
       //                         processor.outputAll(true);
       //                         System.out.print(
       //                             "#info: Final result has been written to "
       //                                 + "\\output directory.\n"
       //                                 + "[ Thank you for using the system ] \n");
       //                         System.exit(0);
       //                 }
       //                 break;
       //                 default:
       //                         {
       //                         System.out.print(
       //                             "!error: System error, please contact support.\n"
       //                                 + "[ Exiting the system... ] \n");
       //                         System.exit(0);
       //                         }
       //                 }
       //         }

       //         System.exit(0);
       // }


        public static void main(String[] args)
        {
                //Greet the user
                System.out.println("[ Welcome to the keeping system ]\n");

                //validate the input
                if (args == null || args.length == 0)
                {
                        System.out.print(
                            "!error: File name needed. Please use the absolute"
                                + " path of the input file.");
                        System.exit(0);
                }

                //read file name, process the file
                String fileName = args[0];
                System.out.println("#info: Input file is: " + fileName);
                Processor processor = new Processor();
                if (processor.processFile(fileName))
                {
                        processor.outputAll(true);
                        System.out.print("#info: Result has been "
                            + "written to \\output directory.\n");
                }
                else
                {
                        System.out.print(
                            "!error: File was not processed correctly, "
                                + "please contact support. \n");
                        System.exit(0);
                }

                //Declare victory and say goodbye
                System.out.print(
                    "[ Thank you for using the system ]\n");

                System.exit(0);
        }
}
