# javac -classpath /home/diogo/Documents/SD/genclass.jar */*.java */*/*.java
 xfce4-terminal --title="ServerGeneralRepos" --hold --execute java serverSide.main.ServerGeneralReposMain 22169 
 sleep 5
 xfce4-terminal --title="ServerKitchen" --hold --execute java serverSide.main.ServerKitchenMain 22162 127.0.0.1 22169 
 xfce4-terminal --title="ServerTable" --hold --execute java serverSide.main.ServerTableMain 22160 127.0.0.1 22169 
 xfce4-terminal --title="ServerBar" --hold --execute java serverSide.main.ServerBarMain 22161 127.0.0.1 22160 127.0.0.1 22169 
  
 #sleep 1
 xfce4-terminal --title="ClientWaiter" --hold --execute java clientSide.main.ClientWaiterMain 127.0.0.1 22160 127.0.0.1 22161 127.0.0.1 22162 127.0.0.1 22169 test
 xfce4-terminal --title="ClientChef" --hold --execute java clientSide.main.ClientChefMain 127.0.0.1 22161 127.0.0.1 22162 127.0.0.1 22169 
 #sleep 1 
 xfce4-terminal --title="ClientStudent" --hold --execute java clientSide.main.ClientStudentMain 127.0.0.1 22161 127.0.0.1 22160 127.0.0.1 22169