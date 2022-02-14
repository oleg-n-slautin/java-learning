package diff;

import lombok.val;

import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("rawtypes")
public class PojoDiffChain {

  private final Queue<PojoDiff> queue = new LinkedList<>();

  public void eval() {

    queue.forEach(PojoDiff::eval);
  }

  public <T> PojoDiffChain and(PojoDiff<T> diff) {
    this.queue.add(diff);
    return this;
  }

  public static <T> PojoDiffChain pojoDiffChain(PojoDiff<T> diff) {
    val chain = new PojoDiffChain();
    chain.queue.add(diff);
    return chain;
  }
}
