xterm  -T "RMI registry" -hold -e "bash RMIRegistryDeployAndRun.sh" &
sleep 5
xterm  -T "Registry" -hold -e "bash RegistryDeployAndRun.sh" &
sleep 3
xterm  -T "General Repository" -hold -e "bash GeneralReposDeployAndRun.sh" &
sleep 2
xterm  -T "Kitchen" -hold -e "bash KitchenDeployAndRun.sh" &
xterm  -T "Table" -hold -e "bash TableDeployAndRun.sh" &
sleep 2
xterm  -T "Bar" -hold -e "bash BarDeployAndRun.sh" &
sleep 2
xterm  -T "Chef" -hold -e "bash ChefDeployAndRun.sh" &
xterm  -T "Waiter" -hold -e "bash WaiterDeployAndRun.sh" &
xterm  -T "Student" -hold -e "bash StudentDeployAndRun.sh" &
