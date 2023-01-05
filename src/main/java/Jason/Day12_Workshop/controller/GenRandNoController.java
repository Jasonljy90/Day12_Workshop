package Jason.Day12_Workshop.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import Jason.Day12_Workshop.exception.RandNoException;
import Jason.Day12_Workshop.models.Generate;

@Controller
//@RequestMapping(path="/")
public class GenRandNoController {
    
    /**
     * Redirect to the generate.html 
     * and show the input form
     */
    @GetMapping(path="/")
    public String showRandForm(Model model){
        // Instantiate the generate object 
        // bind the noOfRandNo to the text field
        Generate g = new Generate();
        // associate the bind var to the view/page
        model.addAttribute("generateObj", g);
        return "generate";
    }

    @PostMapping(path="/generate")
    public String postRandNum(@ModelAttribute Generate generate, Model model){
        this.randomizeNum(model, generate.getNumberVal());
        return "result";
    }

    private void randomizeNum(Model model, int noOfGenerateNo){
        int maxGenNo = 30;
        String[] imgNumbers = new String[maxGenNo+1];

        // Validate only accept gt 0 lte 30
        if(noOfGenerateNo < 0 || noOfGenerateNo > maxGenNo){
            throw new RandNoException();
        }

        // generate all the number images store it 
        // to the imgNumbers array 
        for(int i = 0 ; i <= maxGenNo; i++){
            imgNumbers[i] = "number" + i + ".jpg";
        }

        List<String> selectedImg = new ArrayList<String>();
        Random rnd = new Random();
        Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
        while(uniqueResult.size() < noOfGenerateNo){
            Integer resultOgfRand = rnd.nextInt(maxGenNo+1);
            uniqueResult.add(resultOgfRand);
        }

        Iterator<Integer> it = uniqueResult.iterator();
        Integer currElem = null;
        while(it.hasNext()){
            currElem = it.next();
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        model.addAttribute("numberRandomNum", noOfGenerateNo);
        model.addAttribute("randNumResult", selectedImg.toArray());
    }
}
