CODEBASE="http://localhost/"$1"/classes/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerRestaurantBar 22264 localhost 22260 localhost 22261 localhost 22262
