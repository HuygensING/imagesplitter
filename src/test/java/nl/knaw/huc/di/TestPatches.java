package nl.knaw.huc.di;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class TestPatches {
  @Test
  public void testIndices() {
    List<Patch> l = Patches.stream(20, 10, 4, 3, 3, 4).collect(Collectors.toList());
    assertEquals(asList(
      new Patch(0, 4, 0, 3),
      new Patch(0, 4, 4, 3),
      new Patch(3, 4, 0, 3),
      new Patch(3, 4, 4, 3),
      new Patch(6, 4, 0, 3),
      new Patch(6, 4, 4, 3),
      new Patch(9, 4, 0, 3),
      new Patch(9, 4, 4, 3),
      new Patch(12, 4, 0, 3),
      new Patch(12, 4, 4, 3),
      new Patch(15, 4, 0, 3),
      new Patch(15, 4, 4, 3)
    ), l);
  }
}
