# FramTest_EmployeeHierarchy

#Run EmployeeHierarchy service

#Precondition
1. Docker was installed

#Step to build 
1. docker build -s demo .
2. docker run -p 8080:8080 demo
   OR
1. docker build --tag=demo:latest .
2. docker run -p 8080:8080 demo:latest