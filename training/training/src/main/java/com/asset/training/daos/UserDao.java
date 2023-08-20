package com.asset.training.daos;

import com.asset.training.constants.ErrorCodes;
import com.asset.training.exceptions.TrainingException;
import com.asset.training.models.UserModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserModel> getAll(){
        String query = "select * from ADM_USERS";
        try {
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(UserModel.class));
        }catch (Exception ex){
            ex.printStackTrace();
            throw new TrainingException(ErrorCodes.ERROR.DATABASE_ERROR, 1);
        }
    }

    public UserModel getByUsername(String username){
        String query = "select * from ADM_USERS where USERNAME = ?";
        try {
            return new UserModel(1, "Mayar", "ANUKE_THEBES", 1, 0, null, "mayar75");
//            return jdbcTemplate.queryForObject(query, UserModel.class, username);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new TrainingException(ErrorCodes.ERROR.DATABASE_ERROR, 1);
        }
    }
}
