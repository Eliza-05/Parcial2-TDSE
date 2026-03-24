package co.edu.eci;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class MathController {

    @GetMapping("/fibwin")
    public MathResponse fibwin(@RequestParam("value") int value) {
        if (value < 2) {
            return new MathResponse("Fibonacci con ventana K=3", value, "Error: value debe ser >= 2");
        }

        List<Integer> serie = new ArrayList<>();
        serie.add(0);
        serie.add(1);

        for (int i = 2; i <= value; i++) {
            int next = serie.get(i - 1) + serie.get(i - 2);
            serie.add(next);
        }

        List<Integer> ventana = new ArrayList<>();
        for (int i = 0; i <= serie.size() - 3; i++) {
            int suma = serie.get(i) + serie.get(i + 1) + serie.get(i + 2);
            ventana.add(suma);
        }

        StringBuilder serieTxt = new StringBuilder();
        for (int i = 0; i < serie.size(); i++) {
            if (i > 0) {
                serieTxt.append(", ");
            }
            serieTxt.append(serie.get(i));
        }

        StringBuilder ventanaTxt = new StringBuilder();
        for (int i = 0; i < ventana.size(); i++) {
            if (i > 0) {
                ventanaTxt.append(", ");
            }
            ventanaTxt.append(ventana.get(i));
        }

        String output = "serie: " + serieTxt + " | ventana: " + ventanaTxt;
        return new MathResponse("Fibonacci con ventana K=3", value, output);
    }
}