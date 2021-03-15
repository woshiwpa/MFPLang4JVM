/*
 * MFP project, ImageMgrJava.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.MultimediaAdapter;

import com.cyzapps.JGI2D.Display2D;
import com.cyzapps.JGI2D.DisplayLib;
import com.cyzapps.Jfcalc.DCHelper;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassExtObjRef;
import com.cyzapps.Jfcalc.DataClassNull;
import com.cyzapps.Jfcalc.DataClassString;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Multimedia.ImageLib.ImageManager;
import com.cyzapps.Multimedia.MultimediaManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author tony
 */
public class ImageMgrJava extends ImageManager {
    public static class ImageWrapper {
        private BufferedImage mbitmap;
        public ImageWrapper(BufferedImage bitmap) {
            mbitmap = bitmap;
        }
        public BufferedImage getImageFromWrapper() {
            return mbitmap;
        }
    }

    @Override
    public DataClass loadImage(String strImagePath) {
        // strImagePath is either file path or url.
        BufferedImage img = null;
        try {
            if (MultimediaManager.isValidURL(strImagePath)) {
                img = ImageIO.read(new URL(strImagePath));
            } else {
                img = ImageIO.read(IOLib.getFileFromCurrentDir(strImagePath));
            }
        } catch (IOException e) {
            // failed to load image.
        }
        if (img != null) {
            try {
                return new DataClassExtObjRef(new ImageWrapper(img));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here
            }
        }
        return new DataClassNull();
    }
    
    @Override
    public DataClass loadImage(InputStream inputStream) {
        // strImagePath is either file path or url.
        BufferedImage img = null;
        try {
            img = ImageIO.read(inputStream);
        } catch (IOException e) {
            // failed to load image.
        }
        if (img != null) {
            try {
                return new DataClassExtObjRef(new ImageWrapper(img));
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here
            }
        }
        return new DataClassNull();
    }
    
