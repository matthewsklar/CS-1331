import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Represents a set of Squares.
 *
 * @author msklar3
 * @version 1.2
 * @since 1.2
 */
public class SquareSet implements Set<Square> {
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
         * @throws NoSuchElementException if the iteration has no more elements
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
            SquareSet.this.remove(get(index--));
        }
    }

    private Object[] elements;
    private int lastIndex;

    /**
     * Constructor for SquareSet
     */
    public SquareSet() {
        elements = new Object[0];
        lastIndex = -1;
    }

    /**
     * Constructor for SquareSet
     *
     * @param c collection of squares to  initiate set with
     */
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
     * @throws IndexOutOfBoundsException if the index is out of range
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
     * @throws NullPointerException if the specified element is null
     * @throws InvalidSquareException if the square is invalid
     */
    public boolean add(Square s) {
        if (s == null) {
            throw new NullPointerException();
        }

        if (contains((Object) s)) {
            return false;
        }

        int file = s.getFile();
        int rank = s.getRank();

        if (!(file >= (int) 'a' && file <= (int) 'h'
              && rank > (int) '0' && rank < (int) '9')) {
            throw new InvalidSquareException("" + file + rank);
        }

        elements = Arrays.copyOf(elements, size() + 1);

        set(++lastIndex, s);

        return true;
    }

    /**
     * Adds all the elements in the specified collection to this set if
     * they are not already present.
     *
     * @param c collection containing elements to be added to this set
     * @return true if this set changed as a result of the call
     * @throws InvalidSquareException if the collection contains an invalid
     *         square
     */
    public boolean addAll(Collection<? extends Square> c) {
        if (containsAll(c)) {
            return false;
        }

        for (Square s : c) {
            int file = s.getFile();
            int rank = s.getRank();

            if (!(file >= (int) 'a' && file <= (int) 'h'
                  && rank > (int) '0' && rank < (int) '9')) {
                throw new InvalidSquareException("" + file + rank);
            }
        }

        for (Square s : c) {
            add(s);
        }

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
        if (o == this) {
            return true;
        }

        if (!(o instanceof Set)) {
            return false;
        }

        Collection c = (Collection) o;

        if (c.size() != size()) {
            return false;
        }

        return containsAll(c);
    }

    /**
     * Returns the hash code value for this set.
     *
     * @return the hash code for this set
     */
    public int hashCode() {
        int hash = 0;

        for (Object s : elements) {
            hash += ((Square) s).hashCode();
        }

        return hash;
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
     * Returns an array containing all of the elements in this set;
     * the runtime type of the returned array is that of the specified
     * array. If the set fits in the specified array, it is returned
     * therein. Otherwise, a new array is allocated with the runtime
     * type of the specified array and the size of this set. Null is
     * added to the end as filler.
     *
     * @param <T> the runtime type of the array containing the collection
     * @param t the array into which the elements of this set are to be
     *        stored, if it is big enough; otherwise, a new array of
     *        the same runtime type is allocated for this purpose
     * @return an array containing all the elements in this set
     * @throws NullPointerException if the specified array is null
     * @throws ArrayStoreException if the runtime type of the specifed
     *         array is not a supertype of the runtime of every element
     *         in this set
     */
    public <T> T[] toArray(T[] t) {
        if (t == null) {
            throw new NullPointerException();
        }

        if (t instanceof Object[]) {
            if (t.length > lastIndex) {
                for (int i = 0; i <= lastIndex; i++) {
                    t[i] = (T) get(i);
                }

                for (int i = lastIndex + 1; i < t.length; i++) {
                    t[i] = null;
                }

                return t;
            } else {
                Square[] squares = new Square[lastIndex + 1];

                for (int i = 0; i <= lastIndex; i++) {
                    squares[i] = get(i);
                }

                return (T[]) squares;
            }
        } else {
            throw new ArrayStoreException();
        }
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

    /**
     * Removes from this set all of its elements that are contained in
     * the specified collection.
     *
     * @param c collection containing elements to be removed from this set
     * @return true if this set changed as a result of the call
     * @throw UnsupportedOperationException if the removeAll operation is
     *        not supported by this set
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Remove all the elements in the set.
     */
    public void clear() {
        elements = new Object[0];
        lastIndex = -1;
    }

    /**
     * Retains only the elements in this set that are contained
     * in the specified collection.
     *
     * @param c collection containing elements to be retained in this set
     * @return true if this set changed as a result of the call
     * @throws UnsupportedOperationException if the retainAll operation
     *         is not supported by this set
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
