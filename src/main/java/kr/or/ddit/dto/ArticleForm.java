package kr.or.ddit.dto;

//자바빈 클래스 : 자바빈 규약을 지키는 클래스
//              1) 프로퍼티 2) 기본생성자 3) getter/setter메소드
public class ArticleForm {
    // 제목을 받을 필드
    private String title;
    // 내용을 받을 필드
    private String content;
    // 기본 생성자
    public ArticleForm() {}
    // getter/setter메소드
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
