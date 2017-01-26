package co.com.datatools.documentos.controller;

import org.springframework.stereotype.Controller;

@Controller
public class PlantillaController {

    public String consultar() {
        return this.getClass().getName() + ".consultar()";
    }
}