package com.crud.kodilla.library.service;

import com.crud.kodilla.library.domain.Title;
import com.crud.kodilla.library.repository.BookRepository;
import com.crud.kodilla.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class TitleService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TitleRepository titleRepository;

    public Optional<Title> findTitle(Long titleId){
        return titleRepository.findById(titleId);
    }

    public Title saveTitle(Title title) {
        return titleRepository.save(title);
    }

    public void deleteTitleById(Long titleId){
        titleRepository.deleteById(titleId);
    }

    public List<Title> getAllTitles(){
        return titleRepository.findAll();
    }

    public long getAvailableCount(Long titleId) {
        return bookRepository.getAvailableTitles(titleId);
    }
}