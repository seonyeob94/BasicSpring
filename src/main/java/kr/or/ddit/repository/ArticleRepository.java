package kr.or.ddit.repository;

import kr.or.ddit.entity.Article;
import org.springframework.data.repository.CrudRepository;

/*
Article : 관리 대상 엔티티의 클래스 타입. 여기서는 Article
Long : 관리 대상 엔티티의 대푯값 타입. Article.java 파일에 가 보면 id를 대푯값으로 지정했음.
    id의 타입은 Long이므로 Long을 입력함
 */

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
