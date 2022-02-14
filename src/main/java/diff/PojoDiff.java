package diff;

import java.util.function.Function;

import static diff.DiffChain.diffChain;
import static java.util.Objects.isNull;

public class PojoDiff<T> {

  private T left;
  private T right;
  private DiffChain chain;

  public void eval() {

    chain.eval();
  }

  public <V> PojoDiff<T> add(final Function<T, V> fun,
                             final DiffAction<V> diffAction) {

    if (isNull(chain)) {
      chain = diffChain(
          () -> fun.apply(left),
          () -> fun.apply(right),
          diffAction
      );
    } else {
      chain.add(
          () -> fun.apply(left),
          () -> fun.apply(right),
          diffAction
      );
    }

    return this;
  }

  public static <T> PojoDiff<T> pojoDiff(final T left,
                                         final T right) {

    final PojoDiff<T> diff = new PojoDiff<>();
    diff.left = left;
    diff.right = right;
    return diff;
  }
}
