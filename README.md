# About
This is a simple command line tool to enable creating and keeping track of tasks that need to be done.

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
{no arguments}              Shows all tasks.
-help   -h                  Prints help message.
-add    -a  <task>          Adds a task.
        -d <n>              Sets a deadline for a task (n days). 
                            For convenience, "today" and "tomorrow"
                            are also supported.
-filter -f  <filter>        Filters saved tasks. Tasks can be filtered 
                            on deadline. Filter can be any of the 
                            following: 
                                * none - unset deadline
                                * integer - days from today
                                * today - same as 0
                                * tomorrow - same as 1
                            If none of these are supplied, filtering
                            is done on the categories instead.
-remove -rm <id>            Deletes a task with a specified id.
-clear                      Deletes all tasks.
~~~
