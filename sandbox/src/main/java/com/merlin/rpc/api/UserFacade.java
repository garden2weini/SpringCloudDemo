package com.merlin.rpc.api;

/**
 * dubbo api接口
 *
 */
public interface UserFacade {

    /**
     * 返回用户名接口
     * @param
     * @return
     */
    public String getUserName(Long id);

}
