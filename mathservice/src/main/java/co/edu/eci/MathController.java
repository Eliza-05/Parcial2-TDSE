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


    }
}