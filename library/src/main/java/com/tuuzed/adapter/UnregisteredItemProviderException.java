package com.tuuzed.adapter;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class UnregisteredItemProviderException extends RuntimeException {
    public UnregisteredItemProviderException(Class<?> clazz) {
        super(clazz.getName() + " unregistered !");
    }
}
