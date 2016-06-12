package com.kildeen.bm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Utility methods for working with Collections
 * 
 * @author Kalle
 *
 */
public final class Col {

	private Col() {
	};

	/**
	 * Unlike Optional and MoreObjects#firstNonNull this method has zero
	 * overhead if the collection is not null
	 */
	public static <E> Collection<E> computeIfEmpty(Collection<E> collection, Supplier<Collection<E>> ifNullSupplier) {
		return collection == null ? ifNullSupplier.get() : collection;
	}

	/**
	 * A shortcut to {@link #computeIfEmpty(Collection, Supplier)} that defaults
	 * the Supplier to {@link Collections#emptyList()}
	 */
	public static <E> Collection<E> emptyIfNull(List<E> existing) {
		return computeIfEmpty(existing, Collections::emptyList);
	}

	/**
	 * Adapted from guava, convenient way to create small lists. Guava has some
	 * size checks that are left out intentionally thus a element size
	 * surpassing Integer.max gives undefined behavior
	 * 
	 */
	@SafeVarargs
	public static <E> ArrayList<E> newArrayList(E... elements) {
		ArrayList<E> list = new ArrayList<E>(elements.length);
		Collections.addAll(list, elements);
		return list;
	}

	public static <E> List<E> filtered(List<E> existing, Predicate<E> predicate) {
		return existing.stream().filter(e -> predicate.test(e)).collect(Collectors.toList());
	}
	

}
