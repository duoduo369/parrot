package com.reworkplan.mappers;

import com.reworkplan.models.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectByUsername(@Param("username") String username);
}
