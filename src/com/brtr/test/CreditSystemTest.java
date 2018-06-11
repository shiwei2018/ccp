package com.brtr.test;


import com.brtr.ccp.Processor;
import com.brtr.util.IOUtils;
import com.brtr.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class CreditSystemTest
{
        private static String testcaseDir = "testcases/";

        private static String testResultDir = "testcases/results/";

        private static String testResultExpectionDir =
            "testcases/assert/";

        private static String testInputFilePattern =
            "test_input_[0-9]*.txt";

        private static String testOutputFilePattern =
            "allAccount.txt";


        public static void main(String[] args)
        {
                //Greet the user
                System.out.println("[ Running test cases for Credit System ]\n");

                //validate the input
                List<String> files =
                    IOUtils.listAllFileNamesInDirectory(
                        testcaseDir, testInputFilePattern);

                List<String> failedFiles = new ArrayList<>();
                int total = files.size();
                int processed = 0;
                int passed = 0;
                int failedProcessing = 0;
                int failedAssert = 0;
                for (String fileName : files)
                {
                        //read file name, process the file
                        System.out.println("#info: Input file is: " + fileName);
                        Processor processor = new Processor();
                        if (processor.processFile(fileName))
                        {
                                processed++;
                                String outFile = processor.outputAllTest(
                                    testResultExpectionDir);

                                //assert
                                String expectedResultPath =
                                    IOUtils.getExpectedTestResultFileNameForTestFile(
                                        fileName,
                                        testResultDir);
                                if (StringUtils.isBlank(expectedResultPath))
                                {
                                        System.out.print(
                                            "!error: test case result file does not exist for <"
                                                + fileName + ">\n");
                                        failedProcessing++;
                                        failedAssert++;
                                }
                                else
                                {
                                        boolean identicle = IOUtils.compareFile(
                                            outFile, expectedResultPath);
                                        if (identicle)
                                        {
                                                passed++;
                                        }
                                        else
                                        {
                                                System.out.print(
                                                    "!error: test case result does not match expectation of <"
                                                        + fileName + ">\n");
                                                failedAssert++;
                                        }
                                }
                        }
                        else
                        {
                                System.out.print(
                                    "!error: failed to process <"
                                        + fileName + ">\n");
                                failedProcessing++;
                                failedAssert++;
                        }


                }

                IOUtils.removeAllFileFromDirectory(
                    testResultExpectionDir,
                    testOutputFilePattern);
                //gather failed files

                //Declare victory and say goodbye
                String result = new String();
                result = result.concat("\nTotal test cases: " + total + "\n");
                result =
                    result.concat("Total processed cases: " + processed + "\n");
                if(failedProcessing > 0)
                {
                        result = result.concat(
                            "!Total test cases failed to process: "
                                + failedProcessing
                                + "\n");
                }
                if(failedProcessing > 0)
                {
                        result = result.concat(
                            "!Total test cases with wrong output: "
                                + failedAssert
                                + "\n");
                }
                result = result.concat("Total passed cases: " + passed + "\n");

                System.out.print(
                    result + "\n");
                System.out.print(
                    "[ Finish running test cases for Credit System ]\n");


                System.exit(0);
        }
}
