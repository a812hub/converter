/*
 * RequestValidator
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.resourse;

import org.springframework.stereotype.Component;
import ru.gnkoshelev.kontur.intern.excertions.ConverterRequestNotValidException;

@Component
public class RequestValidator {
    public void validate(ConverterRequest request) throws ConverterRequestNotValidException {
        if (request.getFrom() == null && request.getTo() == null) {
            throw new ConverterRequestNotValidException("The request body must contain fields 'from' and 'to'");
        }
        if (request.getFrom() == null) {
            throw new ConverterRequestNotValidException("The request body must contain the field 'from'");
        }
        if (request.getTo() == null) {
            throw new ConverterRequestNotValidException("The request body must contain the field 'to'");
        }
    }
}
