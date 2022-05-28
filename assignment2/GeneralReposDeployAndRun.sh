echo "Transfering data to the general repository node."
sshpass -f password ssh sd2007@l040101-ws08.ua.pt 'mkdir -p test/theRestaurant'
sshpass -f password ssh sd2007@l040101-ws08.ua.pt 'rm -rf test/theRestaurant/*'
sshpass -f password scp dirGeneralRepos.zip sd2007@l040101-ws08.ua.pt:test/theRestaurant
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd2007@l040101-ws08.ua.pt 'cd test/theRestaurant ; unzip -uq dirGeneralRepos.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh sd2007@l040101-ws08.ua.pt 'cd test/theRestaurant/dirGeneralRepos ; java serverSide.main.ServerGeneralReposMain 22000'
echo "Server shutdown."
sshpass -f password ssh sd2007@l040101-ws08.ua.pt 'cd test/theRestaurant/dirGeneralRepos ; less stat'
