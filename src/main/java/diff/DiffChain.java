package diff;

import lombok.val;
import tips.LazyValue;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Supplier;

import static tips.Utils.ifNonNull;
import static tips.Utils.nvl;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DiffChain {

  private static final Supplier NULL = () -> null;

  private static final DiffAction NOP = (left, right) -> {
    //nop
  };

  private static final Difference DEF_DIF = Objects::deepEquals;

  private static interface Difference<T> {
    boolean test(T t1, T t2);
  }

  @SuppressWarnings("rawtypes")
  private static class DiffNode {

    private Supplier left = NULL;
    private Supplier right = NULL;
    private Difference diff;
    private DiffAction action = NOP;

    private final Supplier leftCache = new LazyValue<>(() -> left.get());
    private final Supplier rightCache = new LazyValue<>(() -> right.get());

    private DiffNode(final Supplier left,
                     final Supplier right,
                     final Difference diff,
                     final DiffAction action) {

      ifNonNull(left, i -> this.left = i);
      ifNonNull(right, i -> this.right = i);
      ifNonNull(action, i -> this.action = i);
      this.diff = diff;
    }

    private boolean eval() {

      return diff.test(leftCache.get(), rightCache.get());
    }
  }

  private final Queue<DiffNode> queue = new LinkedList<>();

  public void eval() {
    queue.forEach(
        i -> {
          if (!i.eval()) {
            DiffAction a = i.action;
            Supplier l = i.leftCache;
            Supplier r = i.rightCache;
            a.accept(l.get(), r.get());
          }
        }
    );
  }

  public <T> DiffChain add(final Supplier<T> left,
                           final Supplier<T> right,
                           final DiffAction<T> action) {

    val node = new DiffNode(left, right, DEF_DIF, action);
    queue.add(node);
    return this;
  }

  public <T> DiffChain add(final Supplier<T> left,
                           final Supplier<T> right,
                           final Difference<T> diff,
                           final DiffAction<T> action) {

    val node = new DiffNode(left,
        right,
        nvl(diff, () -> DEF_DIF),
        action
    );
    queue.add(node);
    return this;
  }

  public static <T> DiffChain diffChain(final Supplier<T> left,
                                        final Supplier<T> right,
                                        final DiffAction<T> action) {

    return new DiffChain().add(left, right, action);
  }

  public static <T> DiffChain diffChain(final Supplier<T> left,
                                        final Supplier<T> right,
                                        final Difference<T> diff,
                                        final DiffAction<T> action) {

    return new DiffChain().add(left, right, diff, action);
  }

}
