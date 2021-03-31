package ru.gnkoshelev.kontur.intern.resourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterRequestJsonTest {

    private JacksonTester<ConverterRequest> json;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void testSerializeConverterRequest() throws Exception {
        ConverterRequest request = new ConverterRequest("мм * с", "м * ч");
        JsonContent<ConverterRequest> result = this.json.write(request);

        assertThat(result).hasJsonPathStringValue("$.from");
        assertThat(result).hasJsonPathStringValue("$.to");
        assertThat(result).extractingJsonPathStringValue("$.from").isEqualTo("мм * с");
        assertThat(result).extractingJsonPathStringValue("$.to").isEqualTo("м * ч");
    }
}
