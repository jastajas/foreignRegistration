

# Foreign Registration

#### General description:
Application is support for business process. It is dedicated for particular type of activity and pharmaceutical industry. Main app activities are: 
1.	assessing suitability of product  dossier for current regulations in destined country
2.	activities planning, which are required for product delivery by preparing dossier for registration in a particular location 
3.	managing tasks within this activities
4.	calculating project profitability
5.	supervising process progress
6.	managing MAs (Marketing Authorizations)

Currently, basic level of two modules is provided: assessments and orders. Profitability calculations base line is on preparing stage.
It is web app developed with different technology such as Java, JavaScript, MySQL, Thymeleaf, HTML, CSS, Spring, Hibernate.


#### Run app:   
App basic configuration use MySQL database engine and it is necessary to provide mysql engine with database schema named: foreign_registration. 

Alternatively, database engine could be provided by H2. In order to adjust H2 database engine it is necessary to replace current configuration of application.properties file by below configuration:

~~~
spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.datasource.url=jdbc:h2:file:~/test
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.type=trace
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.initialization-mode=always
~~~

Additionally it is required to provide server for deployment this app.

Because of authentication, below are data required for this action:  
__LOGIN:__ *j.kowalski@example.com*  
__PASSWORD:__ *mojkod12*
