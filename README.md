# About
This is a simple command line tool to enable creating keeping track of tasks that need to be done.

# But why
The major reason for this project is to have a simple project that allows some degree of experimentation. 
While the logic of an app is not particularly perplexing, it allows to gain, improve and solidify knowledge 
about:
* Java (8/9)
* SQL
* HSQLDB
* JDBC
* Liquibase
* Log4j2
* Maven
* Commons CLI
* Design patters

If something useful comes out of it, all the better for it.

# Usage
1. Clone the repo
2. Ensure you have [Maven](https://maven.apache.org/install.html) installed
3. Run the following commands:
    ~~~
    mvn clean install
    mvn exec:java -Dexec.args="arguments"
    ~~~

# Supported Commands
Currently supported commands are:
~~~
Adding tasks:
-add <task>     Adds a task.
-d <n>          Sets a deadline for a task (n days). 
                For convenience, "today" and "tomorrow"
                are also supported.
-show           Shows all curently saved tasks.
~~~
