/*
 * MFP project, SoundMgrJava.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.MultimediaAdapter;

import com.cyzapps.Jfcalc.DCHelper;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassExtObjRef;
import com.cyzapps.Jfcalc.DataClassNull;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Multimedia.SoundLib.SoundManager;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author tony
 */
public class SoundMgrJava extends SoundManager{
    public static class MediaPlayerWrapper {
        protected String audioFilePath;
        protected int mnFileType;
        protected String mstrReferencePath;
        protected AtomicBoolean started;
        protected AtomicInteger repeatingTimes;   // how many More the music plays after the first run. < 0 means repeat forever.
        protected AtomicInteger lastVolume;   // this is for volume resetting.
        protected AtomicInteger volume;   // volume is a float, but has to be stored as atomic integer as java doesn't support atomic float or atomic double
        //protected ExecutorService executor = Executors.newSingleThreadExecutor();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
        ExecutorService executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1),
            handler);
        protected Future executionResult = null;
        
        private AudioFormat getOutFormat(AudioFormat inFormat) {
            final int ch = inFormat.getChannels();
            final float rate = inFormat.getSampleRate();
            return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
        }
        
        private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
            final byte[] buffer = new byte[65536];
            float lastVlm = Float.intBitsToFloat(lastVolume.get());
            for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
                float thisVolume = Float.intBitsToFloat(volume.get());
                if (lastVlm != thisVolume) { // adjust volume now
                    try {
                        FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);        
                        gainControl.setValue(20f * (float) Math.log10(thisVolume));
                        lastVlm = thisVolume;
                    } catch (Exception ex) {
                        // may throw IllegalArgumentException if haven't been started.
                        Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                line.write(buffer, 0, n);
                if (!started.get()) {
                    break;
                }
            }
        }

        public MediaPlayerWrapper(String filePath, int nFileType, String strReferencePath) {
            audioFilePath = filePath;
            mnFileType = nFileType;
            mstrReferencePath = strReferencePath;
            started = new AtomicBoolean(false);
            repeatingTimes = new AtomicInteger(0);
            volume = new AtomicInteger(Float.floatToIntBits(1.0f));
            lastVolume = new AtomicInteger(Float.floatToIntBits(1.0f));
        }
        public int getFileType() {
            return mnFileType;
        }
        public String getReferencePath() {
            return mstrReferencePath;
        }
        
        public void play() throws IOException {
            File audioFile;
            try {
                audioFile = new File(new URI(audioFilePath));
                if (!audioFile.isFile() || !audioFile.exists()) {
                    throw new IOException("Invalid File");
                }
                try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
                    // we add an empty try - catch clause to identify if the audio File is with right format
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                    throw new IOException(ex.getMessage());
                }
                started.set(true);
                executionResult = executor.submit(new Runnable() {
                        public void run() {
                            try {
                                int idx = 0;
                                while (true) {
                                    int reptCnt = repeatingTimes.get();
                                    if (started.get() && (reptCnt < 0 || idx <= reptCnt)) {
                                        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
                                            AudioFormat format = getOutFormat(audioStream.getFormat());
                                            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                                            SourceDataLine audioSrcDataLine = (SourceDataLine) AudioSystem.getLine(info);
                                            SourceDataLine srcDataLine = audioSrcDataLine;
                                            srcDataLine.open(format);
                                            srcDataLine.start();
                                            stream(AudioSystem.getAudioInputStream(format, audioStream), srcDataLine);
                                            srcDataLine.drain();
                                            srcDataLine.stop();
                                        }
                                        idx ++;
                                    } else {
                                        break;
                                    }
                                }
                                started.set(false);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (UnsupportedAudioFileException ex) {
                                Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
            } catch (URISyntaxException ex) {
                Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                throw new IOException(ex.getMessage());
            }
        }
        
        public void stop() {
            started.set(false);
            if (executionResult != null) {
                try {
                    executionResult.get();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        public void setCycleCount(int repeatTimes) {
            repeatingTimes.set(repeatTimes);
        }
        
        public int getCycleCount() {
            return repeatingTimes.get();
        }
        
        public Boolean getStatus() {
            return started.get();
        }
        
        public String getSource() {
            return audioFilePath;
        }
        
        public double getVolume() {
            return Float.intBitsToFloat(volume.get());
        }

        public void setVolume(double thisVolume) {
            lastVolume.set(volume.get());
            if (thisVolume < 0.0001f) { // minimum is -80db
                volume.set(Float.floatToIntBits(0f));
            } else if (thisVolume > 1.0) {
                volume.set(Float.floatToIntBits(1f));
            } else {
                volume.set(Float.floatToIntBits((float)thisVolume));
            }
        }
    }

    protected List<MediaPlayerWrapper> mlistMediaPlayers = new LinkedList<>();
    
    @Override
    public boolean isPlaying(DataClass datumSndHdl) {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            return mediaPlayerWrapper.getStatus();
        } else {
            return false;
        }
    }

    @Override
    public DataClass playSound(SoundFileInfo sndFileInfo, boolean bRepeat, double dVolume, boolean bCreateNew) throws IOException {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        if (!bCreateNew) {
            for (MediaPlayerWrapper mpw : mlistMediaPlayers) {
                if (mpw.getReferencePath().equals(sndFileInfo.mstrFileReference)
                        && mpw.getFileType() == sndFileInfo.mnFileType) {
                    // OK, reuse this one
                    mediaPlayerWrapper = mpw;
                    break;
                }
            }
        }
        if (mediaPlayerWrapper == null) {
            MediaPlayerWrapper mpw;
            mpw = new MediaPlayerWrapper(sndFileInfo.mstrFilePath, sndFileInfo.mnFileType, sndFileInfo.mstrFileReference);
            mlistMediaPlayers.add(0, mpw);   // new media player always add first.
            mediaPlayerWrapper = mpw;
        } else {
            mediaPlayerWrapper.stop(); // force it to start to play from beginning.
        }
        if (mediaPlayerWrapper != null) {
            if (bRepeat) {
                mediaPlayerWrapper.setCycleCount(-1);
            } else {
                mediaPlayerWrapper.setCycleCount(0);
            }
            mediaPlayerWrapper.play();
            mediaPlayerWrapper.setVolume(dVolume);
            try {
                return new DataClassExtObjRef(mediaPlayerWrapper);
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                return new DataClassNull(); // will not be here
            }
        } else {
            return new DataClassNull(); // media player wrapper creation fail
        }
    }

    @Override
    public DataClass startSound(DataClass datumSndHdl) throws IOException {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            //mediaPlayerWrapper.stop();  // no need to stop it first as we garantee there is one playing
            mediaPlayerWrapper.play();
            try {
                return new DataClassExtObjRef(mediaPlayerWrapper);
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                // will not be here.
                Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
                return new DataClassNull();
            }
        }
        return new DataClassNull();
    }

    @Override
    public String getSoundPath(DataClass datumSndHdl) {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            return mediaPlayerWrapper.getSource();
        } else {
            return null;
        }
    }

    @Override
    public String getSoundReferencePath(DataClass datumSndHdl) {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            return mediaPlayerWrapper.getReferencePath();
        } else {
            return null;
        }
    }

    @Override
    public int getSoundSourceType(DataClass datumSndHdl) {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            return mediaPlayerWrapper.getFileType();
        } else {
            return 0;
        }
    }

    @Override
    public boolean getSoundRepeat(DataClass datumSndHdl) {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            return mediaPlayerWrapper.getCycleCount() != 0;
        } else {
            return false;
        }
    }

    @Override
    public void setSoundRepeat(DataClass datumSndHdl, boolean bRepeat) {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            if (bRepeat) {
                mediaPlayerWrapper.setCycleCount(-1);
            } else {
                mediaPlayerWrapper.setCycleCount(0);   // do not repeat
            }
        }
    }

    @Override
    public double getSoundVolume(DataClass datumSndHdl) {
        // note that setSoundVolume is setting maxium sound relative to system max sound.
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            return mediaPlayerWrapper.getVolume();
        } else {
            return 0;
        }
    }

    @Override
    public void setSoundVolume(DataClass datumSndHdl, double dVolume) {
        // note that setSoundVolume is setting maxium sound relative to system max sound.
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            mediaPlayerWrapper.setVolume(dVolume);
        }
    }
    
    @Override
    public void stopSound(DataClass datumSndHdl) {
        MediaPlayerWrapper mediaPlayerWrapper = null;
        try {
            Object o = DCHelper.lightCvtOrRetDCExtObjRef(datumSndHdl).getExternalObject();
            if (o instanceof MediaPlayerWrapper) {
                mediaPlayerWrapper = (MediaPlayerWrapper)o;
            }
        } catch (ErrProcessor.JFCALCExpErrException ex) {
            // will not be here.
            Logger.getLogger(SoundMgrJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mediaPlayerWrapper != null) {
            mediaPlayerWrapper.stop();
        }
    }
    
    @Override
    public void stopAllSounds() {
        for (MediaPlayerWrapper mpw : mlistMediaPlayers) {
            if (mpw != null) {
                mpw.stop();
            }
        }
    }
}
