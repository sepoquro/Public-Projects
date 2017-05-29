/** Defines a basic pair object that is used to store two values */
package com.mustard.util;

import java.io.Serializable;

public class Pair <K, V> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected K key;
	protected V value;
	
	/** Constructs a basic pair storing two elements.
	 * 		@param k - key
	 * 		@param v - value */
	public Pair (K k, V v) {
		key = k;
		value = v;
	}
	
	/* GETTERS */
	
	/** Returns the key stored in this pair */
	public K getKey() { return key; }
	
	/** Returns the value stored in this pair */
	public V getValue () { return value; }
	
	/* SETTERS */
	
	/** Sets the pair's key to the @param k
	 * 		@param k - new key value */
	public void setKey (K k) { key = k; }
	
	/** Sets the pair's value to the @param v
	 * 		@param v - new value */
	public void setValue (V v) { value = v; }
}
