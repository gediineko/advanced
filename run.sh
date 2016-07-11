echo "Compiling..."
javac -d out -sourcepath src src/com/test/advanced/App.java
echo "Done!"
clear
echo "Running..."
java -cp out com.test.advanced.App
