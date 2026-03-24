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
        List<Integer> fibSequence = new ArrayList<>();

        if (value > 0) {
            fibSequence.add(0);
        }
        if (value > 1) {
            fibSequence.add(1);
        }

        for (int i = 2; i < value; i++) {
            int next = fibSequence.get(i - 1) + fibSequence.get(i - 2);
            fibSequence.add(next);
        }

        return new MathResponse("fibwin", value, fibSequence.toString());
    }
}