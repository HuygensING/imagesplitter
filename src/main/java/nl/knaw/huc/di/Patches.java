package nl.knaw.huc.di;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.stream.IntStream.range;

/**
 * Extracts fixed-size patches from an image/matrix.
 */
public class Patches {
  /**
   * Generates a stream of patches.
   *
   * @param imgWidth    Width of base image.
   * @param imgHeight   Height of base image.
   * @param patchWidth  Width of the desired patches, in pixels.
   * @param patchHeight Height of the desired patches, in pixels.
   * @param wstride     Horizontal stride of the patches. Each patch starts this many pixels right of its left
   *                    neighbor.
   * @param hstride     Vertical stride of the patches. Each patch starts this many pixels below its upper neighbor.
   * @return A stream of patches.
   */
  public static Stream<Patch> stream(int imgWidth, int imgHeight, int patchWidth, int patchHeight, int wstride,
                                     int hstride) {
    return range(0, imgWidth / wstride)
      .filter(j -> j * wstride + patchWidth <= imgWidth)
      .boxed()
      .flatMap(j -> range(0, imgHeight / hstride)
        .filter(i -> i * hstride + patchHeight <= imgHeight)
        .mapToObj(i -> new Patch(j * wstride, patchWidth, i * hstride, patchHeight)));
  }
}
