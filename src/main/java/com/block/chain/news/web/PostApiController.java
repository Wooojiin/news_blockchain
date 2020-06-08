package com.block.chain.news.web;

import com.block.chain.news.service.NaverAPIService;
import com.block.chain.news.service.PostService;
import com.block.chain.news.service.RestTemplateService;
import com.block.chain.news.web.dto.posts.*;
import com.block.chain.news.web.dto.subject.SubjectListResponseDto;
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

    @GetMapping("/api/v1/posts/EveryThing/{userEmail}")
    public ResponseEntity<List<PostFollowerCheckDto>> getList(@PathVariable String userEmail){
        log.info("getList");
        return new ResponseEntity<List<PostFollowerCheckDto>>(postService.findAllDesc(userEmail),HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/desc")
    public ResponseEntity<List<PostEveryResponseDto>> getDesc(){
        return new ResponseEntity<List<PostEveryResponseDto>>(postService.findRecent(), HttpStatus.OK);
    }
    @GetMapping("/api/v1/posts/find/{words}")
    public ResponseEntity<List<PostEveryResponseDto>> findByTitle(@PathVariable String words){
        return new ResponseEntity<List<PostEveryResponseDto>>(postService.findByTitle(words), HttpStatus.OK);
    }
    @PutMapping("/api/v1/posts/update/{postId}")
    public ResponseEntity<Long> postUpdate(@PathVariable Long postId,
                                           @RequestBody PostUpdateDto postUpdateDto){
        return new ResponseEntity<Long>(postService.updatePost(postId, postUpdateDto), HttpStatus.OK);
    }
    @GetMapping("/api/v1/posts/suggestion/{postId}")
    public SuggestionResponseDto getSuggestion(@PathVariable Long postId){
        return postService.getSuggestion(postId);
    }

    @GetMapping("/api/v1/posts/user/{userEmail}")
    public ResponseEntity<PostListResponseDto>findAllByUser(@PathVariable String userEmail){
        return new ResponseEntity<PostListResponseDto>(postService.findByUserEmail(userEmail), HttpStatus.OK);
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
        log.info("Post requestDto : {}", requestDto.getAuthor());
        log.info("Post requestDto : {}", requestDto.getContent());
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
                                       @RequestBody PostDeployRequestDto postDeployRequestDto) throws Exception{
        log.info("subjectId : {}", postDeployRequestDto.getSubjectId());
        return new ResponseEntity<Long>(postService.deploy(postId, postDeployRequestDto.getKinds(), postDeployRequestDto.getSubjectId(),postDeployRequestDto.getBanner()),HttpStatus.OK);

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
    public ResponseEntity<List<FollowerPostResponseDto>> getFollowers(@PathVariable String email){
        return new ResponseEntity<List<FollowerPostResponseDto>>(postService.getFollowersGroup(email), HttpStatus.OK);
    }

    @PostMapping("/api/v1/posts/translation")
    public ResponseEntity<String> translation(@RequestBody String text){
        log.info("Translation .......");
        String inputLanguage = "ko";
        String outputLanguage = "en";
        return new ResponseEntity<String>(naverAPIService.translation(text, inputLanguage, outputLanguage), HttpStatus.OK);
    }

    @PutMapping("/api/v1/posts/views/{postId}/{userEmail}")
    public ResponseEntity<String> click(@PathVariable Long postId, @PathVariable String userEmail){
        if(postService.click(postId, userEmail)){
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}