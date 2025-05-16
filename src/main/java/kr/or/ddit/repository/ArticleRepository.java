package kr.or.ddit.repository;

import kr.or.ddit.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/*
Article : 관리 대상 엔티티의 클래스 타입. 여기서는 Article
Long : 관리 대상 엔티티의 대푯값 타입. Article.java 파일에 가 보면 id를 대푯값으로 지정했음.
    id의 타입은 Long이므로 Long을 입력함
 */

public interface ArticleRepository extends CrudRepository<Article, Long> {
    //CrudRepository의 메서드를
    // 오버라이딩(상위 클래스가 가지고 있는 메서드를 하위 클래스가
    //  재정의해서 사용하는 것을 말함)해보자

    @Override
    ArrayList<Article> findAll();   //Iterable -> ArrayList 수정
}
