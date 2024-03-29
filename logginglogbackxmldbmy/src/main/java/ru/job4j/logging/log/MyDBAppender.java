package ru.job4j.logging.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class MyDBAppender extends DBAppender {
    //=======================================================================================
// GET INSERT SQL
//=======================================================================================
    @Override
    protected String getInsertSQL() {
        return "INSERT INTO transaction (date, level, message) VALUES (?, ?, ?)";
    }
    //=======================================================================================
// SUB APPEND
//=======================================================================================
    @Override
    protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement preparedStatement)
            throws SQLException {
//GET LOG EVENT DATA
        String level = event.getLevel().toString();
        String message = event.getMessage();
//PREPARE STATEMENT
        preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setString (2, level );
        preparedStatement.setString (3, message);
//EXECUTE PREPARED STATEMENT
        preparedStatement.executeUpdate();
    }
}
