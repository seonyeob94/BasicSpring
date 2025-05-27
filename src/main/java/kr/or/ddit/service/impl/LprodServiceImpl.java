package kr.or.ddit.service.impl;

import kr.or.ddit.entity.Lprod;
import kr.or.ddit.repository.LprodRepository;
import kr.or.ddit.service.LprodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LprodServiceImpl implements LprodService {

    //Mapper 인터페이스
    //DI
    @Autowired
    private LprodRepository lprodRepository;

    @Override
    public ArrayList<Lprod> findAll() {
        return this.lprodRepository.findAll();
    }

    @Override
    public Lprod findById(Long lprodId) {
        return this.lprodRepository.findById(lprodId).orElse(null);
    }

    @Override
    public Lprod save(Lprod lprodEntity) {
        return this.lprodRepository.save(lprodEntity);
    }

    @Override
    public Lprod delete(Lprod lprodEntity) {
        if(lprodEntity != null){

            lprodRepository.delete(lprodEntity);
        }
        return lprodEntity;
    }


}
