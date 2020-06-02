package com.block.chain.news.web;

import com.block.chain.news.service.PostService;
import com.block.chain.news.web.dto.posts.PostListResponseDto;
import com.block.chain.news.web.dto.posts.PostResponseDto;
import com.block.chain.news.web.dto.posts.PostSaveRequestDto;
import com.block.chain.news.web.dto.posts.SubjectListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;

    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<PostListResponseDto>> getList(){
        log.info("getList");
        return new ResponseEntity<List<PostListResponseDto>>(postService.findAllDesc(),HttpStatus.OK);
    }

//    @GetMapping("/api/v1/posts/{postId}")
//    public PostListResponseDto findById(@PathVariable Long postId){
//        log.info("findById : {}", postId);
//        return postService.findById(postId);
//    }

    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long postId){
        log.info("findById : {}", postId);
        return new ResponseEntity<PostResponseDto>(postService.findById(postId), HttpStatus.OK);
    }

    @PostMapping("/api/v1/posts")
    public ResponseEntity<Long> save(@RequestParam(value="title") String title,
                     @RequestParam(value="content") String content,
                     @RequestParam(value="author") String author,
                     @RequestParam(value="words") String words) throws Exception{
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .words(words)
                .build();
        return new ResponseEntity<Long>(postService.save(postSaveRequestDto),HttpStatus.OK);
    }

    @PutMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Long> deploy(@PathVariable Long postId,
                       @RequestParam(value="selected") String [] selected,
                                       @RequestParam(value="subjectId") Long subjectId) throws Exception{
        log.info("subjectId : {}", subjectId);
        return new ResponseEntity<Long>(postService.deploy(postId, selected, subjectId),HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Long> delete(@PathVariable Long postId){
        return new ResponseEntity<Long>(postService.delete(postId),HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/subject")
    public List<SubjectListResponseDto> getSubject(){

        return postService.getSubject();
    }

}
