rm -r bin/*
javac -sourcepath src/ -d bin src/tkv_project/*/*.java
echo -e "\n\nBUILD FINISHED!"
