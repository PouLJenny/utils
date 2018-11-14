package top.poul.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * 
 * @author 杨霄鹏
 * @version 1.0, 2017-02-06
 * @since
 */
public class TextFile extends ArrayList<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -2546682685500995455L;
    
    

    // Read a file as a single string
    public static String read(final String fileName) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        String result = null;
        try {
            try {
                in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
                String s;
                boolean removeFlag = false;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                    if (!removeFlag) {
                        removeFlag = true;
                    }
                }
                result = sb.toString();
                if (removeFlag) {
                    result = result.substring(0, result.length() - 1);
                } 
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return result;
    }
    
    // Write a single file in one method call
    public static void write(final String fileName, final String text) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile(),"UTF-8");
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    // Read a file, split by any regular expression
    public TextFile (final String fileName,final String splitter) {
        super(Arrays.asList(read(fileName).split(splitter)));
        if (get(0).equals("")) {
            remove(0);
        } 
    }
    
    // Normally read by lines
    public TextFile(final String fileName) {
        this(fileName, "\n");
    }
    
    public void write(final String fileName) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                for (String item : this) {
                    out.println(item);
                }
            } finally {
                out.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
        String file = read("F:\\TextFile.java");
        write("F:\\test2.txt",file);
        TextFile text = new TextFile("F:\\test2.txt");
        text.write("F:\\test3.txt");
        TreeSet<String> words = new TreeSet<String>(new TextFile("F:\\TextFile.java","\\W+"));
        System.out.println(words.headSet("a"));
    }
}
