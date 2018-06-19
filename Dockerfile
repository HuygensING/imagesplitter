FROM openjdk:8-jdk-stretch

RUN apt -y update && apt -y upgrade && apt -y install libopencv2.4-java

# Build ImageSplitter.java "by hand" -- configuring Maven to use a jar
# from /usr/share/java is just plain hard.
WORKDIR /build
COPY src/main/java/nl/knaw/huc/di/ImageSplitter.java .
RUN javac -cp /usr/share/java/opencv.jar ImageSplitter.java \
    && mkdir -p nl/knaw/huc/di \
    && mv ImageSplitter.class nl/knaw/huc/di

ENTRYPOINT ["java", "-cp", ".:/usr/share/java/opencv.jar", "nl.knaw.huc.di.ImageSplitter"]
