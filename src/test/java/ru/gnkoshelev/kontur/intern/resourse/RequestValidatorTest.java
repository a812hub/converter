package ru.gnkoshelev.kontur.intern.resourse;

import org.junit.Test;
import ru.gnkoshelev.kontur.intern.excertions.ConverterRequestNotValidException;

public class RequestValidatorTest {

    @Test(expected = ConverterRequestNotValidException.class)
    public void validateNullFromAndToTest() throws ConverterRequestNotValidException {
        RequestValidator validator = new RequestValidator();
        ConverterRequest request = new ConverterRequest();
        validator.validate(request);
    }

    @Test(expected = ConverterRequestNotValidException.class)
    public void validateNullFromTest() throws ConverterRequestNotValidException {
        RequestValidator validator = new RequestValidator();
        ConverterRequest request = new ConverterRequest(null, "м");
        validator.validate(request);
    }

    @Test(expected = ConverterRequestNotValidException.class)
    public void validateNullToTest() throws ConverterRequestNotValidException {
        RequestValidator validator = new RequestValidator();
        ConverterRequest request = new ConverterRequest("км", null);
        validator.validate(request);
    }

    @Test
    public void validateTest() throws ConverterRequestNotValidException {
        RequestValidator validator = new RequestValidator();
        ConverterRequest request = new ConverterRequest("км", "м");
        validator.validate(request);
    }
}
