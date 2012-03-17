package org.vaadin.teemu.jsoncontainer.demo;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.vaadin.terminal.gwt.server.ApplicationServlet;

@SuppressWarnings("serial")
public class DemoApplicationServlet extends ApplicationServlet {

    @Override
    protected void writeAjaxPageHtmlHeader(BufferedWriter page, String title,
            String themeUri, HttpServletRequest request) throws IOException {
        super.writeAjaxPageHtmlHeader(page, title, themeUri, request);
        page.write("<meta name=\"viewport\" content=\"width=1100\"/>\n");
    }

}
