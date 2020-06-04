package com.block.chain.news.service;

import com.block.chain.news.domain.postList.PostList;
import com.block.chain.news.domain.postList.PostListRepository;
import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.post.PostRepository;
import com.block.chain.news.domain.subject.Subject;
import com.block.chain.news.domain.subject.SubjectRepository;
import com.block.chain.news.domain.subjectList.SubjectListRepository;

import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.SuggestionList;
import com.block.chain.news.web.dto.follow.FollowingPostResponseDto;
import com.block.chain.news.web.dto.posts.*;
import com.block.chain.news.web.dto.follow.FollowResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostListRepository postListRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectListRepository subjectListRepository;
    private final FollowService followService;

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc(){
        List<Post> postList =postRepository.findAll();
        List<PostListResponseDto> postResponseDto = new LinkedList<>();
        for (Post post : postList){
            PostListResponseDto postDto = new PostListResponseDto(post);
            postResponseDto.add(postDto);
        }
        return postResponseDto;
    }

    public PostResponseDto findById(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("잘못된 기사를 선택하셨습니다"));
        //List<Topic> topics = topicRepository.findByPostsDesc(post);     //=> 이거 스트링 값으로 바꿔야 한다
        List<Subject> suggestions = new LinkedList<>();
        if (post.getState().equals("SAVE")){
            suggestions = suggestion(postId);
        }
        return new PostResponseDto(post, suggestions);
    }

    @Transactional              //이게 유사한 기사 추천해주는 부분
    public List<Subject> suggestion(Long postId) {
        String target = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException( "잘못 보냄!"))
                .getTopics();                             //찾고자 하는 기사의 형태소 가져와서
        String[] targetList = target.split(",");                                           // 배열로 만들고
        List<Subject> suggestions = new LinkedList<>();
        List<Subject> subjects = new LinkedList<>();
        for (String targetOne : targetList) {                                                    // 이 기사에서 target(형태소 10개) 하나씩 돌아가면서 뽑아오고
            List<Subject> find = subjectRepository.findAllByTitleContaining(targetOne);             // Subject들 중에서 title에 해당 형태소(뽑아온 아이) 가지고 있는 얘 검색 => 이거 여기서 오류 뜬다
            for (Subject one : find) {                                                               //subject 가지고 있는 아이들 중에서
                if (!subjects.contains(one)) {                                                                  // 추가 안 되어 있으면
                    subjects.add(one);                                                                          //Subjects에 추가
                }
            }
        }
        List<SuggestionList> Similarities = new LinkedList<>();
        for (Subject subject: subjects) {
            int current = getSimilarity(target, subject.getTitle());            //유사도 계산해서
            SuggestionList newOne = new SuggestionList(subject, current);                                                                    // Array에 다 넣고(index, current값)
            Similarities.add(newOne);                                           // 정렬해서
        }                                                                       // current낮은 순으로 3개 뽑아서
        Collections.sort(Similarities);
        int limits = 0;
        if (Similarities.size() >= 3){
            limits = 3;
        }else{
            limits = Similarities.size();
        }
        for(int idx = 0 ; idx < limits;idx++){
            suggestions.add(Similarities.get(idx).getSubject());
        }
        return suggestions;
    }

    //쉽게 생각해서 s1의 각 값들이 s2에 몇개나 들어가 있나 count => 유사도
    public int getSimilarity(String s1, String s2){
        String [] target = s1.split(",");
        int result = 0;
        for (String s : target){
            if (s2.contains(s)){
                result += 1;
            }
        }
        return result;
    }

    @Transactional
    public Long save(PostSaveRequestDto requestDto, String words) throws Exception{
        User user= userRepository.findByEmail(requestDto.getAuthor())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 존재 X"));
        Post post = requestDto.toEntity(words);
        Long postId = postRepository.save(post).getPostId();
        PostList postList = PostList.builder()
                .user(user)
                .postId(postId)
                .build();
        postListRepository.save(postList);
//        String topics = post.getSelects();
//        Optional<Subject> subject = subjectRepository.findByTitle(topics);
//        //여기서 subject 있는지 없는지 어떻게 판단해야하지..?
//        if (subject.isPresent()){
//            subject.get().addPost(post);
//        }else{
//            List<Post> lists = new LinkedList<>();
//            lists.add(post);
//            Subject.builder()
//                    .posts(lists)
//                    .title(topics)
//                    .build();
//        }
        return postId;
    }
    @Transactional
    public List<KindsResponseDto> findAllByKinds(){
        String [] kind = {"정치","연애","IT","경제"};
        List<KindsResponseDto> resultSet = new LinkedList<>();
        for (int idx = 0; idx < kind.length; idx++){
            List<Post> lists = postRepository.findAllByKindsEquals(idx);
            KindsResponseDto kindsResponseDto = new KindsResponseDto(kind[idx],lists);
            resultSet.add(kindsResponseDto);
        }
        return resultSet;
    }
    @Transactional
    public Long deploy(Long postId, String [] selected, Long subjectId){
        Post post = postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("잘못된 기사를 선택 하셨습니다"));
        StringBuilder sb = new StringBuilder();
        for (String one : selected){
            sb.append(one);
            sb.append(',');
        }
        post.updateState("Started");
        post.updateSelect(sb.toString());
        if (subjectId == -1){
            List<Post> newLists = new LinkedList<>();
            newLists.add(post);
            Subject newOne = Subject.builder()
                    .title(post.getTopics())
                    .posts(newLists)
                    .build();
            subjectRepository.save(newOne);
            post.updateSubject(newOne);
        }else{
            Subject oldOne = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("잘못된 호출"));
            oldOne.addPost(post);
            post.updateSubject(oldOne);
        }
        return postId;
    }

    @Transactional
    public Long delete(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 신청입니다"));
        postRepository.delete(post);
        return postId;
    }


    @Transactional
    public List<SubjectListResponseDto> getSubject(){
        List<Subject> subjects = subjectRepository.findAll();
        List<Post> postLists = new LinkedList<>();
        List<SubjectListResponseDto> subjectListResponseDto = new LinkedList<>();
        if (subjects.isEmpty()){
            return subjectListResponseDto;
        }
        else {
            for (Subject subject : subjects) {
                SubjectListResponseDto listResponseDto= new SubjectListResponseDto(subject.getTitle(), subject.getPosts());
                if (listResponseDto.getPosts().size() != 0){
                    subjectListResponseDto.add(listResponseDto);
                }
            }
            return subjectListResponseDto;
        }
    }

    public List<PostListResponseDto> findByUserId(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다"));
        List<Post> posts = postRepository.findAllByAuthor(user.getEmail());
        List<PostListResponseDto> result = new LinkedList<>();
        for (Post post : posts){
            PostListResponseDto postListResponseDto = new PostListResponseDto(post);
            result.add(postListResponseDto);
        }
        return result;
    }

    public List<FollowingPostResponseDto> getFollowers(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니닫닫"));
        List<FollowingPostResponseDto> resultSet = new LinkedList<>();
        List<String> followResponseDto = new FollowResponseDto(user).getFollowing();
        for (String userEmail : followResponseDto){
            List<Post> posts = postRepository.findAllByAuthor(userEmail);
            FollowingPostResponseDto followingListResponseDto = new FollowingPostResponseDto(userEmail,posts);
            resultSet.add(followingListResponseDto);
        }
        return resultSet;
    }
}
