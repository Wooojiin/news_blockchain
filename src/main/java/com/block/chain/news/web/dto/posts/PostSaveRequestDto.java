package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String words;
    private int kinds;

    @Builder
    public PostSaveRequestDto(String title,String content, String author, String words, int kinds){
        this.title = title;
        this.content = content;
        this.author = author;
        this.words = words;
        this.kinds = kinds;
    }
//    public String stringify(String [][] words){
//        StringBuilder sb = new StringBuilder();
//        for (String [] idx : words){
//            StringBuilder sb2 = new StringBuilder();
//            sb2.append(idx[0]);
//            sb2.append("|");
//            sb2.append(idx[1]);
//            sb2.append(",");
//            sb.append(sb2.toString());
//        }
//        return sb.toString();
//    }
//    public void Extracting(String content,Long postId){
//        List<Topic> words = new LinkedList<>();
        //words.add(new Topic("abc",0.23));
        //여기에 대윤씨가 만든 코드(서버)로 content보내서 함축단어 리스트 받고 받은 아이들 For문 돌려서 서버에서 받은 데이터와 id 사용해서 Topic 저장
    //} // 일단 String 한줄로 Join해서 저장하기
    // List<String> 받아서 이걸 StringBuilder로 String을 ㅗ변환해서 반환하는 함수


    public Post toEntity(){
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .topics(this.words)
                .kinds(this.kinds)
                .state("SAVE")
                .build();
    }
}
