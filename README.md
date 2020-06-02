# Backend

> Spring Boot, JPA





##### User

- id - Long
- nickname - String
- email - String
- profile picture - String



##### Post

- postId - Long
- title - String
- author - String
- content - String
- image - String
- state - String
- topic - Topic
- subject_id



##### Subject

- subject_id - Long
- keyword?



###### Report

* reportId - Long
* post - Post
* total - Long(총 투표 한사람 인원 수)
* agree - Long(찬성한 사람 수)



##### ReportList

* reportListId - Long
* user - User
* reportId - Long
* decision - int( -1 => Report한사람, 0 => 거부, 1 => 찬성)