package nl.knaw.huc.di;

import org.opencv.core.Mat;

import java.util.Objects;

public class Patch {
  private final int rStart, nRows, cStart, nCols;

  Patch(int colStart, int nCols, int rowStart, int nRows) {
    this.rStart = rowStart;
    this.nRows = nRows;
    this.cStart = colStart;
    this.nCols = nCols;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Patch p = (Patch) o;
    return rStart == p.rStart &&
      cStart == p.cStart &&
      nRows == p.nRows &&
      nCols == p.nCols;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rStart, nRows, cStart, nCols);
  }

  public Mat extract(Mat img) {
    return img.submat(rStart, rStart + nRows, cStart, cStart + nCols);
  }

  public int rowStart() {
    return rStart;
  }

  public int colStart() {
    return cStart;
  }

  public int rowEnd() {
    return rStart + nRows;
  }

  public int colEnd() {
    return cStart + nCols;
  }
}
