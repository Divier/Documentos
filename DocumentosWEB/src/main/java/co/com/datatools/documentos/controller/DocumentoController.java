package co.com.datatools.documentos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/documento/*")
public class DocumentoController {

    @RequestMapping("/helloWorld")
    public @ResponseBody
    String byPath() {
        return "Mapped by path!";
    }
}