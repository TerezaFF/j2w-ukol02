package cz.czechitas.java2webapps.ukol2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class QuotesContoroller {
    private final List <String> quotes;
    private final Random random;

    // Loads quote and picture
    public QuotesContoroller() throws IOException {
        quotes =readAllLines("citaty.txt");
        random = new Random();
    }


    @GetMapping("/")
    public ModelAndView quote() {
        ModelAndView result =  new ModelAndView("quoteAndPicture");

        // Selects a random quote from a file according to its size
        result.addObject("quote", quotes.get(random.nextInt(quotes.size())));

        // Selects a random image from the folder /images using a random number generator with limit value corresponding to the number of images.
        int randomPic = random.nextInt(5);
        result.addObject("picture", String.format("background-image: url('/images/%d.jpg')", randomPic));
        return result;

    }

    private static List<String> readAllLines(String resource)throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

}
