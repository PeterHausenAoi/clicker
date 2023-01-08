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

cd /app

echo "Copy service..."
sudo cp tmp/clicker-main/clicker.service /etc/systemd/system
rm -rf tmp

export AWS_DEFAULT_REGION=eu-central-1

export RDS_ENDPOINT=$(aws ssm get-parameter --name="/clicker/RDS_ENDPOINT" --query "Parameter.Value" --output text)
export RDS_PORT=$(aws ssm get-parameter --name="/clicker/RDS_PORT" --query "Parameter.Value" --output text)
export RDS_USER=$(aws ssm get-parameter --name="/clicker/RDS_USER" --query "Parameter.Value" --output text)
export RDS_PASSWORD=$(aws ssm get-parameter --name="/clicker/RDS_PASSWORD" --query "Parameter.Value" --output text)

echo "Start service..."
sudo systemctl start clicker.service
sudo systemctl enable clicker.service
sudo systemctl status clicker.service

echo "Deployed"
