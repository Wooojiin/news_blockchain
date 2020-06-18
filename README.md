# News Live

> REST API Server





## 📝 Feature

- JDK 1.8
- [Gradle](https://gradle.org/) 4.10.2
- [Spring Boot](https://spring.io/projects/spring-boot) 2.2.1
- [Swagger](https://swagger.io/) 2
- [Hyperledger Fabric Java SDK](https://github.com/hyperledger/fabric) 1.4





## ⚙️Getting Started

##### Installation

1. Clone the repository

   ```
   git clone https://github.com/Wooojiin/news_blockchain.git
   ```

2. Install 

   ```ubu
   sudo apt-get update
   sudo apt-get install openjdk-8-jdk
   ```



##### Usage

1. Create `application-key.properties` File at `news_blockchain/src/main/resources/`

   ```properties
   naver.clientId = "Your naver API Information"
   naver.clientSecret = ""Your naver API Information"
   
   
   fabric.caServer.url = "Your Fabric Network Information"
   fabric.caServer.adminName = "Your Fabric Network Information"
   
   fabric.org.name = "Your Fabric Network Information"
   fabric.org.mspName = "Your Fabric Network Information"
   fabric.org.adminName = "Your Fabric Network Information"
   fabric.org.userName = "Your Fabric Network Information"
   fabric.org.userSecret = "Your Fabric Network Information"
   
   fabric.peer.name = "Your Fabric Network Information"
   fabric.peer.url = "Your Fabric Network Information"
   
   fabric.orderer.name = "Your Fabric Network Information"
   fabric.orderer.url = "Your Fabric Network Information"
   
   fabric.channel.name = "Your Fabric Network Information"
   ```

2. Modify `WebConfig.java` and `AdvertisementService.java`

   1. Run only Localhost

      - `WebConfig.java`

        ```java
        @Configuration
        public class WebConfig implements WebMvcConfigurer {
        
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        }
        ```

      - `AdvertisementService.java`

        ```java
        public static final String SAVE_FOLDER = "Your Project save Folder" +  "/news_blockchain/src/main/resources/static/image/";
        public static final String IMAGE_URL = localhost:8080/image/";
        ```

   2. Others

      - `WebConfig.java`

        ```java
        @Configuration
        public class WebConfig implements WebMvcConfigurer {
        
            private String urlPath;
        
            public WebConfig() {
                this.urlPath = "Your Save Folder";
            }
        
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/image/**").addResourceLocations(urlPath).setCachePeriod(20);
            }
        
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        }
        ```

      - `AdvertisementService.java`

        ```java
        public static final String SAVE_FOLDER = "Your save Folder";
        public static final String IMAGE_URL = "Your url" + "/image/";
        ```

3. Build

   ```
   cd news_blockchain
   sudo chmod +x gradlew
   ./gradlew build
   ```

4. Run

   ```
   cd build/lib
   java -jar news-1.0-SNAPSHOT.jar
   ```

5. Swagger

   - Run on Localhost

     `http://localhost:8080/swagger-ui.html#`

   - Others

     `Your Url + "/swagger-ui.html#"`

