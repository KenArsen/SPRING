package com.exam.mydictionary.Services;

import com.exam.mydictionary.Models.Dict;
import com.exam.mydictionary.Repositories.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DictService {

    private DictRepository dictRepository;
    @Autowired
    public DictService(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    // All dictionary
    public Page<Dict> listAll(int pageNumber, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

        if(keyword != null){
            return dictRepository.findAll(keyword, pageable);
        }
        return dictRepository.findAll(pageable);
    }

    // add
    public void add(String english, String russian) {
        Dict dict = new Dict(english, russian);
        dictRepository.save(dict);
    }

    // edit
    public String edit(Long id, Model model) {
        // .existsById(id) бул функция эгерде dbте ушул индекс бар болсо true
        if (!dictRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Dict> dict = dictRepository.findById(id);
        ArrayList<Dict> res = new ArrayList<>();
        dict.ifPresent(res::add);
        // blog-details шаблонуна "dict":res беребиз
        model.addAttribute("dict", res);
        return "dict-edit";
    }

    // Post edit
    public String postEdit(Long id, String english, String russian) {
        Dict dict = dictRepository.findById(id).orElseThrow();
        dict.setEnglish(english);
        dict.setRussian(russian);
        dictRepository.save(dict);
        return "redirect:/";
    }

    // Удалить данные
    public String delete(Long id) {
        Dict dict = dictRepository.findById(id).orElseThrow();
        dictRepository.delete(dict);
        return "redirect:/";
    }

}
