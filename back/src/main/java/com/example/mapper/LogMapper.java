package com.example.mapper;

import com.example.pojo.Log;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogMapper {
    @Insert("INSERT INTO sys_log(user_id, username, action, timestamp, ip, method) " +
            "VALUES(#{userId}, #{username}, #{action}, #{timestamp}, #{ip}, #{method})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Log log);

    @Select("<script>" +
            "SELECT * FROM sys_log " +
            "<where>" +
            "   <if test='username != null and username != \"\"'> AND username LIKE CONCAT('%', #{username}, '%') </if>" +
            "</where>" +
            "ORDER BY timestamp DESC " +
            "LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<Log> selectPage(@Param("username") String username,
                         @Param("offset") int offset,
                         @Param("pageSize") int pageSize);

    @Select("<script>" +
            "SELECT COUNT(*) FROM sys_log " +
            "<where>" +
            "   <if test='username != null and username != \"\"'> AND username LIKE CONCAT('%', #{username}, '%') </if>" +
            "</where>" +
            "</script>")
    long count(@Param("username") String username);
}
