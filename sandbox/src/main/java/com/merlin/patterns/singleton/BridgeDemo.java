package com.merlin.patterns.singleton;

public class BridgeDemo {
    public static void main(String[] args) {
        ScanService scanService = new ScanServiceImpl(new ScanBuyServcie() {
            @Override
            public void buy() {
                //
            }
        }, new ScanLoginService() {
            @Override
            public void login() {
                //
            }
        });
        scanService.scanBuy();
    }

    static class ScanServiceImpl implements ScanService {
        // NOTE：注入
        private ScanBuyServcie scanBuyServcie;
        private ScanLoginService scanLoginService;

        ScanServiceImpl(ScanBuyServcie scanBuyServcie, ScanLoginService scanLoginService) {
            this.scanBuyServcie = scanBuyServcie;
            this.scanLoginService = scanLoginService;
        }

        @Override
        public void scanBuy() {
            scanBuyServcie.buy();
        }

        @Override
        public void scanLogin() {
            scanLoginService.login();
        }
    }

    interface ScanService {
        void scanBuy();
        void scanLogin();
    }

    interface  ScanBuyServcie {
        void buy();
    }

    interface ScanLoginService {
        void login();
    }
}
