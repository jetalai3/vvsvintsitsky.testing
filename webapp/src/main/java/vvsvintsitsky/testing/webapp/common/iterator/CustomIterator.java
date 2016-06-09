package vvsvintsitsky.testing.webapp.common.iterator;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

public class CustomIterator<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<T> list;
	private int cursor;

	public CustomIterator(List<T> list) {
		this.list = list;
		cursor = -1;
	}

	public boolean hasPrevious() {
		return cursor > 0;
	}

	public boolean hasNext() {
		return cursor < list.size() - 1;
	}

	public T previous() {
		if (!hasPrevious())
			throw new NoSuchElementException();
		return list.get(--cursor);
	}

	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return list.get(++cursor);
	}
}
