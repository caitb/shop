package com.masiis.shop.web.mall.utils.qrcode;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 创建和解析二维码类
 * Created by cai_tb on 16/3/22.
 */  
public class CreateParseCode {

    public static void  main(String [] args) throws IOException, WriterException{  
        //生成二维码
        CreateParseCode.createCode(300,300,"www.baidu.com", "/Users/cai_tb/Documents/TDCeeeee-test.png");
        //解析二维码
        CreateParseCode.parseCode(new File("/Users/cai_tb/Documents/TDCeeeee-test.png"));
          
    }

    /** 
     * 二维码的生成 
     * 
     */  
    public static void createCode(int width, int height, String content, String savePath){
        System.out.println("savePath: " + savePath);
        // 二维码的图片格式
        String format = "png";  
        /** 
         * 设置二维码的参数 
         */  
        HashMap hints = new HashMap();  
        // 内容所使用编码  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        try {  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE,width,height,hints);
            // 生成二维码  
            MatrixToImageWriter.writeToFile(bitMatrix, format, new File(savePath));
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
    }

    /** 
     * 二维码的解析 
     * 
     * @param file 
     */  
    public static void parseCode(File file)
    {  
        try  
        {  
            MultiFormatReader formatReader = new MultiFormatReader();  
   
            if (!file.exists())  
            {  
                return;  
            }  
   
            BufferedImage image = ImageIO.read(file);  
   
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            Binarizer binarizer = new HybridBinarizer(source);  
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
   
            Map hints = new HashMap();  
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
   
            Result result = formatReader.decode(binaryBitmap, hints);  

        }  
        catch (Exception e)  
        {
            e.printStackTrace();  
        }  
    }


}