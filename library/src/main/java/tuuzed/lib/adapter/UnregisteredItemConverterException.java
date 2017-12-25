package tuuzed.lib.adapter;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class UnregisteredItemConverterException extends RuntimeException {
    public UnregisteredItemConverterException(Class<?> clazz) {
        super(clazz.getName() + " unregistered !");
    }
}
