package org.nooon.kutya.servlet;

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import org.nooon.kutya.ui.MyVaadinUIProvider;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/VAADIN/*", "/service/*"},
        initParams = {
                @WebInitParam(name = "widgetset", value = "org.nooon.AppWidgetSet")
        })
public class ApplicationServlet extends VaadinServlet implements SessionInitListener {

    @Inject
    private MyVaadinUIProvider uiProvider;

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
    }


    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        event.getSession().addUIProvider(uiProvider);
    }
}
