call cd plugin
call mvn install -f "pom.xml"
call robocopy "target" "..\server\plugins" "opensourcery-beta.jar" /is

call cd ..
curl http://localhost:8080/OpenSourcery





