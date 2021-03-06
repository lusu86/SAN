package eshop.utils;

import java.util.Stack;

public class DynVar<T> {

  public static <S> DynVar<S> initially(S initialValue) {
    return new DynVar<S>(initialValue);
  }

  public static <S> DynVar<S> create() {
    return new DynVar<S>(null);
  }

  public T value() {
    Stack<T> stack = localStacks.get();
    return !stack.isEmpty() ? stack.peek() : initialValue;
  }

  public void binding(T value, Runnable body) {
    Stack<T> stack = localStacks.get();
    stack.push(value);

    try {
      body.run();
    }
    finally {
      localStacks.get().pop();
    }
  }

  private final ThreadLocal<Stack<T>> localStacks =
      new ThreadLocal<Stack<T>>() {
        @Override
        protected Stack<T> initialValue() {
          return new Stack<T>();
        }
      };

  private final T initialValue;

  private DynVar(T initialValue) {
    this.initialValue = initialValue;
  }
}
