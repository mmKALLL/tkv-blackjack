rm -r bin/*
javac -sourcepath src/ -d bin src/tkv_project/*/*.java
echo -e "\n\n\n"
java -cp bin/ tkv_project.server.ServerLauncher
