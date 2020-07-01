package com.merlin.patterns.chains.demo2;

//抽象处理者角色
public abstract class RoleHandler {

    /**
     * 持有后继的责任对象
     */
    protected RoleHandler successor;

    /**
     * 取值方法
     */
    public RoleHandler getSuccessor() {
        return successor;
    }

    /**
     * 赋值方法，设置后继的责任对象
     */
    public void setSuccessor(RoleHandler successor) {
        this.successor = successor;
    }

    /**
     * 处理聚餐费用的申请
     *
     * @param user 申请人
     * @param fee  申请的钱数
     * @return 成功或失败的具体通知
     */
    public abstract String handleFeeRequest(String user, double fee);


}
