package com.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transaction.pojo.Users;
import com.transaction.mapper.UsersMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Luo Shikun
 * @since 2023-04-19
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    //新添加
    public Users selectUserByName(String user_name);

    public Users selectUserById(String user_id);

    public void updateUserPassword(String user_id, String new_password);

    public void updateUserPhone(String user_id, String new_phone);

    public void updateUserAddress(String user_id, String new_address);
}