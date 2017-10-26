echo "Please enter CLI arguments: " 
read arguments
mvn exec:java -Dexec.args="$arguments"
