package com.example.demo.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * @author Djh
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> parameterNames = super.getParameterNames();
        ArrayList<String> list = Collections.list(parameterNames);
        list.add("id");
        return Collections.enumeration(list);
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        if (parameter == null || "id".equals(name)) {
            parameter = "1234";
        }

        return parameter;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null || "id".equals(name)) {
            parameterValues = new String[] {"1234"};
        }

        return parameterValues;
    }
}
