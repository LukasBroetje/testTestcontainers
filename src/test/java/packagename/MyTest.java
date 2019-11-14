package packagename;

import static org.junit.Assert.assertTrue;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.DockerComposeContainer;

public class MyTest {

    private static final String DOCKER_COMPOSE_PATH = "src/test/resources/docker-compose.yml";

    @ClassRule
    public static DockerComposeContainer environment = new DockerComposeContainerWrapper(DOCKER_COMPOSE_PATH)
            .waitFor(Services.SPRINGIO, Services.REDIS)
            .start();


    @Test
    public void test() {
        assertTrue(true);
    }


}
