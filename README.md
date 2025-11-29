<br />
<div align="center">
<h3 align="center">Plazoleta Users Microservice</h3>

<p>Template: 
<a href="https://gitlab.com/bootcamp-pragma/powerup-arquetipo">https://gitlab.com/bootcamp-pragma/powerup-arquetipo</a>
</p>

</div>


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 11 [https://jdk.java.net/java-se-ri/11](https://jdk.java.net/java-se-ri/11)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* PostgreSQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

1. Clone the repo
2. Change directory
   ```sh
   cd power-up-arquetipo
   ```
3. Create a new database in MySQL called powerup
4. Update the database connection settings 
   ```yml
   # src/main/resources/application.yml   
   spring:
      datasource:
          url: jdbc:mysql://localhost/powerup
          username: root
          password: 1234
   ```

<!-- USAGE -->
## Usage

1. Right-click the class PowerUpApplication and choose Run
2. Open [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage


