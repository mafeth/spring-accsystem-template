package net.cuodex.limeboard;

import lombok.Getter;
import net.cuodex.limeboard.utils.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.logging.Logger;

@SpringBootApplication
public class LimeboardBackendApplication {


    public static Logger LOGGER = Logger.getLogger(LimeboardBackendApplication.class.getName());

    @Autowired
    @Getter
    private Environment env;


    public static void main(String[] args) {
        SpringApplication.run(LimeboardBackendApplication.class, args);
    }


    @PostConstruct
    private void postConstruct() throws IOException {
        Variables.API_NAME = env.getProperty("net.cuodex.passx.api.name");
        Variables.API_AUTHOR = env.getProperty("net.cuodex.passx.api.author");
        Variables.API_CONTEXT_PATH = env.getProperty("server.servlet.context-path");
        Variables.API_VERSION = env.getProperty("net.cuodex.passx.api.version");
        Variables.API_HOST = env.getProperty("net.cuodex.passx.api.host");

    }

}
