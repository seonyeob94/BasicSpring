package kr.or.ddit.service;

import kr.or.ddit.entity.Article;

import java.util.ArrayList;
import java.util.List;

public interface ArticleService {
    //글 목록
    public ArrayList<Article> findAll();

    //글 상세
    public Article findById(Long id);

    //글 저장
    public Article save(Article articleEntity);

    //글 삭제
    public Article delete(Article articleEntity);

    //과일 목록

    public List<String> getStringList();
    //과일 목록

    public ArrayList<String> getStringArrayList();
}
