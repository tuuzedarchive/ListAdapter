package io.github.tuuzed.adapter;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */

public class NotFindItemComponent extends RuntimeException {
    public NotFindItemComponent() {
        super("ItemComponent object not found, please register ItemComponent object!");
    }
}
