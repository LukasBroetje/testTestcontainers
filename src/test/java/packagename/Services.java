package packagename;

public enum Services
{
    SPRINGIO("springio", 8080),
    REDIS("redis", 6379);

    public final String containerName;
    public final int port;

    Services(String containerName, int port)
    {
        this.containerName = containerName;
        this.port = port;
    }
}
