package com.brtr.util;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class IOUtils
{
        private static String fileOutputDirectory = "output/";

        private static String ext = ".txt";

        private static String testCaseInputIdentifier = "input";

        private static String testCaseExpectedResultIdentifier = "r";


        /**
         * Giving a file path(absolute), return a list of line commands as String
         * If exception happens, will return null;
         *
         * @param fp String of full file path
         * @return List of command Strings
         */
        public static List<String> readFile(String fp)
        {
                try
                {
                        return readFileWithException(fp);
                }
                catch (Exception e)
                {
                        return null;
                }
        }


        /**
         * Write a list of lines of String to a file with name fp. The file
         * will be created under predefined directory
         *
         * @param ct  List of content, each element contains a String
         * @param pt, file name to write to
         * @return absolute path of the created file
         */
        public static String outputToFile(
            List<String> ct,
            String pt)
        {

                if (ct == null || ct.isEmpty())
                {
                        System.out.println(
                            "No content to white.");
                        return null;
                }

                if (StringUtils.isBlank(pt))
                {
                        System.out.println(
                            "File name is not specified.");
                        return null;
                }

                String content = new String();
                for (String str : ct)
                {
                        content = content.concat(str + "\n");
                }

                String filePath = fileOutputDirectory.concat(pt).concat(ext);
                //See if the file exist
                File file = new File(filePath);

                if (file.exists())
                {
                        file.delete();
                }

                try
                {
                        writeToFile(content, fileOutputDirectory, filePath);
                }
                catch (Exception e)
                {
                        System.out.println(
                            "Exception happened while writing to file <"
                                + filePath + ">.");
                        return null;
                }

                return filePath;
        }

        /**
         * Write a list of lines of String to a file with name fp.
         * @param ct List of content, each element contains a String
         * @param pt, file name to write to
         * @param directory, under which directory will the file be written
         * @return absolute path of the created file
         */
        public static String outputToFileInDirectory(
            List<String> ct,
            String pt,
            String directory)
        {

                if (ct == null || ct.isEmpty())
                {
                        System.out.println(
                            "No content to white.");
                        return null;
                }

                if (StringUtils.isBlank(pt))
                {
                        System.out.println(
                            "File name is not specified.");
                        return null;
                }

                String content = new String();
                for (String str : ct)
                {
                        content = content.concat(str + "\n");
                }

                String filePath = directory.concat(pt).concat(ext);

                try
                {
                        writeToFile(content, directory, filePath);
                }
                catch (Exception e)
                {
                        System.out.println(
                            "Exception happened while writing to file <"
                                + filePath + ">.");
                        return null;
                }

                return filePath;
        }


        /**
         * List all files under a certain file directory
         *
         * @param dir target directry absolute path
         * @return a List of file absolute path
         */
        public static List<String> listAllFileNamesInDirectory(
            String dir,
            String nameFilter)
        {
                File folder = new File(dir);

                FilenameFilter textFilter = new FilenameFilter()
                {
                        public boolean accept(File dir, String name)
                        {
                                String lowercaseName = name.toLowerCase();
                                if (lowercaseName.matches(nameFilter))
                                {
                                        return true;
                                }
                                else
                                {
                                        return false;
                                }
                        }
                };

                File[] listOfFiles = StringUtils.isNotBlank(nameFilter) ?
                    folder.listFiles(textFilter) :
                    folder.listFiles();


                List<String> names = new ArrayList<>();
                for (int i = 0; i < listOfFiles.length; i++)
                {
                        if (listOfFiles[i].isFile())
                        {
                                names.add(listOfFiles[i].getAbsolutePath());
                        }
                }

                return names;
        }


        /**
         * Get the expected result file of a test case.
         * @param fileName test case input file name
         * @param resultDirectory the directory path where the expected result
         *                        is located
         * @return name of the expected test case result file
         */
        public static String getExpectedTestResultFileNameForTestFile(
            String fileName,
            String resultDirectory)
        {
                File file = new File(fileName);
                String name = file.getName();
                name = resultDirectory.concat(name.replace(
                    testCaseInputIdentifier,
                    testCaseExpectedResultIdentifier));
                File f = new File(name);

                if (file.exists())
                {
                        return name;
                }
                else
                        return null;
        }


        /**
         * Giving two file absolute paths, compare to see if the two files are
         * identical
         * @param f1, absolute path of file 1
         * @param f2, absolute path of file 2
         * @return boolean
         */
        public static boolean compareFile(
            String f1,
            String f2)
        {
                List<String> f1l = new ArrayList<>();
                List<String> f2l = new ArrayList<>();
                try
                {
                        f1l = readFileWithException(f1);
                }
                catch (Exception e)
                {
                        System.out.println(
                            "Exception happened while reading file 1 <"
                                + f1 + ">.");
                        return false;
                }

                try
                {
                        f2l = readFileWithException(f2);
                }
                catch (Exception e)
                {
                        System.out.println(
                            "Exception happened while reading file 2 <"
                                + f2 + ">.");
                        return false;
                }

                if (f1l == null || f2l == null)
                {
                        return false;
                }

                if (f1l.size() != f2l.size())
                {
                        return false;
                }

                int ln = 0;
                for (; ln < f1l.size(); ln++)
                {
                        String l1 = f1l.get(0);
                        String l2 = f2l.get(0);
                        if (l1 == null)
                        {
                                if (l2 != null)
                                {
                                        return false;
                                }
                                else
                                {
                                        continue;
                                }
                        }
                        else
                        {
                                if (l2 == null)
                                {
                                        return false;
                                }
                                else if (!l2.equals(l1))
                                {
                                        return false;
                                }
                                else
                                {
                                        continue;
                                }
                        }
                }

                return true;
        }


        /**
         * Remove all files matching the naming pattern from a specific directory
         * @param dir directory of target
         * @param pattern naming pattern of target files.
         */
        public static void removeAllFileFromDirectory(
            String dir,
            String pattern)
        {
                //validate the input
                List<String> files =
                    IOUtils.listAllFileNamesInDirectory(
                        dir,
                        pattern);
                for (String file : files)
                {
                        File f = new File(file);

                        if (f.exists())
                        {
                                f.delete();
                        }
                }

                return;
        }

        /* ************************************************************ */
        /* ************************************************************ */
        /* private method below */
        /* ************************************************************ */
        /* ************************************************************ */


        /**
         * Write content to a file
         * @param content
         * @param fileParentDirectory
         * @param fileAbsolutePath
         * @return
         * @throws IOException
         * @throws Exception
         */
        private static boolean writeToFile(
            String content,
            String fileParentDirectory,
            String fileAbsolutePath)
            throws IOException, Exception
        {

                if (StringUtils.isBlank(content)
                    || StringUtils.isBlank(fileParentDirectory)
                    || StringUtils.isBlank(fileAbsolutePath))
                {
                        String msg = "!error: Input params are missing";
                        System.out.println(msg);
                        throw new Exception(msg);
                }

                File file = new File(fileAbsolutePath);

                if (file.exists())
                {
                        file.delete();
                }

                byte[] strToBytes = content.getBytes();

                try
                {
                        File pathch = new File(fileParentDirectory);
                        if (!pathch.exists())
                        {
                                pathch.mkdirs();
                        }

                        FileOutputStream fos = new FileOutputStream(fileAbsolutePath);
                        fos.write(strToBytes);
                        fos.close();
                }
                catch (IOException ioe)
                {
                        System.out.println(
                            "Exception happened while writing to file <"
                                + fileAbsolutePath + ">.");
                        throw ioe;
                }

                return true;
        }


        /**
         * Read lines from a file. Exceptions will be thrown if error happens
         * @param fp file path.
         * @return
         * @throws Exception
         */
        private static List<String> readFileWithException(String fp)
            throws Exception
        {
                if (StringUtils.isBlank(fp))
                {
                        String msg = "!error: Input file name is missing";
                        System.out.println(msg);
                        throw new Exception(msg);
                }

                List<String> rets = new ArrayList<>();
                // We need to provide file path as the parameter:
                // double backquote is to avoid compiler interpret words
                // like \com.brtr.test as \t (ie. as a escape sequence)
                File file = new File(fp);

                BufferedReader br;
                try
                {
                        br =
                            new BufferedReader(new FileReader(file));

                }
                catch (FileNotFoundException e)
                {
                        String msg = "!error: This file does not exist";
                        System.out.println(msg);
                        throw new Exception(msg);
                }

                try
                {
                        String st;
                        while ((st = br.readLine()) != null)
                        {
                                if(StringUtils.isNotBlank(st))
                                        rets.add(st);
                        }
                        br.close();
                }
                catch (IOException ioe)
                {
                        String msg = "!error: " + ioe.getMessage();
                        System.out.println(msg);
                        throw new Exception(msg);
                }
                return rets;
        }

}
