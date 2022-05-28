javac /*/*.java 
 xfce4-terminal --title="ServerGeneralRepos" --execute java serverSide.main.ServerGeneralRepos 22169 
 xfce4-terminal --title="ServerBar" --execute java serverSide.main.ServerBar 22160 127.0.0.1 22169 
 xfce4-terminal --title="ServerKitchen" --execute java serverSide.main.ServerKitchen 22161 127.0.0.1 22169 
 xfce4-terminal --title="ServerTable" --execute java serverSide.main.ServerTable 22162 127.0.0.1 22169 
  
 #sleep 1 
 xfce4-terminal --title="ClientWaiter" --execute java clientSide.main.ClientWaiter 127.0.0.1 22161 127.0.0.1 22160 127.0.0.1 22162 127.0.0.1 22169 
 xfce4-terminal --title="ClientChef" --execute java clientSide.main.ClientChef 127.0.0.1 22161 127.0.0.1 22160 127.0.0.1 22169 
 #sleep 1 
 xfce4-terminal --title="ClientStudent" --execute java clientSide.main.ClientStudent 127.0.0.1 22160 127.0.0.1 22162 127.0.0.1 22169