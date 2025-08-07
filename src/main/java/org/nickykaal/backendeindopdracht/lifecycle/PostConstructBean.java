package org.nickykaal.backendeindopdracht.lifecycle;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


@Component
public class PostConstructBean {

    private static final Logger LOG = Logger.getLogger(String.valueOf(PostConstructBean.class));


    @PostConstruct
    public void init() throws IOException {
        LOG.info("\n\u001B[37m##########################\n ###\t\u001B[35mINIT uploads/\u001B[37m\t###\n##########################");

        File source = new File("src/main/resources/uploads");
        File dest = new File("uploads");

        FileSystemUtils.deleteRecursively(dest);
        FileSystemUtils.copyRecursively(source,dest);
    }
}