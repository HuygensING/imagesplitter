package nl.knaw.huc.di;

import org.junit.Assert;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.net.URL;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opencv.highgui.Highgui.imread;

public class TestRotation {
  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  // Simple regression test on the Afaka letter
  // (https://upload.wikimedia.org/wikipedia/en/4/42/Afaka_letter.png)
  @Test
  public void testAfaka() {
    String input = TestRotation.class.getResource("/Afaka_letter.png").getPath();
    Mat img = imread(input);
    Mat rot = Rotation.apply(img, 10);

    String output = TestRotation.class.getResource("/Afaka_rot10.png").getPath();
    Mat wanted = imread(output);

    assertEquals(rot, wanted);
  }

  private static void assertEquals(Mat a, Mat b) {
    Assert.assertEquals(a.height(), b.height());
    Assert.assertEquals(a.width(), b.width());

    for (int i = 0, h = a.height(); i < h; i++) {
      for (int j = 0, w = a.width(); j < w; j++) {
        assertTrue(abs(a.get(i, j)[0] - b.get(i, j)[0]) < 1e-7);
      }
    }
  }
}
