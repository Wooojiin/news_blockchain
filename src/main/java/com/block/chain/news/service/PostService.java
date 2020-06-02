package com.block.chain.news.service;

import com.block.chain.news.domain.postList.PostList;
import com.block.chain.news.domain.postList.PostListRepository;
import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.post.PostRepository;
import com.block.chain.news.domain.subject.Subject;
import com.block.chain.news.domain.subject.SubjectRepository;
import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.SuggestionList;
import com.block.chain.news.web.dto.posts.PostListResponseDto;
import com.block.chain.news.web.dto.posts.PostResponseDto;
import com.block.chain.news.web.dto.posts.PostSaveRequestDto;
import com.block.chain.news.web.dto.posts.SubjectListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostListRepository postListRepository;
    private final SubjectRepository subjectRepository;

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


    @Transactional
    public Long save(PostSaveRequestDto requestDto) throws Exception{
        User user= userRepository.findByEmail(requestDto.getAuthor())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 존재 X"));
        Post post = requestDto.toEntity();
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

    @Transactional              //이게 유사한 기사 추천해주는 부분
    public List<Subject> suggestion(Long postId) {
        String target = postRepository.getOne(postId).getTopics();                             //찾고자 하는 기사의 형태소 가져와서
        String[] targetList = target.split(",");                                           // 배열로 만들고
        List<Subject> suggestions = new LinkedList<>();
        List<Subject> subjects = new LinkedList<>();
        for (String targetOne : targetList) {                                                    // 이 기사에서 target(형태소 10개) 하나씩 돌아가면서 뽑아오고
            List<Subject> find = subjectRepository.findAllByTitleContaining(targetOne);             // Subject들 중에서 title에 해당 형태소(뽑아온 아이) 가지고 있는 얘 검색
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
    //일단 String 2개 입력받아서 그거의 유사도 계산하기 0 >> 좋은거 높을수록 안좋은거. 이거 수정 Contain으로 확인해서 Contain 갯수 확인
    // 이거 일단 일단 보류
//    public int getSimilarity(String s1, String s2){
//       int longStrLen = s1.length() + 1;
//       int shortStrLen = s2.length() + 1;
//       int[] cost = new int[longStrLen];
//       int[] newcost = new int[longStrLen];
//       for (int i = 0; i < longStrLen; i++) { cost[i] = i; }
//       for (int j = 1; j < shortStrLen; j++) {
//           newcost[0] = j;
//           for (int i = 1; i < longStrLen; i++) {
//               int match = 0;
//               if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
//                   match = 1;
//               }
//               int replace = cost[i - 1] + match;
//               int insert = cost[i] + 1;
//               int delete = newcost[i - 1] + 1;
//               newcost[i] = Math.min(Math.min(insert, delete), replace);
//           }
//           int[] temp = cost; cost = newcost; newcost = temp;
//       }
//       return cost[longStrLen -1];
//    }
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
            List<Post> lists = new LinkedList<>();
            lists.add(post);
            Subject newOne = Subject.builder()
                    .posts(lists)
                    .title(post.getTopics())
                    .build();
            subjectRepository.save(newOne);
        }else{
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 Subject가 존재하지 않습니다"));
            subject.addPost(post);
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

    //SubjectListReponseDto 만들고
    //그 안에 SubjectResponseDto 하나씩 추가
    @Transactional
    public List<SubjectListResponseDto> getSubject(){
        List<Subject> subjects = subjectRepository.findAll();
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

}
