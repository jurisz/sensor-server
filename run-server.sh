
unzip -o build/libs/sensor-server-1.0-SNAPSHOT.jar -d build/libs/jar
cp support/logback.xml build/libs/jar/BOOT-INF/classes/logback.xml

cd build/libs/jar

java -Dfile.encoding=UTF-8 org.springframework.boot.loader.JarLauncher

cd ../../../