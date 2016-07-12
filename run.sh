echo "Compiling..."
javac -d out -sourcepath src src/com/test/advanced/App.java
echo "Done!"
clear
echo "Running..."
java -cp out com.test.advanced.App

# If using lib
# Compile
# javac -cp lib -d out -sourcepath src src/com/test/advanced/App.java
# Run
# java -cp out;lib com.test.advanced.App
