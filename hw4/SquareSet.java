import java.util.*;

/**
 *
 *
 * @author msklar3
 * @version 1.2
 * @since 1.2
 */
public class SquareSet<Square> implements Set<Square> {
    private class SquareSetIterator implements Iterator<Square> {
        private int index = 0;

        /**
         * Check if the iterator has another element after the current one.
         *
         * @return true if the iteration has more elements
         */
        public boolean hasNext() {
            return index <= lastIndex;
        }

        /**
         * Get the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        public Square next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return (Square) get(index++);
        }

        /**
         * Removes from the underlying collection the last element returned by
         * this iterator.
         */
        public void remove() {
            // TODO: Check this
            SquareSet.this.remove(get(index - 1));
        }
    }

    private Object[] elements;
    private int lastIndex;

    public SquareSet() {
        elements = new Object[0];
        lastIndex = -1;
    }

    public SquareSet(Collection<Square> c) {
        elements = new Object[0];

        lastIndex = -1;

        for (Square s : c) {
            add(s);
        }
    }

    /**
     * Get the square at a given index in the set.
     *
     * @param index index of the square to get
     * @return the square at the specified index
     */
    public Square get(int index) {
        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException(new Integer(index).toString());
        }

        return (Square) elements[index];
    }

    /**
     * Set the square at a given index.
     *
     * @param index index of the square to set
     * @param value square to set the square at the index to
     */
    public void set(int index, Square value) {
        elements[index] = value;
    }

    /**
     * Adds the specified square to this set if it is not present or null.
     *
     * @param s square to be added to this set
     * @return true if the square is added to this set
     */
    public boolean add(Square s) {
        if (s == null) {
            throw new NullPointerException();
        }

        if (contains((Object) s)) {
            return false;
        }

        elements = Arrays.copyOf(elements, size() + 1);

        set(++lastIndex, s);

        return true;
    }

    /**
     * Check if the set contains the object.
     *
     * @param o object to check if is in set
     * @return true if the object is in the set
     */
    public boolean contains(Object o) {
        for (Object e : elements) {
            if (o.equals(e)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if the set contains all the elements in the collection.
     *
     * @param c the collection to compare to the set
     * @return true if the set contains all the elements in the collection
     */
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compares the specified object with the set for equality.
     *
     * @param o object to be compared for equality with the set
     * @return true if the specified object is equal to this set
     */
    public boolean equals(Object o) {
        Collection c = (Collection) o;

        return containsAll(c) && c.containsAll(this);

        // TODO: Check which implementation works

        /*if (o == this) {
            return true;
        }

        if (!(o instanceof Set)) {
            return false;
        }

        Collection c = (Collection) o;

        if (c.size() != size()) {
            return false;
        }

        return containsAll(c);*/
    }

    /**
     *
     */
    public int hashCode() {
        return 0;
    }

    /**
     * Check if the set is empty.
     *
     * @return true if the set is empty
     */
    public boolean isEmpty() {
        return elements.length == 0;
    }

    /**
     * Returns an iterator over the elements in this set.
     *
     * @return an iterator over the elements in this set
     */
    public Iterator<Square> iterator() {
        return new SquareSetIterator();
    }

    /**
     * Get the number of elements in the set.
     *
     * @return number of elements in the set
     */
    public int size() {
        return elements.length;
    }

    /**
     * Get an array containing all the elements in the set.
     *
     * @return An array containing all the elements in the set
     */
    public Object[] toArray() {
        return elements;
    }

    /**
     *
     */
    public <T> T[] toArray(T[] t) {
        return null;
    }

    /**
     * Remove the specified element from the set.
     *
     * @param o element to remove from set
     * @return true if an element was removed from the set
     */
    public boolean remove(Object o) {
        if (contains(o)) {
            Object[] tempElements = Arrays.copyOf(elements, size());

            clear();

            for (Object s : tempElements) {
                if (!s.equals(o)) {
                    add((Square) s);
                }
            }

            return true;
        }

        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return true;
    }

    /**
     * Remove all the elements in the set.
     */
    public void clear() {
        elements = new Object[0];
    }

    public boolean retainAll(Collection<?> c) {
        return true;
    }

    /**
     *
     */
    public boolean addAll(Collection<? extends Square> c) {
        return true;
    }
}
