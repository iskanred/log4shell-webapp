package iu.iskanred.log4shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@SuppressWarnings("unused")
public class Log4ShellController {

    private static final Logger logger = LogManager.getLogger("Log4shellApplication");

    @GetMapping("/")
    @ResponseBody
    public String home(
            @RequestHeader(value = "User-Agent")
            String userAgent
    ) {
        // Try to put here ${env:JAVA_HOME} or ${jndi:ldap:127.0.0.1/entry}
        logger.info("A new GET request to '/' from User-agent: \"{}\"", userAgent);
        return "<html>" +
                    "<body>" +
                        "<h1>Summator</h1>" +
                        "<p>Here you can download our famous program for summing numbers</p>" +
                        "<a href=\"/download\">Download the latest version for Linux x64</a>" +
                    "</body>" +
                "</html>";
    }

    @GetMapping(
            path = "/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public Resource download(
            @RequestHeader(value = "User-Agent")
            String userAgent
    ) throws IOException {
        logger.info("A new GET request to '/download' from User-agent: \"{}\"", userAgent);
        Path path = Paths.get("summator");
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
