package com.tuuzed.adapter;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class NotFindItemProvider extends RuntimeException {
    public NotFindItemProvider() {
        super("ItemProvider object not found, please register ItemProvider object!");
    }
}
