Run application on port server.port=8900 (you can configure any port at file src/main/resource/application.properties

Application provides 2 resources: courses and topics

Course (id, name, description)
Topic(id, name, description)
*** http://localhost:8900/courses  ***

GET all course => go to http://localhost:8900/courses 
POST (to insert 1 course) = > go to http://localhost:8900/courses
PUT (to update 1 course) => go to http://localhost:8900/courses/{id}
DELETE 1 course => go to http://localhost:8900/courses/{id}

The same for topics