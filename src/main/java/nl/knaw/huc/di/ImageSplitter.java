package nl.knaw.huc.di;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Math.max;
import static org.opencv.highgui.Highgui.CV_IMWRITE_PNG_COMPRESSION;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;

public class ImageSplitter {
  public static void main(String[] args) throws IOException {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    final int height = Integer.parseInt(args[0]);
    final int width = Integer.parseInt(args[1]);
    int hstride = Integer.parseInt(args[2]);
    int wstride = Integer.parseInt(args[3]);

    String basedir = args[4];
    String indir = args[5];
    String outdir = args[6];

    MatOfInt pngParams = new MatOfInt(1, 2);
    pngParams.put(0, 0, CV_IMWRITE_PNG_COMPRESSION);
    pngParams.put(0, 1, 9);

    Files.walk(Paths.get(indir).resolve(basedir)).filter(Files::isRegularFile).parallel().forEach(f -> {
      Mat img = imread(f.toString());

      int h = height, w = width;
      if (img.height() < h || img.width() < w) {
        h = max(height, img.height());
        w = max(width, img.width());

        Mat newimg = new Mat(h, w, img.type());
        newimg.setTo(new Scalar(255, 255, 255)); // all white
        newimg.submat(0, img.height(), 0, img.width()).setTo(img);

        img.release();
        img = newimg;
      }

      for (int i = 0; i <= img.height() - h; i += hstride) {
        for (int j = 0; j <= img.width() - w; j += wstride) {
          String outpath = String.format("%s/%s/%s_%d_%d", basedir, outdir, f.getFileName(), i, j);
          System.out.println(outpath);
          imwrite(outpath, img.submat(i, i + height, j, j + width), pngParams);
        }
      }
    });
  }
}
