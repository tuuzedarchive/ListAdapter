package io.github.tuuzed.adapter;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class NotFindItemProvider extends RuntimeException {
    public NotFindItemProvider() {
        super("IItemView object not found, please register IItemView object!");
    }
}
