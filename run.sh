rm -r bin/*
javac -sourcepath src/ -d bin src/tkv_project/*/*.java
java -cp bin/ tkv_project.client.Launcher
echo -e "\n\n\n"
