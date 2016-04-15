/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argochamber.security.integrity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class Checksum {

    public static final int TRIM_LEN = 15;

    private final String md5sum;

    /**
     * Gets the MD5 Checksum value of the input file.
     *
     * @return
     */
    public String getMd5sum() {
        return md5sum;
    }

    /**
     * Checks if both checksums are equal.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return this.md5sum.equals(((Checksum) obj).md5sum);
    }

    /**
     * Creates a new object that makes a checksum.
     *
     * @param path
     */
    public Checksum(String path) throws Exception {
        this.md5sum = getMD5Checksum(path);
    }
    
    /**
     * This will create an hexadecimal md5 checksum.
     * For the readable version:
     * @see #getMD5Checksum(java.lang.String) 
     * @param filename
     * @return
     * @throws Exception 
     */
    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    // see this How-to for a faster way to convert
    // a byte array to a HEX string
    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    /**
     * Recusive call, it will scan the whole folder.
     *
     * @param folder
     */
    public static void listFiles(File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFiles(fileEntry);
            } else {
                try {
                    Checksum sum = new Checksum(fileEntry.getAbsolutePath());
                    System.out.println(
                            fileEntry.getName()
                            .substring(0,
                                    fileEntry.getName().length() > TRIM_LEN
                                            ? TRIM_LEN
                                            : fileEntry.getName().length()
                            )
                            .concat("\t->\t")
                            .concat(sum.getMd5sum())
                    );
                } catch (IOException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(Checksum.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Checksum.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * This will be called if the file is used as an executable.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        File folder = new File(System.getProperty("user.dir"));

        listFiles(folder);

    }

}
