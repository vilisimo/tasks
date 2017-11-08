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

## Execution From Anywhere
Alternatively, it is possible to set up the program so that it can be called
by simply entering `tasks` (or any other preferred word).

To do so:
1. Clone the repo
2. Ensure you have [Maven](https://maven.apache.org/install.html) installed
3. Run the following command:
    ~~~
    mvn clean package
    ~~~
   This will package the program into one massive jar (~10mb), which can be 
   launched like this:
    ~~~
    java -jar target/tasks-1.0-SNAPSHOT-jar-with-dependencies.jar {arguments}
    ~~~
4. Copy this jar to a location that you feel is appropriate for it. For example,
   `/opt/`, `/usr/local/`, or simply `~/`
5. Create a shell script with a name `tasks` (or any other preferred name).
   How the script should/might look can be seen in 
   [`tasks`](https://github.com/vilisimo/tasks/blob/master/tasks) file.
6. Give execution permissions to the file you created:
    ~~~
    chmod u+x filename
    ~~~
7. Move the file to `/usr/local/bin`:
    ~~~
    sudo mv filename /usr/local/bin
    ~~~ 
8. Reload the terminal:
    ~~~
    source ~/.bashrc
    ~~~
9. Try it out:
    ~~~
    tasks -a Example task -d today -cat test
    ~~~
    If all goes well, you should see the following output:
    > TASKS :: Successfully saved a task with id=1
    


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
