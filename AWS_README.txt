
Setup Code Commit
    - https://docs.aws.amazon.com/codecommit/latest/userguide/setting-up-gc.html
    - Grant IAM User full access to CodeCommit (aka, not the 'master/root' account)
    - Generate and download HTTPS Credentials - done while viewing IAM user in IAM
    - Get Repository HTTPS Address - done when signing in as IAM user and going to the repo in CodeCommit
    - Add Repo and Credentials to IDE via git clone (even if clone is not successful

Setup Code Build
    - https://docs.aws.amazon.com/codebuild/latest/userguide/getting-started.html
    - https://docs.aws.amazon.com/codebuild/latest/userguide/getting-started-create-build-spec-console.html

Exposing Multiple Ports on Spring Boot
    https://tech.asimio.net/2016/12/15/Configuring-Tomcat-to-Listen-on-Multiple-ports-using-Spring-Boot.html
    https://petervarga.org/2019/03/13/define-multiple-ports-for-spring-boot-application/
    https://docs.spring.io/spring-boot/docs/1.2.3.RELEASE/reference/html/howto-embedded-servlet-containers.html
    https://tomcat.apache.org/tomcat-8.5-doc/api/org/apache/catalina/connector/Connector.html
