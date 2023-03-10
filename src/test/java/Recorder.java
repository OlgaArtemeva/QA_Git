import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Recorder extends ScreenRecorder {

    private String name;


    public Recorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                    Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    public List<File> getCreatedMovieFiles() {
        return super.getCreatedMovieFiles();
    }
}
