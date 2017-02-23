package io.github.tuuzed.adapter;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */

class ItemComponentNotFoundException extends RuntimeException {

    ItemComponentNotFoundException(Class clazz) {
        super(String.format("Do you registered %s.class in the adapter ?", clazz.getSimpleName()));
    }

}
