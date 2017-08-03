package oauth2.oltu.custom.common.context;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author yuezh2   2017年8月2日 下午6:06:54
 *
 */
public abstract class BeanProvider {

    private static ApplicationContext applicationContext;

    private BeanProvider() {
    }

    public static void initialize(ApplicationContext applicationContext) {
        BeanProvider.applicationContext = applicationContext;
    }

    /**
     * Get Bean by clazz.
     *
     * @param clazz Class
     * @param <T>   class type
     * @return Bean instance
     */
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (applicationContext == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanId);
    }

}