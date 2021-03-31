/*
 * ConverterRequest
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.resourse;

import java.util.Objects;

public class ConverterRequest {
    private String from;
    private String to;

    public ConverterRequest() {
    }

    public ConverterRequest(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConverterRequest converterRequest = (ConverterRequest) o;
        return Objects.equals(from, converterRequest.from) &&
                Objects.equals(to, converterRequest.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
