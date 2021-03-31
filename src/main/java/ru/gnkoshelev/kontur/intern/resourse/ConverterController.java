/*
 * ConverterController
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.resourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gnkoshelev.kontur.intern.excertions.UnknownUnitException;
import ru.gnkoshelev.kontur.intern.excertions.ConvertException;
import ru.gnkoshelev.kontur.intern.excertions.ConverterRequestNotValidException;
import ru.gnkoshelev.kontur.intern.service.ConverterService;

@RestController
@RequestMapping("/convert")
public class ConverterController {

    private final ConverterService converterService;
    private final RequestValidator requestValidator;

    @Autowired
    public ConverterController(ConverterService converterService, RequestValidator requestValidator) {
        this.converterService = converterService;
        this.requestValidator = requestValidator;
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ConverterRequest request) throws UnknownUnitException, ConvertException, ConverterRequestNotValidException {
        requestValidator.validate(request);
        String response = converterService.convert(request.getFrom(), request.getTo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
