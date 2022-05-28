echo "Transfering data to the chef node."
sshpass -f password ssh sd2007@l040101-ws05.ua.pt 'mkdir -p test/theRestaurant'
sshpass -f password ssh sd2007@l040101-ws05.ua.pt 'rm -rf test/theRestaurant/*'
sshpass -f password scp dirChef.zip sd2007@l040101-ws05.ua.pt:test/theRestaurant
echo "Decompressing data sent to the chef node."
sshpass -f password ssh sd2007@l040101-ws05.ua.pt 'cd test/theRestaurant ; unzip -uq dirChef.zip'
echo "Executing program at the chef node."
sshpass -f password ssh sd2007@l040101-ws05.ua.pt 'cd test/theRestaurant/dirChef ; java clientSide.main.ClientChefMain l040101-ws01.ua.pt 22001 l040101-ws03.ua.pt 22003 l040101-ws08.ua.pt 22000'
