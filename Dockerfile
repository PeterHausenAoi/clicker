FROM amazonlinux:latest

RUN yum update -y
RUN yum -y install systemd
RUN yum install java-11-amazon-corretto -y
RUN yum install git -y
RUN yum install -y which
RUN yum install -y tar
RUN yum install -y sudo

RUN javac --version
RUN java --version
RUN git --version
RUN tar --version

RUN mkdir app
WORKDIR app

RUN mkdir tmp
WORKDIR tmp

RUN curl -L https://github.com/PeterHausenAoi/clicker/archive/master.tar.gz  --output master.tar.gz
RUN tar -xf master.tar.gz

WORKDIR clicker-main

RUN chmod +x deploy.sh
RUN cp deploy.sh /app

RUN ls -la /app

WORKDIR /app

RUN rm -rf tmp

RUN ./deploy.sh

# v1
#!/bin/bash
#yum update -y
#yum install java-11-amazon-corretto -y
#yum install git -y
#yum install -y which
#mkdir app
#cd app
#curl -L https://github.com/PeterHausenAoi/clicker/archive/master.tar.gz  --output master.tar.gz
#yum install -y tar
#tar -xf master.tar.gz
#cd clicker-main
#./mvnw package
#sudo cp clicker.service /etc/systemd/system
#sudo systemctl start clicker.service
#sudo systemctl enable clicker.service
#sudo systemctl status clicker.service

