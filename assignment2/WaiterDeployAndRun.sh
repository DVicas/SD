echo "Transfering data to the waiter node."
sshpass -f password ssh sd2007@l040101-ws06.ua.pt 'mkdir -p test/theRestaurant'
sshpass -f password ssh sd2007@l040101-ws06.ua.pt 'rm -rf test/theRestaurant/*'
sshpass -f password scp dirWaiter.zip sd2007@l040101-ws06.ua.pt:test/theRestaurant
echo "Decompressing data sent to the waiter node."
sshpass -f password ssh sd2007@l040101-ws06.ua.pt 'cd test/theRestaurant ; unzip -uq dirWaiter.zip'
echo "Executing program at the waiter node."
sshpass -f password ssh sd2007@l040101-ws06.ua.pt 'cd test/theRestaurant/dirWaiter ; java clientSide.main.ClientWaiterMain l040101-ws01.ua.pt 22001 l040101-ws02.ua.pt 22002 l040101-ws03.ua.pt 22003 l040101-ws08.ua.pt 22000'
