package app.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Fresher on 27/07/2017.
 */
public class AppContext {
    private ApplicationContext applicationContext;
    private AppContext(){
        applicationContext= new ClassPathXmlApplicationContext("beans.xml");
    }

    private static AppContext appContext= new AppContext();

    public static AppContext getInstance(){
        return appContext;
    }


    public <T> T getBean(String id, Class<T> c){
        return applicationContext.getBean(id, c);
    }
}

