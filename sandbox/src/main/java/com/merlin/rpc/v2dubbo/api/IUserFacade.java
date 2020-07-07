package com.merlin.rpc.v2dubbo.api;

/**
 * dubbo api接口
 *
 */
public interface IUserFacade {

    /**
     * 返回用户名接口
     * @param
     * @return
     */
    public String getUserName(Long id);

}
