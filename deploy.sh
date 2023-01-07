#!/bin/bash

echo "I am Deploy Script"

echo "Cleaning up prev version..."
rm -rf bin
mkdir bin
echo "Cleaned up prev version."

mkdir tmp
cd tmp

echo "Checking out..."
curl -L https://github.com/PeterHausenAoi/clicker/archive/master.tar.gz  --output master.tar.gz
echo "Checked out"

echo "Packaging..."
tar -xf master.tar.gz
cd clicker-main
./mvnw package
echo "Packaged"

echo "Deploying..."
echo "Copy jar..."
sudo cp target/Clicker-0.0.1-SNAPSHOT.jar ../../bin/clicker.jar

echo "Copy service..."
sudo cp tmp/clicker-main/clicker.service /etc/systemd/system
rm -rf tmp

echo "Start service..."
sudo systemctl start clicker.service
sudo systemctl enable clicker.service
sudo systemctl status clicker.service

echo "Deployed"
