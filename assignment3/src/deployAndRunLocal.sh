xterm  -T "RMI registry" -hold -e " cd ~/test/Restaurant/dirRMIRegistry; bash set_rmiregistry_alt.sh 22150" &
sleep 5
xterm  -T "Registry" -hold -e "cd ~/test/Restaurant/dirRegistry; bash registry_com_alt.sh rsa" &
sleep 3
xterm  -T "General Repository" -hold -e "cd ~/test/Restaurant/dirGeneralRepos; bash repos_com_alt.sh rsa" &
sleep 2
xterm  -T "Kitchen" -hold -e "cd ~/test/Restaurant/dirKitchen; bash kitchen_com_alt.sh rsa" &
xterm  -T "Table" -hold -e "cd ~/test/Restaurant/dirTable; bash table_com_alt.sh rsa" &
sleep 2
xterm  -T "Bar" -hold -e "cd ~/test/Restaurant/dirBar; bash bar_com_alt.sh rsa" &
sleep 2
xterm  -T "Chef" -hold -e "cd ~/test/Restaurant/dirChef; bash chef_com_alt.sh rsa" &
xterm  -T "Waiter" -hold -e "cd ~/test/Restaurant/dirWaiter; bash waiter_com_alt.sh rsa" &
xterm  -T "Student" -hold -e "cd ~/test/Restaurant/dirStudent; bash student_com_alt.sh rsa" &
