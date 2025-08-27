package co.credit.app.r2dbc.config;

//@ConfigurationProperties(prefix = "adapters.r2dbc")
public record MysqlConnectionProperties (
    String host,
    Integer port,
    String database,
    String username,
    String password,
    // Opcional: zona horaria, ssl, etc.
    String timezone
) {}
