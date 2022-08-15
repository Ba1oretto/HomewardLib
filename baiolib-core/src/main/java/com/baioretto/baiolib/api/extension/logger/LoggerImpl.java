package com.baioretto.baiolib.api.extension.logger;

import java.util.logging.Logger;

/**
 * The {@link Logger} extensions class
 *
 * @author baioretto
 * @since 1.1.0
 */
public class LoggerImpl {
    public static void warn(Logger in, String msg) {
        in.warning(msg);
    }
}
