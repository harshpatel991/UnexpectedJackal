package com.unexpectedjackal.me.rezzit;

import android.os.Environment;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;

public class MyCache {
    private static final int CACHE_TIME = 1000 * 60 * 5;
    private static String CACHE_LOCATION =
            "/Android/data/com.unexpectedjackal.me.rezzit/cache/";

    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            CACHE_LOCATION = Environment.getExternalStorageDirectory() + CACHE_LOCATION;
            File f = new File(CACHE_LOCATION);
            f.mkdirs();
        }
    }

    public static String read(String key) {
        try {
            String fileName = CACHE_LOCATION + "/" + hash(key);
            File foundFile = new File(fileName);
            if (!foundFile.exists() || foundFile.length() < 1) {
                return null;
            }

            if (isTooOld(foundFile.lastModified())) {
                if (foundFile.exists()) {
                    foundFile.delete();
                }
            }

            byte data[] = new byte[(int) foundFile.length()];
            DataInputStream fis = new DataInputStream(new FileInputStream(foundFile));
            try {
                fis.readFully(data);
            }
            finally {
                fis.close();
            }

            return new String(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static void write(String key, String value) {
        try {
            String file = CACHE_LOCATION + "/" + hash(key);
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(value);
            pw.close();
        } catch (Exception e) {
            Log.println(Log.ERROR, "MyCache.write()", e.getMessage());
        }
    }

    static private String hash(String key) {
        byte[] hash = Hashing.md5().hashString(key, Charsets.UTF_8).asBytes();
        BigInteger bi = new BigInteger(1, hash);
        return String.format("%0" + (hash.length << 1) + "X", bi);
    }

    private static boolean isTooOld(long time) {
        long now = new Date().getTime();
        long diff = now - time;
        if (diff > CACHE_TIME)
            return true;
        return false;
    }
}
