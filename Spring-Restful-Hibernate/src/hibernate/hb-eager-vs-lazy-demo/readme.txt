Create database following description:

Course (id, title, instructor)
Instructor(firstName, lastName, email) 
InstructorDetail(id, hobby, youtubeChannel)

1 course just have one instructor
1 instructor can teach many course.
1 intructor only have 1 instructorDetail

Application run on mysql database. If you want to change another database or any configure about user, password to connect database, go to src/hibetnate.cfg.xml to modify.

