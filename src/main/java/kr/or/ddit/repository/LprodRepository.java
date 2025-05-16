package kr.or.ddit.repository;

import kr.or.ddit.entity.Lprod;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface LprodRepository extends CrudRepository<Lprod, Long> {
    //CrudRepository의 메서드를
    // 오버라이딩(상위 클래스가 가지고 있는 메서드를 하위 클래스가
    //  재정의해서 사용하는 것을 말함)해보자

    @Override
    ArrayList<Lprod> findAll();   //Iterable -> ArrayList 수정
}
