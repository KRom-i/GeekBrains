package Logger;

import Format.CharFormat;
import javafx.application.Application;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class LOG {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void info(String strInfoLogger){
        LOGGER.info(strInfoLogger);
    }

    public static void error(String strInfoLogger, Exception e){
        LOGGER.error(strInfoLogger);
        LOGGER.error(e);
    }

}
