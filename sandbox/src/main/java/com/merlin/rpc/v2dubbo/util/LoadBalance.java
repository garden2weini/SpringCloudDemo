package com.merlin.rpc.v2dubbo.util;

import java.util.List;

public abstract class LoadBalance {

    public volatile static List<String> SERVICE_LIST;

    public abstract String chooseServiceHost();
}
