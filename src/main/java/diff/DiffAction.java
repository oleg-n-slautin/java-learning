package diff;

public interface DiffAction<V> {

  void accept(V left, V right);
}
