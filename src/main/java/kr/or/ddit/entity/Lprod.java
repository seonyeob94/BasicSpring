package kr.or.ddit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class Lprod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lprodId;

    @Column
    private String lprodGu;

    @Column
    private String lprodNa;



    //public Lprod() {}

    /*public Lprod(Long lprodId, String lprodGu, String lprodNa) {
        this.lprodId = lprodId;
        this.lprodGu = lprodGu;
        this.lprodNa = lprodNa;
    }*/
}
