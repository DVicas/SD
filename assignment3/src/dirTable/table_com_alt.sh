CODEBASE="file:///home/"$1"/test/Restaurant/dirTable/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerRestaurantTable 22151 localhost 22150
