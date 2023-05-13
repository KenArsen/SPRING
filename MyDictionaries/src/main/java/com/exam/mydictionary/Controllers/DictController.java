package com.exam.mydictionary.Controllers;

import com.exam.mydictionary.Models.Dict;
import com.exam.mydictionary.Services.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DictController {
    private final DictService dictService;

    @GetMapping("/")
    public String home(Model model){
        String keyword = "";
        return listByPage(model, 1, "english", "asc", keyword);
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model,
                             @PathVariable("pageNumber")int currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword
                             ){
        Page<Dict> page = dictService.listAll(currentPage, sortField, sortDir, keyword);
        // getTotalElements бул базадагы бардык элементердин санын чыгарат
        Long totalItems = page.getTotalElements();
        // getTotalPages бул канча бет болорун чыгарып берет
        int totalPages = page.getTotalPages();
        List<Dict> dicts = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("dictionaries", dicts);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);
        return "home";
    }

    @GetMapping("/add")
    public String add(){
        return "dict-add";
    }

    @PostMapping("/add")
    public String addDict(@RequestParam String english, @RequestParam String russian){
        if(english == "" || russian == ""){
            return "redirect:/add";
        }
        dictService.add(english, russian);
        return "redirect:/add";
    }

    @GetMapping("/{id}/edit")
    // @PathVariable(value = "id") шаблондон келген id ни Long id ге ыйгарып, анан ошо мене иштейбиз
    public String editDict(@PathVariable(value = "id") long id, Model model) {
        return dictService.edit(id, model);
    }

    @PostMapping("/{id}/edit")
    public String editDict(
            @PathVariable(value = "id") long id,
            @RequestParam String english,
            @RequestParam String russian,
            Model model) {
        return dictService.postEdit(id, english, russian);
    }

    @RequestMapping("/{id}/delete")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        return dictService.delete(id);
    }
}
