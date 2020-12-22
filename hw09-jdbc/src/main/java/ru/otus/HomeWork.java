package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.service.DbServiceImpl;
import ru.otus.demo.DataSourceDemo;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.model.Account;
import ru.otus.jdbc.model.Client;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.util.Optional;


public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DataSourceDemo();
        flywayMigrations(dataSource);
        try(var sessionManager = new SessionManagerJdbc(dataSource)) {

// Работа с пользователем
            DbExecutorImpl<Client> dbExecutor = new DbExecutorImpl<>();
            JdbcMapper jdbcMapper = new JdbcMapperImpl(sessionManager, dbExecutor);

// Код дальше должен остаться, т.е. clientDao должен использоваться
//Используется JdbcMapper
            var dbService = new DbServiceImpl(jdbcMapper);
            var client = new Client();
            client.id = 5;
            client.name = "Client";
            client.age = 55;
            var id = dbService.saveObject(client);
            Optional<Object> clientOptional = dbService.getObject(id, Client.class);

            clientOptional.ifPresentOrElse(
                    clt -> logger.info("created client: {}", ((Client)clt).toString()),
                    () -> logger.info("client was not created")
            );
// Работа со счетом
            var account = new Account();
            account.no = "#5";
            account.type = "test";
            account.rest = 0.01;

            var no = dbService.saveObject(account);
            Optional<Object> accountOptional = dbService.getObject(no, Account.class);

            accountOptional.ifPresentOrElse(
                    act -> logger.info("created account: {}", ((Account)act).toString()),
                    () -> logger.info("account was not created")
            );


        }
    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }
}
