package com.example.filehandler.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class imageUtils {

    //Compression method, best effort (9)
    public static byte[] compressImg(byte[] img){
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(img);
        deflater.finish();

        ByteArrayOutputStream output = new ByteArrayOutputStream(img.length);
        //the size can be 4*1024?
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()){
            int size = deflater.deflate(tmp);
            output.write(tmp,0, size);
        }
        try {
            output.close();
        }catch(Exception ignored){}
        return output.toByteArray();
    }

    //Decompress output
    public static byte[] decompressImg(byte[] img){
        Inflater inflater = new Inflater();
        inflater.setInput(img);
        ByteArrayOutputStream output = new ByteArrayOutputStream(img.length);
        //trying 4*1024
        byte[] tmp = new byte[4*1024];
        try{
            while(!inflater.finished()){
                int size = inflater.inflate(tmp);
                output.write(tmp, 0, size);
            }
            output.close();
        } catch (DataFormatException ignored) {
            System.out.println("imageUtils.decompressImg not working");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toByteArray();
    }
}
