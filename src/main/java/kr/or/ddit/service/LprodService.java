package kr.or.ddit.service;

import kr.or.ddit.entity.Article;
import kr.or.ddit.entity.Lprod;

import java.util.ArrayList;

public interface LprodService {

    // 상품 분류 목록
    public ArrayList<Lprod> findAll();

    public Lprod findById(Long lprodId);

    public Lprod save(Lprod lprodEntity);

    public Lprod delete(Lprod lprodEntity);
}
