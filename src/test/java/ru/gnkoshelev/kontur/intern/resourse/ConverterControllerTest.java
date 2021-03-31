package ru.gnkoshelev.kontur.intern.resourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.gnkoshelev.kontur.intern.excertions.ConvertException;
import ru.gnkoshelev.kontur.intern.excertions.ConverterRequestNotValidException;
import ru.gnkoshelev.kontur.intern.excertions.UnknownUnitException;
import ru.gnkoshelev.kontur.intern.service.ConverterService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConverterController.class)
public class ConverterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConverterService service;
    @MockBean
    private RequestValidator validator;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

    @Test
    public void post1() throws Exception {
        String from = "м / с";
        String to = "км / час";
        String response = "3.6";

        ConverterRequest request = new ConverterRequest(from, to);

        String requestJson=writer.writeValueAsString(request);

        given(service.convert(from, to)).willReturn(response);

        mvc.perform(
                MockMvcRequestBuilders.post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));
    }

    @Test
    public void post2() throws Exception {
        String from = "с";
        String to = "час";
        String response = "0.000277777777778";

        ConverterRequest request = new ConverterRequest(from, to);

        String requestJson=writer.writeValueAsString(request);

        given(service.convert(from, to)).willReturn(response);

        mvc.perform(
                MockMvcRequestBuilders.post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));
    }

    @Test
    public void postNotValidRequest() throws Exception {
        String from = null;
        String to = "час";
        String response = "";

        ConverterRequest request = new ConverterRequest(from, to);

        String requestJson=writer.writeValueAsString(request);

        doThrow(ConverterRequestNotValidException.class).when(validator).validate(request);

        mvc.perform(
                MockMvcRequestBuilders.post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo(response)));
    }

    @Test
    public void postConvertException() throws Exception {
        String from = "с";
        String to = "час";
        String response = "";

        ConverterRequest request = new ConverterRequest(from, to);

        String requestJson=writer.writeValueAsString(request);

        given(service.convert(from, to)).willThrow(ConvertException.class);

        mvc.perform(
                MockMvcRequestBuilders.post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo(response)));
    }

    @Test
    public void postUnknownUnitException() throws Exception {
        String from = "с";
        String to = "час";
        String response = "";

        ConverterRequest request = new ConverterRequest(from, to);

        String requestJson=writer.writeValueAsString(request);

        given(service.convert(from, to)).willThrow(UnknownUnitException.class);

        mvc.perform(
                MockMvcRequestBuilders.post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo(response)));
    }
}
