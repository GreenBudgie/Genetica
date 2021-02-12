package com.greenbudgie.genetica.engineering;

/**
 * Represents a result of an injection or extraction a substance
 */
public enum IEResult {

    /**
     * Injector breaks when making an I/E, similar to {@link #FAIL}
     */
    ITEM_BREAK,
    /**
     * Injector does nothing when making an I/E
     */
    FAIL,
    /**
     * An I/E successfully performs
     */
    SUCCESS

}
