CODEBASE="file:///home/"$1"/test/Restaurant/dirKitchen/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerRestaurantKitchen 22152 localhost 22150
