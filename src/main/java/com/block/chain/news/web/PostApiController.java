package com.block.chain.news.web;

import com.block.chain.news.service.NaverAPIService;
import com.block.chain.news.service.PostService;
import com.block.chain.news.service.RestTemplateService;
import com.block.chain.news.web.dto.follow.FollowingPostResponseDto;
import com.block.chain.news.web.dto.posts.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    private final RestTemplateService restTemplateService;
    private final NaverAPIService naverAPIService;

    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<PostListResponseDto>> getList(){
        log.info("getList");
        return new ResponseEntity<List<PostListResponseDto>>(postService.findAllDesc(),HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/user/{userId}")
    public ResponseEntity<List<PostListResponseDto>>findAllByUser(@PathVariable Long userId){
        return new ResponseEntity<List<PostListResponseDto>>(postService.findByUserId(userId), HttpStatus.OK);
    }
    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long postId){
        log.info("findById : {}", postId);
        return new ResponseEntity<PostResponseDto>(postService.findById(postId), HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/kinds")
    public ResponseEntity<List<KindsResponseDto>> getKinds(){
        return new ResponseEntity<List<KindsResponseDto>>(postService.findAllByKinds(), HttpStatus.OK);
    }

    @PostMapping("/api/v1/posts")
    public ResponseEntity<Long> save(@RequestBody PostSaveRequestDto requestDto) throws Exception{
        String word = Jsoup.parse(requestDto.getContent()).text();
        log.info(word);
        String results = "";
        try{
            results = restTemplateService.getMorpheme(word);
        }catch(Exception e){
            return new ResponseEntity<Long>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<Long>(postService.save(requestDto, results),HttpStatus.OK);
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
    public ResponseEntity<List<SubjectListResponseDto>> getSubject(){

        return new ResponseEntity<List<SubjectListResponseDto>>(postService.getSubject(), HttpStatus.OK);
    }
    @GetMapping("/api/v1/posts/following/{email}")
    public ResponseEntity<List<FollowingPostResponseDto>> getFollowers(@PathVariable String email){
        return new ResponseEntity<List<FollowingPostResponseDto>>(postService.getFollowers(email),HttpStatus.OK);
    }

    @PostMapping("/api/v1/posts/translation")
    public ResponseEntity<String> translation(@RequestBody String text){
        log.info("Translation .......");
        String inputLanguage = "ko";
        String outputLanguage = "en";
        return new ResponseEntity<String>(naverAPIService.translation(text, inputLanguage, outputLanguage), HttpStatus.OK);
    }
}
