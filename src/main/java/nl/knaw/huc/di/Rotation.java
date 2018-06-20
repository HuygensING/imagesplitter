package nl.knaw.huc.di;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.opencv.core.Core.gemm;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;
import static org.opencv.imgproc.Imgproc.INTER_LANCZOS4;
import static org.opencv.imgproc.Imgproc.getRotationMatrix2D;
import static org.opencv.imgproc.Imgproc.warpAffine;

/**
 * Applies a random rotation to images.
 */
public class Rotation {
  private final double angleStddev;
  private final Random rand;

  public Rotation(Random rand, double angleStddev) {
    this.angleStddev = angleStddev;
    this.rand = rand;
  }

  public Mat apply(Mat img) {
    return apply(img, angleStddev * rand.nextGaussian());
  }

  static Mat apply(Mat img, double angle) {
    // http://john.freml.in/opencv-rotation
    double rangle = toRadians(angle);

    int h = img.height();
    int w = img.width();

    double nw = abs(sin(rangle) * h) + abs(cos(rangle) * w);
    double nh = abs(cos(rangle) * h) + abs(sin(rangle) * w);

    Mat rot = getRotationMatrix2D(new Point(.5 * nw, .5 * nh), angle, 1);

    Mat v = new Mat(3, 1, rot.type());
    v.put(0, 0, .5 * (nw - w));
    v.put(1, 0, .5 * (nh - h));
    v.put(2, 0, 0);
    Mat move = new Mat(2, 1, rot.type());
    gemm(rot, v, 1, move, 0, move);

    rot.put(0, 2, move.get(0, 0)[0] + rot.get(0, 2)[0]);
    rot.put(1, 2, move.get(1, 0)[0] + rot.get(1, 2)[0]);

    Mat out = new Mat();
    warpAffine(img, out, rot, new Size(ceil(nw), ceil(nh)), INTER_LANCZOS4);
    return out;
  }
}
