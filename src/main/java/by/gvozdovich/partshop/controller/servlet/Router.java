package by.gvozdovich.partshop.controller.servlet;

public class Router {
    public enum RouterType {
        FORWARD,
        REDIRECT
    }

    private String page;
    private RouterType routerType = RouterType.FORWARD;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public RouterType getRouterType() {
        return routerType;
    }

    public void setRouterType(RouterType routerType) {
        if (routerType != null) {
            this.routerType = routerType;
        }
    }
}
