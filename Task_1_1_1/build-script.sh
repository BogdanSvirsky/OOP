if 
    javac -d $1 src/main/java/ru/nsu/svirsky/*.java && 
    javac -d $1 -cp $1:jars/junit-platform-console-standalone-1.11.0.jar src/test/java/ru/nsu/svirsky/*.java
then
    if (
        cp Manifest.txt $1;
        cd $1; 
        jar cfm "heapsort.jar" "Manifest.txt" ru/nsu/svirsky/*.class;
        rm Manifest.txt;
    )
    then
        if 
            java -javaagent:jars/jacocoagent.jar=destfile=$1/jacoco.exec -jar jars/junit-platform-console-standalone-1.11.0.jar execute --class-path $1 --scan-class-path
        then
            if
                javadoc -d $1/javadoc -sourcepath src/main/java -subpackages ru.nsu.svirsky
            then
                if 
                    java -jar jars/jacococli.jar report $1/jacoco.exec --html $1/jacoco-report --classfiles $1 --sourcefiles src/main/java:src/test/java
                then
                    echo "Build DONE!\n"
                    exit 0
                else
                    echo "Build FAILED: jacococo report ERROR\n"
                    exit 5
                fi
            else
                echo "Build FAILED: javadoc ERROR"
                exit 4
            fi
        else
            echo "Build FAILED: JUnit ERROR"
            exit 3
        fi
    else
        echo "Build FAILED: jar ERROR"
        exit 2
    fi
else
    echo "Build FAILED: javac ERROR"
    exit 1
fi