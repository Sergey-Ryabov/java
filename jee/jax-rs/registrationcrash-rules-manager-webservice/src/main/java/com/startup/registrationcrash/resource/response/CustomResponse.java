package com.startup.registrationcrash.resource.response;

import java.util.List;

/**
 *
 * @author Сергей
 */
public class CustomResponse {

    private ResponseHeader header;
    private List rows;

    public CustomResponse() {
    }

    public CustomResponse(ResponseHeader header, List rows) {
        this.header = header;
        this.rows = rows;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

}
