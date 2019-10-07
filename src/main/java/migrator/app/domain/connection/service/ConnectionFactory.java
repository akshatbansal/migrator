package migrator.app.domain.connection.service;

import migrator.app.domain.connection.model.Connection;

public class ConnectionFactory {
    public Connection create(String name) {
        return new Connection(
            name,
            "user",
            "",
            "localhost",
            "3306",
            "mysql"
        );
    }
}