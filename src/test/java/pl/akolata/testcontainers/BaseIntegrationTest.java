package pl.akolata.testcontainers;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(parallel = true)
public abstract class BaseIntegrationTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.0"))
            .withDatabaseName("testcontainers")
            .withDatabaseName("postgres")
            .withPassword("postgres");

}
