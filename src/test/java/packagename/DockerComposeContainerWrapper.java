package packagename;

import java.io.File;
import java.time.Duration;
import java.util.function.Predicate;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * A simple wrapper for Testcontainers {@link DockerComposeContainer} class. Makes it easier to use the
 * Testcontainers API by using service constants.
 */
public class DockerComposeContainerWrapper
{

    private static final Duration TIMEOUT = Duration.ofMinutes(5);

    /**
     * Machtes all [200,300) and [400,500) status codes which indicate that the undertow server is started.
     */
    private static final Predicate<Integer> statusCodeMatcher = code -> (code >= 200 && code < 300)
            || (code >= 400 && code < 500);

    private DockerComposeContainer containers;

    public DockerComposeContainerWrapper(String dockerComposeFilePath)
    {
        containers = new DockerComposeContainer(new File(dockerComposeFilePath)).withPull(false)
                .withLocalCompose(false);
    }

    /**
     * Waits for a service to be available by querying its http status code.
     *
     * @param services
     * @return
     */
    public DockerComposeContainerWrapper waitFor(Services... services)
    {
        for (Services service : services)
        {
            waitFor(service);
        }
        return this;
    }

    /**
     * Forces a pull on every start. Also updates :latest tags.
     *
     * @return
     */
    public DockerComposeContainerWrapper pullImages()
    {
        containers.withPull(true);
        return this;
    }

    /**
     * Toggles the option to use a locally installed docker-compose binary. Otherwise Testcontainers starts its own
     * container with a docker-compose binary.
     *
     * @return
     */
    public DockerComposeContainerWrapper useLocalDockerComposeBinary()
    {
        containers.withLocalCompose(true);
        return this;
    }

    public DockerComposeContainer start()
    {
        return containers;
    }

    private void waitFor(Services service)
    {
        containers.waitingFor(service.containerName,
                Wait.forHttp("").forPort(service.port).forStatusCodeMatching(statusCodeMatcher).withStartupTimeout(TIMEOUT));
    }
}

