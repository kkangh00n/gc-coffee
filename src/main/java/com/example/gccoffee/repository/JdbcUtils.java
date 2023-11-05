package com.example.gccoffee.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JdbcUtils {

    public static LocalDateTime toLocalDateTime(Timestamp timeStamp){
        return timeStamp!=null?timeStamp.toLocalDateTime():null;
    }

}
