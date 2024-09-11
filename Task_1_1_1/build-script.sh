javac -d $1 src/main/java/ru/nsu/svirsky/*.java
(
    cp Manifest.txt $1;
    cd $1; 
    jar cfm "heapsort.jar" "Manifest.txt" ru/nsu/svirsky/*.class;
    rm Manifest.txt;
)
javadoc -d $1/javadoc -sourcepath src/main/java -subpackages ru.nsu.svirsky
echo "Build done!\n"