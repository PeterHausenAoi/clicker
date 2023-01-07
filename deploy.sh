#!/bin/bash

echo "I am Deploy Script"

echo "Cleaning up prev version..."
rm -rf bin
mkdir bin
echo "Cleaned up prev version."

mkdir tmp
cd tmp

curl -L https://github.com/PeterHausenAoi/clicker/archive/master.tar.gz  --output master.tar.gz
tar -xf master.tar.gz
cd clicker-main
./mvnw package

cp target/Clicker-0.0.1-SNAPSHOT.jar ../../bin/clicker.jar

#sudo cp clicker.service /etc/systemd/system
#sudo systemctl start clicker.service
#sudo systemctl enable clicker.service
#sudo systemctl status clicker.service