    @Override
    public int[] getImageSize(DataClassExtObjRef img) {
        try {
            if (img.getExternalObject() instanceof ImageWrapper) {
                BufferedImage bufferedImg = ((ImageWrapper)img.getExternalObject()).getImageFromWrapper();
                int[] size = new int[2];
                size[0] = bufferedImg.getWidth();
                size[1] = bufferedImg.getHeight();
                return size;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // do nothing here
        }
        return null;
    }

    @Override
    public Display2D openImageDisplay(DataClass pathOrImgHandler) {
        if (pathOrImgHandler.isNull()) {
            // if parameter is explicitly null, it means we just want an empty display
            return new ImageDisplay();
        } else {
            // if parameter is not explicity null, will return a display with background image,
            // however, if pathOrImgHandler is invalid, will return null;
            DataClassString imgPath = DCHelper.try2LightCvtOrRetDCString(pathOrImgHandler);
            String path = null;
            BufferedImage img = null;
            if (imgPath == null) {
                DataClassExtObjRef imgHandler = DCHelper.try2LightCvtOrRetDCExtObjRef(pathOrImgHandler);
                try {
                    if (imgHandler == null || !(imgHandler.getExternalObject() instanceof ImageWrapper)) {
                        return null;    // invalid path or image handler.
                    } else {
                        img = ((ImageWrapper)imgHandler.getExternalObject()).getImageFromWrapper();
                    }
                } catch (ErrProcessor.JFCALCExpErrException ex) {
                    Logger.getLogger(ImageMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
                // now buffered image must not be null.
            } else {
                try {
                    path = imgPath.getStringValue();
                    if (path == null) {
                        return null;
                    }
                } catch (ErrProcessor.JFCALCExpErrException ex) {
                    Logger.getLogger(ImageMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
                try {
                    if (MultimediaManager.isValidURL(path)) {
                        img = ImageIO.read(new URL(path));
                    } else {
                        img = ImageIO.read(IOLib.getFileFromCurrentDir(path));
                    }
                } catch (IOException e) {
                    // failed to load image.
                    return null;
                }
            }
            if (img == null) {
                return null; // invalid image.
            }
            // now we have non-null img.
            ImageDisplay imgDisplay = new ImageDisplay();
            imgDisplay.setDisplaySize(img.getWidth(), img.getHeight());
            try {
                imgDisplay.setBackgroundImage(new DataClassExtObjRef(new ImageWrapper(img)), 0);
                if (path != null) {
                    imgDisplay.mstrFilePath = path;
                }
                return imgDisplay;
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(ImageMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                return null;    // will not be here.
            }
        }
    }

    @Override
    public void shutdownImageDisplay(DisplayLib.IGraphicDisplay display) {
        if (display != null) {
            display.close();
        }
    }

    @Override
    public boolean isValidImageHandle(DataClassExtObjRef img) {
        try {
            return img.getExternalObject() instanceof ImageWrapper;
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            return false;   // will not be here.
        }
    }

    @Override
    public DataClass createImage(int w, int h) {
        try {
            if (w <= 0 || h <= 0) {
                return new DataClassNull();
            } else {
                BufferedImage imgHandler = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                return new DataClassExtObjRef(new ImageWrapper(imgHandler));
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            Logger.getLogger(ImageMgrJava.class.getName()).log(Level.SEVERE, null, ex);
            return new DataClassNull();
        }
    }

    @Override
    public DataClass cloneImage(DataClassExtObjRef img) {
        try {
            if (img == null || !(img.getExternalObject() instanceof ImageWrapper)) {
                return new DataClassNull();
            } else {
                BufferedImage imgHandler = ((ImageWrapper)img.getExternalObject()).getImageFromWrapper();
                ColorModel cm = imgHandler.getColorModel();
                boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
                WritableRaster raster = imgHandler.copyData(null);
                return new DataClassExtObjRef(new ImageWrapper(new BufferedImage(cm, raster, isAlphaPremultiplied, null)));
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            Logger.getLogger(ImageMgrJava.class.getName()).log(Level.SEVERE, null, ex);
            return new DataClassNull();
        }
    }

    @Override
    public DataClass cloneImage(DataClassExtObjRef img, int x1, int y1, int x2, int y2, int destW, int destH) {
        try {
            if (img == null || !(img.getExternalObject() instanceof ImageWrapper) || x1 >= x2 || y1 >= y2
                    || x1 >= ((ImageWrapper)img.getExternalObject()).getImageFromWrapper().getWidth() || x2 <= 0
                    || y1 >= ((ImageWrapper)img.getExternalObject()).getImageFromWrapper().getHeight() || y2 <= 0
                    || destW <= 0 || destH <= 0) {
                // invalid bitmap or copy range
                return new DataClassNull();
            } else {
                BufferedImage imgHandler = ((ImageWrapper)img.getExternalObject()).getImageFromWrapper();
                x1 = Math.max(0, Math.min(x1, imgHandler.getWidth()));
                x2 = Math.max(0, Math.min(x2, imgHandler.getWidth()));
                y1 = Math.max(0, Math.min(y1, imgHandler.getHeight()));
                y2 = Math.max(0, Math.min(y2, imgHandler.getHeight()));
                BufferedImage imgCopy = new BufferedImage(destW, destH, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = imgCopy.createGraphics();
                g.drawImage(imgHandler, 0, 0, destW, destH, x1, y1, x2, y2, null);
                g.dispose();
                return new DataClassExtObjRef(new ImageWrapper(imgCopy));
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            Logger.getLogger(ImageMgrJava.class.getName()).log(Level.SEVERE, null, ex);
            return new DataClassNull();
        }
    }
    
    @Override
    public boolean saveImage(DataClassExtObjRef img, String imgFormat, String strImagePath) {
        try {
            // retrieve image
            if (img == null || !(img.getExternalObject() instanceof ImageWrapper)) {
                return false;
            }
            BufferedImage imgHandler = ((ImageWrapper)img.getExternalObject()).getImageFromWrapper();
            File outputfile = new File(strImagePath);
            if (outputfile.getParentFile() != null) {
                // create parent folders
                outputfile.getParentFile().mkdirs();
            }
            if (imgFormat.equalsIgnoreCase("jpeg")) {
                imgFormat = "JPG";
            } else {
                imgFormat = imgFormat.toUpperCase(Locale.US);
            }
            if (imgFormat.equals("JPG")) {  // JPG doesn't support alpha, so have to convert to RGB format
                BufferedImage imgRGB = new BufferedImage(imgHandler.getWidth(), imgHandler.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d= imgRGB.createGraphics();
                g2d.drawImage(imgHandler, 0, 0, null);
                g2d.dispose();
                imgHandler = imgRGB;
            }
            boolean bSaveFileOK = false;
            if (!(bSaveFileOK = ImageIO.write(imgHandler, imgFormat, outputfile))) {
                if (imgFormat.equals("BMP")) {
                    // another chance for BMP
                    BufferedImage newBufferedImage = new BufferedImage(
                            imgHandler.getWidth(), imgHandler.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    newBufferedImage.createGraphics().drawImage(imgHandler, 0, 0, Color.WHITE, null);
                    bSaveFileOK = ImageIO.write(newBufferedImage, imgFormat, outputfile);
                }
            }
            return bSaveFileOK;
        } catch (Exception e) {
            return false;
        }
    }
}
