package kr.or.ddit.service.impl;

import kr.or.ddit.entity.Article;
import kr.or.ddit.repository.ArticleRepository;
import kr.or.ddit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;


    //부모 메서드 재정의
    @Override
    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }
}
