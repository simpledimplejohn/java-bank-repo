
JAVA Application has JDBC but needs- special driver specific software - now connects to the database


How to build project with tests

1. do this first to delete the created jar file
  `mvm clean install`
2. then do this
  `mvm clean package`
   -generates jar file
   -installs dependencies onto your machine
   -prints test to the console
3. refresh folders
	- target folder has .jar
	- site/jacoco/index.html
		open with web-browser

Connecting to the database
- com.revature.util/CoonectionUtil.java has the link to the repo
- application.properties has the url to connect to postgres
- 