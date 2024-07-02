#!/bin/bash

# Navigate to the script directory
cd "$(dirname "$0")"

# Navigate to the project directory
cd ..

# Run Maven
mvn clean package

# Path to the JAR file
JAR_FILE="target/pme-1.0-SNAPSHOT.jar"

# Function to run a main class from the JAR file
run_main_class() {
  local main_class=$1
  java -cp "$JAR_FILE" "$main_class"
}

# Run the main classes
run_main_class "com.test.nonblocking.NonBlockingServerMain" &
sleep 1  # Give the server some time to start
run_main_class "com.test.nonblocking.NonBlockingInitiatorPlayerMain" &
run_main_class "com.test.nonblocking.NonBlockingOtherPlayerMain"
