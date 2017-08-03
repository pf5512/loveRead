package com.unionpay.loveRead.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 * @Desc:
 * @Author: tony
 * @Date: Created in 17/8/2 下午7:44  
 */
public class QrCodeUtil {
    // 设置二维码编码格式
    private static final String CHARSET = "UTF-8";
    // 保存的二维码格式
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 450;
    // LOGO宽度
    private static final int LOGO_WIDTH = 50;
    // LOGO高度
    private static final int LOGO_HEIGHT = 50;
    // 条形码宽度
    private static final int BARCODE_DEFAULT_WIDTH = 450;
    // 条形码高度
    private static final int BARCODE_DEFAULT_HEIGHT = 100;

    /**
     * @param content 二维码内容
     * @param output  输出流
     *
     * @return void 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    public static void createQrCode(String content, OutputStream output) throws Exception {
        createQrCode(content, null, output, false);
    }

    /**
     * @param content  二维码内容
     * @param destPath 目标保存地址
     *
     * @return void 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    public static void createQrCode(String content, String destPath) throws Exception {
        createQrCode(content, null, destPath, false);
    }

    /**
     * @param content      二维码内容
     * @param output       输出流
     * @param needCompress 是否压缩logo图片大小
     *
     * @return void 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    public static void createQrCode(String content, String logoPath, OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * @param content      二维码内容
     * @param destPath     目标保存地址
     * @param needCompress 是否压缩logo图片大小
     *
     * @return void 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    private static void createQrCode(String content, String logoPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        if (mkdirs(destPath)) {
            String file = new Random().nextInt(99999999) + ".jpg";
            ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
        }
    }


    /**
     * @param file 文件对象
     *
     * @return String 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    private static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        return new MultiFormatReader().decode(bitmap, hints).getText();
    }

    /**
     * @param path 文件路径
     *
     * @return String 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    public static String decode(String path) throws Exception {
        return decode(new File(path));
    }

    /**
     * @param content      二维码内容
     * @param needCompress 是否压缩logo图片大小
     *
     * @return BufferedImage 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入logo
        insertImage(image, logoPath, needCompress);
        return image;
    }


    /**
     * 生成条形码
     *
     * @param content
     *
     * @throws Exception
     */
    public static void createBarCode(String content, OutputStream output) throws Exception {
        createBarCode(content, output, BARCODE_DEFAULT_WIDTH, BARCODE_DEFAULT_HEIGHT);
    }

    /**
     * 生成条形码
     *
     * @param content
     * @param output
     *
     * @throws Exception
     */
    public static void createBarCode(String content, OutputStream output, int width, int height) throws Exception {
        BufferedImage image = createImage(content, width, height);
        ImageIO.write(image, FORMAT_NAME, output);
    }


    /**
     * 条形码编码
     *
     * @param contents
     * @param width
     * @param height
     */
    public static BufferedImage createImage(String contents, int width, int height) throws Exception {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                BarcodeFormat.CODE_128, codeWidth, height, null);

        BufferedImage image = new BufferedImage(codeWidth, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * @param source       二维码Image流
     * @param needCompress 是否压缩大小
     *
     * @return void 返回类型
     *
     * @throws Exception 参数说明
     * @throws
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws Exception {
        File file = new File(logoPath);
        if (!file.exists()) {
            System.err.println("" + logoPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(logoPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * @param destPath 文件夹地址
     *
     * @return void 返回类型
     *
     * @throws
     */
    private static boolean mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
            return true;
        }
        return false;
    }
}
