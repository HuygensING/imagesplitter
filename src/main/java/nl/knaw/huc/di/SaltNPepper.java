package nl.knaw.huc.di;

import org.opencv.core.Mat;

import java.util.Random;

/**
 * Adds salt-n-pepper noise to an image/matrix.
 */
public class SaltNPepper {
  private final double prob;
  private final Random rand;

  public SaltNPepper(double prob) {
    this(prob, new Random());
  }

  public SaltNPepper(double prob, Random rand) {
    if (prob < 0 || prob > 1) {
      throw new IllegalArgumentException("prob must be between 0 and 1 (inclusive)");
    }
    this.prob = prob;
    this.rand = rand;
  }

  /**
   * Sets elements of the matrix m to 0 or 255.
   *
   * @param m An image matrix. Must be grayscale.
   */
  public void apply(Mat m) {
    if (m.channels() > 1) {
      throw new IllegalArgumentException("m may not have more than one channel");
    }
    for (int i = 0; i < m.height(); i++) {
      for (int j = 0; j < m.width(); j++) {
        if (rand.nextDouble() < prob) {
          m.put(i, j, rand.nextBoolean() ? 0 : 255);
        }
      }
    }
  }
}
